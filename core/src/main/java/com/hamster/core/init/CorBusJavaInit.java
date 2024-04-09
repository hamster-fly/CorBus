package com.hamster.core.init;

import com.hamster.core.inner.debug.ICorBusFactory;

public class CorBusJavaInit {

    public static void debugLog(boolean isOpen,ICorBusFactory.ICorBusDebug customDebug){
        CorBusInit.Companion.debugLog(isOpen,customDebug);
    }
}
