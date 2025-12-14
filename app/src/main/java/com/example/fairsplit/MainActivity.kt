package com.example.fairsplit

import android.os.Bundle
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fairsplit.ViewModels.DebtViewModel
import com.example.fairsplit.ViewModels.GroupViewModel
import com.example.fairsplit.ui.screens.CreateGroupScreen
import com.example.fairsplit.ui.screens.GroupDetailsScreen
import com.example.fairsplit.ui.screens.HomeScreen
import com.example.fairsplit.ui.screens.SplashScreen
import com.example.fairsplit.ui.theme.FairSplitTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Remove action bar programmatically
        window.requestFeature(Window.FEATURE_NO_TITLE)


        enableEdgeToEdge()

        val groupViewModel = GroupViewModel()
        val debtViewModel = DebtViewModel()

        installSplashScreen()
        setContent {
            FairSplitTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AppNavigation(groupViewModel, debtViewModel)
                }
            }
        }
    }
}

@Composable
fun AppNavigation(groupViewModel: GroupViewModel,
                  debtViewModel: DebtViewModel
                  ) {

    val navController: NavHostController = rememberNavController()


    NavHost(navController = navController, startDestination = "home") {
        composable("splash") { SplashScreen(navController) }
        composable("home") { HomeScreen(navController, groupViewModel) }
        composable("createGroup") { CreateGroupScreen(navController, groupViewModel) }
        composable("groupDetails") { GroupDetailsScreen(navController, groupViewModel, debtViewModel) }

    }
}

