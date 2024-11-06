package com.example.individual_project_2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.individual_project_2.ui.theme.Individualproject2Theme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Individualproject2Theme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val showSplash = remember { mutableStateOf(true) }

    if (showSplash.value) {
        SplashScreen {
            showSplash.value = false
        }
    } else {
        LoginOrRegisterScreen(
            onLoginClick = { /* Navigate to Login Screen */ },
            onRegisterClick = { /* Navigate to Register Screen */ }
        )
    }
}

@Composable
fun SplashScreen(onTimeout: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(2000)
        onTimeout()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "App Logo",
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Welcome to QuizApp!", style = MaterialTheme.typography.headlineLarge)
        }
    }
}

@Composable
fun LoginOrRegisterScreen(onLoginClick: () -> Unit, onRegisterClick: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(text = "Welcome to QuizApp", style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onLoginClick, modifier = Modifier.fillMaxWidth(0.6f)) {
                Text(text = "Log In")
            }
            Button(onClick = onRegisterClick, modifier = Modifier.fillMaxWidth(0.6f)) {
                Text(text = "Register")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    Individualproject2Theme {
        SplashScreen(onTimeout = {})
    }
}

@Preview(showBackground = true)
@Composable
fun LoginOrRegisterScreenPreview() {
    Individualproject2Theme {
        LoginOrRegisterScreen(
            onLoginClick = {},
            onRegisterClick = {}
        )
    }
}
