import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.database.dao.NewsDao
import com.example.network.datasource.AuthNetworkDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class NetworkTestViewModel : ViewModel(), KoinComponent {
    private val database: NewsDao by inject()
    private val network: AuthNetworkDataSource by inject()

    val data = MutableStateFlow<List<NewsModel>>(emptyList())

    init {
        getData()
    }

    fun getData() {
        viewModelScope.launch {
            network.test().let { newData ->
                data.update {
                    newData
                }
            }
        }
    }
}