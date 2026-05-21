package com.settlr.app.domain.usecase

data class ParsedEntry(
    val personName: String,
    val amount: Double,
    val isOwedToMe: Boolean,
    val note: String
)

class ParseNaturalLanguageUseCase {
    operator fun invoke(input: String): ParsedEntry? {
        val cleanInput = input.trim()

        val owesMeRegex = Regex("(?i)^(.+?)\\s+owes\\s+me\\s+[^\\d]*([\\d.,]+)(?:\\s+(.*))?$")
        owesMeRegex.find(cleanInput)?.let { return parseMatch(it, true) }

        val iOweRegex = Regex("(?i)^(?:i\\s+)?owe\\s+(.+?)\\s+[^\\d]*([\\d.,]+)(?:\\s+(.*))?$")
        iOweRegex.find(cleanInput)?.let { return parseMatch(it, false) }

        val paidLentRegex = Regex("(?i)^(?:paid|lent)\\s+(.+?)\\s+[^\\d]*([\\d.,]+)(?:\\s+(.*))?$")
        paidLentRegex.find(cleanInput)?.let { return parseMatch(it, true) }

        return null
    }

    private fun parseMatch(match: MatchResult, isOwedToMe: Boolean): ParsedEntry {
        val personName = match.groupValues[1].trim()
        val amountStr = match.groupValues[2].replace(",", "")
        val note = match.groupValues.getOrNull(3)?.trim() ?: ""
        
        return ParsedEntry(
            personName = personName,
            amount = amountStr.toDoubleOrNull() ?: 0.0,
            isOwedToMe = isOwedToMe,
            note = note
        )
    }
}
