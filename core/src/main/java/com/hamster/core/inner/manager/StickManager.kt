package com.hamster.core.inner.manager

import java.util.concurrent.ConcurrentHashMap

internal object StickManager {

    private val stickMap = ConcurrentHashMap<String,Any?>()

    fun addStick(key: String,msg: Any?){
        stickMap[key] = msg
    }

    fun findStick(key: String): Any? = stickMap[key]

    fun removeStick(key : String){
        stickMap.remove(key)
    }

}