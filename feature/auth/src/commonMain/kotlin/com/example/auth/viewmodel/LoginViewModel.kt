package com.example.auth.viewmodel

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.auth.repository.AuthRepository
import com.example.common.MyResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container

internal class LoginViewModel(
    private val dispatcher: CoroutineDispatcher,
    private val authRepository: AuthRepository
) : ContainerHost<LoginState, LoginEffect>, ViewModel() {
    override val container = viewModelScope.container<LoginState, LoginEffect>(LoginState.Loading)

    fun onEvent(event: LoginEvent) {
        viewModelScope.launch(dispatcher) {
            when (event) {
                is LoginEvent.Login -> {
                    login(event.email, event.password)
                }

                is LoginEvent.SendResetCode -> {
                    resetPassword(event.email)
                }

                is LoginEvent.ResetPassword -> {}
            }
        }
    }

    private fun login(email: String, password: String) = intent {
        when (val result = authRepository.login(email, password)) {
            is MyResult.Success -> {
                reduce { LoginState.Success(email, password, null) }
                postSideEffect(LoginEffect.SuccessLogin("Success"))
            }

            is MyResult.Error -> {
                reduce { LoginState.Success(email, password, result.exception.message) }
                postSideEffect(LoginEffect.ShowError(result.exception.message ?: "Error"))
            }

            is MyResult.Loading -> {
                reduce { LoginState.Loading }
            }
        }
    }

    private fun resetPassword(email: String) = intent {
        when (val result = authRepository.sendResetCode(email)) {
            is MyResult.Success<*> -> {
                postSideEffect(LoginEffect.SuccessLogin("Success"))
            }

            is MyResult.Error -> {
                postSideEffect(LoginEffect.ShowError(result.exception.message ?: "Error"))
            }

            is MyResult.Loading -> {
                reduce { LoginState.Loading }
            }
        }
    }
}

@Stable
internal sealed interface LoginEvent {
    data class Login(val email: String, val password: String) : LoginEvent
    data class SendResetCode(val email: String) : LoginEvent
    data class ResetPassword(val code: Int, val newPassword: String) : LoginEvent
}

@Stable
internal sealed interface LoginState {
    data object Loading : LoginState
    data class Error(val message: String) : LoginState
    data class Success(val email: String, val password: String, val errorMessage: String?) :
        LoginState
}

@Stable
internal sealed interface LoginEffect {
    data class ShowError(val message: String) : LoginEffect
    data class SuccessLogin(val message: String) : LoginEffect
}