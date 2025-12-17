package com.example.fairsplit.ViewModels

import androidx.lifecycle.ViewModel
import com.example.fairsplit.DataModels.DebtRecordModel
import com.example.fairsplit.DataModels.GroupModel
import com.example.fairsplit.Repositories.DebtRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * ViewModel untuk menghitung dan menyajikan data hutang (debt) berdasarkan grup.
 *
 * - Menggunakan `DebtRepository` untuk melakukan perhitungan raw dan simplifikasi hutang.
 * - Menyediakan `StateFlow` untuk data raw dan yang sudah disederhanakan agar UI
 *   dapat merespons perubahan secara reaktif.
 */
class DebtViewModel : ViewModel() {

    // Repository yang berisi logika perhitungan hutang
    private val repository = DebtRepository()

    // Grup yang sedang dianalisis
    private val _group = MutableStateFlow<GroupModel?>(null)
    val group: StateFlow<GroupModel?> = _group

    // Daftar catatan hutang mentah (satu baris per transaksi debt)
    private val _rawDebts = MutableStateFlow<List<DebtRecordModel>>(emptyList())
    val rawDebts: StateFlow<List<DebtRecordModel>> = _rawDebts

    // Daftar hutang yang sudah disederhanakan (pairwise minimal)
    private val _simplifiedDebts = MutableStateFlow<List<DebtRecordModel>>(emptyList())
    val simplifiedDebts: StateFlow<List<DebtRecordModel>> = _simplifiedDebts

    /**
     * Muat data grup dan hitung raw debts serta simplified debts.
     */
    fun loadGroupData(group: GroupModel) {
        _group.value = group
        _rawDebts.value = repository.calculateRawDebts(group)
        _simplifiedDebts.value = repository.simplifyDebts(_rawDebts.value)
    }

    /**
     * Dapatkan analitik per-user seperti siapa yang berhutang dan siapa yang diberi hutang.
     */
    fun getUserAnalytics(name: String): UserAnalytics {
        val owedBy = repository.getDebtsOwedBy(name, _rawDebts.value)
        val owedTo = repository.getDebtsOwedTo(name, _rawDebts.value)
        val biggestBenefactor = repository.getPersonWithMostBenefit(name, _rawDebts.value)

        return UserAnalytics(name, owedBy, owedTo, biggestBenefactor)
    }

    /**
     * Hitung saldo bersih per orang dari daftar raw debts.
     */
    fun getNetBalances(): Map<String, Double> {
        return repository.calculateNetPerPerson(_rawDebts.value)
    }
}

/**
 * Struktur data ringkasan analitik untuk satu pengguna.
 */
data class UserAnalytics(
    val name: String,
    val owes: List<DebtRecordModel>,
    val isOwedBy: List<DebtRecordModel>,
    val mostHelpedBy: String?
)
