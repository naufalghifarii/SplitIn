package com.example.fairsplit.Repositories

import com.example.fairsplit.DataModels.DebtRecordModel
import com.example.fairsplit.DataModels.GroupModel

/**
 * Repository berisi logika perhitungan hutang berdasarkan pengeluaran dalam satu grup.
 *
 * Fungsi utama:
 * - `calculateRawDebts` : mengubah setiap pengeluaran menjadi daftar hutang individual.
 * - `simplifyDebts` : menyederhanakan daftar hutang menjadi transaksi minimal antar-orang.
 * - utilitas lain untuk mengambil ringkasan dan perhitungan saldo.
 */
class DebtRepository {

    /**
     * Konversi daftar pengeluaran di grup menjadi `DebtRecordModel` mentah.
     * Setiap pengeluaran dipisah menjadi beberapa catatan "si A berutang ke si B".
     */
    fun calculateRawDebts(group: GroupModel): List<DebtRecordModel> {
        val debts = mutableListOf<DebtRecordModel>()
        val debtMap = mutableMapOf<Pair<String, String>, Double>()

        for (expense in group.expenseList) {
            val payer = expense.paidBy
            for (split in expense.splitBetween) {
                if (split.name != payer) {
                    debts.add(
                        DebtRecordModel(
                            owedTo = payer,
                            owedBy = split.name,
                            amount = split.share
                        )
                    )
                }
            }
        }

        // Gabungkan hutang yang sama (owedTo, owedBy) menjadi jumlah total
        for (debt in debts) {
            val key = Pair(debt.owedTo, debt.owedBy)
            debtMap[key] = debtMap.getOrDefault(key, 0.0) + debt.amount
        }

        return debtMap.map { (key, amount) ->
            DebtRecordModel(
                owedTo = key.first,
                owedBy = key.second,
                amount = amount
            )
        }
    }

    /**
     * Sederhanakan daftar hutang mentah menjadi transaksi minimal antara orang-orang.
     * Algoritma:
     * 1) Hitung saldo bersih tiap orang (positif = diterima, negatif = harus bayar).
     * 2) Cocokkan mereka yang menerima dengan yang berutang untuk membuat transaksi minimal.
     */
    fun simplifyDebts(debts: List<DebtRecordModel>): List<DebtRecordModel> {
        val netBalance = mutableMapOf<String, Double>()

        // Hitung saldo bersih per orang
        for (debt in debts) {
            netBalance[debt.owedBy] = netBalance.getOrDefault(debt.owedBy, 0.0) - debt.amount
            netBalance[debt.owedTo] = netBalance.getOrDefault(debt.owedTo, 0.0) + debt.amount
        }

        val simplifiedDebts = mutableListOf<DebtRecordModel>()

        // Pisahkan orang yang berutang dan yang berhak menerima
        val peopleOwing = netBalance.filter { it.value < 0.0 }
        val peopleOwed = netBalance.filter { it.value > 0.0 }

        var sortedOwed = peopleOwed.toList().sortedByDescending { it.second }
        var sortedOwing = peopleOwing.toList().sortedBy { it.second }

        var i = 0
        var j = 0

        // Cocokkan sampai salah satu list habis
        while (i < sortedOwed.size && j < sortedOwing.size) {
            val owedPerson = sortedOwed[i]
            val owingPerson = sortedOwing[j]

            val owedAmount = owedPerson.second
            val owingAmount = -owingPerson.second

            val transactionAmount = minOf(owedAmount, owingAmount)

            simplifiedDebts.add(
                DebtRecordModel(
                    owedTo = owedPerson.first,
                    owedBy = owingPerson.first,
                    amount = transactionAmount
                )
            )

            // Update saldo yang tersisa setelah transaksi
            if (owedAmount == transactionAmount) {
                i++
            } else {
                sortedOwed = sortedOwed.toMutableList().apply {
                    this[i] = this[i].copy(second = owedAmount - transactionAmount)
                }
            }

            if (owingAmount == transactionAmount) {
                j++
            } else {
                sortedOwing = sortedOwing.toMutableList().apply {
                    this[j] = this[j].copy(second = owingAmount - transactionAmount)
                }
            }
        }

        return simplifiedDebts
    }

    /**
     * Hitung saldo bersih per orang dari daftar `debts` mentah.
     */
    fun calculateNetPerPerson(debts: List<DebtRecordModel>): Map<String, Double> {
        val netBalance = mutableMapOf<String, Double>()

        for (debt in debts) {
            netBalance[debt.owedBy] = netBalance.getOrDefault(debt.owedBy, 0.0) - debt.amount
            netBalance[debt.owedTo] = netBalance.getOrDefault(debt.owedTo, 0.0) + debt.amount
        }

        return netBalance
    }


    fun getDebtsOwedBy(name: String, debts: List<DebtRecordModel>): List<DebtRecordModel> {
        return debts.filter { it.owedBy == name }
    }

    fun getDebtsOwedTo(name: String, debts: List<DebtRecordModel>): List<DebtRecordModel> {
        return debts.filter { it.owedTo == name }
    }

    fun getPersonWithMostBenefit(name: String, debts: List<DebtRecordModel>): String? {
        return debts.filter { it.owedBy == name }
            .groupBy { it.owedTo }
            .maxByOrNull { it.value.sumOf { d -> d.amount } }
            ?.key
    }

    fun printDebts(debts: List<DebtRecordModel>) {
        debts.forEach {
            println("${it.owedBy} owes ${it.owedTo} £${"%.2f".format(it.amount)}")
        }
    }

}
