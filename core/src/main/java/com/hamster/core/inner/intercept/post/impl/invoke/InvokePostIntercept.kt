package com.hamster.core.inner.intercept.post.impl.invoke

import com.hamster.core.inner.intercept.post.protol.IPostIntercept
import com.hamster.core.inner.manager.PostWaitManager

/**
 * 执行发送消息
 */
internal class InvokePostIntercept(private val isOrderly: Boolean) : IPostIntercept {

    override fun intercept(chain: IPostIntercept.IPostChain) {
        val poster = chain.getPoster()
        if(isOrderly){
            PostWaitManager.addLast(poster)
        }else{
            ImmediatelyInvokePoster(poster).immediatelyInvoke()
        }
    }

}