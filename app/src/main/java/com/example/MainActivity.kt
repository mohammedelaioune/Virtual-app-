package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ui.AppViewModel
import com.example.ui.screens.CloneScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.screens.TestCameraScreen
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
  private val appViewModel: AppViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
          VirtualCameraApp(appViewModel)
        }
      }
    }
  }
}

@Composable
fun VirtualCameraApp(appViewModel: AppViewModel) {
  val navController = rememberNavController()
  
  NavHost(navController = navController, startDestination = "home") {
    composable("home") {
      HomeScreen(
        viewModel = appViewModel,
        onNavigateToClone = { navController.navigate("clone") },
        onNavigateToSettings = { navController.navigate("settings") }
      )
    }
    composable("clone") {
      CloneScreen(
        viewModel = appViewModel,
        onNavigateBack = { navController.popBackStack() }
      )
    }
    composable("settings") {
      SettingsScreen(
        viewModel = appViewModel,
        onNavigateBack = { navController.popBackStack() },
        onTestCamera = { navController.navigate("test_camera") }
      )
    }
    composable("test_camera") {
      TestCameraScreen(
        viewModel = appViewModel,
        onNavigateBack = { navController.popBackStack() }
      )
    }
  }
}
