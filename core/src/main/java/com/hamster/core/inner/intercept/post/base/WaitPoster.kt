package com.hamster.core.inner.intercept.post.base

import com.hamster.core.inner.debug.CorBusDebug
import com.hamster.core.inner.intercept.receive.base.IReceiver


internal interface IWaitPoster {
    val poster: Poster
    val receiversList: MutableList<IReceiver<*>>?
    suspend fun post()
}

/**
 * 待发送消息载体
 */
internal class WaitPoster(
    override val poster: Poster,
    override val receiversList: MutableList<IReceiver<*>>?
) : IWaitPoster {
    override suspend fun post() {
        if(receiversList.isNullOrEmpty()){
            CorBusDebug.lod("post key:${poster.key}==> not receiving...")
            poster.progressCallback?.unHandler()
        }else{
            receiversList.forEach {
                // 执行receive的发送
                it.receiving(poster)
            }
        }
    }

}