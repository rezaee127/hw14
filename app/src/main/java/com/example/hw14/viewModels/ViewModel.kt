package com.example.hw14.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.hw14.model.Word
import com.example.hw14.repository.Repository

class MainViewModel(app: Application):AndroidViewModel(app) {

    var db=Repository.initDB(app.applicationContext)


    init {
        Repository.initDB(app.applicationContext)

    }

    fun insert(word:Word){
        Repository.insert(word)
    }

    fun getCountWordLiveData(): LiveData<Int>{
        return  Repository.getCountWordLiveData()
    }

    fun searchWord(word:String):Int{
        return Repository.search(word)
    }

    fun searchMeaning(Meaning:String):Int{
        return Repository.searchMeaning(Meaning)
    }

    fun getWord(id:Int): Word{
        return Repository.getWord(id)
    }

    fun update(word: Word){
        Repository.update(word)
    }

    fun deleteById(id:Int){
        Repository.deleteById(id)
    }

//    fun deleteWord(word:Word){
//        Repository.deleteWord(word)
//    }

}