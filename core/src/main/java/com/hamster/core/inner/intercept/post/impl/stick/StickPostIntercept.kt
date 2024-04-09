package com.hamster.core.inner.intercept.post.impl.stick

import com.hamster.core.inner.intercept.post.protol.IPostIntercept
import com.hamster.core.inner.manager.StickManager

/**
 * 粘性消息拦截处理
 */
internal class StickPostIntercept(private val isStick: Boolean): IPostIntercept {

    override fun intercept(chain: IPostIntercept.IPostChain) {
        val bean = chain.getPoster()
        if(isStick){
            StickManager.addStick(bean.poster.key,bean.poster.msg)
        }
        chain.process(bean)
    }

}