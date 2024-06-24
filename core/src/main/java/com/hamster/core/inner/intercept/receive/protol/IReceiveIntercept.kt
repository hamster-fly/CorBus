package com.hamster.core.inner.intercept.receive.protol

import com.hamster.core.inner.intercept.receive.base.IReceiver

/**
 * 聆听拦截
 */
internal interface IReceiveIntercept<T>{

    fun intercept(chain: IReceiverChain<T>): IReceiver<T>

    interface IReceiverChain<C> {

        fun process(listener: IReceiver<C>): IReceiver<C>

        fun getChainContent(): IReceiver<C>

    }
}