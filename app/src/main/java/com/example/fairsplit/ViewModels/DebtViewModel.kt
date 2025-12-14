package com.example.fairsplit.ViewModels

import androidx.lifecycle.ViewModel
import com.example.fairsplit.DataModels.DebtRecordModel
import com.example.fairsplit.DataModels.GroupModel
import com.example.fairsplit.Repositories.DebtRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DebtViewModel : ViewModel() {

    private val repository = DebtRepository()

    private val _group = MutableStateFlow<GroupModel?>(null)
    val group: StateFlow<GroupModel?> = _group

    private val _rawDebts = MutableStateFlow<List<DebtRecordModel>>(emptyList())
    val rawDebts: StateFlow<List<DebtRecordModel>> = _rawDebts

    private val _simplifiedDebts = MutableStateFlow<List<DebtRecordModel>>(emptyList())
    val simplifiedDebts: StateFlow<List<DebtRecordModel>> = _simplifiedDebts

    fun loadGroupData(group: GroupModel) {
        _group.value = group
        _rawDebts.value = repository.calculateRawDebts(group)
        _simplifiedDebts.value = repository.simplifyDebts(_rawDebts.value)
    }

    fun getUserAnalytics(name: String): UserAnalytics {
        val owedBy = repository.getDebtsOwedBy(name, _rawDebts.value)
        val owedTo = repository.getDebtsOwedTo(name, _rawDebts.value)
        val biggestBenefactor = repository.getPersonWithMostBenefit(name, _rawDebts.value)

        return UserAnalytics(name, owedBy, owedTo, biggestBenefactor)
    }

    fun getNetBalances(): Map<String, Double> {
        return repository.calculateNetPerPerson(_rawDebts.value)
    }
}

data class UserAnalytics(
    val name: String,
    val owes: List<DebtRecordModel>,
    val isOwedBy: List<DebtRecordModel>,
    val mostHelpedBy: String?
)
