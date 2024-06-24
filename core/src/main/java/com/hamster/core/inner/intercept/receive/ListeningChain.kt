package com.hamster.core.inner.intercept.receive

import com.hamster.core.api.builder.control.ReceiverControl
import com.hamster.core.inner.intercept.receive.base.IReceiver
import com.hamster.core.inner.intercept.receive.protol.IReceiveIntercept


/**
 * 监听拦截器启动者
 */
internal class ListeningChain<T>(
    private val contentListener: IReceiver<T>,
    private val index: Int,
    private val intercepts: List<IReceiveIntercept<T>>
) : IReceiveIntercept.IReceiverChain<T>{

    private fun createChain(index: Int,contentListener: IReceiver<T>) =
        ListeningChain(contentListener,index, intercepts)

    fun call() = ReceiverControl(process(contentListener))

    override fun process(listener: IReceiver<T>) : IReceiver<T> {
        if(index >= intercepts.size){
            return listener
        }
        return intercepts[index].intercept(createChain(index + 1,listener))
    }

    override fun getChainContent(): IReceiver<T> {
        return contentListener
    }

}
