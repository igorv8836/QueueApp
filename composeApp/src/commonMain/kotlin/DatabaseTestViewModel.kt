import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.database.dao.NewsDao
import com.example.database.model.NewsEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DatabaseTestViewModel : ViewModel(), KoinComponent {
    private val database: NewsDao by inject()

    val data = MutableStateFlow<List<NewsEntity>>(emptyList())

    init {
        insert()
        getData()
    }

    fun insert() {
        viewModelScope.launch {
            for (i in 0..100) {
                database.insertNews(
                    listOf(
                        NewsEntity(
                            i,
                            "test$i",
                            "test$i",
                            null
                        )
                    )
                )
            }
        }

    }

    private fun getData() {
        viewModelScope.launch {
            database.getFlowAllNews().collect { newData ->
                data.update{
                    newData
                }
            }
        }
    }
}