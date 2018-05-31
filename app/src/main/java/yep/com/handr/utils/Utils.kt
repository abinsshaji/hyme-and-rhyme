package yep.com.handr.utils

import android.arch.persistence.room.Room
import android.content.Context
import io.reactivex.Observable
import io.reactivex.Single
import yep.com.handr.room.DbSinglton

/**
 * Created by Abins Shaji on 09/04/18.
 */
object Utils
{
    fun getDb(context: Context):DbSinglton?
    {

            return DbSinglton.getInstance(context)


    }
}