package app.util

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

interface SchedulersProvider {

    fun io(): Scheduler
    fun ui(): Scheduler
    fun computation(): Scheduler
    fun newThread(): Scheduler
}

class AppSchedulersProvider @Inject internal constructor() : SchedulersProvider {
    override fun io(): Scheduler = Schedulers.io()
    override fun ui(): Scheduler = AndroidSchedulers.mainThread()
    override fun computation(): Scheduler = Schedulers.computation()
    override fun newThread() = Schedulers.newThread()
}

abstract class BaseSchedulers : SchedulersProvider{

}
