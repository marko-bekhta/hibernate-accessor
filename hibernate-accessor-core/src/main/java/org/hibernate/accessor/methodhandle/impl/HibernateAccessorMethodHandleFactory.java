package org.hibernate.accessor.methodhandle.impl;

import org.hibernate.accessor.HibernateAccessorFactory;
import org.hibernate.accessor.HibernateAccessorInstantiator;
import org.hibernate.accessor.HibernateAccessorValueReader;
import org.hibernate.accessor.HibernateAccessorValueWriter;
import org.hibernate.accessor.logging.impl.CoreLog;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class HibernateAccessorMethodHandleFactory implements HibernateAccessorFactory {

    private final MethodHandles.Lookup lookup;

    public HibernateAccessorMethodHandleFactory(MethodHandles.Lookup lookup) {
        this.lookup = lookup;
    }

    @Override
    public <T> HibernateAccessorInstantiator<T> instantiator(Constructor<T> constructor) {
        try {
            return new HibernateAccessorMethodHandleInstantiator<>(lookup.unreflectConstructor(constructor));
        } catch (IllegalAccessException e) {
            throw CoreLog.INSTANCE.errorCreatingHandle(constructor, e, e.getMessage());
        }
    }

    @Override
    public HibernateAccessorValueReader<?> valueReader(Field field) {
        try {
            return new HibernateAccessorMethodHandleFieldValueReader<>(lookup.unreflectGetter(field));
        } catch (IllegalAccessException e) {
            throw CoreLog.INSTANCE.errorCreatingHandle(field, e, e.getMessage());
        }
    }

    @Override
    public HibernateAccessorValueReader<?> valueReader(Method method) {
        try {
            return new HibernateAccessorMethodHandleMethodValueReader<>(lookup.unreflect(method));
        } catch (IllegalAccessException e) {
            throw CoreLog.INSTANCE.errorCreatingHandle(method, e, e.getMessage());
        }
    }

    @Override
    public HibernateAccessorValueWriter<?> valueWriter(Field field) {
        try {
            return new HibernateAccessorMethodHandleFieldValueWriter<>(lookup.unreflectSetter(field));
        } catch (IllegalAccessException e) {
            throw CoreLog.INSTANCE.errorCreatingHandle(field, e, e.getMessage());
        }
    }

    @Override
    public HibernateAccessorValueWriter<?> valueWriter(Method setter) {
        try {
            return new HibernateAccessorMethodHandleMethodValueWriter<>(lookup.unreflect(setter));
        } catch (IllegalAccessException e) {
            throw CoreLog.INSTANCE.errorCreatingHandle(setter, e, e.getMessage());
        }
    }
}
