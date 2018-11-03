package mytaxi.challenge.code.com.org.simplecodechallengemytaxi.model.db.threadManager

import android.os.Handler
import android.os.HandlerThread

class DbWorkerThread(threadName: String): HandlerThread(threadName){
    private lateinit var mWorkerThreadHandler: Handler

    override fun onLooperPrepared() {
        super.onLooperPrepared()
        mWorkerThreadHandler = Handler(looper)
    }

    fun postTask(task: Runnable){
        mWorkerThreadHandler.post(task)
    }

    fun prepareHandler(){
        mWorkerThreadHandler = Handler(looper)
    }

}