package com.hamster.core.api.builder.control

import com.hamster.core.inner.intercept.receive.base.IReceiver

/**
 * 控制器具体实现
 */
internal class ReceiverControl(private val receiver: IReceiver<*>): IReceiverControl {
    override fun unRegistry() {
        receiver.unregister()
    }


}