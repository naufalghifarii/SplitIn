package com.example.fairsplit.ViewModels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import android.util.Log
import kotlinx.coroutines.flow.StateFlow
import com.example.fairsplit.DataModels.GroupModel
import com.example.fairsplit.sampledata.sampleGroups

class GroupViewModel : ViewModel() {
    // currently selected group (for details screen) exposed as StateFlow so UI can observe changes
    private val _selectedGroup = MutableStateFlow<GroupModel?>(null)
    val selectedGroup = _selectedGroup.asStateFlow()

    // Exposed list of groups backed by a MutableStateFlow so UI can observe additions
    private val _groups = MutableStateFlow(sampleGroups.toMutableList())
    val groups: StateFlow<List<GroupModel>> = _groups

    fun selectGroup(group: GroupModel) {
        _selectedGroup.value = group
    }

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
            // if the updated group is currently selected, update the selectedGroup flow
            if (_selectedGroup.value?.id == groupId) {
                _selectedGroup.value = updatedGroup
            }
            Log.d("FairSplit", "Group ${group.id} updated. totalExpense=${updatedGroup.totalExpense} expenses=${updatedGroup.expenseList.size}")
        }
    }
}