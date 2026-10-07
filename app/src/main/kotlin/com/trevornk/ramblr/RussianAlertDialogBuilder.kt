package com.trevornk.ramblr

import android.app.AlertDialog
import android.content.Context

/** Applies the settings translation to the separate window used by Android alert dialogs. */
class RussianAlertDialogBuilder(context: Context) : AlertDialog.Builder(context) {
    override fun show(): AlertDialog = super.show().also { dialog ->
        localizeDialog(context, dialog)
    }

    companion object {
        fun localizeDialog(context: Context, dialog: AlertDialog) {
            if (context.resources.configuration.locales[0].language == "ru") {
                dialog.window?.decorView?.let(RussianUi::localize)
            }
        }
    }
}
