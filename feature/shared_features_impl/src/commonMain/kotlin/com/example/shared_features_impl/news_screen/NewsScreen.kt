package com.example.shared_features_impl.news_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.shared_features_api.data.model.NewsModel
import kotlinx.datetime.*
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun NewsScreen(viewModel: NewsViewModel = koinViewModel()) {
    val newsState by viewModel.news.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Новости") })
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (newsState) {
                is NewsState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                is NewsState.Error -> {
                    val errorMessage = (newsState as NewsState.Error).exception
                    Text(
                        text = "Ошибка: $errorMessage",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                is NewsState.Success -> {
                    val newsList = (newsState as NewsState.Success).news
                    LazyColumn {
                        items(newsList) { newsItem ->
                            NewsCard(newsItem)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NewsCard(newsItem: NewsModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.elevatedCardElevation(4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = newsItem.title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = newsItem.content,
                style = MaterialTheme.typography.bodyLarge
            )
            newsItem.createdAt?.let { it ->
                Spacer(modifier = Modifier.height(8.dp))
                val formattedDate = Instant.fromEpochMilliseconds(it)
                    .toLocalDateTime(TimeZone.currentSystemDefault())
                    .let { "${it.dayOfMonth} ${it.month} ${it.year}" }
                Text(
                    text = "Опубликовано: $formattedDate",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                )
            }
        }
    }
}
