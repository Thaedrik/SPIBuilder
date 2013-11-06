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

import java.util.Map;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;

/**
 * {@link IncrementalProjectBuilder ProjectBuilder} for Java projects for which we use
 * the <em>Service Provider API</em>.
 * 
 * @author Thaedrik <thaedrik@gmail.com>
 */
public class IncrementalSPIBuilder extends IncrementalProjectBuilder {

	@Override
	protected IProject[] build(int kind, Map<String, String> args, IProgressMonitor monitor) throws CoreException {
		final IProject project = getProject();
		final IPath path = new Path("/META-INF"); //$NON-NLS-1$
		final IResource metaInf = project.findMember(path);
		if (metaInf != null) {
			StringBuilder copyPath = new StringBuilder();
			copyPath.append('/');
			copyPath.append(project.getName());
			final String binMetaInf = "/bin/META-INF"; //$NON-NLS-1$
			copyPath.append(binMetaInf);
			IResource copy = project.findMember(new Path(binMetaInf));
			if (copy != null && copy.exists()) {
				copy.delete(true, null);
			}
			((IFolder) metaInf).copy(new Path(copyPath.toString()), true, null);
		}
		return null;
	}

}
