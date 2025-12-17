package com.example.fairsplit.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fairsplit.R
import com.example.fairsplit.ui.theme.FairSplitTheme
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController){

    /**
     * Halaman splash sederhana.
     *
     * - Menjalankan `LaunchedEffect` untuk menunggu beberapa detik lalu
     *   melakukan navigasi ke layar utama (`home`).
     * - Menggunakan `popUpTo` agar splash tidak tersimpan di back stack.
     */
    LaunchedEffect(key1 = Unit) {
        delay(2000) // 2 seconds delay
        navController.navigate("home") { // Replace with your target screen route
            popUpTo("splash") { inclusive = true } // Optionally pop up from the back stack
        }
    }

    // Tampilkan konten visual splash
    SplashScreenContent()
}

@Composable
fun SplashScreenContent(){
    /**
     * Konten visual splash: logo, nama aplikasi, dan subjudul.
     * Menggunakan tema warna dari `MaterialTheme`.
     */
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(50.dp)
    ) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo aplikasi
            AppLogo()

            // Nama aplikasi dengan styling sebagian berwarna primary
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        append("Fair")
                    }
                    append("Split")
                },
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(top = 16.dp)
            )

            // Subjudul singkat di bawah nama aplikasi
            Text(
                text = "Fair & Square - The Easiest Way to Split",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(top = 16.dp)
            )

        }
    }
}

@Composable
fun AppLogo(isDarkTheme: Boolean = isSystemInDarkTheme()) {
    /**
     * Pilih logo sesuai tema (dark/light) lalu tampilkan.
     * @param isDarkTheme Nilai default mengambil dari sistem.
     */
    val logoResId = if (isDarkTheme) {
        R.drawable.logo_dark
    } else {
        R.drawable.logo_light
    }

    Image(
        painter = painterResource(id = logoResId),
        contentDescription = "App Logo"
    )
}


@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    FairSplitTheme {
        SplashScreenContent()
    }
}