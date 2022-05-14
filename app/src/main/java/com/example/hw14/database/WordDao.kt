package com.example.hw14.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.hw14.model.Word


@Dao
interface WordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(word: Word)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun update(word: Word)

    @Query("SELECT COUNT(*) FROM Word" )
    fun getCountWord():Int

    @Query("SELECT COUNT(*) FROM Word" )
    fun getCountWordLiveData(): LiveData<Int>

    @Query("SELECT id FROM word WHERE word = :word")
    fun search(word:String):Int

    @Query("SELECT id FROM word WHERE meaning = :Meaning")
    fun searchMeaning(Meaning:String):Int

    @Query("SELECT * FROM Word WHERE id IN(:id)")
    fun getWord(id:Int): Word

    @Delete
    fun delete(word:Word)

    @Query("DELETE FROM Word WHERE id=(:id)")
    fun deleteById(id:Int)

}