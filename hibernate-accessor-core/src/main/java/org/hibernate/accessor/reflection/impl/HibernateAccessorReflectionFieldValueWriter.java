package org.hibernate.accessor.reflection.impl;

import org.hibernate.accessor.HibernateAccessorValueWriter;
import org.hibernate.accessor.logging.impl.CoreLog;

import java.lang.reflect.Field;
import java.util.Objects;

public class HibernateAccessorReflectionFieldValueWriter<T> implements HibernateAccessorValueWriter<T> {

    private final Field field;

    public HibernateAccessorReflectionFieldValueWriter(Field field) {
        this.field = field;
    }

    @Override
    public void set(Object instance, T value) {
        try {
            field.set(instance, value);
        } catch (RuntimeException | IllegalAccessException e) {
            throw CoreLog.INSTANCE.errorInvokingMember(field, Objects.toString(instance), e,
                    e.getMessage());
        }
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + field + "]";
    }

    @Override
    public int hashCode() {
        return field.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !obj.getClass().equals(getClass())) {
            return false;
        }
        HibernateAccessorReflectionFieldValueWriter<?> other = (HibernateAccessorReflectionFieldValueWriter<?>) obj;
        return field.equals(other.field);
    }
}
