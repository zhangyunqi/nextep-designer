/*******************************************************************************
 * Copyright (c) 2011 neXtep Software and contributors.
 * All rights reserved.
 *
 * This file is part of neXtep designer.
 *
 * NeXtep designer is free software: you can redistribute it 
 * and/or modify it under the terms of the GNU General Public 
 * License as published by the Free Software Foundation, either 
 * version 3 of the License, or any later version.
 *
 * NeXtep designer is distributed in the hope that it will be 
 * useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contributors:
 *     neXtep Softwares - initial API and implementation
 *******************************************************************************/
package com.nextep.designer.sqlclient.ui.handlers;

import java.sql.Connection;
import java.sql.SQLException;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;
import com.nextep.datadesigner.exception.ErrorException;
import com.nextep.designer.sqlgen.ui.model.IConnectable;

/**
 * This handler toggles the current auto-commit state of an underlying connection for editors whose
 * input implements the {@link IConnectable} interface and can provide a JDBC connection.
 * 
 * @author Christophe Fondacci
 */
public class ToggleAutoCommitHandler extends AbstractHandler implements IHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final IEditorPart editor = HandlerUtil.getActiveEditor(event);
		final IEditorInput input = editor.getEditorInput();
		if (input instanceof IConnectable) {
			final Connection connection = ((IConnectable) input).getSqlConnection();
			if (connection != null) {
				try {
					final boolean autoCommit = connection.getAutoCommit();
					connection.setAutoCommit(!autoCommit);
				} catch (SQLException e) {
					throw new ErrorException(
							"Cannot toggle auto-commit state for current connection: "
									+ e.getMessage(), e);
				}
			}
		}
		return null;
	}

}
