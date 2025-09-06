package com.wilkinszhang;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

//看过threadlocal源码吗，如果让你设计ThreadLocal，你会怎么设计
public class MyThreadLocal<T> {
    // 线程安全的弱键映射，键为 Thread，值为 T
    private final Map<Thread, T> threadValues =
            Collections.synchronizedMap(new WeakHashMap<>());
    // 获取当前线程的值，没有则设置初始值
    public T get() {
        Thread t = Thread.currentThread();
        T value = threadValues.get(t);
        if (value == null && !threadValues.containsKey(t)) {
            // 未找到值，使用初始值并存储
            value = initialValue();
            threadValues.put(t, value);
        }
        return value;
    }
    public void set(T value) {
        threadValues.put(Thread.currentThread(), value);
    }
    public void remove() {
        threadValues.remove(Thread.currentThread());
    }
    protected T initialValue() {
        return null;
    }
}

