package com.example.fairsplit.DataModels

/**
 * Model data untuk sebuah grup pengeluaran bersama.
 *
 * - `memberList` : daftar nama peserta.
 * - `expenseList` : daftar pengeluaran yang dicatat pada grup.
 * - `totalExpense` : jumlah total pengeluaran pada grup.
 */
data class GroupModel(
    val id: Int,
    val name: String,
    val memberList: List<String>,
    val expenseList: List<ExpenseModel> = emptyList(),
    val image: String,
    val date: Long,
    val totalExpense: Double,
)
