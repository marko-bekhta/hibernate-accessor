package org.hibernate.accessor.lambda.impl;

import org.hibernate.accessor.HibernateAccessorInstantiator;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;

public class LambdaInstantiator<T> implements HibernateAccessorInstantiator<T> {
    private final MethodHandle handle;

    public LambdaInstantiator(MethodHandles.Lookup lookup, Constructor<T> constructor) {
        try {
            this.handle = lookup.unreflectConstructor(constructor)
                    .asSpreader(Object[].class, constructor.getParameterCount());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public T create(Object... args) {
        try {
            return (T) handle.invoke(args);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }
}
