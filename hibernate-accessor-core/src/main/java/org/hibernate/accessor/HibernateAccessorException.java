package org.hibernate.accessor;

public class HibernateAccessorException extends RuntimeException {

    public HibernateAccessorException() {
    }

    public HibernateAccessorException(String message) {
        super(message);
    }

    public HibernateAccessorException(String message, Throwable cause) {
        super(message, cause);
    }

    public HibernateAccessorException(Throwable cause) {
        super(cause);
    }
}
