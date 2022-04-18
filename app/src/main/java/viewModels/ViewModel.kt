package viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class ViewModel(app: Application):AndroidViewModel(app) {
    init {
        Repository.initDB(app.applicationContext)
    }
}