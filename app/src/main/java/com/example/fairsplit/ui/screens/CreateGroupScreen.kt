package com.example.fairsplit.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fairsplit.ViewModels.GroupViewModel
import com.example.fairsplit.R
import com.example.fairsplit.ui.theme.FairSplitTheme

/**
 * Layar pembuatan grup baru. Memungkinkan memasukkan judul, memilih mata uang,
 * dan menambahkan anggota grup.
 */
@Composable
fun CreateGroupScreen(navController: NavController, groupViewModel: GroupViewModel) {
    var title by remember { mutableStateOf("") }
    var selectedCurrency by remember { mutableStateOf("") }
    var peopleList by remember { mutableStateOf<List<String>>(emptyList()) }


    CreateGroupScreenContent(
        onBackButtonClick = {navController.popBackStack()},
        title = title,
        onTitleChange ={title = it},
        selectedCurrency = selectedCurrency,
        onCurrencySelected = {selectedCurrency = it},
        peopleList = peopleList,
        onPeopleAdded = {peopleList = it},
        onCreateGroup = {
            // create the group via ViewModel and navigate to details
            val members = peopleList
            val newGroup = groupViewModel.createGroup(title.ifBlank { "New Group" }, members)
            groupViewModel.selectGroup(newGroup)
            navController.navigate("groupDetails")
        }

    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateGroupScreenContent(
    onBackButtonClick: () -> Unit,
    title: String,
    onTitleChange:(String) -> Unit,
    selectedCurrency: String,
    onCurrencySelected: (String) -> Unit,
    peopleList: List<String>,
    onPeopleAdded: (List<String>) -> Unit
    , onCreateGroup: () -> Unit
) {
    Scaffold(
        // Top bar of the app
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Create a new group",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {onBackButtonClick()}) {
                        Icon(painterResource(R.drawable.ic_arrow_back),
                            contentDescription = "Go Back",
                            tint = MaterialTheme.colorScheme.onSurface)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },

        // bottom bar for the create group button
        bottomBar = {
            val isCreateEnabled = title.isNotBlank() && peopleList.isNotEmpty()

            Button(
                onClick = onCreateGroup,
                enabled = isCreateEnabled,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Create Group",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }


        ) { paddingValues ->
        Surface(modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {

            val scrollState = rememberScrollState()

            Column(modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .padding(12.dp)) {

                // Top Row: Rounded icon button + TextField
                Text(
                    text = "Title",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = 12.dp, bottom = 4.dp)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Button(onClick = { /* Handle back */ },
                            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary),
                            shape = RoundedCornerShape(12.dp),
                            contentPadding = PaddingValues(0.dp),
                            modifier = Modifier.size(56.dp)

                        ) {
                            Icon(
                                painterResource(R.drawable.ic_add_photo_outline), // or your image resource
                                contentDescription = "Back",
                                tint = MaterialTheme.colorScheme.onSecondary,

                            )
                        }


                    Spacer(modifier = Modifier.width(12.dp))

                    TextField(
                        value = title,
                        onValueChange = onTitleChange,
                        placeholder = { Text("Enter group title") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .height(56.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        textStyle = MaterialTheme.typography.bodyLarge,

                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                            unfocusedBorderColor = Color.Transparent,
                            focusedContainerColor = MaterialTheme.colorScheme.surface,
                            focusedBorderColor = MaterialTheme.colorScheme.onSurface,
                            )
                    )

                }

                // Currency selection
                CurrencySelector(
                    selectedCurrency = selectedCurrency,
                    onCurrencySelected = onCurrencySelected
                )

                // People added to the group
                PeopleEntrySection(onPeopleUpdated = onPeopleAdded,
                    currentPeopleList = peopleList)

                Spacer(modifier = Modifier.height(24.dp))

            }
        }

    }
}

/**
 * Komponen pemilih mata uang sederhana.
 */
@Composable
fun CurrencySelector(
    selectedCurrency: String,
    onCurrencySelected: (String) -> Unit) {

    var expanded by remember { mutableStateOf(false) }



    val currencyOptions = listOf(
        Triple("£", "\uD83C\uDDEC\uD83C\uDDE7", "GBP: Pound Sterling"),
        Triple("$", "\uD83C\uDDFA\uD83C\uDDF8", "USD: US Dollar"),
        Triple("€", "\uD83C\uDDEA\uD83C\uDDFA", "EUR: Euro"),
        Triple("¥", "\uD83C\uDDEF\uD83C\uDDF5", "JPY: Yen"),
        Triple("₹", "\uD83C\uDDEE\uD83C\uDDF3", "INR: Indian Rupee"),
        Triple("Rp", "\uD83C\uDDEE\uD83C\uDDE9", "IDR: Indonesian Rupiah")
    )

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 8.dp)) {

        Text(
            text = "Currency",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 12.dp, bottom = 4.dp)
        )

        OutlinedButton(
            onClick = { expanded = true },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary)
        ) {
            Text(
                text = "Selected | $selectedCurrency",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground
                )
        }

        Spacer(modifier = Modifier.height(8.dp))

        DropdownMenu(
            modifier = Modifier
                .fillMaxWidth(),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            currencyOptions.forEach { (symbol, emoji, description) ->
                DropdownMenuItem(
                    text = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = emoji,
                                modifier = Modifier.width(32.dp),
                                style = MaterialTheme.typography.headlineLarge
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = description,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    },
                    onClick = {
                        onCurrencySelected("$description ($symbol)")
                        expanded = false
                    }
                )
            }

        }
    }
}

/**
 * Section untuk menambah orang ke dalam grup.
 */
@Composable
fun PeopleEntrySection(
    currentPeopleList: List<String>,
    onPeopleUpdated: (List<String>) -> Unit
) {
    var nameInput by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxWidth()) {


        Text(
            text = "People in the Group",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 12.dp, bottom = 4.dp)
        )

        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TextField(
                value = nameInput,
                onValueChange = { nameInput = it },
                placeholder = { Text("Enter name") },
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 8.dp)
                    .height(56.dp).clip(RoundedCornerShape(12.dp)),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                textStyle = MaterialTheme.typography.bodyLarge,

                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedBorderColor = Color.Transparent,
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedBorderColor = MaterialTheme.colorScheme.onSurface,
                )
            )




            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    if (nameInput.isNotBlank()) {
                        val updatedList = currentPeopleList + nameInput.trim()
                        onPeopleUpdated(updatedList)
                        nameInput = ""
                    }
                },
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary),
                shape = RoundedCornerShape(12.dp),
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier.size(56.dp)
            ) {
                Icon(
                    painterResource(R.drawable.ic_add), // or your image resource
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onSecondary,

                    )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider()

        currentPeopleList.forEach { person ->
            Row(modifier = Modifier.fillMaxWidth().height(52.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = person,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(1f)
                )

                Button(
                    onClick = {
                        val updatedList = currentPeopleList.toMutableList().apply {
                            remove(person)
                        }
                        onPeopleUpdated(updatedList)
                    },
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error),
                    shape = CircleShape,
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        painterResource(R.drawable.ic_remove),
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.onError,

                        )
                }
            }

            HorizontalDivider()

        }


    }
}
@Preview(showBackground = true)
@Composable
fun CreateGroupScreenPreview() {

    FairSplitTheme {
        CreateGroupScreenContent(
            onBackButtonClick = {},
            title = "",
            onTitleChange = {},
            selectedCurrency = "£",
            onCurrencySelected = {},
            peopleList = listOf("John", "Harry"),
            onPeopleAdded = {},
            onCreateGroup = {}
        )
    }
}