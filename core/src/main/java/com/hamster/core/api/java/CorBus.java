package com.hamster.core.api.java;

import com.hamster.core.api.builder.PostBuilder;
import com.hamster.core.api.builder.ReceiveBuilder;
import com.hamster.core.api.builder.control.IReceiverControl;
import com.hamster.core.api.builder.java.CorBusBuilder;
import com.hamster.core.api.builder.java.CorBusPostBuilder;
import com.hamster.core.api.java.callback.CorBusCallback;
import com.hamster.core.inner.utils.KotlinToJavaKt;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;


/**
 * java CorBus对外公开方法
 */
public class CorBus {

    public static void post(String key, CorBusPostBuilder builder, Object message){
        PostBuilder postBuilder = new PostBuilder();
        postBuilder.setOrderly(builder.isOrderly);
        postBuilder.setStick(builder.isStick);
        postBuilder.setProgressCallback(builder.progressCallback);
        postBuilder.posting(key,message);
    }

    public static void post(String key,Object message){
        PostBuilder builder = new PostBuilder();
        builder.posting(key,message);
    }

    public static <T> IReceiverControl receive(String key,CorBusBuilder<T> builder,CorBusCallback<T> callback){
        ReceiveBuilder<T> receiveBuilder = new ReceiveBuilder<>();
        receiveBuilder.setCustomLifecycle(builder.lifecycle);
        receiveBuilder.setIntercepts(builder.intercepts);
        receiveBuilder.setContextLifecycle(builder.context);
        receiveBuilder.setReceiveThread(builder.onThread);
        if(builder.autoCancelCall != null){
            receiveBuilder.setAutoCancel(t -> builder.autoCancelCall.call(t));
        }
        return receiveBuilder.receiving(key, (t, continuation) -> {
            callback.call(t);
            return t;
        });
    }

    public static <T> IReceiverControl receive(String key,CorBusCallback<T> callback){
        ReceiveBuilder<T> receiveBuilder = new ReceiveBuilder<>();
        return receiveBuilder.receiving(key, (t, continuation) -> {
            callback.call(t);
            return t;
        });
    }

}
