package org.hibernate.accessor;

public interface HibernateAccessorValueWriter<T> {
    void set(Object instance, T value);
}
