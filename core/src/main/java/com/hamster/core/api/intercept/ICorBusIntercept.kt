package com.hamster.core.api.intercept

import com.hamster.core.api.intercept.bean.CorBusBean

/**
 * 自定义拦截器
 */
interface ICorBusIntercept {

    suspend fun intercept(chain: ICorBusChain): CorBusBean<*>

    interface ICorBusChain{

        suspend fun process(chainBean: CorBusBean<*>) : CorBusBean<*>

        fun <T> getChainBean(): CorBusBean<T>
        fun <R> newChainBean(): CorBusBean<R>
    }
}
