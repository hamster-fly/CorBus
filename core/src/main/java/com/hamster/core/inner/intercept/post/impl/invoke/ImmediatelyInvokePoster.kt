package com.hamster.core.inner.intercept.post.impl.invoke

import com.hamster.core.inner.debug.CorBusDebug
import com.hamster.core.inner.intercept.post.base.IWaitPoster
import com.hamster.core.inner.manager.ReceiverManager
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch


/**
 * 立即执行的poster
 */
internal class ImmediatelyInvokePoster(private val client: IWaitPoster): IWaitPoster by client{

    fun immediatelyInvoke(){
        val job = SupervisorJob().apply {
            invokeOnCompletion {
                CorBusDebug.loe("post key:${poster.key} job has completion.. with error ${it?.message}")
            }
        }
        val crashHandler = CoroutineExceptionHandler{_,e->
            CorBusDebug.loe("post key:${poster.key} job has receiving with error ${e.message}")
        }
        CoroutineScope(job + Dispatchers.Default + crashHandler).launch {
            client.post()
            job.cancel()
        }
    }

}