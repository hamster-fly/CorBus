package com.hamster.core.inner.intercept.post.protol

import com.hamster.core.inner.intercept.post.base.IWaitPoster

/**
 * 消息发送时拦截
 */
internal interface IPostIntercept {
    /**
     * 拦截方法
     */
    fun intercept(chain: IPostChain)
    interface IPostChain {

        fun process(poster: IWaitPoster)

        fun getPoster(): IWaitPoster

    }
}