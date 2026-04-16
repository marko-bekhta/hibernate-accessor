package org.hibernate.accessor.lambda.impl;

import org.hibernate.accessor.HibernateAccessorFactory;
import org.hibernate.accessor.HibernateAccessorInstantiator;
import org.hibernate.accessor.HibernateAccessorValueReader;
import org.hibernate.accessor.HibernateAccessorValueWriter;
import org.hibernate.accessor.logging.impl.CoreLog;

import java.lang.invoke.CallSite;
import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class HibernateAccessorLambdaFactory implements HibernateAccessorFactory {

    private final MethodHandles.Lookup lookup;

    public HibernateAccessorLambdaFactory(MethodHandles.Lookup lookup) {
        this.lookup = lookup;
    }

    @Override
    public <T> HibernateAccessorInstantiator<T> instantiator(Constructor<T> constructor) {
        return new LambdaInstantiator<>(lookup, constructor);
    }

    @Override
    public HibernateAccessorValueReader<?> valueReader(Field field) {
        try {
            return new LambdaFieldValueReader<>(lookup.unreflectGetter(field));
        } catch (IllegalAccessException e) {
            throw CoreLog.INSTANCE.errorCreatingHandle(field, e, e.getMessage());
        }
    }

    @Override
    public HibernateAccessorValueReader<?> valueReader(Method method) {
        try {
            MethodHandles.Lookup lookup = MethodHandles.privateLookupIn(method.getDeclaringClass(), this.lookup);
            MethodHandle target = lookup.unreflect(method);
            CallSite site = LambdaMetafactory.metafactory(
                    lookup,
                    "get",
                    MethodType.methodType(HibernateAccessorValueReader.class),
                    MethodType.methodType(Object.class, Object.class),
                    target,
                    MethodType.methodType(method.getReturnType(), method.getDeclaringClass())
            );
            return (HibernateAccessorValueReader<?>) site.getTarget().invokeExact();
        } catch (Throwable t) {
            throw new RuntimeException("Failed to create lambda for " + method, t);
        }
    }

    @Override
    public HibernateAccessorValueWriter<?> valueWriter(Field field) {
        try {
            return new LambdaFieldValueWriter<>(lookup.unreflectSetter(field));
        } catch (IllegalAccessException e) {
            throw CoreLog.INSTANCE.errorCreatingHandle(field, e, e.getMessage());
        }
    }

    @Override
    public HibernateAccessorValueWriter<?> valueWriter(Method setter) {
        try {
            MethodHandles.Lookup lookup = MethodHandles.privateLookupIn(setter.getDeclaringClass(), this.lookup);
            MethodHandle target = lookup.unreflect(setter);
            CallSite site = LambdaMetafactory.metafactory(
                    lookup,
                    "set",
                    MethodType.methodType(HibernateAccessorValueWriter.class),
                    MethodType.methodType(void.class, Object.class, Object.class),
                    target,
                    MethodType.methodType(void.class, setter.getDeclaringClass(), setter.getParameterTypes()[0])
            );
            return (HibernateAccessorValueWriter<?>) site.getTarget().invokeExact();
        } catch (Throwable t) {
            throw new RuntimeException("Failed to create lambda for " + setter, t);
        }
    }
}
