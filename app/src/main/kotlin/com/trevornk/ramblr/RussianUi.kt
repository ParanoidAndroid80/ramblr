package com.trevornk.ramblr

import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView

/** Russian labels for the settings UI that predates Android string resources. */
object RussianUi {
    private val labels = mapOf(
        "Checking..." to "Проверка…",
        "Settings" to "Настройки",
        "Status" to "Состояние",
        "Setup" to "Первоначальная настройка",
        "Transcription" to "Распознавание речи",
        "Cleanup" to "Корректировка текста",
        "Live Preview" to "Предпросмотр в реальном времени",
        "Cloud" to "Облачные сервисы",
        "Personal vocabulary" to "Личный словарь",
        "Invocation" to "Запуск диктовки",
        "Voice keyboard" to "Голосовая клавиатура",
        "History" to "История",
        "Advanced" to "Дополнительно",
        "Behavior" to "Поведение",
        "Updates" to "Обновления",
        "Check for and manage app updates" to "Проверить обновления приложения",
        "Data & Logs" to "Данные и журналы",
        "Overlay Appearance" to "Вид плавающей кнопки",
        "Audio permission" to "Доступ к микрофону",
        "Accessibility service" to "Служба специальных возможностей",
        "Use cloud transcription" to "Использовать облачное распознавание",
        "Uses the Cloud provider chain" to "Использует настроенные облачные сервисы",
        "Local models" to "Локальные модели",
        "Local cleanup models" to "Локальные модели корректировки",
        "Fallback: cloud providers" to "Резерв: облачные сервисы",
        "Cloud provider chain" to "Цепочка облачных сервисов",
        "Language" to "Язык",
        "Dictation language" to "Язык диктовки",
        "Effective routing" to "Текущий маршрут обработки",
        "Improve my dictation with AI" to "Улучшать текст с помощью ИИ",
        "Style" to "Стиль",
        "How cleanup runs" to "Способ корректировки",
        "Local" to "Локально",
        "Private, runs on-device, no API key needed" to "Работает на устройстве, ключ API не нужен",
        "Configure providers and keys under Cloud" to "Настройте сервисы и ключи в разделе «Облако»",
        "Mode" to "Режим",
        "Floating icon (default)" to "Плавающая кнопка (по умолчанию)",
        "System controls" to "Системные элементы управления",
        "Ramblr keyboard" to "Клавиатура Ramblr",
        "How to start dictation" to "Как начать диктовку",
        "Floating ring" to "Плавающий значок",
        "System accessibility button / gesture" to "Системная кнопка или жест",
        "Volume-keys hold" to "Удержание кнопок громкости",
        "Turn Ramblr off" to "Выключить Ramblr",
        "Direct shortcut control" to "Прямое управление ярлыком",
        "Icon is currently hidden" to "Значок сейчас скрыт",
        "Tap to show it again" to "Нажмите, чтобы показать его",
        "Snippets" to "Шаблоны текста",
        "Vocabulary" to "Словарь",
        "Advanced tuning" to "Дополнительные параметры",
        "Local transcription threads" to "Потоки локального распознавания",
        "Canary language" to "Язык модели Canary",
        "Suggested terms" to "Предлагаемые слова",
        "Icon size" to "Размер значка",
        "Border color" to "Цвет рамки",
        "Fill color (idle only)" to "Цвет заливки (в покое)",
        "Microphone icon color" to "Цвет микрофона",
        "Custom icon image" to "Свой значок",
        "Remove custom icon" to "Удалить свой значок",
        "Go back to the default mic icon" to "Вернуть обычный значок микрофона",
        "Dictation mode" to "Режим диктовки",
        "Fallback behavior" to "Резервные варианты",
        "Add provider" to "Добавить сервис",
        "Advanced: per-feature override" to "Дополнительно: настройка по функциям",
        "Experimental" to "Экспериментальные функции",
        "Enabled" to "Включено",
        "Model" to "Модель",
        "Transcription model" to "Модель распознавания",
        "Advanced: enter a custom model id" to "Дополнительно: указать ID модели",
        "Advanced: enter a custom transcription model id" to "Дополнительно: указать ID модели распознавания",
        "Advanced: base URL override" to "Дополнительно: другой адрес сервиса",
        "Use for" to "Использовать для",
        "Credential" to "Ключ доступа",
        "Dictation history" to "История диктовок",
        "Recover past transcripts, tap to copy" to "Нажмите, чтобы скопировать прежний текст",
        "Backup & restore" to "Резервное копирование и восстановление",
        "Diagnostic logs" to "Журналы диагностики",
        "No dictations yet" to "Диктовок пока нет",
        "App exclusions" to "Исключения для приложений",
        "Add" to "Добавить",
        "No apps excluded yet" to "Исключений пока нет",
        "+ New snippet" to "+ Новый шаблон",
        "Add a trigger phrase and its expansion" to "Добавьте фразу и текст для подстановки",
        "No snippets configured yet." to "Шаблонов пока нет.",
        "Manage styles" to "Управление стилями",
        "+ New style" to "+ Новый стиль",
        "Write your own cleanup prompt" to "Напишите свою инструкцию для корректировки",
        "Dismiss" to "Закрыть",
        "Installing..." to "Установка…",
        "Extracting..." to "Распаковка…",
        "Off" to "Выключено",
        "Cloud · set up a provider" to "Облако · настройте сервис",
        "Local · no model installed yet" to "Локально · модель ещё не установлена",
        "Permission and Ramblr Voice enabled" to "Доступ к микрофону и голосовая клавиатура включены",
        "Audio permission and Ramblr Voice needed" to "Нужны доступ к микрофону и голосовая клавиатура",
        "Audio permission needed" to "Нужен доступ к микрофону",
        "Ramblr Voice needs enabling" to "Нужно включить голосовую клавиатуру",
        "Required — choose how speech becomes text. Local (default): fastest, fully private, works with no internet. Cloud: uses your Cloud provider chain." to
            "Выберите способ распознавания речи. Локальный режим работает без интернета и сохраняет запись на устройстве. Облачный режим использует настроенные сервисы.",
        "Manage styles, quick-menu selection, and custom prompts" to "Управление стилями, быстрым меню и своими инструкциями",
        "One ordered list of cloud providers, shared by transcription and cleanup. Each feature automatically uses the first entry in the list that supports it. On-device always works underneath, with no setup needed." to
            "Общий упорядоченный список облачных сервисов для распознавания и корректировки. Для каждой функции выбирается первый подходящий сервис. Локальная обработка доступна без настройки.",
        "Pick a preset, or fine-tune with the toggles below. Each mode controls both how your speech is transcribed and how the text gets cleaned up." to
            "Выберите готовый режим или настройте переключатели ниже. Режим управляет распознаванием речи и корректировкой текста.",
        "These control what happens automatically when your current mode's provider fails." to
            "Здесь задаётся поведение приложения при сбое выбранного сервиса.",
        "Fall back to on-device if cloud fails" to "Использовать устройство при сбое облака",
        "Fall back to cloud if on-device fails" to "Использовать облако при локальном сбое",
        "No cloud providers configured yet. On-device transcription and cleanup are used instead." to
            "Облачные сервисы ещё не настроены. Используется локальное распознавание и корректировка.",
        "Usually set together by the mode preset above. Only change these individually if you want a mix, e.g. cloud transcription with on-device cleanup." to
            "Обычно эти параметры задаются выбранным режимом. Изменяйте их отдельно, если нужен смешанный вариант, например облачное распознавание и локальная корректировка.",
        "Always on-device — a cloud round trip would defeat the point of showing text live while you speak" to
            "Всегда работает на устройстве: задержка облака мешала бы показывать текст во время речи",
        "Live cloud transcription" to "Потоковое облачное распознавание",
        "Streams audio to Gemini while you speak, so text appears as you talk instead of after you stop. Ramblr keyboard only. Requires cloud transcription and a Gemini key — it does not change which provider transcribes you, or the order of the chain above. Unproven on real devices and roughly 1.8x the cost of the normal upload, so it is off until you turn it on. If a stream fails, the recording still finishes over the normal path." to
            "Передаёт звук в Gemini во время речи, чтобы текст появлялся сразу. Работает только с клавиатурой Ramblr; нужны облачное распознавание и ключ Gemini. Функция экспериментальная, дороже обычной отправки записи и по умолчанию выключена. При сбое запись обрабатывается обычным способом.",
    )

    fun localize(view: View) {
        if (view is TextView && view !is EditText) {
            val source = view.text.toString()
            val translated = labels[source] ?: when {
                source.startsWith("Error: ") -> "Ошибка: " + source.removePrefix("Error: ")
                source.startsWith("Local · ") -> "Локально · " + source.removePrefix("Local · ")
                source.startsWith("Cloud · ") -> "Облако · " + source.removePrefix("Cloud · ")
                else -> null
            }
            if (translated != null && translated != source) view.text = translated
            view.hint?.toString()?.let { labels[it] }?.let { view.hint = it }
        }
        if (view is ViewGroup) for (index in 0 until view.childCount) localize(view.getChildAt(index))
    }
}
