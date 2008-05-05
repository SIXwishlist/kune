/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
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
 */
package org.ourproject.kune.workspace.client.licensefoot;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.LicenseDTO;
import org.ourproject.kune.platf.client.dto.StateDTO;

public class LicensePresenter implements LicenseComponent {

    private LicenseView view;
    private LicenseDTO license;

    public LicensePresenter() {
    }

    public void init(final LicenseView view) {
        this.view = view;
    }

    public View getView() {
        return view;
    }

    public void setLicense(final StateDTO state) {
        this.license = state.getLicense();
        view.showLicense(state.getGroup().getLongName(), license);
    }

    public void onLicenseClick() {
        view.openWindow(license.getUrl());
    }
}