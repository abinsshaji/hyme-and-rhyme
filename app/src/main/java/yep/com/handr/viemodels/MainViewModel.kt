package yep.com.handr.viemodels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.database.sqlite.SQLiteDatabase
import io.reactivex.Flowable
import io.reactivex.Single
import yep.com.handr.room.Hyme
import yep.com.handr.room.Rhyme
import yep.com.handr.utils.Utils

/**
 * Created by Abins Shaji on 09/04/18.
 */
class MainViewModel(application: Application): AndroidViewModel(application) {

    fun insertRhymeDb(rhyme: Rhyme)
    {

        return Utils.getDb(getApplication())?.rhymeDao()!!.inSertRhyme(rhyme)

    }
    fun insertHymeDb(hyme: Hyme)
    {

        return Utils.getDb(getApplication())?.hymDao()!!.insertHymn(hyme)

    }

    fun getAllRhymeDb():io.reactivex.Flowable<List<Rhyme>>?
    {
        return Utils.getDb(getApplication())?.rhymeDao()?.getRhyme()
    }
    fun getAllHymeDb():io.reactivex.Flowable<List<Hyme>>?
    {
        return Utils.getDb(getApplication())?.hymDao()?.getHymn()
    }


    fun getSingleRhyme(num:Int):Single<Rhyme>?
    {
        return Utils.getDb(getApplication())?.rhymeDao()?.getRhymeByNo(num)
    }

    fun getSingleHyme(num:Int):Single<Hyme>?
    {
        return Utils.getDb(getApplication())?.hymDao()?.getHymnByNo(num)
    }

    fun insertDb(db:SQLiteDatabase)
    {
       // return Utils.getDb(getApplication())?.rhymeDao()!!.insertDb(db)
    }
}