package com.hamster.core.inner.intercept.receive.impl.coroutine

import com.hamster.core.api.configure.DispatchThread
import com.hamster.core.api.intercept.bean.CorBusBean
import com.hamster.core.inner.debug.CorBusDebug
import com.hamster.core.inner.intercept.custom.CorBusChain
import com.hamster.core.inner.intercept.post.base.Poster
import com.hamster.core.inner.intercept.receive.base.IReceiver
import com.hamster.core.inner.manager.ReceiverManager
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import java.util.concurrent.atomic.AtomicBoolean


/**
 * 协程包装
 */
internal class ReceiverCoroutineProxy<T>(private val client: IReceiver<T>) : IReceiver<T> by client {

    private var job: Job? = null
    private var coroutineScope : CoroutineScope? = null
    private val mutex = Mutex()
    private var isRegister = AtomicBoolean(false)


    override fun register() {
        client.register()
        isRegister.set(true)
        ReceiverManager.addReceiver(this)
    }

    override fun unregister() {
        client.unregister()
        isRegister.set(false)
        ReceiverManager.removeListener(this)
        job?.cancel()
    }

    override suspend fun receiving(poster: Poster) {
        if(!isRegister.get()){
            return
        }
        mutex.lock()
        job?.cancel()
        job = Job().apply {
            invokeOnCompletion {
                CorBusDebug.loe("receiver key:${poster.key}==>job is complete with error: ${it?.message}")
            }
        }
        val dispatcher = when(client.onThread){
            DispatchThread.MAIN->Dispatchers.Main
            DispatchThread.IO->Dispatchers.IO
            DispatchThread.DEFAULT->Dispatchers.Default
        }
        val crashHandler = CoroutineExceptionHandler { _,e->
            poster.progressCallback?.onFail(e)
            mutex.unlock()
        }
        coroutineScope = CoroutineScope(dispatcher + job!! + crashHandler)
        coroutineScope!!.launch {
            var corBusBean = CorBusBean(poster.msg, poster.progressCallback, onThread)
            // 执行用户自定义的拦截器
            intercepts?.run {
                corBusBean = CorBusChain.runPostCustomIntercept(corBusBean,intercepts) as CorBusBean<Any>
            }
            val message = corBusBean.msg as T?
            autoCancel?.let {
                // 条件反注册,先判断
                if(it.invoke(message)){
                    CorBusDebug.lod("取消注册")
                    unregister()
                }
            }
            val newJob = Job()
            val coroutineScope = CoroutineScope(dispatcher + newJob)
            coroutineScope.launch{
                launch{
                    CorBusDebug.lod("receiver key:${poster.key}==>complete invoke receiver listen....")
                    listen.invoke(message)
                    newJob.cancel()
                }
            }
            job?.cancel()
            mutex.unlock()
        }
        job?.join()
        CorBusDebug.lod("分发完毕")
    }
}