package com.hamster.corbus;

import android.util.Log;

import androidx.annotation.NonNull;

import com.hamster.core.api.builder.callback.PostProgressCallback;
import com.hamster.core.api.builder.java.CorBusBuilder;
import com.hamster.core.api.builder.java.CorBusPostBuilder;
import com.hamster.core.api.configure.DispatchThread;
import com.hamster.core.api.java.CorBus;
import com.hamster.core.api.java.callback.CorBusCallback;
import com.hamster.core.init.CorBusJavaInit;
import com.hamster.core.inner.debug.ICorBusFactory;

import java.util.ArrayList;

import kotlinx.coroutines.GlobalScope;


public class Test {

    public static void t() {

//        CorBus.post("key", "value");
//        CorBus.receive("key", (CorBusCallback<String>) value -> {
//            Log.d("tag","receive"+value);
//        });
//        CorBusPostBuilder postBuilder = new CorBusPostBuilder.Builder()
//                .setOrderly(true)
//                .setStick(true)
//                .setProgressCallback(new PostProgressCallback() {
//                    @Override
//                    public void onFail(@NonNull Throwable e) {
//                        super.onFail(e);
//                    }
//
//                    @Override
//                    public void unHandler() {
//                        super.unHandler();
//                    }
//                })
//                .build();
//        CorBus.post("key",postBuilder,"value");

//        CorBusJavaInit.debugLog(true,null);
//
//        CorBusJavaInit.debugLog(true, new ICorBusFactory.ICorBusDebug() {
//            @Override
//            public void lod(@NonNull String msg) {
//
//            }
//
//            @Override
//            public void loe(@NonNull String msg) {
//
//            }
//        });
        CorBusBuilder<String> builder = new CorBusBuilder.Builder<String>()
                .setReceiveThread(DispatchThread.IO)
                .build();
        CorBus.receive("key", builder, value -> {
            Log.d("tag","receive"+value);
            try {
                Thread.sleep(1000);
                throw new NullPointerException("测试");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        CorBus.post("key", "value");
        CorBus.post("key", "value1");
        CorBus.post("key", "value2");
        CorBus.post("key", "value3");
        CorBus.post("key", "value4");
        CorBus.post("key", "value5");
        CorBus.post("key", "value6");
        CorBus.post("key", "value7");
        CorBus.post("key", "value8");
        CorBus.post("key", "value9");
    }
}
