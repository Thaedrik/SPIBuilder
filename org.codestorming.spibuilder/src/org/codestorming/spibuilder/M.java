/****************************************************************************
 * Copyright (c) 2013 Codestorming.org
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Codestorming - initial implementation and API
 ****************************************************************************/
package org.codestorming.spibuilder;

import org.eclipse.osgi.util.NLS;

/**
 * SPIBuilder plugin's internationalization bundle.
 * 
 * @author Thaedrik <thaedrik@gmail.com>
 */
public class M extends NLS {
	private static final String BUNDLE_NAME = "org.codestorming.spibuilder.messages"; //$NON-NLS-1$
	public static String err_toogleFailedOnProject;
	public static String err_toogleSpiBuilder;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, M.class);
	}

	private M() {}
}
