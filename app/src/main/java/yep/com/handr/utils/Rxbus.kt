package yep.com.handr.utils

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import java.util.*

/**
 * Created by Abins Shaji on 11/04/18.
 */
object Rxbus {
private val publisher=PublishSubject.create<Any>()

    fun send(event:Any)
    {
        publisher.onNext(event)
    }
   fun listen():Observable<Any>
   {
       return publisher
   }
}