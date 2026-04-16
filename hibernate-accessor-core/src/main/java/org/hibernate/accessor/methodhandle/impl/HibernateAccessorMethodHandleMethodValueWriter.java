package org.hibernate.accessor.methodhandle.impl;

import org.hibernate.accessor.HibernateAccessorValueWriter;
import org.hibernate.accessor.logging.impl.CoreLog;

import java.lang.invoke.MethodHandle;

public class HibernateAccessorMethodHandleMethodValueWriter<T> implements HibernateAccessorValueWriter<T> {
    private final MethodHandle target;

    public HibernateAccessorMethodHandleMethodValueWriter(MethodHandle target) {
        this.target = target;
    }

    @Override
    public void set(Object instance, T value) {
        try {
            target.invoke(instance, value);
        } catch (Throwable t) {
            throw CoreLog.INSTANCE.errorInvokingHandle(target, String.valueOf(instance), t, t.getMessage());
        }
    }
}
