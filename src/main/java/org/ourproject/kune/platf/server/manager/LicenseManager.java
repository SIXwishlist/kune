
package org.ourproject.kune.platf.server.manager;

import java.util.List;

import org.ourproject.kune.platf.server.domain.License;

public interface LicenseManager {

    List<License> getAll();

    License persist(final License license);

    void setLicenseFinder(final License licenseFinder);

    List<License> getCC();

    List<License> getNotCC();

    License findByShortName(String licenseDef);

}