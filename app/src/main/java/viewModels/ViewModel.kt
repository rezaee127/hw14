package viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import model.Word

class MainViewModel(app: Application):AndroidViewModel(app) {



    init {
        Repository.initDB(app.applicationContext)
    }

    fun insert(word:Word){
        Repository.insert(word)
    }

}