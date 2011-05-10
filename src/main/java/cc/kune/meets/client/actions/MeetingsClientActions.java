/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 \*/
package cc.kune.meets.client.actions;

import static cc.kune.meets.shared.MeetingsConstants.TYPE_MEETING;
import static cc.kune.meets.shared.MeetingsConstants.TYPE_ROOT;
import cc.kune.core.client.actions.ActionRegistryByType;
import cc.kune.core.client.i18n.I18nUITranslationService;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.gspace.client.actions.AbstractFoldableToolActions;
import cc.kune.gspace.client.actions.ActionGroups;
import cc.kune.gspace.client.actions.ContentViewerOptionsMenu;
import cc.kune.gspace.client.actions.ParticipateInContentBtn;
import cc.kune.gspace.client.actions.RefreshContentMenuItem;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class MeetingsClientActions extends AbstractFoldableToolActions {
  final String[] all = { TYPE_ROOT, TYPE_MEETING };
  final String[] containers = { TYPE_ROOT };
  final String[] containersNoRoot = {};
  final String[] contents = { TYPE_MEETING };

  @Inject
  public MeetingsClientActions(final I18nUITranslationService i18n, final Session session,
      final StateManager stateManager, final ActionRegistryByType registry, final CoreResources res,
      final Provider<NewMeetingsBtn> newMeetingsBtn, final Provider<GoParentFolderBtn> folderGoUp,
      final Provider<OpenMeetingsMenuItem> openContentMenuItem,
      final Provider<DelMeetingsMenuItem> delContentMenuItem,
      final Provider<ContentViewerOptionsMenu> optionsMenuContent,
      final Provider<ParticipateInContentBtn> participateBtn,
      final Provider<RefreshContentMenuItem> refresh) {
    super(session, stateManager, i18n, registry);
    actionsRegistry.addAction(ActionGroups.VIEW, optionsMenuContent, all);
    actionsRegistry.addAction(ActionGroups.VIEW, refresh, all);
    actionsRegistry.addAction(ActionGroups.VIEW, newMeetingsBtn, containers);
    actionsRegistry.addAction(ActionGroups.VIEW, participateBtn, contents);
    actionsRegistry.addAction(ActionGroups.VIEW, folderGoUp, contents);
    actionsRegistry.addAction(ActionGroups.MENUITEM, openContentMenuItem, contents);
    actionsRegistry.addAction(ActionGroups.MENUITEM, openContentMenuItem, containersNoRoot);
    actionsRegistry.addAction(ActionGroups.MENUITEM, delContentMenuItem, contents);
  }

  @Override
  protected void createPostSessionInitActions() {
  }
}
