package com.hamster.core.api.intercept.bean

import com.hamster.core.api.builder.callback.PostProgressCallback
import com.hamster.core.api.configure.DispatchThread

data class CorBusBean<T>(
    var msg: T?,
    val progressCallback: PostProgressCallback?,
    var onThread: DispatchThread,
)


