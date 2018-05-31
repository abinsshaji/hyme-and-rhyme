package yep.com.handr.room

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Abins Shaji on 05/04/18.
 */
@Entity(tableName = "Rhyme")
data class Rhyme(@PrimaryKey
                 @ColumnInfo(name = "number") var number:Int,
                 @ColumnInfo(name = "content") var content:String)


