package com.hamster.core.inner.intercept.receive.impl.stick

import com.hamster.core.inner.intercept.post.base.Poster
import com.hamster.core.inner.intercept.post.base.WaitPoster
import com.hamster.core.inner.intercept.receive.base.IReceiver
import com.hamster.core.inner.intercept.receive.protol.IReceiveIntercept
import com.hamster.core.inner.manager.PostWaitManager
import com.hamster.core.inner.manager.StickManager

/**
 * 粘性消息拦截
 */
internal class StickReceiverIntercept<T>: IReceiveIntercept<T> {

    override fun intercept(chain: IReceiveIntercept.IReceiverChain<T>) : IReceiver<T> {
        val content = chain.getChainContent()
        val stick = StickManager.findStick(content.key)
        if(stick != null){
            val poster = Poster(content.key,stick,
                isStick = false,
                isOrderly = true,
                progressCallback = null
            )
            val waitPoster = WaitPoster(poster, mutableListOf(content))
            PostWaitManager.addFirst(waitPoster)
        }
        return chain.process(content)
    }

}