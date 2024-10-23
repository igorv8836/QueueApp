package com.example.auth.ui

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.auth.navigation.*
import com.example.auth.viewmodel.*
import com.example.orbit_mvi.compose.collectSideEffect
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import queueapp.feature.auth.generated.resources.*


@OptIn(KoinExperimentalAPI::class)
@Composable
internal fun LoginScreen(
    viewModel: LoginViewModel = koinViewModel(),
    navController: NavController
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val state by viewModel.container.stateFlow.collectAsStateWithLifecycle()

    viewModel.collectSideEffect {
        when (it) {
            is LoginEffect.ShowError -> {
                snackBarHostState.showSnackbar(it.message)
            }

            is LoginEffect.ShowSuccessLogin -> {
                snackBarHostState.showSnackbar(it.message)
                delay(300)
                navController.navigateToMain()
            }
        }
    }

    LoginScreen(
        state = state,
        onEvent = viewModel::onEvent,
        navController = navController
    )
}

@Composable
internal fun LoginScreen(
    state: LoginState,
    onEvent: (LoginEvent) -> Unit,
    navController: NavController
) {
    val snackBarHostState = remember { SnackbarHostState() }

    if (state.showRecoveryDialog) {
        RecoveryDialog(state, onEvent)
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it).verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(resource = Res.drawable.main),
                    contentDescription = "App Icon",
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .size(240.dp)
                )


                OutlinedTextField(
                    value = state.email,
                    onValueChange = { it1 -> onEvent(LoginEvent.EmailChanged(it1)) },
                    label = { Text("Введите почту") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    leadingIcon = {
                        Icon(Icons.Default.Email, contentDescription = "email")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp, bottom = 8.dp)
                )

                var passwordVisible by remember { mutableStateOf(false) }
                OutlinedTextField(
                    value = state.password,
                    onValueChange = { it1 -> onEvent(LoginEvent.PasswordChanged(it1)) },
                    label = { Text("Введите пароль") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    ),
                    trailingIcon = {
                        val image =
                            if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                        val description = if (passwordVisible) "Hide password" else "Show password"

                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(imageVector = image, contentDescription = description)
                        }
                    },
                    leadingIcon = {
                        Icon(Icons.Default.Password, contentDescription = "password")
                    },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )

                TextButton(
                    onClick = { onEvent(LoginEvent.ToggleRecoveryDialog) },
                ) {
                    Text("Забыли пароль?")
                }

                Spacer(modifier = Modifier.height(4.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        enabled = !state.isLoading,
                        onClick = { onEvent(LoginEvent.Login) },
                        shape = MaterialTheme.shapes.medium,
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .padding(bottom = 8.dp)
                    ) {
                        if (state.isLoading) {
                            CircularProgressIndicator()
                        } else {
                            Text("Войти")
                        }
                    }

                    Button(
                        onClick = { navController.navigateToRegister(state.email) },
                        shape = MaterialTheme.shapes.medium,
                        modifier = Modifier.fillMaxWidth(0.5f)
                    ) {
                        Text("Зарегистрироваться")
                    }
                }

            }
        }

    }
}

@Composable
internal fun RecoveryDialog(
    state: LoginState,
    onEvent: (LoginEvent) -> Unit,
) {
    AlertDialog(
        title = { Text("Восстановление пароля") },
        onDismissRequest = { onEvent(LoginEvent.ToggleRecoveryDialog) },
        confirmButton = {
            if (state.emailForReset.isNotBlank()) {
                TextButton(onClick = { onEvent(LoginEvent.SendResetCode) }) {
                    Text("Отправить")
                }
            }
        },
        dismissButton = {
            TextButton(onClick = { onEvent(LoginEvent.ToggleRecoveryDialog) }) {
                Text("Отмена")
            }
        },
        text = {
            Column {
                OutlinedTextField(
                    value = state.emailForReset,
                    onValueChange = { onEvent(LoginEvent.ResetEmailChanged(it)) },
                    label = { Text("Введите почту") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier.fillMaxWidth()
                )

                TextButton(
                    onClick = { onEvent(LoginEvent.ToggleNewPassword) },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Ввести код и новый пароль")
                }

                AnimatedVisibility(
                    visible = state.showResetCodeTextField,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically()
                ) {
                    Column {
                        OutlinedTextField(
                            value = state.resetCode,
                            onValueChange = { onEvent(LoginEvent.ResetCodeChanged(it)) },
                            label = { Text("Введите код") },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                        )

                        OutlinedTextField(
                            value = state.newPassword,
                            onValueChange = { onEvent(LoginEvent.NewPasswordChanged(it)) },
                            label = { Text("Введите новый пароль") },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            visualTransformation = PasswordVisualTransformation(),
                            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                        )
                    }
                }
            }
        }
    )
}



@Preview
@Composable
internal fun RecoveryDialogPreview() {
    RecoveryDialog(LoginState()) {}
}

@Preview
@Composable
internal fun LoginScreenPreview() {
    LoginScreen(
        state = LoginState(),
        onEvent = {},
        navController = rememberNavController()
    )
}