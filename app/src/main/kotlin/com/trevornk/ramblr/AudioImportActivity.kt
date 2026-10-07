package com.trevornk.ramblr

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import java.io.File
import java.util.concurrent.CancellationException
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.concurrent.thread

/** Explicit entry point for files selected in Ramblr or shared by another application. */
class AudioImportActivity : BaseSettingsActivity() {
    private val picker = registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
        if (uri == null) {
            if (selectedUri == null) finish()
        } else select(uri)
    }

    private var selectedUri: Uri? = null
    private var sourceName = ""
    private var processing = false
    private val cancelled = AtomicBoolean(false)
    private lateinit var fileLabel: TextView
    private lateinit var status: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var transcript: TextView
    private lateinit var chooseButton: Button
    private lateinit var cancelButton: Button
    private lateinit var historyButton: Button
    private lateinit var copyButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val root = vertical(0, 0)
        root.addView(TextView(this).apply {
            text = getString(R.string.import_audio_title)
            textSize = 32f
            setPadding(dp(24), dp(64), dp(24), dp(16))
        })
        root.addView(TextView(this).apply {
            text = getString(R.string.import_audio_description)
            setPadding(dp(24), 0, dp(24), dp(20))
        })
        fileLabel = TextView(this).apply {
            setPadding(dp(24), 0, dp(24), dp(16))
            tag = "user_content"
        }
        root.addView(fileLabel)
        status = TextView(this).apply { setPadding(dp(24), 0, dp(24), dp(12)) }
        root.addView(status)
        progressBar = ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal).apply {
            max = 100
            visibility = View.GONE
        }
        root.addView(progressBar, LinearLayout.LayoutParams(-1, dp(8)).apply {
            leftMargin = dp(24)
            rightMargin = dp(24)
        })
        chooseButton = Button(this).apply {
            text = getString(R.string.import_audio_choose)
            setOnClickListener { picker.launch(arrayOf("audio/*", "application/ogg")) }
        }
        root.addView(chooseButton)
        cancelButton = Button(this).apply {
            text = getString(R.string.import_audio_cancel)
            visibility = View.GONE
            setOnClickListener {
                cancelled.set(true)
                status.text = getString(R.string.import_audio_cancelling)
            }
        }
        root.addView(cancelButton)
        historyButton = Button(this).apply {
            text = getString(R.string.import_audio_history)
            visibility = View.GONE
            setOnClickListener {
                startActivity(Intent(this@AudioImportActivity, DataLogsActivity::class.java)
                    .putExtra(DataLogsActivity.EXTRA_SHOW_HISTORY, true))
            }
        }
        root.addView(historyButton)
        copyButton = Button(this).apply {
            text = getString(R.string.import_audio_copy)
            visibility = View.GONE
            setOnClickListener { ClipboardUtil.copy(this@AudioImportActivity, transcript.text.toString()) }
        }
        root.addView(copyButton)
        transcript = TextView(this).apply {
            setPadding(dp(24), dp(16), dp(24), dp(24))
            setTextIsSelectable(true)
            tag = "user_content"
        }
        root.addView(transcript)
        setContentView(ScrollView(this).apply {
            setBackgroundColor(attrColor(android.R.attr.colorBackground))
            addView(root)
        })

        val shared = when (intent.action) {
            Intent.ACTION_SEND -> intent.getParcelableExtra<Uri>(Intent.EXTRA_STREAM)
                ?: intent.clipData?.getItemAt(0)?.uri
            Intent.ACTION_VIEW -> intent.data
            else -> null
        }
        if (shared != null) select(shared)
        else picker.launch(arrayOf("audio/*", "application/ogg"))
    }

    private fun select(uri: Uri) {
        if (processing) return
        selectedUri = uri
        sourceName = runCatching {
            contentResolver.query(uri, arrayOf(OpenableColumns.DISPLAY_NAME), null, null, null)?.use { cursor ->
                if (cursor.moveToFirst()) cursor.getString(0) else null
            }
        }.getOrNull()?.take(120) ?: uri.lastPathSegment?.substringAfterLast('/')?.take(120) ?: "audio"
        fileLabel.text = sourceName
        transcript.text = ""
        historyButton.visibility = View.GONE
        copyButton.visibility = View.GONE
        beginWithHistoryCheck()
    }

    private fun beginWithHistoryCheck() {
        if (getSharedPreferences("ramblr", MODE_PRIVATE).getBoolean("dictation_history_enabled", true)) {
            startImport()
        } else {
            status.text = getString(R.string.import_audio_history_off)
            RussianAlertDialogBuilder(this)
                .setTitle(R.string.import_audio_enable_history_title)
                .setMessage(R.string.import_audio_enable_history_message)
                .setPositiveButton(R.string.import_audio_enable_history) { _, _ ->
                    getSharedPreferences("ramblr", MODE_PRIVATE).edit()
                        .putBoolean("dictation_history_enabled", true).apply()
                    startImport()
                }
                .setNegativeButton(android.R.string.cancel, null)
                .show()
        }
    }

    private fun startImport() {
        val uri = selectedUri ?: return
        if (processing) return
        processing = true
        cancelled.set(false)
        chooseButton.isEnabled = false
        cancelButton.visibility = View.VISIBLE
        progressBar.visibility = View.VISIBLE
        progressBar.progress = 0
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        status.text = getString(R.string.import_audio_preparing)
        thread(name = "ramblr-audio-import") {
            var pcm: File? = null
            try {
                pcm = AudioFileDecoder.decode(this, uri, cancelled::get) { value ->
                    showProgress(R.string.import_audio_decoding, value)
                }
                if (cancelled.get()) throw CancellationException()
                // The current file can use bounded fixed chunks while VAD downloads; later
                // imports get speech-aligned segments automatically.
                if (ModelDownloader.vadModelFile(this, SILERO_VAD_MODEL) == null) {
                    ModelDownloadWorker.enqueue(this, SILERO_VAD_MODEL)
                }
                val service = WhisperAccessibilityService.instance
                val result = if (service != null) {
                    service.runtime.transcribeImportedAudio(pcm, cancelled::get) { value ->
                        showProgress(R.string.import_audio_transcribing, value)
                    }
                } else {
                    val prefs = getSharedPreferences("ramblr", MODE_PRIVATE)
                    val selected = prefs.getString("model_name", "").orEmpty()
                        .ifBlank { LocalTranscriber.availableModels(this).firstOrNull().orEmpty() }
                    val transcriber = LocalTranscriber.create(this, selected)
                        ?: error("No local transcription model is installed")
                    val vad = ModelDownloader.vadModelFile(this, SILERO_VAD_MODEL)
                        ?.let { SherpaVadHandle.create(it) }
                    try {
                        ImportedAudioTranscription.transcribe(transcriber, pcm, vad, cancelled::get) { value ->
                            showProgress(R.string.import_audio_transcribing, value)
                        }
                    } finally {
                        vad?.close()
                        transcriber.release()
                    }
                }
                if (cancelled.get()) throw CancellationException()
                require(result.isNotBlank()) { "No speech detected" }
                DictationHistoryStore.forContext(this).add(
                    DictationHistoryEntry(System.currentTimeMillis(), result, null, sourceName = sourceName)
                )
                runOnUiThread {
                    transcript.text = result
                    status.text = getString(R.string.import_audio_saved)
                    historyButton.visibility = View.VISIBLE
                    copyButton.visibility = View.VISIBLE
                    endProcessing()
                }
            } catch (_: CancellationException) {
                runOnUiThread {
                    status.text = getString(R.string.import_audio_cancelled)
                    endProcessing()
                }
            } catch (error: Exception) {
                runOnUiThread {
                    status.text = getString(R.string.import_audio_failed, readableError(error))
                    endProcessing()
                }
            } finally {
                pcm?.delete()
            }
        }
    }

    private fun showProgress(label: Int, value: Int) {
        runOnUiThread {
            if (!cancelled.get() && !isFinishing && !isDestroyed) {
                status.text = getString(label, value)
                progressBar.progress = value
            }
        }
    }

    private fun endProcessing() {
        processing = false
        window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        chooseButton.isEnabled = true
        cancelButton.visibility = View.GONE
        progressBar.visibility = View.GONE
    }

    private fun readableError(error: Exception): String {
        val message = error.message.orEmpty()
        if (resources.configuration.locales[0].language != "ru") return message.ifBlank { getString(R.string.import_audio_error_generic) }
        val resource = when {
            message.contains("512 MB") -> R.string.import_audio_error_size
            message.contains("2 hours") -> R.string.import_audio_error_duration
            message.contains("model", ignoreCase = true) -> R.string.import_audio_error_model
            message.contains("dictation", ignoreCase = true) -> R.string.import_audio_error_busy
            message.contains("No speech") || message.contains("No usable audio") -> R.string.import_audio_error_speech
            else -> R.string.import_audio_error_generic
        }
        return getString(resource)
    }

    override fun onDestroy() {
        cancelled.set(true)
        super.onDestroy()
    }
}
