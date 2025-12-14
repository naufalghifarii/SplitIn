package com.example.fairsplit.DataModels

data class ExpenseModel(
    val id: Int,
    val description: String,
    val amount: Double,
    val paidBy: String, // The person who paid for the bill
    val splitBetween: List<SplitExpenseModel> = emptyList(),
    val date: Long, // Can use LocalDate or String
    val category: String
)
