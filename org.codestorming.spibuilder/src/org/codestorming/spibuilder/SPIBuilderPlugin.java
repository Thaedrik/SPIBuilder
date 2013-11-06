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

import org.codestorming.eclipse.util.pde.BundleActivatorWithLog;

/**
 * SPIBuilder plugin activator.
 *
 * @author Thaedrik <thaedrik@gmail.com>
 */
public class SPIBuilderPlugin extends BundleActivatorWithLog {

	@Override
	public String getPluginID() {
		return SPIBuilderPluginConstants.PLUGIN_ID;
	}

}
