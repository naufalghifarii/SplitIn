package com.example.fairsplit.Repositories

import com.example.fairsplit.DataModels.DebtRecordModel
import com.example.fairsplit.DataModels.GroupModel

class DebtRepository {

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

    fun simplifyDebts(debts: List<DebtRecordModel>): List<DebtRecordModel> {
        // Map to store net balance for each person
        val netBalance = mutableMapOf<String, Double>()

        // Step 1: Calculate the net balance for each person
        for (debt in debts) {
            // If someone owes someone else, subtract from their balance
            netBalance[debt.owedBy] = netBalance.getOrDefault(debt.owedBy, 0.0) - debt.amount
            // If someone is owed money, add to their balance
            netBalance[debt.owedTo] = netBalance.getOrDefault(debt.owedTo, 0.0) + debt.amount
        }

        // Step 2: Create a simplified list of transactions based on net balances
        val simplifiedDebts = mutableListOf<DebtRecordModel>()

        // Step 3: Calculate the simplified debt relationships
        val peopleOwing = netBalance.filter { it.value < 0.0 } // Those who owe money
        val peopleOwed = netBalance.filter { it.value > 0.0 } // Those who are owed money

        // Step 4: Match those who owe with those who are owed
        var sortedOwed = peopleOwed.toList().sortedByDescending { it.second } // Highest owed first
        var sortedOwing = peopleOwing.toList().sortedBy { it.second } // Most negative first

        var i = 0
        var j = 0

        // Step 5: Simplify debt by matching those who owe with those who are owed
        while (i < sortedOwed.size && j < sortedOwing.size) {
            val owedPerson = sortedOwed[i]
            val owingPerson = sortedOwing[j]

            val owedAmount = owedPerson.second
            val owingAmount = -owingPerson.second // Amount owed (positive balance)

            val transactionAmount = minOf(owedAmount, owingAmount)

            simplifiedDebts.add(
                DebtRecordModel(
                    owedTo = owedPerson.first,
                    owedBy = owingPerson.first,
                    amount = transactionAmount
                )
            )

            // Update the balances
            if (owedAmount == transactionAmount) {
                i++ // Move to next person who is owed
            } else {
                sortedOwed = sortedOwed.toMutableList().apply {
                    this[i] = this[i].copy(second = owedAmount - transactionAmount)
                }
            }

            if (owingAmount == transactionAmount) {
                j++ // Move to next person who owes
            } else {
                sortedOwing = sortedOwing.toMutableList().apply {
                    this[j] = this[j].copy(second = owingAmount - transactionAmount)
                }
            }
        }

        return simplifiedDebts    }

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
