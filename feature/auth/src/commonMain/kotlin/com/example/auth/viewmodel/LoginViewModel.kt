package com.example.auth.viewmodel

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import com.example.auth.repository.AuthRepository
import com.example.common.MyResult
import com.example.orbit_mvi.viewmodel.container
import org.orbitmvi.orbit.ContainerHost

internal class LoginViewModel(
    private val authRepository: AuthRepository
) : ContainerHost<LoginState, LoginEffect>, ViewModel() {
    override val container = container<LoginState, LoginEffect>(LoginState())

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EmailChanged -> blockingIntent { reduce { state.copy(email = event.email) } }
            is LoginEvent.PasswordChanged -> blockingIntent { reduce { state.copy(password = event.password) } }
            is LoginEvent.ToggleRecoveryDialog -> intent { reduce { state.copy(showRecoveryDialog = !state.showRecoveryDialog) } }
            is LoginEvent.Login -> login()
            is LoginEvent.ToggleNewPassword -> intent { reduce { state.copy(showResetCodeTextField = !state.showResetCodeTextField) } }
            is LoginEvent.SendResetCode -> sendResetCode()
            is LoginEvent.ResetEmailChanged -> blockingIntent { reduce { state.copy(emailForReset = event.email) } }
            is LoginEvent.UseResetCode -> useResetCode()
            is LoginEvent.NewPasswordChanged -> blockingIntent { reduce { state.copy(newPassword = event.password) } }
            is LoginEvent.ResetCodeChanged -> blockingIntent { reduce { state.copy(resetCode = event.code) } }
        }
    }

    private fun useResetCode() = intent {
        state.resetCode.toIntOrNull()?.let {
            when (val res =
                authRepository.resetPassword(state.emailForReset, it, state.newPassword)) {
                is MyResult.Error -> postSideEffect(
                    LoginEffect.ShowError(
                        res.exception.message ?: "Error"
                    )
                )

                is MyResult.Success -> {
                    postSideEffect(LoginEffect.ShowSuccessLogin("Password has been changed"))
                    reduce {
                        state.copy(
                            showResetCodeTextField = false,
                            showRecoveryDialog = false
                        )
                    }
                }

                else -> {}
            }
        } ?: run {
            postSideEffect(LoginEffect.ShowError("String is entered instead of a number"))
        }
    }

    private fun login() = intent {
        reduce { state.copy(isLoading = true) }
        when (val result = authRepository.login(state.email, state.password)) {
            is MyResult.Success -> {
                postSideEffect(LoginEffect.ShowSuccessLogin("Successful login"))
                reduce { state.copy(isLoading = false) }
            }

            is MyResult.Error -> {
                postSideEffect(LoginEffect.ShowError(result.exception.message ?: "Error"))
                reduce { state.copy(isLoading = false) }
            }

            is MyResult.Loading -> {
                reduce { state.copy(isLoading = true) }
            }
        }
    }

    private fun sendResetCode() = intent {
        reduce { state.copy(showResetCodeTextField = true) }
        when (val result = authRepository.sendResetCode(state.emailForReset)) {
            is MyResult.Success<*> -> {
                postSideEffect(LoginEffect.ShowSuccessLogin("Code has been sent"))
            }

            is MyResult.Error -> {
                postSideEffect(LoginEffect.ShowError(result.exception.message ?: "Error"))
            }

            else -> {}
        }
    }
}

@Stable
sealed interface LoginEvent {
    data class EmailChanged(val email: String) : LoginEvent
    data class ResetEmailChanged(val email: String) : LoginEvent
    data class PasswordChanged(val password: String) : LoginEvent
    data object ToggleRecoveryDialog : LoginEvent
    data object Login : LoginEvent
    data object SendResetCode : LoginEvent
    data object UseResetCode : LoginEvent
    data object ToggleNewPassword : LoginEvent
    data class ResetCodeChanged(val code: String) : LoginEvent
    data class NewPasswordChanged(val password: String) : LoginEvent
}


@Stable
data class LoginState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val emailErrorText: String? = null,
    val passwordErrorText: String? = null,
    val showRecoveryDialog: Boolean = false,
    val showResetCodeTextField: Boolean = false,
    val emailForReset: String = "",
    val resetCode: String = "",
    val newPassword: String = ""
)


@Stable
internal sealed interface LoginEffect {
    data class ShowError(val message: String) : LoginEffect
    data class ShowSuccessLogin(val message: String) : LoginEffect
}