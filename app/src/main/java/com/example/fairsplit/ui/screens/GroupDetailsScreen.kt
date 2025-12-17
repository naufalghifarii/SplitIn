package com.example.fairsplit.ui.screens
package com.example.fairsplit.ui.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
// using fully-qualified names for KeyboardOptions/KeyboardType/ImeAction to avoid import resolution issues
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.fairsplit.R
import com.example.fairsplit.DataModels.DebtRecordModel
import com.example.fairsplit.DataModels.ExpenseModel
import com.example.fairsplit.DataModels.GroupModel
import com.example.fairsplit.DataModels.SplitExpenseModel
import com.example.fairsplit.ViewModels.DebtViewModel
import com.example.fairsplit.ViewModels.GroupViewModel
import com.example.fairsplit.ui.theme.FairSplitTheme
import kotlin.math.abs
import com.example.fairsplit.util.formatCurrency

/**
 * Layar detail grup yang menampilkan header grup, tab untuk Expenses/Debts/Contributions
 * serta dialog untuk menambah pengeluaran.
 */
@Composable
fun GroupDetailsScreen(navController: NavController,
                       groupViewModel: GroupViewModel,
                       debtViewModel: DebtViewModel) {

    val groupState = groupViewModel.selectedGroup.collectAsState()
    val group = groupState.value

    // Saat grup berubah, muat data hutang ke viewmodel debt
    LaunchedEffect(group) {
        group?.let { debtViewModel.loadGroupData(it) }
    }

    // Observe debt lists from the ViewModel
    val simplifiedDebts by debtViewModel.simplifiedDebts.collectAsState()
    val rawDebts by debtViewModel.rawDebts.collectAsState()

    // Jika grup tidak null, tampilkan konten utama
    group?.let {
        GroupDetailsScreenContent(
            onBackButtonClick = {navController.popBackStack()},
            onMenuButtonClick = {},
            groupViewModel = groupViewModel,
            debtViewModel = debtViewModel,
            group = it,
            selectedTab = 0,
            onTabChange = {},
            onExpenseClick = {},
            simplifiedDebts = simplifiedDebts,
            rawDebts = rawDebts
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupDetailsScreenContent(
    onBackButtonClick: () -> Unit,
    onMenuButtonClick: () -> Unit,
    groupViewModel: com.example.fairsplit.ViewModels.GroupViewModel,
    debtViewModel: DebtViewModel,
    group: GroupModel,
    selectedTab: Int = 0,
    onTabChange: (Int) -> Unit,
    onExpenseClick: () -> Unit,
    simplifiedDebts: List<DebtRecordModel>,
    rawDebts: List<DebtRecordModel>

    ) {
    var tabIndex by remember { mutableStateOf(selectedTab) }
    val tabTitles = listOf("Expenses", "Debts", "Contributions")



    var showAddExpense by remember { mutableStateOf(false) }

    Scaffold(
        // Top bar of the app
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = FormatDate(group.date),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                        },

                navigationIcon = {
                    IconButton(onClick = {onBackButtonClick()}) {
                        Icon(painterResource(R.drawable.ic_arrow_back),
                            contentDescription = "Go Back",
                            tint = MaterialTheme.colorScheme.onPrimary)
                    }
                },

                actions = {
                    IconButton(onClick = { onMenuButtonClick() }) {
                        Icon(
                            painterResource(R.drawable.ic_dot_menu_vert),
                            contentDescription = "Menu",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        },

        // Add new group button
        floatingActionButton = {
            FloatingActionButton(
                onClick = { 
                    Log.d("FairSplit", "FAB clicked in GroupDetails for group=${group.id}")
                    showAddExpense = true 
                },
                containerColor = MaterialTheme.colorScheme.primary,
            ) {
                Icon(painterResource(R.drawable.ic_add), contentDescription = "Add Event", tint = MaterialTheme.colorScheme.onPrimary)
            }
        }


    ) { paddingValues ->
        Column(modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
        ) {
            // Top Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(128.dp)
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(12.dp)
            ) {
                Row {
                    // Left side image
                    AsyncImage(
                        model = group.image, // group.image is the URL
                        contentDescription = null,
                        modifier = Modifier
                            .size(86.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop,
                        error = painterResource(R.drawable.istanbul)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = group.name,
                            style = MaterialTheme.typography.headlineLarge,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "Total Expenses",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                        Text(
                            text = formatCurrency(group.totalExpense),
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.tertiary
                        )
                    }
                }
            }

            // Tab selector row
            TabRow(
                selectedTabIndex = tabIndex,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,

            ) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        selected = tabIndex == index,
                        onClick = { tabIndex = index; onTabChange(index) },
                        text = { Text(title) }
                    )
                }
            }

            // Tab Content
            when (tabIndex) {
                0 -> ExpensesPanel(group.expenseList, onExpenseClick)
                1 -> OwedBreakdownPanel(simplifiedDebts, rawDebts)
                2 -> ContributionPanel(group)
            }

        }
    }

    // Render the AddExpenseDialog after the Scaffold so it appears above the Scaffold's content
    if (showAddExpense) {
        Log.d("FairSplit", "showAddExpense=true for group=${group.id}")
        AddExpenseDialog(
            group = group,
            onDismiss = {
                Log.d("FairSplit", "AddExpenseDialog dismissed for group=${group.id}")
                showAddExpense = false
            },
            onSave = { expense ->
                Log.d("FairSplit", "AddExpenseDialog onSave called for group=${group.id} expense=${expense.description} amount=${expense.amount}")
                groupViewModel.addExpenseToGroup(group.id, expense)
                val updated = groupViewModel.groups.value.find { it.id == group.id }
                updated?.let { debtViewModel.loadGroupData(it) }
                showAddExpense = false
            }
        )
    }
}



/**
 * Panel yang menampilkan daftar pengeluaran (expenses) yang dikelompokkan berdasarkan tanggal.
 */
@Composable
fun ExpensesPanel(expenseList: List<ExpenseModel>, onExpenseClick: () -> Unit) {
    var expandedExpenseId by remember { mutableStateOf<Int?>(null) }

    // Group the expenses by the date
    val groupedExpenses = expenseList
        .sortedByDescending { it.date } // Sort by timestamp in descending order (newest first)
        .groupBy { it.date } // Group by formatted date

    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(12.dp),
    ) {
        groupedExpenses.forEach { (date, expenses) ->
            // Display the date heading first
            item {
                Text(
                    text = FormatDate(date),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            // Display the list of expenses for that date
            items(expenses) { expense ->
                ExpenseListItem(expense = expense,
                    isExpanded = expandedExpenseId == expense.id,
                    onClick = {
                        expandedExpenseId = if (expandedExpenseId == expense.id) null else expense.id
                        onExpenseClick()
                    })
            }
        }
    }
}

/**
 * Item yang merepresentasikan satu baris pengeluaran; bisa diperluas untuk melihat rinciannya.
 */
@Composable
fun ExpenseListItem(
    expense: ExpenseModel,
    isExpanded: Boolean,
    onClick: () -> Unit) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(onClick = onClick)
            .animateContentSize(),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .height(42.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = categoryToEmoji(expense.category),
                fontSize = 24.sp,
                modifier = Modifier.padding(start = 8.dp, end = 16.dp)
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(text = expense.description, style = MaterialTheme.typography.bodyLarge)
                Text(text = "Paid by: ${expense.paidBy}", style = MaterialTheme.typography.labelLarge)
            }

            Text(text = formatCurrency(expense.amount),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(end = 8.dp)
            )
        }
    }
    Spacer(modifier = Modifier.height(2.dp))

    // Expanded section
    if (isExpanded) {
        Spacer(modifier = Modifier.height(2.dp))
        expense.splitBetween.forEach { split ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp, top = 4.dp, bottom = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = split.name, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = formatCurrency(split.share), style = MaterialTheme.typography.bodyMedium)
            }
        }


//        Spacer(modifier = Modifier.height(12.dp))
//
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(start = 24.dp, end = 24.dp, top = 4.dp, bottom = 4.dp),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.End
//        ) {
////            Text(text = expense.date,
////                modifier = Modifier.weight(1f),
////                style = MaterialTheme.typography.bodyMedium)
//            Button(
//                onClick = { },
//                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.background),
//                shape = CircleShape,
//                contentPadding = PaddingValues(0.dp),
//                modifier = Modifier.size(48.dp)
//            ) {
//                Icon(
//                    painterResource(R.drawable.ic_edit),
//                    contentDescription = "Back",
//                    tint = MaterialTheme.colorScheme.onBackground,
//
//                    )
//            }
//        }
        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(8.dp))

    }


}


/**
 * Panel yang menampilkan breakdown hutang baik dalam bentuk simplified maupun raw.
 */
@Composable
fun OwedBreakdownPanel(
    simplifiedDebts: List<DebtRecordModel>,
    rawDebts: List<DebtRecordModel>
) {
    var showSimplified by remember { mutableStateOf(true) }
    val debtList = if (showSimplified) simplifiedDebts else rawDebts

    Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {

            // Overview of the debts Section
            // Top row, text and switch
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 0.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Overview", style = MaterialTheme.typography.headlineSmall)


                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Simplified", style = MaterialTheme.typography.bodySmall
                        , modifier = Modifier.padding(end = 8.dp))
                    Switch(
                        checked = showSimplified,
                        onCheckedChange = {showSimplified = it}
                    )
                }

            }

            Spacer(modifier = Modifier.height(8.dp))

            // All the debts listed out
            for (debt in debtList) {

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable(onClick = {  })
                        .animateContentSize(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                            .height(44.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        // Left side, owed by
                        AsyncImage(
                            model = {}, // group.image is the URL
                            contentDescription = null,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop,
                            error = painterResource(R.drawable.image_blank_profile)
                        )
                        Column(modifier = Modifier.weight(1f).padding(start = 8.dp)) {
                            Text(
                                text = debt.owedBy,
                                style = MaterialTheme.typography.bodySmall,
                                textAlign = TextAlign.Start,
                            )
                            Text(
                                text = formatCurrency(abs(debt.amount)),
                                fontWeight = FontWeight.SemiBold,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.error,

                                textAlign = TextAlign.Center
                            )
                        }

                        Icon(painterResource( R.drawable.ic_arrow_forward), contentDescription = null)

                        // Right side, owed to
                        Text(
                            text = debt.owedTo,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.weight(1f).padding(end = 8.dp),
                            textAlign = TextAlign.End
                        )
                        AsyncImage(
                            model = {}, // group.image is the URL
                            contentDescription = null,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop,
                            error = painterResource(R.drawable.image_blank_profile)
                        )
                    }
                }


                Spacer(modifier = Modifier.height(4.dp))
            }
            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(12.dp))

        }
}


/**
 * Placeholder untuk panel kontribusi (masih kosong saat ini).
 */
@Composable
fun ContributionPanel(group: GroupModel) {
    Surface {  }
}

/**
 * Dialog sederhana untuk menambah pengeluaran baru ke grup.
 * Membangun `ExpenseModel` berdasarkan input pengguna dan memanggil `onSave`.
 */
@Composable
fun AddExpenseDialog(
    group: GroupModel,
    onDismiss: () -> Unit,
    onSave: (com.example.fairsplit.DataModels.ExpenseModel) -> Unit
) {
    // Simple full-screen dialog replacement for input
    androidx.compose.material3.Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Column {
            Text(text = "Add Expense", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(12.dp))

            var description by remember { mutableStateOf("") }
            var amountText by remember { mutableStateOf("") }
            var selectedPayer by remember { mutableStateOf(if (group.memberList.isNotEmpty()) group.memberList[0] else "") }
            var category by remember { mutableStateOf("") }

            Text(text = "Description")
            androidx.compose.material3.TextField(value = description, onValueChange = { description = it }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Amount")
            androidx.compose.material3.TextField(
                value = amountText,
                onValueChange = { amountText = it },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "Paid by")
            // Simple payer selector as row of buttons
            Row(modifier = Modifier.fillMaxWidth()) {
                group.memberList.forEach { member ->
                    androidx.compose.material3.Button(onClick = { selectedPayer = member }, modifier = Modifier.padding(4.dp)) {
                        Text(text = member)
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Category")
            androidx.compose.material3.TextField(value = category, onValueChange = { category = it }, modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(12.dp))
            Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                androidx.compose.material3.Button(onClick = onDismiss, colors = androidx.compose.material3.ButtonDefaults.buttonColors(MaterialTheme.colorScheme.surface)) {
                    Text(text = "Cancel")
                }
                Spacer(modifier = Modifier.width(8.dp))
                androidx.compose.material3.Button(onClick = {
                    val amount = amountText.toDoubleOrNull() ?: 0.0

                    if (amount <= 0.0) {
                        Log.d("FairSplit", "Attempt to save expense with non-positive amount: $amountText")
                        return@Button
                    }

                    // Require a non-empty description
                    if (description.isBlank()) {
                        Log.d("FairSplit", "Attempt to save expense with empty description")
                        return@Button
                    }

                    // build split equally among members; fallback to payer only
                    val members = if (group.memberList.isNotEmpty()) group.memberList else listOf(selectedPayer)

                    // Deterministic cent-based split: convert to integer cents, divide, then distribute leftover cents
                    val cents = kotlin.math.round(amount * 100).toLong()
                    val n = if (members.isNotEmpty()) members.size else 1
                    val base = cents / n
                    val remainder = (cents % n).toInt()

                    val roundedShares = mutableListOf<Double>()
                    for (i in 0 until n) {
                        val shareCents = base + if (i < remainder) 1L else 0L
                        roundedShares.add(shareCents / 100.0)
                    }

                    val splits = members.mapIndexed { idx, name ->
                        com.example.fairsplit.DataModels.SplitExpenseModel(name, roundedShares.getOrElse(idx) { 0.0 })
                    }

                    val newExpenseId = (group.expenseList.maxOfOrNull { it.id } ?: 0) + 1
                    val expense = com.example.fairsplit.DataModels.ExpenseModel(
                        id = newExpenseId,
                        description = description.ifBlank { "Expense" },
                        amount = amount,
                        paidBy = selectedPayer.ifBlank { members.firstOrNull() ?: "" },
                        splitBetween = splits,
                        date = System.currentTimeMillis(),
                        category = category.ifBlank { "Other" }
                    )

                    onSave(expense)
                }) {
                    Text(text = "Save")
                }
            }
        }
    }
}

/**
 * Ubah kategori menjadi emoji untuk tampilan ringkas.
 */
fun categoryToEmoji(category: String): String {
    return when (category.lowercase()) {
        "food" -> "🍔"
        "travel" -> "✈️"
        "accommodation" -> "🏨"
        "shopping" -> "🛍️"
        "activities" -> "🎯"
        "transport" -> "🚗"
        "other" -> "💸"
        else -> "❓"
    }
}

@Preview
@Composable
fun GroupDetailsScreenPreview() {
    FairSplitTheme {
        // Mocked debt data
        val simplifiedDebts = listOf(
            DebtRecordModel(owedBy = "Ali", owedTo = "Omar", amount = 50.0),
            DebtRecordModel(owedBy = "Omar", owedTo = "Soran", amount = 40.0),
            DebtRecordModel(owedBy = "Soran", owedTo = "Ali", amount = 30.0)
        )

        val rawDebts = listOf(
            DebtRecordModel(owedBy = "Ali", owedTo = "Soran", amount = 39.0),
            DebtRecordModel(owedBy = "Omar", owedTo = "Ali", amount = 50.0),
            DebtRecordModel(owedBy = "Soran", owedTo = "Omar", amount = 40.0)
        )

        // Mocked GroupModel
        val group = GroupModel(
            id = 1,
            name = "Trip to Istanbul",
            memberList = listOf("Ali", "Omar", "Soran"),
            expenseList = listOf(
                ExpenseModel(1, "Flights", 200.0, "Ali",
                    listOf(
                        SplitExpenseModel("Ali", 66.67),
                        SplitExpenseModel("Omar", 66.67),
                        SplitExpenseModel("Soran", 66.67)
                    ),
                    1746111600000, "Travel"),
                ExpenseModel(2, "Hotel", 250.0, "Omar",
                    listOf(
                        SplitExpenseModel("Omar", 100.0),
                        SplitExpenseModel("Ali", 75.0),
                        SplitExpenseModel("Soran", 75.0)
                    ),
                    1746198000000, "Accommodation"),
                ExpenseModel(3, "Museum tickets", 117.0, "Ali",
                    listOf(
                        SplitExpenseModel("Ali", 39.0),
                        SplitExpenseModel("Omar", 39.0),
                        SplitExpenseModel("Soran", 39.0)
                    ),
                    1746284400000, "Activities")
            ),
            image = "https://cdn-imgix.headout.com/media/images/0bc7bfc5d039409e94b0fc256ca3008d-25579-istanbul-combo-topkapi-palace--hagia-sophia---blue-mosque-02.jpg?auto=format&w=702.4499999999999&h=401.4&q=90&fit=crop&ar=7%3A4&crop=faces",
            date = 1746111600000,
            totalExpense = 567.0
        )

        // Display the screen with mock data
        GroupDetailsScreenContent(
            onBackButtonClick = {},
            onMenuButtonClick = {},
            groupViewModel = GroupViewModel(),
            debtViewModel = DebtViewModel(),
            group = group,
            selectedTab = 1,
            onTabChange = {},
            onExpenseClick = {},
            simplifiedDebts = simplifiedDebts,
            rawDebts = rawDebts
        )
    }
}
