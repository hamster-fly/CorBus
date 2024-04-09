package com.hamster.core.inner.intercept.custom

import com.hamster.core.api.intercept.ICorBusIntercept
import com.hamster.core.api.intercept.bean.CorBusBean

/**
 * 自定义拦截器
 */
internal class CorBusChain(
    private val chainBean: CorBusBean<*>,
    private val index: Int,
    private val intercepts: List<ICorBusIntercept>,
) : ICorBusIntercept.ICorBusChain {

    private fun createChain(index: Int,bean: CorBusBean<*>) =
        CorBusChain(bean, index, intercepts)

    /**
     * 拦截器链执行的位置
     * 默认从第一个开始执行
     */
    suspend fun call() = process(chainBean)


    override suspend fun process(chainBean: CorBusBean<*>): CorBusBean<*> {
        if(index >= intercepts.size){
            return chainBean
        }
        return intercepts[index].intercept(createChain(index + 1,chainBean))
    }

    override fun <T> getChainBean(): CorBusBean<T> {
        return chainBean as CorBusBean<T>
    }

    override fun <R> newChainBean(): CorBusBean<R> {
        return CorBusBean(null,chainBean.progressCallback,chainBean.onThread)
    }


    companion object{
        /**
         * 执行自定义拦截器
         */
        suspend fun runPostCustomIntercept(bean: CorBusBean<*>,intercepts: List<ICorBusIntercept>): CorBusBean<*> {
            val customPostChain = CorBusChain(bean, 0, intercepts)
            // 执行得到自定义拦截器结果
            return customPostChain.call()
        }
    }
}

