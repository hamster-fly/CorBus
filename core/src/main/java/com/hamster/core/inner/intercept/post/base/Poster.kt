package com.hamster.core.inner.intercept.post.base

import com.hamster.core.api.builder.callback.PostProgressCallback

internal data class Poster(
    val key: String,
    val msg: Any?,
    val isStick: Boolean,
    val isOrderly: Boolean,
    val progressCallback: PostProgressCallback?
)