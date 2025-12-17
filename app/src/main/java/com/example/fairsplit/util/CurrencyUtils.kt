package com.example.fairsplit.util

import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

/**
 * Utility untuk memformat angka menjadi string mata uang.
 *
 * Fungsi ini mendukung default `IDR` (Rupiah) dengan format lokal Indonesia.
 * Untuk `IDR`, fungsi akan menghilangkan desimal jika jumlahnya bulat.
 *
 * @param amount Nilai angka yang akan diformat.
 * @param currencyCode Kode mata uang (mis. "IDR", "USD"). Default "IDR".
 * @return String yang sudah diformat sebagai mata uang.
 */
fun formatCurrency(amount: Double, currencyCode: String = "IDR"): String {
    return try {
        if (currencyCode == "IDR") {
            val nf = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
            nf.currency = Currency.getInstance("IDR")
            // Di Indonesia, desimal sering dihilangkan untuk jumlah bulat
            nf.maximumFractionDigits = if (amount % 1.0 == 0.0) 0 else 2
            nf.format(amount)
        } else {
            val nf = NumberFormat.getCurrencyInstance()
            nf.currency = Currency.getInstance(currencyCode)
            nf.format(amount)
        }
    } catch (e: Exception) {
        // Fallback sederhana jika terjadi error pemformatan
        "${currencyCode} ${"%.2f".format(amount)}"
    }
}
