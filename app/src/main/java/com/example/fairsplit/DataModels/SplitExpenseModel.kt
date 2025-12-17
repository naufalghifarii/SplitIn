package com.example.fairsplit.DataModels

/**
 * Model data untuk bagian pembagian sebuah pengeluaran.
 *
 * Contoh: jika pengeluaran dibagi ke 3 orang, masing-masing mendapat `SplitExpenseModel`
 * dengan `name` dan `share` (jumlah yang menjadi tanggung jawab orang tersebut).
 */
data class SplitExpenseModel(
    val name: String,
    val share: Double
)