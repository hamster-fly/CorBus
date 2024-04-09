package com.hamster.corbus.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.hamster.corbus.R
import com.hamster.core.api.configure.DispatchThread
import com.hamster.core.api.post
import com.hamster.core.api.receive

class PostTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_test)

        receive<String>("key", {
            setReceiveThread(DispatchThread.MAIN)
            setCustomLifecycle(lifecycle)
            setAutoCancel { it == "cancel" }
        }) {
            Log.d("PostTestActivity", "1-->获取到数据-->${it}")
        }

        receive<String>("key", {
            setReceiveThread(DispatchThread.IO)
            setCustomLifecycle(lifecycle)
            setAutoCancel { it == "cancel" }
        }) {
            Log.d("PostTestActivity", "2-->获取到数据-->${it}")
        }

        receive<String>("key", {
            setReceiveThread(DispatchThread.DEFAULT)
            setContextLifecycle(this@PostTestActivity)
            setAutoCancel { it == "cancel" }
        }) {
            Log.d("PostTestActivity", "3-->获取到数据-->${it}")
        }
        receive<String>("key"){
            Log.d("tag", "获取到数据-->${it}")
        }
        post("key", "普通消息1")
        post("key", "普通消息2")
        post("key", "cancel")
        post("key", "普通消息3"){
            setStick(true)
        }
    }
}