package org.hibernate.accessor.methodhandle.impl;

import org.hibernate.accessor.HibernateAccessorValueWriter;
import org.hibernate.accessor.logging.impl.CoreLog;

import java.lang.invoke.MethodHandle;

public class HibernateAccessorMethodHandleFieldValueWriter<T> implements HibernateAccessorValueWriter<T> {
    private final MethodHandle setter;

    public HibernateAccessorMethodHandleFieldValueWriter(MethodHandle setter) {
        this.setter = setter;
    }

    @Override
    public void set(Object instance, T value) {
        try {
            setter.invoke(instance, value);
        } catch (Throwable t) {
            throw CoreLog.INSTANCE.errorInvokingHandle(setter, String.valueOf(instance), t, t.getMessage());
        }
    }
}
