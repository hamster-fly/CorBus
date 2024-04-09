package com.hamster.core.api.builder.java;


import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;

import com.hamster.core.api.builder.callback.AutoCancelCall;
import com.hamster.core.api.configure.DispatchThread;
import com.hamster.core.api.intercept.ICorBusIntercept;

import java.util.List;

public class CorBusBuilder<T> {
    public DispatchThread onThread;
    public Lifecycle lifecycle;
    public Context context;
    public Fragment fragment;
    public List<ICorBusIntercept> intercepts;
    public AutoCancelCall<T> autoCancelCall;

    private CorBusBuilder(Builder<T> builder) {
        this.onThread = builder.onThread;
        this.lifecycle = builder.lifecycle;
        this.context = builder.context;
        this.fragment = builder.fragment;
        this.intercepts = builder.intercepts;
    }

    public static class Builder<T> {
        private DispatchThread onThread = DispatchThread.DEFAULT;
        private Lifecycle lifecycle = null;
        private Context context = null;
        private Fragment fragment = null;
        private List<ICorBusIntercept> intercepts = null;
        private AutoCancelCall<T> autoCancelCall = null;

        public Builder<T> setReceiveThread(DispatchThread thread) {
            this.onThread = thread;
            return this;
        }

        public Builder<T> setCustomLifecycle(Lifecycle lifecycle) {
            this.lifecycle = lifecycle;
            return this;
        }

        public Builder<T> setContextLifecycle(Context context) {
            this.context = context;
            return this;
        }

        public Builder<T> setIntercepts(List<ICorBusIntercept> intercepts) {
            this.intercepts = intercepts;
            return this;
        }

        public Builder<T> setFragment(Fragment fragment) {
            this.fragment = fragment;
            return this;
        }

        public Builder<T> setAutoCancelCall(AutoCancelCall<T> autoCancelCall) {
            this.autoCancelCall = autoCancelCall;
            return this;
        }

        public CorBusBuilder<T> build() {
            return new CorBusBuilder<>(this);
        }
    }
}
