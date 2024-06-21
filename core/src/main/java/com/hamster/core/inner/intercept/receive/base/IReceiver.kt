package com.hamster.core.inner.intercept.receive.base

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.hamster.core.api.configure.DispatchThread
import com.hamster.core.api.intercept.ICorBusIntercept
import com.hamster.core.inner.intercept.post.base.Poster
import java.lang.ref.WeakReference
import java.util.UUID

internal interface IContentReceiver<T>{
    val key: String
    val onThread : DispatchThread
    val listen : suspend (T)->Unit
    val autoCancel : ((T)->Boolean)?
    val intercepts : List<ICorBusIntercept>?
}
internal interface IReceiver<T> : IContentReceiver<T> {
    /**
     * 注册
     */
    fun register()
    /**
     * 反注册
     */
    fun unregister()

    /**
     * 准备监听
     */
    fun prepareListening()


    /**
     * 监听执行中
     */
    suspend fun receiving(poster: Poster)

}

sealed class LifecycleType

internal data class LifecycleCustom(val lifecycle: Lifecycle): LifecycleType()
internal data class LifecycleFragment(val fragment: Fragment): LifecycleType()
internal data class LifecycleActivity(val activityRef: WeakReference<Activity>): LifecycleType()
internal class LifecycleApplication: LifecycleType()

