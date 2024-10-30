package com.example.auth_impl.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.*

@Composable
internal fun PasswordOutlinedTextField(
    text: String,
    passwordErrorText: String,
    modifier: Modifier,
    label: String = "Введите пароль",
    onValueChanged: (String) -> Unit
    ) {
    var passwordVisible by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = text,
        supportingText = {
            if (passwordErrorText.isNotBlank()){
                Text(passwordErrorText)
            }
        },
        isError = passwordErrorText.isNotBlank(),
        onValueChange = onValueChanged,
        label = { Text(label) },
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
        modifier = modifier
    )
}

@Composable
@Preview
internal fun PasswordOutlinedTextFieldPreview() {
    PasswordOutlinedTextField(
        text = "password",
        passwordErrorText = "Error",
        modifier = Modifier,
        label = "Введите пароль",
    ) {
    }
}
