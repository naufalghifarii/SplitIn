package com.example.fairsplit.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.fairsplit.DataModels.GroupModel
import com.example.fairsplit.DataModels.NavItem
import com.example.fairsplit.R
import com.example.fairsplit.ViewModels.GroupViewModel
import com.example.fairsplit.sampledata.sampleGroups
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.fairsplit.ui.theme.FairSplitTheme
import java.text.SimpleDateFormat
import java.util.Locale
import com.example.fairsplit.util.formatCurrency

/**
 * Layar utama yang menampilkan daftar grup.
 *
 * - `HomeScreen` mengambil data dari `GroupViewModel` dan meneruskan ke `HomeScreenContent`.
 * - `HomeScreenContent` mengatur layout, top bar, bottom bar, dan daftar grup.
 */
@Composable
fun HomeScreen(navController: NavController,
               groupViewModel: GroupViewModel) {

    val groupsState by groupViewModel.groups.collectAsState()
    val groups = groupsState

    val navItemList = listOf(
        NavItem("Home", R.drawable.ic_home_outline),
        NavItem("Payments", R.drawable.ic_payment_outline),
        NavItem("Settings", R.drawable.ic_settings_outline)
    )

    HomeScreenContent(
        groups = groups,
        navItemList = navItemList,
        onFabClick = {navController.navigate("createGroup")},
        onGroupClick = {group ->
            groupViewModel.selectGroup(group)
            navController.navigate("groupDetails")})
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    groups: List<GroupModel>,
    navItemList: List<NavItem>,
    onFabClick: () -> Unit,
    onGroupClick: (GroupModel) -> Unit
) {
    Scaffold (

        // Top bar of the app
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Text(
                            text = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                ) {
                                    append("Split")
                                }
                                append("In")
                            },
                            style = MaterialTheme.typography.displayLarge,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                )
                HorizontalDivider()
            }
        },

        // bottom bar of the app for navigation
        bottomBar = {
            Column {
                HorizontalDivider()
                NavigationBar(containerColor = MaterialTheme.colorScheme.surface,
                    tonalElevation = 0.dp) {
                    navItemList.forEach { item ->
                        NavigationBarItem(
                            icon = {Icon(
                                painterResource(id = item.icon),
                                contentDescription = item.label,
                                tint = MaterialTheme.colorScheme.onSurface)},
                            label = {
                                Text(text = item.label,
                                    color = MaterialTheme.colorScheme.onSurface) },
                            selected = false,
                            onClick = {}
                        )
                    }
                }
            }


        },

        // Add new group button
        floatingActionButton = {
            FloatingActionButton(
                onClick = {onFabClick()},
                containerColor = MaterialTheme.colorScheme.primary,
            ) {
                Icon(painterResource(R.drawable.ic_add), contentDescription = "Add Event", tint = MaterialTheme.colorScheme.onPrimary)
            }
        }



    ) { paddingValues ->
        Column(modifier = Modifier.fillMaxSize().padding(paddingValues).background(MaterialTheme.colorScheme.background)) {
            Text(
                text = "Groups",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(12.dp, 12.dp, 12.dp, 12.dp)
            )
            HorizontalDivider()
            LazyColumn(modifier = Modifier
                .fillMaxSize()
                .padding(8.dp, 0.dp, 8.dp, 0.dp),
            ) {
                items(groups) { group ->
                    GroupItem(group = group, onClick = onGroupClick)
                }
            }
        }



    }
}

/**
 * Item kartu untuk menampilkan ringkasan sebuah grup pada daftar.
 */
@Composable
fun GroupItem(group: GroupModel, onClick: (GroupModel) -> Unit) {
    Card(modifier = Modifier
        .fillMaxSize()
        .height(100.dp)
        .padding(4.dp)
        .clickable { onClick(group) },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(8.dp)


    ) {
        Box(modifier = Modifier
            .fillMaxSize(),
            contentAlignment = Alignment.Center) {
            Row(modifier = Modifier.fillMaxWidth().padding(8.dp)) {

                // Left side image
                AsyncImage(
                    model = group.image, // group.image is the URL
                    contentDescription = null,
                    modifier = Modifier
                        .size(72.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(8.dp))

                // middle text
                Column {
                    Text(
                        text = group.name,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                    Text(
                        text = FormatDate(group.date),
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                // Right side total cost
                Text(
                    text = formatCurrency(group.totalExpense),
                    style = MaterialTheme.typography.bodyLarge
                )

            }
        }
    }
    Spacer(modifier = Modifier.height(4.dp))

}

/**
 * Format timestamp (Long) menjadi tanggal yang mudah dibaca.
 */
fun FormatDate(dateLong: Long) : String {
    val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    val formattedDate = formatter.format(dateLong)

    return formattedDate
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val groups = sampleGroups

    val navItemList = listOf(
        NavItem("Home", R.drawable.ic_home_outline),
        NavItem("Payments", R.drawable.ic_payment_outline),
        NavItem("Settings", R.drawable.ic_settings_outline)
    )

    FairSplitTheme {
        HomeScreenContent(
            groups = groups,
            navItemList = navItemList,
            onFabClick = {},
            onGroupClick = {})
    }
}

