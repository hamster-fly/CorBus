package com.hamster.core.init

import com.hamster.core.inner.debug.CorBusDebug
import com.hamster.core.inner.debug.ICorBusFactory

class CorBusInit {
    companion object{

        fun debugLog(isOpen: Boolean,customDebug: ICorBusFactory.ICorBusDebug? = null){
            if(isOpen){
                CorBusDebug.debug(ICorBusFactory.DebugMode.LOG,customDebug)
                CorBusDebug.debug(ICorBusFactory.DebugMode.LOG,customDebug)
            }else{
                CorBusDebug.debug(ICorBusFactory.DebugMode.NONE,customDebug)
            }
        }

    }
}