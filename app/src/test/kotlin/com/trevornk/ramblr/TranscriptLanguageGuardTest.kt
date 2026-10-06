package com.trevornk.ramblr

import org.junit.Assert.assertEquals
import org.junit.Test

class TranscriptLanguageGuardTest {
    @Test fun `rejects a Russian transcript translated into English`() {
        val original = "Завтра нужно позвонить клиенту и обсудить условия договора"
        assertEquals(original, TranscriptLanguageGuard.preserve(original, "Tomorrow we need to call the client and discuss the contract terms."))
    }

    @Test fun `keeps punctuation edits and mixed-language technical terms`() {
        val original = "Проверь пожалуйста API и исправь баг в приложении"
        val cleaned = "Проверь, пожалуйста, API и исправь баг в приложении."
        assertEquals(cleaned, TranscriptLanguageGuard.preserve(original, cleaned))
    }

    @Test fun `rejects an English transcript translated into Russian`() {
        val original = "Please call the client tomorrow and discuss the contract terms"
        assertEquals(original, TranscriptLanguageGuard.preserve(original, "Пожалуйста, позвоните клиенту завтра и обсудите условия договора."))
    }
}
