package com.example.fairsplit.util

import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

fun formatCurrency(amount: Double, currencyCode: String = "IDR"): String {
    return try {
        if (currencyCode == "IDR") {
            val nf = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
            nf.currency = Currency.getInstance("IDR")
            // In Indonesia, decimals are usually omitted for whole amounts; keep two decimals if needed
            nf.maximumFractionDigits = if (amount % 1.0 == 0.0) 0 else 2
            nf.format(amount)
        } else {
            val nf = NumberFormat.getCurrencyInstance()
            nf.currency = Currency.getInstance(currencyCode)
            nf.format(amount)
        }
    } catch (e: Exception) {
        // Fallback
        "${currencyCode} ${"%.2f".format(amount)}"
    }
}
