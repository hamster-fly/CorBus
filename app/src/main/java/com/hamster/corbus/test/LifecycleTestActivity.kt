package com.hamster.corbus.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.hamster.corbus.R
import com.hamster.core.api.post
import com.hamster.core.api.receive

class LifecycleTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lifecycle_test)

        receive<String>("activityLife",{
            setContextLifecycle(this@LifecycleTestActivity)
        }){
            Log.d("LifecycleTestActivity","获取到数据-->${it}")
        }
    }

    override fun onResume() {
        super.onResume()
        post("activityLife","页面OnResume发送的数据")
    }

    override fun onDestroy() {
        super.onDestroy()
        post("activityLife","页面销毁,发送的数据")
    }
}