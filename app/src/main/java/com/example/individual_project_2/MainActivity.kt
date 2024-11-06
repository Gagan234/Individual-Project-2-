package com.example.individual_project_2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
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
    var currentScreen by remember { mutableStateOf(Screen.Splash) }
    var score by remember { mutableStateOf(0) }

    when (currentScreen) {
        Screen.Splash -> SplashScreen { currentScreen = Screen.LoginOrRegister }
        Screen.LoginOrRegister -> LoginOrRegisterScreen(
            onLoginClick = { currentScreen = Screen.Login },
            onRegisterClick = { currentScreen = Screen.Register }
        )
        Screen.Login -> LoginScreen { currentScreen = Screen.GameRules }
        Screen.Register -> RegistrationScreen { currentScreen = Screen.LoginOrRegister }
        Screen.GameRules -> GameRulesScreen { currentScreen = Screen.Quiz }
        Screen.Quiz -> QuizScreen(
            onQuizFinish = { quizScore ->
                score = quizScore
                currentScreen = Screen.Result
            }
        )
        Screen.Result -> ResultScreen(score = score) { currentScreen = Screen.GameRules }
    }
}

enum class Screen {
    Splash, LoginOrRegister, Login, Register, GameRules, Quiz, Result
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

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit) {
    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val errorMessage = remember { mutableStateOf<String?>(null) }

    fun validateLogin() {
        if (username.value.isNotEmpty() && password.value.isNotEmpty()) {
            errorMessage.value = null
            onLoginSuccess()
        } else {
            errorMessage.value = "Please enter both username and password."
        }
    }

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Login", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = username.value, onValueChange = { username.value = it }, label = { Text("Username") })
        OutlinedTextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )


        errorMessage.value?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { validateLogin() }) {
            Text(text = "Login")
        }
    }
}

@Composable
fun RegistrationScreen(onRegisterSuccess: () -> Unit) {
    val firstName = remember { mutableStateOf("") }
    val familyName = remember { mutableStateOf("") }
    val dob = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val errorMessage = remember { mutableStateOf<String?>(null) }

    fun validateAndRegister() {
        if (firstName.value.isEmpty() || familyName.value.isEmpty() || dob.value.isEmpty() || email.value.isEmpty() || password.value.isEmpty()) {
            errorMessage.value = "All fields are required."
        } else if (firstName.value.length < 3 || firstName.value.length > 30) {
            errorMessage.value = "First name must be between 3 and 30 characters."
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.value).matches()) {
            errorMessage.value = "Invalid email address."
        } else {
            errorMessage.value = null
            onRegisterSuccess()
        }
    }

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Register", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = firstName.value, onValueChange = { firstName.value = it }, label = { Text("First Name") })
        OutlinedTextField(value = familyName.value, onValueChange = { familyName.value = it }, label = { Text("Family Name") })
        OutlinedTextField(value = dob.value, onValueChange = { dob.value = it }, label = { Text("Date of Birth") })
        OutlinedTextField(value = email.value, onValueChange = { email.value = it }, label = { Text("Email") })
        OutlinedTextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )


        errorMessage.value?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { validateAndRegister() }) {
            Text(text = "Register")
        }
    }
}

@Composable
fun GameRulesScreen(onStartGame: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Quiz Rules", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Text("1. Answer all questions to the best of your ability.")
        Text("2. Some questions have multiple correct answers.")
        Text("3. Confirm your answer before proceeding.")

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onStartGame) {
            Text("Start Quiz")
        }
    }
}

@Composable
fun QuizScreen(onQuizFinish: (Int) -> Unit) {

    val questions = listOf("Question 1", "Question 2", "Question 3")
    var currentQuestion by remember { mutableStateOf(0) }
    var score by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = questions[currentQuestion], style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (currentQuestion < questions.size - 1) {
                currentQuestion++
            } else {
                onQuizFinish(score)
            }
        }) {
            Text("Next Question")
        }
    }
}

@Composable
fun ResultScreen(score: Int, onPlayAgain: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Your Score: $score", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onPlayAgain) {
            Text("Play Again")
        }
    }
}
