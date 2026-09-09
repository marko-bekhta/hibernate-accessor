/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright: Hibernate Authors. See AUTHORS.txt.
 */
module org.hibernate.accessor {
	requires static org.jboss.logging;
	requires static org.jboss.logging.annotations;

	exports org.hibernate.accessor;
	exports org.hibernate.accessor.spi;
}
