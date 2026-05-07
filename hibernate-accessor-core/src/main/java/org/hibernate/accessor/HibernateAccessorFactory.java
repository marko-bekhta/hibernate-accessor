package org.hibernate.accessor;

import org.hibernate.accessor.lambda.impl.HibernateAccessorLambdaFactory;
import org.hibernate.accessor.reflection.impl.HibernateAccessorReflectionFactory;

import java.io.Serializable;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public interface HibernateAccessorFactory extends Serializable {

    static HibernateAccessorFactory reflection() {
        return HibernateAccessorReflectionFactory.INSTANCE;
    }

    static HibernateAccessorFactory lambda(MethodHandles.Lookup lookup) {
        return new HibernateAccessorLambdaFactory(lookup);
    }

    <T> HibernateAccessorInstantiator<T> instantiator(Constructor<T> constructor);

    HibernateAccessorValueReader<?> valueReader(Field field);

    HibernateAccessorValueReader<?> valueReader(Method method);

    HibernateAccessorValueWriter valueWriter(Field field);

    HibernateAccessorValueWriter valueWriter(Method setter);
}
