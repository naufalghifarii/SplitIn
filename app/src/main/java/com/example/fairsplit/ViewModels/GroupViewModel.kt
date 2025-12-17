package com.example.fairsplit.ViewModels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import android.util.Log
import kotlinx.coroutines.flow.StateFlow
import com.example.fairsplit.DataModels.GroupModel
import com.example.fairsplit.sampledata.sampleGroups

/**
 * ViewModel sederhana untuk mengelola daftar grup dan grup yang dipilih.
 *
 * - Menyimpan daftar grup sebagai `StateFlow` agar UI dapat berlangganan perubahan.
 * - Menyimpan grup yang sedang dipilih (untuk layar detail) juga sebagai `StateFlow`.
 */
class GroupViewModel : ViewModel() {
    // Grup yang sedang dipilih; dapat bernilai null jika tidak ada yang dipilih
    private val _selectedGroup = MutableStateFlow<GroupModel?>(null)
    val selectedGroup = _selectedGroup.asStateFlow()

    // Daftar grup yang diekspos ke UI; awalnya dari `sampleGroups`
    private val _groups = MutableStateFlow(sampleGroups.toMutableList())
    val groups: StateFlow<List<GroupModel>> = _groups

    /**
     * Set grup yang sedang dipilih untuk ditampilkan di layar detail.
     */
    fun selectGroup(group: GroupModel) {
        _selectedGroup.value = group
    }

    /**
     * Buat grup baru dan tambahkan ke daftar (di posisi paling depan).
     *
     * @return grup yang baru dibuat.
     */
    fun createGroup(name: String, members: List<String>, image: String = "", date: Long = System.currentTimeMillis()): GroupModel {
        val current = _groups.value.toMutableList()
        val newId = (current.maxOfOrNull { it.id } ?: 0) + 1
        val newGroup = GroupModel(
            id = newId,
            name = name,
            memberList = members,
            expenseList = emptyList(),
            image = image,
            date = date,
            totalExpense = 0.0
        )
        current.add(0, newGroup)
        _groups.value = current
        return newGroup
    }

    /**
     * Tambah pengeluaran (`expense`) ke grup dengan `groupId`.
     * Jika grup ada, pengeluaran ditambahkan di depan daftar dan total diperbarui.
     */
    fun addExpenseToGroup(groupId: Int, expense: com.example.fairsplit.DataModels.ExpenseModel) {
        val current = _groups.value.toMutableList()
        val index = current.indexOfFirst { it.id == groupId }
        if (index >= 0) {
            val group = current[index]
            val newExpenses = group.expenseList.toMutableList().apply { add(0, expense) }
            val updatedGroup = group.copy(
                expenseList = newExpenses,
                totalExpense = newExpenses.sumOf { it.amount }
            )
            current[index] = updatedGroup
            _groups.value = current
            // Jika grup yang diperbarui sedang dipilih, sinkronkan value selectedGroup
            if (_selectedGroup.value?.id == groupId) {
                _selectedGroup.value = updatedGroup
            }
            Log.d("FairSplit", "Group ${group.id} updated. totalExpense=${updatedGroup.totalExpense} expenses=${updatedGroup.expenseList.size}")
        }
    }
}