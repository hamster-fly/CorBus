package com.hamster.core.inner.intercept.receive.impl.coroutine

import com.hamster.core.inner.intercept.receive.base.IReceiver
import com.hamster.core.inner.intercept.receive.protol.IReceiveIntercept

/**
 * 协程包装拦截器
 */
internal class CoroutineReceiverIntercept<T> : IReceiveIntercept<T> {

    override fun intercept(chain: IReceiveIntercept.IReceiverChain<T?>) : IReceiver<T?> {
        val content = chain.getChainContent()
        val proxy = ReceiverCoroutineProxy(content)
        return chain.process(proxy)
    }

}