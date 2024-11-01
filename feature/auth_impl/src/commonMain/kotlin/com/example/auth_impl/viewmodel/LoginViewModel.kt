package com.example.auth_impl.viewmodel

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import com.example.auth_api.data.AuthRepository
import com.example.orbit_mvi.viewmodel.container
import org.orbitmvi.orbit.ContainerHost

internal class LoginViewModel(
    private val authRepository: AuthRepository
) : ContainerHost<LoginState, LoginEffect>, ViewModel() {
    override val container = container<LoginState, LoginEffect>(LoginState())

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.Login -> login(event.email, event.password)
            is LoginEvent.SendResetCode -> sendResetCode(event.email)
            is LoginEvent.UseResetCode -> useResetCode(event.email, event.code, event.newPassword)
        }
    }

    private fun useResetCode(email: String, code: String, newPassword: String) = intent {
        code.toIntOrNull()?.let {
            val result = authRepository.resetPassword(email, it, newPassword)
            if (result.isFailure) {
                val exceptionMessage = result.exceptionOrNull()?.message ?: "Ошибка"
                postSideEffect(LoginEffect.ShowMessage(exceptionMessage))
            } else {
                postSideEffect(LoginEffect.ShowMessage("Пароль изменен"))
            }
            reduce { state.copy(codeErrorText = null) }
        } ?: run {
            reduce { state.copy(codeErrorText = "Введите цифры, а не строки") }
        }
    }

    private fun login(email: String, password: String) = intent {
        reduce { state.copy(isLoading = true) }
        val result = authRepository.login(email, password)
        if (result.isSuccess) {
            postSideEffect(LoginEffect.ShowSuccessLogin("Успешный вход"))
        } else {
            val exceptionMessage = result.exceptionOrNull()?.message ?: "Ошибка"
            postSideEffect(LoginEffect.ShowMessage(exceptionMessage))
        }
        reduce { state.copy(isLoading = false) }
    }

    private fun sendResetCode(email: String) = intent {
        val result = authRepository.sendResetCode(email)
        if (result.isSuccess) {
            postSideEffect(LoginEffect.ShowMessage("Код отправлен"))
        } else {
            val exceptionMessage = result.exceptionOrNull()?.message ?: "Ошибка"
            postSideEffect(LoginEffect.ErrorInSendCode(exceptionMessage))
        }
    }
}

@Stable
sealed interface LoginEvent {
    data class Login(val email: String, val password: String) : LoginEvent
    data class SendResetCode(val email: String) : LoginEvent
    data class UseResetCode(val email: String, val code: String, val newPassword: String) : LoginEvent
}


@Stable
data class LoginState(
    val isLoading: Boolean = false,
    val emailErrorText: String? = null,
    val passwordErrorText: String? = null,
    val resetEmailErrorText: String? = null,
    val newPasswordErrorText: String? = null,
    val codeErrorText: String? = null,
)


@Stable
internal sealed interface LoginEffect {
    data class ShowMessage(val message: String) : LoginEffect
    data class ErrorInSendCode(val message: String) : LoginEffect
    data class ShowSuccessLogin(val message: String) : LoginEffect
}