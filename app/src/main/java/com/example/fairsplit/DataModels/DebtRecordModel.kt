package com.example.fairsplit.DataModels

data class DebtRecordModel(
    val owedTo: String,
    val owedBy: String,
    var amount: Double
) {
}