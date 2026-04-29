package org.hibernate.accessor.lambda.impl;

import org.hibernate.accessor.HibernateAccessorValueReader;
import org.hibernate.accessor.logging.impl.CoreLog;

import java.lang.invoke.CallSite;
import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.util.function.Function;

public class LambdaFieldValueReader<T> implements HibernateAccessorValueReader<T> {
    private final MethodHandle getter;

    @SuppressWarnings("unchecked")
    public LambdaFieldValueReader(MethodHandle getter) {
        this.getter = getter;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T get(Object instance) {
        try {
            return (T) getter.invoke(instance);
        } catch (Throwable t) {
            if (t instanceof Error) {
                throw (Error) t;
            }
            throw CoreLog.INSTANCE.errorInvokingHandle(getter, String.valueOf(instance), t, t.getMessage());
        }
    }
}
