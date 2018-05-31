package yep.com.handr.room

import android.arch.persistence.room.*
import android.database.sqlite.SQLiteDatabase
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import java.util.*

/**
 * Created by Abins Shaji on 05/04/18.
 */
@Dao
interface RhymeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public fun inSertRhyme(rhyme:Rhyme)


    @Update
    fun updateRhyme(rhyme: Rhyme)

    @Query("Select * from Rhyme")
    fun getRhyme():Flowable<List<Rhyme>>

    @Query("Select * from Rhyme where number=:numb")
    fun getRhymeByNo(numb:Int) :Single<Rhyme>



}