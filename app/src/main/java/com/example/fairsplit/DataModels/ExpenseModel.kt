package com.example.fairsplit.DataModels

/**
 * Model data untuk sebuah pengeluaran (expense) di dalam grup.
 *
 * @param id Identifier unik pengeluaran
 * @param description Deskripsi singkat pengeluaran
 * @param amount Jumlah uang pengeluaran
 * @param paidBy Nama orang yang membayar
 * @param splitBetween Rincian pembagian biaya per orang
 * @param date Timestamp pengeluaran (Long)
 * @param category Kategori pengeluaran (mis. Food, Travel)
 */
data class ExpenseModel(
    val id: Int,
    val description: String,
    val amount: Double,
    val paidBy: String, // The person who paid for the bill
    val splitBetween: List<SplitExpenseModel> = emptyList(),
    val date: Long, // Can use LocalDate or String
    val category: String
)
