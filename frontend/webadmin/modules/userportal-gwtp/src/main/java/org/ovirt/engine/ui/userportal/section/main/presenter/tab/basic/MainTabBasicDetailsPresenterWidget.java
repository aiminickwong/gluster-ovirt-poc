package org.ovirt.engine.ui.userportal.section.main.presenter.tab.basic;

import org.ovirt.engine.core.common.businessentities.DiskImage;
import org.ovirt.engine.core.compat.Event;
import org.ovirt.engine.core.compat.EventArgs;
import org.ovirt.engine.core.compat.IEventListener;
import org.ovirt.engine.ui.common.widget.HasEditorDriver;
import org.ovirt.engine.ui.uicommonweb.models.userportal.UserPortalBasicListModel;
import org.ovirt.engine.ui.uicommonweb.models.userportal.UserPortalItemModel;
import org.ovirt.engine.ui.userportal.section.main.presenter.popup.console.ConsoleModelChangedEvent;
import org.ovirt.engine.ui.userportal.section.main.presenter.popup.console.ConsolePopupPresenterWidget;
import org.ovirt.engine.ui.userportal.section.main.presenter.popup.console.ConsoleModelChangedEvent.ConsoleModelChangedHandler;
import org.ovirt.engine.ui.userportal.section.main.view.tab.basic.widget.ConsoleProtocol;
import org.ovirt.engine.ui.userportal.section.main.view.tab.basic.widget.ConsoleUtils;
import org.ovirt.engine.ui.userportal.uicommon.model.UserPortalBasicListProvider;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.RevealRootPopupContentEvent;

public class MainTabBasicDetailsPresenterWidget extends PresenterWidget<MainTabBasicDetailsPresenterWidget.ViewDef> {

    private final ConsolePopupPresenterWidget consolePopup;
    private final ConsoleUtils consoleUtils;

    public interface ViewDef extends View, HasEditorDriver<UserPortalBasicListModel> {

        void editDistItems(Iterable<DiskImage> diskImages);

        void setConsoleWarningMessage(String message);

        void setConsoleProtocol(String protocol);

        void setEditConsoleEnabled(boolean enabled);

        HasClickHandlers getEditButton();
    }

    @Inject
    public MainTabBasicDetailsPresenterWidget(EventBus eventBus,
            ViewDef view,
            final UserPortalBasicListProvider modelProvider,
            final ConsoleUtils consoleUtils,
            ConsolePopupPresenterWidget consolePopup) {
        super(eventBus, view);
        this.consoleUtils = consoleUtils;
        this.consolePopup = consolePopup;

        listenOnSelectedItemEvent(modelProvider);

        listenOnDiskModelChangeEvent(modelProvider);

        listenOnEditButton(modelProvider);

        listenOnConsoleModelChangeEvent(eventBus, modelProvider);

    }

    protected void listenOnConsoleModelChangeEvent(EventBus eventBus, final UserPortalBasicListProvider modelProvider) {
        eventBus.addHandler(ConsoleModelChangedEvent.getType(), new ConsoleModelChangedHandler() {

            @Override
            public void onConsoleModelChanged(ConsoleModelChangedEvent event) {
                setupConsole(modelProvider);
            }

        });
    }

    private void listenOnDiskModelChangeEvent(final UserPortalBasicListProvider modelProvider) {
        modelProvider.getModel().getvmBasicDiskListModel().getItemsChangedEvent().addListener(new IEventListener() {

            @Override
            public void eventRaised(Event ev, Object sender, EventArgs args) {
                if (modelProvider.getModel().getSelectedItem() == null) {
                    return;
                }
                @SuppressWarnings("unchecked")
                Iterable<DiskImage> diskImages = modelProvider.getModel().getvmBasicDiskListModel().getItems();
                getView().editDistItems(diskImages);
            }
        });
    }

    private void listenOnSelectedItemEvent(final UserPortalBasicListProvider modelProvider) {

        // TODO check if this works on logout/login
        modelProvider.getModel().getSelectedItemChangedEvent().addListener(new IEventListener() {

            @Override
            public void eventRaised(Event ev, Object sender, EventArgs args) {
                if (modelProvider.getModel().getSelectedItem() == null) {
                    return;
                }
                getView().edit(modelProvider.getModel());
                setupConsole(modelProvider);
            }

        });
    }

    private void listenOnEditButton(final UserPortalBasicListProvider modelProvider) {
        registerHandler(getView().getEditButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                if (!isEditConsoleEnabled(modelProvider.getModel().getSelectedItem())) {
                    return;
                }

                consolePopup.init(modelProvider.getModel());
                RevealRootPopupContentEvent.fire(getEventBus(), consolePopup);
            }
        }));
    }

    private void setupConsole(final UserPortalBasicListProvider modelProvider) {
        UserPortalItemModel item = modelProvider.getModel().getSelectedItem();

        getView().setEditConsoleEnabled(isEditConsoleEnabled(item));

        if (!item.getIsPool()) {
            ConsoleProtocol protocol = consoleUtils.determineDefaultProtocol(item);
            if (protocol == null) {
                getView().setConsoleWarningMessage(consoleUtils.determineProtocolMessage(item));
            } else {
                getView().setConsoleProtocol(protocol == null ? "" : protocol.displayName);
            }
        } else {
            getView().setConsoleProtocol("");
        }
    }

    private boolean isEditConsoleEnabled(UserPortalItemModel item) {
        if (!item.getIsPool() && consoleUtils.determineDefaultProtocol(item) != null) {
            return true;
        }

        return false;

    }
}