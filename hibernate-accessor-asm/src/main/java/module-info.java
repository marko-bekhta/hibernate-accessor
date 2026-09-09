/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright: Hibernate Authors. See AUTHORS.txt.
 */
module org.hibernate.accessor.asm {
	requires org.hibernate.accessor;
	requires org.objectweb.asm;
	requires static org.jboss.logging;

	exports org.hibernate.accessor.asm;
	exports org.hibernate.accessor.asm.spi;
}
