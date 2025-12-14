package com.example.fairsplit.DataModels

data class GroupModel(
    val id: Int,
    val name: String,
    val memberList: List<String>,
    val expenseList: List<ExpenseModel> = emptyList(),
    val image: String,
    val date: Long,
    val totalExpense: Double,
)
