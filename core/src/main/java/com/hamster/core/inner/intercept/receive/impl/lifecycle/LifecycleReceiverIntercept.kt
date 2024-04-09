package com.hamster.core.inner.intercept.receive.impl.lifecycle

import android.app.Activity
import android.app.Application
import android.app.Service
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.hamster.core.inner.intercept.receive.base.IReceiver
import com.hamster.core.inner.intercept.receive.base.LifecycleActivity
import com.hamster.core.inner.intercept.receive.base.LifecycleApplication
import com.hamster.core.inner.intercept.receive.base.LifecycleCustom
import com.hamster.core.inner.intercept.receive.base.LifecycleFragment
import com.hamster.core.inner.intercept.receive.base.LifecycleType
import com.hamster.core.inner.intercept.receive.protol.IReceiveIntercept
import java.lang.ref.WeakReference

/**
 * 对于执行的监听者进行生命周期包装
 * support fragment 决定不支持，太早了，支持意义不大
 */
internal class LifecycleReceiverIntercept<T>(
    private val fragment: Fragment?,
    private val context: Context?,
    private val lifecycle: Lifecycle?
) : IReceiveIntercept<T> {
    // 什么都没有传，是全局
    private var lifecycleType: LifecycleType = LifecycleApplication()
    override fun intercept(chain: IReceiveIntercept.IReceiverChain<T?>) : IReceiver<T?> {
        if(lifecycle != null){
            lifecycleType = LifecycleCustom(lifecycle)
        }else if(fragment != null){
            lifecycleType = LifecycleFragment(fragment)
        }else if(context != null){
            unpackContext(context)
        }
        val content = chain.getChainContent()
        // 对消息进行包装，等待消息加入消息监听再启动生命周期
        val proxy = ReceiverLifeProxy(content,lifecycleType)
        return chain.process(proxy)
    }

    /**
     * 解包context并进行参数注入
     */
    private fun unpackContext(context: Context){
        when (context) {
            // 这里处理了lifeService，以及非activity等情况
            is LifecycleOwner -> {
                // 能拿到lifecycle，优先判断
                lifecycleType = LifecycleCustom(context.lifecycle)
            }
            is Activity -> {
                // 记录下来，在application的切换中，做移除监听
                lifecycleType = LifecycleActivity(WeakReference(context))
            }
            is Application ->{}
            is Service -> {
                // 服务目前没有办法支持
                throw IllegalArgumentException("service 目前不支持自动释放，请手动释放")
            }
            else -> {
                throw IllegalArgumentException("抱歉，无法识别的Context。")
            }
        }
    }

}