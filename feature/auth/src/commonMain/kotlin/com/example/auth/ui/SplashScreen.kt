package com.example.auth.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.auth.viewmodel.*
import com.example.orbit_mvi.compose.collectAsState
import com.example.ui_common.navigation.navigateToMain
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel


@Composable
internal fun SplashScreen(
    viewModel: SplashViewModel = koinViewModel(),
    navController: NavController
) {
    val state by viewModel.collectAsState()

    SplashScreen(state, navController, viewModel::onEvent)
}

@Composable
internal fun SplashScreen(
    state: SplashState,
    navController: NavController,
    onEvent: (SplashEvent) -> Unit
) {
    when (state) {
        is SplashState.Loading -> {
            SplashLoading()
        }

        is SplashState.Success -> {
            LaunchedEffect(Unit) {
                navController.navigateToMain()
            }
        }

        is SplashState.Error -> {
            SplashError(
                message = state.message,
                onRetry = {
                    onEvent(SplashEvent.Retry)
                })
        }
    }
}

@Composable
internal fun SplashLoading() {
    val color = MaterialTheme.colorScheme.primary
    val infiniteTransition = rememberInfiniteTransition()
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(100.dp)) {
            drawCircle(
                color = color,
                radius = size.minDimension / 2 * scale
            )
        }
    }
}


@Composable
fun SplashError(
    message: String,
    onRetry: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.ErrorOutline,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.error,
                modifier = Modifier.size(64.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = message,
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onRetry,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text(text = "Повторить", color = MaterialTheme.colorScheme.onError)
            }
        }
    }
}

@Composable
@Preview
internal fun SplashScreenPreview() {
    SplashScreen(SplashState.Loading, rememberNavController()) {

    }
}
