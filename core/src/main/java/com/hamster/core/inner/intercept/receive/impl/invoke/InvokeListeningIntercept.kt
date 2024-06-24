package com.hamster.core.inner.intercept.receive.impl.invoke

import com.hamster.core.inner.intercept.receive.base.IReceiver
import com.hamster.core.inner.intercept.receive.protol.IReceiveIntercept

/**
 * 执行监听拦截
 */
internal class InvokeListeningIntercept<T>: IReceiveIntercept<T> {

    override fun intercept(chain: IReceiveIntercept.IReceiverChain<T>) : IReceiver<T> {
        val listener = chain.getChainContent()
        // 准备监听了。
        listener.prepareListening()
        return chain.process(chain.getChainContent())
    }
}