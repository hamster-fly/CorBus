# 欢迎使用 CorBus 协程总线

[English document]()
CorBus，是一款基于 `kotlin`、`Coroutine`，为android项目提供的异步总线。

## 功能点

- 任意协程（发送/接收）消息
- 支持发送有序消息、无序消息
- 支持发送异步粘性消息
- 支持接收器自动注册与反注册
- 支持接收器条件反注册

## 如何使用

```
kts:
dependencies {}
groovy:
dependencies{  } 
```


## API示例

### Kotlin用法

---

**发送消息**

```kotlin
fun postTest(){
    //简易用法
    post("key","value")
    // 自定义用法
    post("key", "value"){
        setStick(true) // 设置是否是粘性消息
        setOrderly(false) // 设置是否有序
        setProgressCallback(object : PostProgressCallback() {
            override fun unHandler() {}// 没有接收者回调
            override fun onFail(e: Throwable) {} // 发送失败，过程异常回调
        })
    }
}
```

**接收消息**

```kotlin
fun receiveTest(){
    // 简易用法
    receive<String>("key"){
        Log.d("tag", "获取到数据-->${it}")
    }
    
    // 注册监听
    receive<String>("key", {
        setReceiveThread(DispatchThread.MAIN) // 可选项: 指定线程
        setCustomLifecycle(lifecycle)// 可选项: 设置自定义lifecycle
        setAutoCancel { it == "cancel" } // 可选项: 设置自动反注册条件
        setIntercepts(mutableListOf()) // 可选项: 设置拦截器
        setContextLifecycle(context) //设置自动反注册生命周期
    }) {
        Log.d("tag", "receive->${it}")
    }
}
```

### Java用法

---

**发送消息**

```java
class Test{
    public void test(){
        // 简易用法
        CorBus.post("key", "value"); 
        
        // 自定义用法
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
    }
}
```

**接收消息**

```java
class Test{
    public void test(){
        // 简易用法
        IReceiverControl control = CorBus.receive("key", (CorBusCallback<String>) value -> {
            Log.d("tag","receive"+value);
        });
        
        // 自定义用法
        CorBusBuilder<String> builder = new CorBusBuilder.Builder<String>()
            .setAutoCancelCall(s -> false) // 设置自动反注册条件
            .setFragment(fragment) // 设置fragment反注册生命周期
            .setContextLifecycle(context) // 设置context反注册生命周期
            .setIntercepts(new ArrayList<>()) // 设置拦截器
            .setReceiveThread(DispatchThread.IO) // 设置接收线程（协程）
            .build();
        IReceiverControl control = CorBus.receive("key", builder, value -> {
            Log.d("tag","receive"+value);
        });
    }
}
```

## 重要说明

1. 同一条消息使用有序与无序发送，建议`不要设置stick`。
2. 接收协程作用域包含自定义拦截器，默认的接收协程是`Dispatchers.Default`。
3. 当不指定任何注册&反注册作用域，默认是`全局`。


## DEBUG支持

1. 支持日志打印与自定义日志
2. 支持view查看当前消息发送状况

### Kotlin用法

```kotlin
fun onCreate(){
    // 简易用法 - 打开日志
    CorBusInit.debugLog(true)
    // 自定义日志
    CorBusInit.debugLog(true,object: ICorBusFactory.ICorBusDebug{
        override fun lod(msg: String) {

        }

        override fun loe(msg: String) {

        }
    })
}
```

### Java用法

```java
class Test{
    public void onCreate(){
        // 简易用法 - 打开日志
        CorBusJavaInit.debugLog(true,null);

        // 自定义日志
        CorBusJavaInit.debugLog(true, new ICorBusFactory.ICorBusDebug() {
            @Override
            public void lod(@NonNull String msg) {

            }

            @Override
            public void loe(@NonNull String msg) {

            }
        });
    }
}
```


## 后续计划

1. 多进程支持
2. 处理系统内存回收导致粘性事件失效

