package me.arwan.mov.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import me.arwan.mov.ui.screens.NavGraphs
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import me.arwan.mov.ui.theme.MovTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var isSplash = true
        installSplashScreen().apply {
            setKeepOnScreenCondition { isSplash }
        }
        lifecycleScope.launchWhenCreated {
            withContext(Dispatchers.IO) { delay(1500) }
            isSplash = false
        }
        setContent {
            MovTheme {
                DestinationsNavHost(
                    navController = rememberNavController(),
                    navGraph = NavGraphs.root,
                )
            }
        }
    }
}

