package database

import androidx.lifecycle.LiveData
import androidx.room.*
import model.Word


@Dao
interface WordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(word: Word)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(word: Word)

    @Query("SELECT * FROM Word" )
    suspend fun getWordList():List<Word>


    @Query("SELECT COUNT(*) FROM Word" )
    suspend fun getCountWord():Int

    @Query("SELECT COUNT(*) FROM Word" )
    fun getCountWordLiveData(): LiveData<Int>

    @Query("SELECT id FROM word WHERE word = :word")
    suspend fun search(word:String):Int

    @Query("SELECT id FROM word WHERE meaning = :Meaning")
    suspend fun searchMeaning(Meaning:String):Int

    @Query("SELECT * FROM Word WHERE id IN(:id)")
    suspend fun getWord(id:Int): Word

    @Delete
    suspend fun delete(word:Word)

    @Query("DELETE FROM Word WHERE id=(:id)")
    suspend fun deleteById(id:Int)

}