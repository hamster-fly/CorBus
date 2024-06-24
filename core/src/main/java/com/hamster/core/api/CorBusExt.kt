package com.hamster.core.api

import com.hamster.core.api.builder.PostBuilder
import com.hamster.core.api.builder.ReceiveBuilder
import com.hamster.core.api.builder.control.IReceiverControl
import com.hamster.core.inner.manager.StickManager


fun post(key: String, msg: Any?, configure: (PostBuilder.() -> Unit)? = null) = PostBuilder().apply {
        configure?.invoke(this)
    }.posting(key, msg)


fun <T> receive(
    key: String,
    optionBuilder: (ReceiveBuilder<T>.() -> Unit)? = null,
    receiveBlock: suspend (T) -> Unit
) : IReceiverControl {
    return ReceiveBuilder<T>().run {
        optionBuilder?.invoke(this)
        receiving(key, receiveBlock)
    }
}

class CorBus{
    companion object{
        fun removeStick(key: String){
            StickManager.removeStick(key)
        }
    }
}
