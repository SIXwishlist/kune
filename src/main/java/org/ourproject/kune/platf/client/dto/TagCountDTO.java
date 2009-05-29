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
 */
package org.ourproject.kune.platf.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class TagCountDTO implements IsSerializable {
    private String name;
    // FIXME: try to use Integer
    private Long count;

    public TagCountDTO() {
        this(null, null);
    }

    public TagCountDTO(final String name, final Long count) {
        this.name = name;
        this.count = count;
    }

    public Long getCount() {
        return count;
    }

    public String getName() {
        return name;
    }

    public void setCount(final Long count) {
        this.count = count;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name + "(" + count + ")";
    }

}
