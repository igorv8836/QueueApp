import androidx.lifecycle.ViewModel
import com.example.database.dao.NewsDao
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class NetworkTestViewModel : ViewModel(), KoinComponent {
    private val database: NewsDao by inject()

    init {
        getData()
    }

    fun getData() {

    }
}