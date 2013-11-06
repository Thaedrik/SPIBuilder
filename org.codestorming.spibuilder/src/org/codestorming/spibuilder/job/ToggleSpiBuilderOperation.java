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
package org.codestorming.spibuilder.job;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.codestorming.eclipse.util.EclipseUtil;
import org.codestorming.spibuilder.SPIBuilderPluginConstants;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

/**
 * Operation for toggling the SPI Builder on Java projects.
 * <p>
 * This operation is transactional, if the toggle failed on one of the given projects, the
 * previous projects will be restored.
 * 
 * @author Thaedrik <thaedrik@gmail.com>
 */
public class ToggleSpiBuilderOperation extends WorkspaceModifyOperation {

	private Collection<IProject> projects;

	private IProject failedProject;

	/**
	 * Creates a new {@code ToogleSpiBuilderOperation}.
	 * <p>
	 * Some kind of unchecked exception will be thrown if the given iterable is
	 * {@code null}.
	 * 
	 * @param projects Projects for which to toggle the SPIBuilder.
	 */
	public ToggleSpiBuilderOperation(Collection<IProject> projects) {
		Assert.isNotNull(projects);
		this.projects = projects;
	}

	/**
	 * Returns the project on which the toggle have failed, or {@code null} if everything
	 * went well.
	 * 
	 * @return the project on which the toggle have failed, or {@code null}.
	 */
	public IProject getFailedProject() {
		return failedProject;
	}

	@Override
	protected void execute(IProgressMonitor monitor) throws CoreException, InvocationTargetException,
			InterruptedException {
		final SubMonitor submonitor = SubMonitor.convert(monitor, "Toggling SPI Builder", projects.size());
		final List<IProject> projectsToggled = new ArrayList<IProject>();
		try {
			for (final IProject project : projects) {
				submonitor.subTask(project.getName());
				failedProject = project;
				if (project.hasNature("org.eclipse.jdt.core.javanature")) { //$NON-NLS-1$
					toggleSpiBuilder(project);
					projectsToggled.add(project);
					submonitor.worked(1);
				}
			}
			// Loop finished : no errors
			failedProject = null;
		} catch (CoreException e) {
			// Re-toggle modified projects
			for (final IProject project : projectsToggled) {
				toggleSpiBuilder(project);
			}
			throw e;
		}
	}

	/**
	 * Toggle the SpiBuilder on the given project.
	 * 
	 * @param project
	 * @throws CoreException
	 */
	private void toggleSpiBuilder(IProject project) throws CoreException {
		if (EclipseUtil.projectHasBuilder(project, SPIBuilderPluginConstants.SPI_BUILDER_ID)) {
			EclipseUtil.removeProjectBuilder(project, SPIBuilderPluginConstants.SPI_BUILDER_ID);
		} else {
			EclipseUtil.addProjectBuilder(project, SPIBuilderPluginConstants.SPI_BUILDER_ID,
					"org.eclipse.jdt.core.javabuilder"); //$NON-NLS-1$
		}
	}

}
