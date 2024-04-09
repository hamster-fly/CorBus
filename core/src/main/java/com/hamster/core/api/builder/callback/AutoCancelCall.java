package com.hamster.core.api.builder.callback;

public interface AutoCancelCall<T> {
    boolean call(T t);
}
