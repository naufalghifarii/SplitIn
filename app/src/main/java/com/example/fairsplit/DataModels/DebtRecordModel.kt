package com.example.fairsplit.DataModels

/**
 * Representasi satu catatan hutang: siapa berutang kepada siapa, dan jumlahnya.
 *
 * `amount` disimpan sebagai `var` karena beberapa algoritma (mis. simplifikasi)
 * dapat mengubah nilainya.
 */
data class DebtRecordModel(
    val owedTo: String,
    val owedBy: String,
    var amount: Double
) {
}