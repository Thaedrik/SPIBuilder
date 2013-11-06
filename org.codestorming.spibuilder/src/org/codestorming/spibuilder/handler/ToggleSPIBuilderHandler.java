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
package org.codestorming.spibuilder.handler;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Named;

import org.codestorming.spibuilder.M;
import org.codestorming.spibuilder.SPIBuilderPlugin;
import org.codestorming.spibuilder.job.ToggleSpiBuilderOperation;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

/**
 * Handler for toggling SPI Builder on Java projects.
 * 
 * @author Thaedrik <thaedrik@gmail.com>
 */
public class ToggleSPIBuilderHandler {

	@Execute
	public void execute(@Named(IServiceConstants.ACTIVE_SELECTION) ISelection selection,
			@Named(IServiceConstants.ACTIVE_SHELL) Shell shell) {
		final List<IProject> projects = new ArrayList<IProject>();
		final Iterator<?> iter = ((IStructuredSelection) selection).iterator();
		while (iter.hasNext()) {
			final IProject element = getProject(iter.next());
			if (element != null) {
				projects.add(element);
			}
		}
		final ToggleSpiBuilderOperation toggleOperation = new ToggleSpiBuilderOperation(projects);
		try {
			PlatformUI.getWorkbench().getProgressService().run(true, false, toggleOperation);
		} catch (InvocationTargetException e) {
			SPIBuilderPlugin.log(e);
		} catch (InterruptedException e) {
			// Should not be thrown (cancelable is false)
			SPIBuilderPlugin.log(e);
		}
		if (toggleOperation.getFailedProject() != null) {
			failMessage(toggleOperation.getFailedProject(), shell);
		}
	}

	@CanExecute
	public boolean canExecute(@Named(IServiceConstants.ACTIVE_SELECTION) ISelection selection) {
		if (selection instanceof IStructuredSelection && !selection.isEmpty()) {
			final Iterator<?> iter = ((IStructuredSelection) selection).iterator();
			while (iter.hasNext()) {
				final Object element = iter.next();
				if (getProject(element) != null) {
					return true;
				}
			}
		}
		return false;
	}

	private static void failMessage(IProject project, Shell shell) {
		MessageBox msgBox = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
		msgBox.setText(M.err_toogleSpiBuilder);
		msgBox.setMessage(M.bind(M.err_toogleFailedOnProject, project.getName()));
	}
	
	private static IProject getProject(Object element) {
		if (element instanceof IProject) {
			return (IProject) element;
		}// else
		if (element instanceof IAdaptable) {
			return (IProject) ((IAdaptable) element).getAdapter(IProject.class);
		}// else
		return null;
	}
}
