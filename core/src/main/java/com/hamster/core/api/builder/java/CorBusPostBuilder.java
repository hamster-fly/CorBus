package com.hamster.core.api.builder.java;

import com.hamster.core.api.builder.callback.PostProgressCallback;

public class CorBusPostBuilder {
    public PostProgressCallback progressCallback; // 进度回调

    // 同时发送同一个有序与无序消息，请不要开启粘性消息
    public boolean isStick; // 是否粘性
    public boolean isOrderly; // 是否有序


    private CorBusPostBuilder(CorBusPostBuilder.Builder builder) {
        this.progressCallback = builder.progressCallback;
        this.isStick = builder.isStick;
        this.isOrderly = builder.isOrderly;
    }

    public static class Builder {

        private PostProgressCallback progressCallback = null; // 进度回调
        // 同时发送同一个有序与无序消息，请不要开启粘性消息
        private boolean isStick = false; // 是否粘性
        private boolean isOrderly = true; // 是否有序

        public Builder setProgressCallback(PostProgressCallback progressCallback) {
            this.progressCallback = progressCallback;
            return this;
        }

        public Builder setStick(boolean stick) {
            isStick = stick;
            return this;
        }

        public Builder setOrderly(boolean orderly) {
            isOrderly = orderly;
            return this;
        }

        public CorBusPostBuilder build() {
            return new CorBusPostBuilder(this);
        }
    }
}
