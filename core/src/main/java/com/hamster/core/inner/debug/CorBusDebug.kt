package com.hamster.core.inner.debug


/**
 * debug工具
 */
internal class CorBusDebug {
    companion object {

        private var debugMode = ICorBusFactory.DebugMode.NONE

        private val debugFactory = DebugFactory()

        private var debugUtils = debugFactory.create(debugMode)

        private var debugView = false

        /**
         * debug 日志开关
         */
        fun debug(mode: ICorBusFactory.DebugMode,customDebug: ICorBusFactory.ICorBusDebug?) {
            debugMode = mode
            debugUtils = debugFactory.create(debugMode,customDebug)
        }

        /**
         * debug View 开关
         */
        fun debugView(open: Boolean){
            debugView = open
        }

        fun lod(msg: String){
            debugUtils?.lod(msg)
        }

        fun loe(msg: String){
            debugUtils?.loe(msg)
        }
    }
}

