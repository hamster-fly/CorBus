package com.hamster.core.inner.manager

import com.hamster.core.inner.debug.CorBusDebug
import com.hamster.core.inner.intercept.post.base.IWaitPoster
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.util.concurrent.LinkedBlockingDeque

/**
 * 待发送消息管理
 */
internal object PostWaitManager {

    // 待发送的消息，带有准备状态，如果准备好了，在loop中会被发出去，没有准备好，
    private val waitMessagesDeque = LinkedBlockingDeque<IWaitPoster>()
    // 是否正在循环发送消息
    private var isLooping = false

    @Synchronized
    fun addLast(poster: IWaitPoster){
        CorBusDebug.lod("poster hashCode:${poster.hashCode()}==> addLast from the send queue...")
        waitMessagesDeque.addLast(poster)
        loop()
    }

    @Synchronized
    fun addFirst(poster: IWaitPoster) {
        CorBusDebug.lod("poster hashCode:${poster.hashCode()}==> addFirst from the send queue...")
        waitMessagesDeque.addFirst(poster)
        loop()
    }
    /**
     * 循环发送存储事件
     */
    private fun loop() {
        if (isLooping) {
            return
        }
        isLooping = true
        val job = SupervisorJob()
        val crashHandler = CoroutineExceptionHandler { _,e->
            CorBusDebug.loe("Post Queue stop loop , with error ${e.message}")
        }
        CoroutineScope(job + Dispatchers.Default + crashHandler).launch {
            while (true) {
                val message = waitMessagesDeque.poll() // 非阻塞地移除队首元素
                if (message != null) {
                    CorBusDebug.lod("poster hashCode:${message.hashCode()}==> posting...")
                    message.post()
                } else {
                    isLooping = false
                    job.cancel()
                    return@launch
                }
            }
        }
    }

}
