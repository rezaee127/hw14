package database

import androidx.lifecycle.LiveData
import androidx.room.*
import model.Word


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


    @Query("SELECT * FROM Word WHERE id IN(:id)")
    fun getWord(id:Int): Word

    @Delete
    fun delete(word:Word)

}