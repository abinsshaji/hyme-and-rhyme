package yep.com.handr.room

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.migration.Migration
import android.content.Context

/**
 * Created by Abins Shaji on 05/04/18.
 */
@Database(entities = [(Rhyme::class),(Hyme::class)],version = 3)
abstract class DbSinglton : RoomDatabase(){

     abstract fun rhymeDao():RhymeDao
    abstract fun hymDao():HymeDao

companion object {
    private var INSTANCE : DbSinglton? =null

    val MIGRATION_1_2: Migration = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("CREATE TABLE Hyme (number INTEGER NOT NULL, content TEXT NOT NULL, PRIMARY KEY(number))")
        }
    }
    fun getInstance(context:Context):DbSinglton?
    {
        if (INSTANCE==null)
        {
            synchronized(DbSinglton::class){
                INSTANCE=Room.databaseBuilder(context.applicationContext,DbSinglton::class.java,"info.db")
                        .addMigrations(MIGRATION_1_2)
                        .build()
            }

        }
        return INSTANCE

    }


}



}