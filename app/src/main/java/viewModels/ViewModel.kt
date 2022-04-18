package viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import model.Word
import repository.Repository

class MainViewModel(app: Application):AndroidViewModel(app) {

    var db=Repository.initDB(app.applicationContext)


    init {
        Repository.initDB(app.applicationContext)
    }

    fun insert(word:Word){
        Repository.insert(word)
    }

}