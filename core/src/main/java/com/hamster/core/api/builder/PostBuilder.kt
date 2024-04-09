package com.hamster.core.api.builder

import com.hamster.core.api.builder.callback.PostProgressCallback
import com.hamster.core.inner.intercept.post.PostChain
import com.hamster.core.inner.intercept.post.base.Poster
import com.hamster.core.inner.intercept.post.impl.invoke.InvokePostIntercept
import com.hamster.core.inner.intercept.post.impl.stick.StickPostIntercept
import com.hamster.core.inner.intercept.post.base.WaitPoster
import com.hamster.core.inner.intercept.receive.base.IReceiver
import com.hamster.core.inner.manager.ReceiverManager
import java.util.Collections.addAll

/**
 * 发送构建者
 */
class PostBuilder internal constructor() {

    private var progressCallback: PostProgressCallback? = null // 进度回调

    // 同时发送同一个有序与无序消息，请不要开启粘性消息
    private var isStick: Boolean = false // 是否粘性
    private var isOrderly: Boolean = true // 是否有序

    fun setStick(isStick: Boolean){
        this.isStick = isStick
    }

    fun setOrderly(isOrderly: Boolean){
        this.isOrderly = isOrderly
    }

    fun setProgressCallback(progressCallback: PostProgressCallback){
        this.progressCallback = progressCallback
    }

    fun posting(key: String, msg: Any?) {
        val innerIntercepts = listOf(
            StickPostIntercept(isStick),
            InvokePostIntercept(isOrderly),
        )
        // 构建Poster对象
        val poster = Poster(key, msg, isStick, isOrderly, progressCallback)
        // 创建receivers为了尽快获取到它的接收者
        val receivers = mutableListOf<IReceiver<*>>().apply{
            ReceiverManager.findReceiver(key)?.let { addAll(it) }
        }
        val waitPoster = WaitPoster(poster,receivers)
        // 构建拦截器处理链
        PostChain(waitPoster, 0, innerIntercepts).call()
    }

}