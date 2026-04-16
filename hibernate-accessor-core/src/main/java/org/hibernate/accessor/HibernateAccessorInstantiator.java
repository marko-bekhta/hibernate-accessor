package org.hibernate.accessor;

public interface HibernateAccessorInstantiator<T> {
    T create(Object... args);
}
