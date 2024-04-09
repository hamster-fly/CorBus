package com.hamster.core.inner.intercept.receive.impl.lifecycle

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.hamster.core.inner.intercept.receive.base.IReceiver
import com.hamster.core.inner.intercept.receive.base.LifecycleActivity
import com.hamster.core.inner.intercept.receive.base.LifecycleApplication
import com.hamster.core.inner.intercept.receive.base.LifecycleCustom
import com.hamster.core.inner.intercept.receive.base.LifecycleFragment
import com.hamster.core.inner.intercept.receive.base.LifecycleType
import com.hamster.core.inner.utils.GlobalAppLifecycle

/**
 * 生命周期包裹的聆听者代理
 * 负责处理事件的回收操作
 */
internal class ReceiverLifeProxy<T>(
    private val client: IReceiver<T>,
    private val lifecycleType: LifecycleType,
) : DefaultLifecycleObserver, IReceiver<T> by client {
    private var lifecycle : Lifecycle? = null
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        unregister()
        lifecycle?.removeObserver(this)
        super.onDestroy(owner)
    }

    override fun register() {
        client.register()
    }

    override fun unregister() {
        client.unregister()
    }

    override fun prepareListening() {
        when (lifecycleType) {
            is LifecycleCustom -> {
                register()
                lifecycleType.lifecycle.addObserver(this)
                lifecycle = lifecycleType.lifecycle
            }
            is LifecycleFragment -> {
                register()
                lifecycleType.fragment.lifecycle.addObserver(this)
                lifecycle = lifecycleType.fragment.lifecycle
            }
            is LifecycleActivity -> {
                // 记录activity WeakReference
                // 如果注册前就被回收了，就不注册了
                lifecycleType.activityRef.get()?.let {
                    register()
                    lifecycle = GlobalAppLifecycle.registry(it).lifecycle
                    GlobalAppLifecycle.registry(it).lifecycle.addObserver(this)
                }
            }
            is LifecycleApplication -> {
                // 全局不回收
                register()
            }
        }
        client.prepareListening()
    }

}