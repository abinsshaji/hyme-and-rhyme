package yep.com.handr.room

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * Created by Abins Shaji on 09/04/18.
 */
@Dao
interface HymeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHymn(hyme:Hyme)

    @Query("Select * from Hyme")
    fun getHymn():Flowable<List<Hyme>>

    @Query("Select * from Hyme where number=:num")
    fun getHymnByNo(num:Int):Single<Hyme>
}