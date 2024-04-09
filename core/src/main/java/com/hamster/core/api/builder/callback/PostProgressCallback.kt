package com.hamster.core.api.builder.callback

/**
 * 发送事件进度回调
 */
abstract class PostProgressCallback{

    /**
     * 发送失败，在发送途中遇到问题
     */
    open fun onFail(e: Throwable){}

    /**
     * 没有接收者
     */
    open fun unHandler(){}
}