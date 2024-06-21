package com.hamster.core.api.builder

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.hamster.core.api.builder.control.IReceiverControl
import com.hamster.core.api.configure.DispatchThread
import com.hamster.core.api.intercept.ICorBusIntercept
import com.hamster.core.api.java.callback.CorBusCallback
import com.hamster.core.inner.debug.CorBusDebug
import com.hamster.core.inner.intercept.receive.ListeningChain
import com.hamster.core.inner.intercept.receive.base.Receiver
import com.hamster.core.inner.intercept.receive.impl.coroutine.CoroutineReceiverIntercept
import com.hamster.core.inner.intercept.receive.impl.invoke.InvokeListeningIntercept
import com.hamster.core.inner.intercept.receive.impl.lifecycle.LifecycleReceiverIntercept
import com.hamster.core.inner.intercept.receive.impl.stick.StickReceiverIntercept
import com.hamster.core.inner.intercept.receive.protol.IReceiveIntercept
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlin.coroutines.coroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * 接收构建者
 */
class ReceiveBuilder<T>{
    // 接收消息&拦截器的执行线程
    private var onThread: DispatchThread = DispatchThread.DEFAULT
    // 注册函数的生命周期
    private var lifecycle: Lifecycle? = null
    // 注册函数的生命周期为context的周期
    private var context: Context? = null
    // 注册函数的生命周期
    private var fragment: Fragment? = null
    // 自动取消注册的条件，执行结果返回true为取消注册
    private var autoCancel: ((T) -> Boolean)? = null
    // 自定义拦截器，在接收消息时执行
    private var intercepts: List<ICorBusIntercept>? = null

    fun setReceiveThread(thread: DispatchThread): ReceiveBuilder<T> = apply{ this.onThread = thread }
    fun setCustomLifecycle(lifecycle: Lifecycle?): ReceiveBuilder<T> = apply{ this.lifecycle = lifecycle }
    fun setContextLifecycle(context: Context?): ReceiveBuilder<T> = apply{ this.context = context }
    fun setAutoCancel(autoCancel: ((T) -> Boolean)?) = apply{ this.autoCancel = autoCancel }
    fun setIntercepts(intercepts: List<ICorBusIntercept>?) = apply{ this.intercepts = intercepts }

    fun receiving(key: String, runBlock: suspend (T) -> Unit) : IReceiverControl{
        val innerIntercepts = mutableListOf<IReceiveIntercept<T>>()
        // 协程包装
        innerIntercepts.add(CoroutineReceiverIntercept())
        // 生命周期处理
        innerIntercepts.add(LifecycleReceiverIntercept(fragment, context, lifecycle))
        // 执行处理器
        innerIntercepts.add(InvokeListeningIntercept())
        // 粘性消息处理
        innerIntercepts.add(StickReceiverIntercept())
        val content = Receiver(key, onThread, runBlock, autoCancel,intercepts)
        return ListeningChain(content, 0, innerIntercepts).call()
    }
}