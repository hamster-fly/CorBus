package com.hamster.core.inner.manager

import com.hamster.core.inner.debug.CorBusDebug
import com.hamster.core.inner.intercept.receive.base.IReceiver
import com.hamster.core.inner.intercept.receive.base.Receiver
import java.util.concurrent.ConcurrentHashMap

/**
 * 监听者任务管理
 */
internal object ReceiverManager {

    // 所有的监听者
    private val receiverMap = ConcurrentHashMap<String, MutableList<IReceiver<*>>?>()

    /**
     * 清空某个key的所有监听
     */
    fun removeAll(key: String) = receiverMap.remove(key)

    /**
     * 清空所有监听
     */
    fun clear() = receiverMap.clear()

    /**
     * 添加监听
     */
    @Synchronized
    fun <T> addReceiver(receiver: IReceiver<T>) {
        CorBusDebug.lod("receiver hashCode:${receiver.hashCode()}==> insert receiverMap...")
        val list = receiverMap.getOrDefault(receiver.key, mutableListOf())
        if(!list!!.contains(receiver)){
            list.add(receiver)
        }
        receiverMap[receiver.key] = list
    }

    /**
     * 查找监听者列表
     */
    fun findReceiver(key: String) = receiverMap[key]

    /**
     * 移除监听
     */
    @Synchronized
    fun <T> removeListener(listener: IReceiver<T>) {
        CorBusDebug.lod("receiver hashCode:${listener.hashCode()}==> remove form receiverMap...")
        val key = listener.key
        receiverMap[key]?.let{
            it.remove(listener)
            if(it.isEmpty()){
                receiverMap.remove(key)
            }
        }
    }
}