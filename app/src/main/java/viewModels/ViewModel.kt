package viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import model.Word
import repository.Repository

class MainViewModel(app: Application):AndroidViewModel(app) {

    var db=Repository.initDB(app.applicationContext)


    init {
        Repository.initDB(app.applicationContext)

    }

    fun insert(word:Word){
        viewModelScope.launch (Dispatchers.IO){
            Repository.insert(word)
        }
    }

    fun getWordList():LiveData<List<Word>>{
        var list=MutableLiveData<List<Word>>()
        viewModelScope.async {
            list.value=Repository.getWordList()
        }
        return list
    }

    fun getCountWordLiveData(): LiveData<Int>{
        return  Repository.getCountWordLiveData()
    }

    fun searchWord(word:String):LiveData<Int>{
        var id=MutableLiveData<Int>()
        viewModelScope.launch {
            id.value=Repository.search(word)
        }
        return id
    }

    fun searchMeaning(Meaning:String):LiveData<Int>{
        var id=MutableLiveData<Int>()
        viewModelScope.launch {
            id.value=Repository.searchMeaning(Meaning)
        }
        return id
    }

    fun getWord(id:Int): LiveData<Word>{
        var word= MutableLiveData<Word>()
        viewModelScope.launch {
             word.value=Repository.getWord(id)
        }
       return word
    }

    fun update(word: Word){
        viewModelScope.launch {
            Repository.update(word)
        }
    }

    fun deleteById(id:Int){
        viewModelScope.async {
            Repository.deleteById(id)
        }
    }

//    fun deleteWord(word:Word){
//        Repository.deleteWord(word)
//    }

}