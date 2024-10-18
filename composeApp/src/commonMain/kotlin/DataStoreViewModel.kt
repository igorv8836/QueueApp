import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DataStoreViewModel : ViewModel(), KoinComponent {
    private val datastoreManager: DataStoreManager by inject()

    val data = MutableStateFlow("")

    init {
        getData()
    }

    fun getData() {
        viewModelScope.launch {
            datastoreManager.getToken().collect { token ->
                data.update {
                    token ?: ""
                }
            }
        }
    }

    fun saveData() {
        viewModelScope.launch {
            datastoreManager.saveToken("testtest")
        }
    }
}