package com.hamster.core.inner.debug

import android.util.Log


interface ICorBusFactory{
  fun create(mode: DebugMode,customDebug: ICorBusDebug? = null): ICorBusDebug?
    interface ICorBusDebug{
        fun lod(msg: String)

        fun loe(msg: String)
    }
    enum class DebugMode{
        LOG,
        NONE,
        CUSTOM,
    }
}

internal class DebugFactory : ICorBusFactory{

    override fun create(mode: ICorBusFactory.DebugMode,customDebug: ICorBusFactory.ICorBusDebug?): ICorBusFactory.ICorBusDebug? {
        return when(mode){
            ICorBusFactory.DebugMode.LOG->LogDebug()
            ICorBusFactory.DebugMode.CUSTOM->customDebug
            ICorBusFactory.DebugMode.NONE->null
        }
    }

}

internal class LogDebug: ICorBusFactory.ICorBusDebug {
    override fun lod(msg: String) {
        Log.d("CorBus-Debug",msg)
    }

    override fun loe(msg: String) {
        Log.e("CorBus-Debug","<****************************(start)***********************************>\n${msg}\n<*****************************(end)************************************>")
    }
}