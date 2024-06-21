package com.hamster.core.inner.intercept.receive.base

import com.hamster.core.api.configure.DispatchThread
import com.hamster.core.api.intercept.ICorBusIntercept
import com.hamster.core.api.intercept.bean.CorBusBean
import com.hamster.core.inner.debug.CorBusDebug
import com.hamster.core.inner.intercept.custom.CorBusChain
import com.hamster.core.inner.intercept.post.base.Poster
import com.hamster.core.inner.manager.ReceiverManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * 聆听者聆听实现
 */
internal class Receiver<T>(
    override val key: String,
    override val onThread: DispatchThread,
    override val listen: suspend (T) -> Unit,
    override val autoCancel : ((T)->Boolean)?,
    override val intercepts: List<ICorBusIntercept>?,
): IReceiver<T> {
    override fun register() {
        CorBusDebug.lod("receiver key:${key}==> register success!")
    }

    override fun unregister() {
        CorBusDebug.lod("receiver key:${key}==> unregister success!")
    }

    /**
     * 没有什么需要准备的操作
     */
    override fun prepareListening() {
        CorBusDebug.lod("receiver key:${key}==> prepareListening...")
    }

    override suspend fun receiving(poster: Poster) {
        CorBusDebug.lod("receiver key:${poster.key}==> receiving...")
    }

}