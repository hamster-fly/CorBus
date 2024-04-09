package com.hamster.corbus;

import android.util.Log;

import androidx.annotation.NonNull;

import com.hamster.core.api.builder.callback.PostProgressCallback;
import com.hamster.core.api.builder.java.CorBusPostBuilder;
import com.hamster.core.api.java.CorBus;
import com.hamster.core.api.java.callback.CorBusCallback;
import com.hamster.core.init.CorBusJavaInit;
import com.hamster.core.inner.debug.ICorBusFactory;


public class Test {

    public void t() {

        CorBus.post("key", "value");
        CorBus.receive("key", (CorBusCallback<String>) value -> {
            Log.d("tag","receive"+value);
        });
        CorBusPostBuilder postBuilder = new CorBusPostBuilder.Builder()
                .setOrderly(true)
                .setStick(true)
                .setProgressCallback(new PostProgressCallback() {
                    @Override
                    public void onFail(@NonNull Throwable e) {
                        super.onFail(e);
                    }

                    @Override
                    public void unHandler() {
                        super.unHandler();
                    }
                })
                .build();
        CorBus.post("key",postBuilder,"value");

        CorBusJavaInit.debugLog(true,null);

        CorBusJavaInit.debugLog(true, new ICorBusFactory.ICorBusDebug() {
            @Override
            public void lod(@NonNull String msg) {

            }

            @Override
            public void loe(@NonNull String msg) {

            }
        });
//        CorBus.post("key", "value");
//        CorBusBuilder<String> builder = new CorBusBuilder.Builder<String>()
//                .setAutoCancelCall(s -> false)
//                .setFragment(fragment)
//                .setContextLifecycle(context)
//                .setIntercepts(new ArrayList<>())
//                .setReceiveThread(DispatchThread.IO)
//                .build();
//        CorBus.receive("key", builder, value -> {
//            Log.d("tag","receive"+value);
//        });



    }
}
