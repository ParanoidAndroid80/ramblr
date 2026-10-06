package com.trevornk.ramblr

/** Keeps cleanup from replacing a predominantly Russian or Latin-script transcript with a translation. */
object TranscriptLanguageGuard {
    const val PROMPT_RULE = " Preserve the language of the transcript. Do not translate it into another language."

    fun preserve(original: String, cleaned: String): String {
        val source = scriptCounts(original)
        val result = scriptCounts(cleaned)
        val sourceTotal = source.first + source.second
        val resultTotal = result.first + result.second
        if (sourceTotal < 12 || resultTotal < 12) return cleaned

        val sourceCyrillic = source.first.toDouble() / sourceTotal
        val resultCyrillic = result.first.toDouble() / resultTotal
        // Restrict this fallback to an unambiguous script change. Mixed-language dictation
        // and edits to short names or commands should pass through unchanged.
        return if ((sourceCyrillic >= 0.8 && resultCyrillic <= 0.2) ||
            (sourceCyrillic <= 0.2 && resultCyrillic >= 0.8)
        ) original else cleaned
    }

    private fun scriptCounts(text: String): Pair<Int, Int> {
        var cyrillic = 0
        var latin = 0
        for (character in text) {
            when (Character.UnicodeScript.of(character.code)) {
                Character.UnicodeScript.CYRILLIC -> cyrillic++
                Character.UnicodeScript.LATIN -> latin++
                else -> Unit
            }
        }
        return cyrillic to latin
    }
}
