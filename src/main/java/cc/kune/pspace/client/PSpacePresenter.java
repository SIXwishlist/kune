/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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
package cc.kune.pspace.client;

import cc.kune.common.client.actions.ui.IsActionExtensible;
import cc.kune.core.client.init.AppStartEvent;
import cc.kune.core.client.state.StateChangedEvent;
import cc.kune.core.client.state.StateChangedEvent.StateChangedHandler;
import cc.kune.core.shared.domain.ContentStatus;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.GroupListDTO;
import cc.kune.core.shared.dto.StateAbstractDTO;
import cc.kune.core.shared.dto.StateContainerDTO;
import cc.kune.core.shared.dto.StateContentDTO;
import cc.kune.core.shared.dto.StateTokenUtils;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.HasText;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

public class PSpacePresenter extends Presenter<PSpacePresenter.PSpaceView, PSpacePresenter.PSpaceProxy> {

    @ProxyCodeSplit
    public interface PSpaceProxy extends Proxy<PSpacePresenter> {
    }
    public interface PSpaceView extends View {

        IsActionExtensible getActionPanel();

        HasText getDescription();

        HasText getTitle();

        void setContentGotoPublicUrl(String publicUrl);
    }

    private final I18nTranslationService i18n;
    private final StateTokenUtils stateTokenUtils;

    @Inject
    public PSpacePresenter(final EventBus eventBus, final PSpaceView view, final PSpaceProxy proxy,
            final I18nTranslationService i18n, final StateTokenUtils stateTokenUtils) {
        super(eventBus, view, proxy);
        this.i18n = i18n;
        this.stateTokenUtils = stateTokenUtils;
        eventBus.addHandler(AppStartEvent.getType(), new AppStartEvent.AppStartHandler() {
            @Override
            public void onAppStart(final AppStartEvent event) {
                eventBus.addHandler(StateChangedEvent.getType(), new StateChangedHandler() {
                    @Override
                    public void onStateChanged(final StateChangedEvent event) {
                        setState(event.getState());
                    }
                });
            }
        });
    }

    @Override
    protected void onReveal() {
        super.onReveal();
    }

    @Override
    protected void revealInParent() {
        RevealRootContentEvent.fire(this, this);
    }

    private void setContentNotPublic() {
        getView().getTitle().setText(i18n.t("Not Published yet"));
        getView().getDescription().setText(
                i18n.t("This is only a preview of how this page will looks like to the general public if you want to publish it"));
    }

    private void setContentNotPublicable() {
        getView().getTitle().setText(i18n.t("Not Publicable"));
        getView().getDescription().setText(i18n.t("This page is not publicable"));
        getView().setContentGotoPublicUrl("about:blank");
    }

    private void setContentPublic() {
        getView().getTitle().setText(i18n.t("Preview"));
        getView().getDescription().setText(
                i18n.t("This is only a preview of how this page looks like to the general public on the web, outside this site."));
    }

    public void setState(final StateAbstractDTO state) {
        if (state instanceof StateContainerDTO) {
            final StateToken token = state.getStateToken();
            if (((StateContainerDTO) state).getAccessLists().getViewers().getMode().equals(GroupListDTO.EVERYONE)) {
                final String publicUrl = stateTokenUtils.getPublicUrl(token);
                getView().setContentGotoPublicUrl(publicUrl);
                // getView().setContentGotoPublicUrl("http://www.google.com");
                if (state instanceof StateContentDTO) {
                    final StateContentDTO content = (StateContentDTO) state;
                    if (content.getStatus().equals(ContentStatus.publishedOnline)) {
                        setContentPublic();
                    } else {
                        setContentNotPublic();
                    }
                } else {
                    setContentPublic();
                }
            } else {
                setContentNotPublic();
            }
        } else {
            setContentNotPublicable();
        }
    }

}
