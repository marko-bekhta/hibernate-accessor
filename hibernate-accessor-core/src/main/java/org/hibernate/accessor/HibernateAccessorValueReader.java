package org.hibernate.accessor;

public interface HibernateAccessorValueReader<T> {
    T get(Object instance);
}
