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

/**
 * Aktivitas utama aplikasi yang men-setup tema, splash screen dan navigasi.
 *
 * - Menginisialisasi `GroupViewModel` dan `DebtViewModel` yang dipakai oleh
 *   layar-layar aplikasi.
 * - Memanggil `installSplashScreen()` untuk mendukung splash screen native.
 * - Menggunakan `setContent` untuk menempatkan komposables UI dengan tema.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Hapus action bar secara programatik
        window.requestFeature(Window.FEATURE_NO_TITLE)

        // Aktifkan fitur edge-to-edge untuk layout
        enableEdgeToEdge()

        // Buat instance sederhana viewmodels (bisa diganti dengan DI)
        val groupViewModel = GroupViewModel()
        val debtViewModel = DebtViewModel()

        // Integrasi splash screen native Android
        installSplashScreen()

        // Set konten Compose dengan tema aplikasi
        setContent {
            FairSplitTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AppNavigation(groupViewModel, debtViewModel)
                }
            }
        }
    }
}

/**
 * Komposabel navigasi utama aplikasi.
 *
 * Mempersiapkan `NavHost` dan rute-rute: splash, home, createGroup, groupDetails.
 * Parameter `groupViewModel` dan `debtViewModel` diteruskan ke layar yang membutuhkannya.
 */
@Composable
fun AppNavigation(groupViewModel: GroupViewModel,
                  debtViewModel: DebtViewModel
                  ) {

    // Controller navigasi Compose
    val navController: NavHostController = rememberNavController()

    // Definisikan rute dan konten masing-masing rute
    NavHost(navController = navController, startDestination = "home") {
        composable("splash") { SplashScreen(navController) }
        composable("home") { HomeScreen(navController, groupViewModel) }
        composable("createGroup") { CreateGroupScreen(navController, groupViewModel) }
        composable("groupDetails") { GroupDetailsScreen(navController, groupViewModel, debtViewModel) }
    }
}

