package org.ovirt.engine.ui.webadmin.gin.uicommon;

import java.util.Map;

import org.ovirt.engine.core.common.businessentities.AuditLog;
import org.ovirt.engine.core.common.businessentities.GlusterBrick;
import org.ovirt.engine.core.common.businessentities.GlusterVolume;
import org.ovirt.engine.core.common.businessentities.GlusterVolumeEntity;
import org.ovirt.engine.core.common.businessentities.GlusterVolumeOption;
import org.ovirt.engine.core.common.businessentities.VDSGroup;
import org.ovirt.engine.core.common.businessentities.permissions;
import org.ovirt.engine.ui.uicommonweb.UICommand;
import org.ovirt.engine.ui.uicommonweb.models.ConfirmationModel;
import org.ovirt.engine.ui.uicommonweb.models.Model;
import org.ovirt.engine.ui.uicommonweb.models.clusters.ClusterListModel;
import org.ovirt.engine.ui.uicommonweb.models.configure.PermissionListModel;
import org.ovirt.engine.ui.uicommonweb.models.gluster.VolumeBrickListModel;
import org.ovirt.engine.ui.uicommonweb.models.gluster.VolumeEventListModel;
import org.ovirt.engine.ui.uicommonweb.models.gluster.VolumeGeneralModel;
import org.ovirt.engine.ui.uicommonweb.models.gluster.VolumeListModel;
import org.ovirt.engine.ui.uicommonweb.models.gluster.VolumeParameterListModel;
import org.ovirt.engine.ui.uicommonweb.models.hosts.HostEventListModel;
import org.ovirt.engine.ui.uicommonweb.models.hosts.HostGeneralModel;
import org.ovirt.engine.ui.uicommonweb.models.hosts.HostHooksListModel;
import org.ovirt.engine.ui.uicommonweb.models.hosts.HostListModel;
import org.ovirt.engine.ui.webadmin.gin.ClientGinjector;
import org.ovirt.engine.ui.webadmin.section.main.presenter.popup.AbstractModelBoundPopupPresenterWidget;
import org.ovirt.engine.ui.webadmin.section.main.presenter.popup.PermissionsPopupPresenterWidget;
import org.ovirt.engine.ui.webadmin.section.main.presenter.popup.RemoveConfirmationPopupPresenterWidget;
import org.ovirt.engine.ui.webadmin.section.main.presenter.popup.cluster.ClusterPopupPresenterWidget;
import org.ovirt.engine.ui.webadmin.section.main.presenter.popup.gluster.VolumePopupPresenterWidget;
import org.ovirt.engine.ui.webadmin.section.main.presenter.popup.host.HostInstallPopupPresenterWidget;
import org.ovirt.engine.ui.webadmin.section.main.presenter.tab.gluster.SubTabVolumeBrickPresenter;
import org.ovirt.engine.ui.webadmin.section.main.presenter.tab.gluster.SubTabVolumeEventPresenter;
import org.ovirt.engine.ui.webadmin.section.main.presenter.tab.gluster.SubTabVolumeGeneralPresenter;
import org.ovirt.engine.ui.webadmin.section.main.presenter.tab.gluster.SubTabVolumeParameterPresenter;
import org.ovirt.engine.ui.webadmin.section.main.presenter.tab.gluster.SubTabVolumePermissionPresenter;
import org.ovirt.engine.ui.webadmin.section.main.presenter.tab.gluster.VolumeSubTabPanelPresenter;
import org.ovirt.engine.ui.webadmin.uicommon.model.DetailModelProvider;
import org.ovirt.engine.ui.webadmin.uicommon.model.DetailTabModelProvider;
import org.ovirt.engine.ui.webadmin.uicommon.model.MainModelProvider;
import org.ovirt.engine.ui.webadmin.uicommon.model.MainTabModelProvider;
import org.ovirt.engine.ui.webadmin.uicommon.model.SearchableDetailModelProvider;
import org.ovirt.engine.ui.webadmin.uicommon.model.SearchableDetailTabModelProvider;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.inject.client.AsyncProvider;
import com.google.inject.Provider;
import com.google.inject.Provides;
import com.google.inject.Singleton;

public class VolumeModule extends AbstractGinModule {

    // Main List Model

	@Provides
    @Singleton
    public MainModelProvider<GlusterVolumeEntity, VolumeListModel> getVolumeListProvider(ClientGinjector ginjector,
            final Provider<VolumePopupPresenterWidget> popupProvider,
            final Provider<RemoveConfirmationPopupPresenterWidget> removeConfirmPopupProvider) {
        return new MainTabModelProvider<GlusterVolumeEntity, VolumeListModel>(ginjector, VolumeListModel.class) {
            @Override
            protected AbstractModelBoundPopupPresenterWidget<? extends Model, ?> getModelPopup(UICommand lastExecutedCommand) {
                if (lastExecutedCommand == getModel().getCreateVolumeCommand()) {
                    return popupProvider.get();
                } else {
                    return super.getModelPopup(lastExecutedCommand);
                }
            }

            @Override
            protected AbstractModelBoundPopupPresenterWidget<? extends ConfirmationModel, ?> getConfirmModelPopup(UICommand lastExecutedCommand) {
                if (lastExecutedCommand == getModel().getCancelCommand()) {
                    return removeConfirmPopupProvider.get();
                } else {
                    return super.getConfirmModelPopup(lastExecutedCommand);
                }
            }
        };
    }
	

	@Provides
    @Singleton
    public DetailModelProvider<VolumeListModel, VolumeGeneralModel> getVolumeGeneralProvider(ClientGinjector ginjector) {
        return new DetailTabModelProvider<VolumeListModel, VolumeGeneralModel>(ginjector,
        		VolumeListModel.class,
        		VolumeGeneralModel.class) {
            @Override
            protected AbstractModelBoundPopupPresenterWidget<? extends Model, ?> getModelPopup(UICommand lastExecutedCommand) {
            	return super.getModelPopup(lastExecutedCommand);
            }
        };
    }
	
	@Provides
    @Singleton
    public SearchableDetailModelProvider<GlusterBrick, VolumeListModel, VolumeBrickListModel> getVolumeBrickListProvider(ClientGinjector ginjector) {
        return new SearchableDetailTabModelProvider<GlusterBrick, VolumeListModel, VolumeBrickListModel>(ginjector,
        		VolumeListModel.class,
        		VolumeBrickListModel.class){
        	
        };
    }
	
	@Provides
    @Singleton
    public SearchableDetailModelProvider<GlusterVolumeOption, VolumeListModel, VolumeParameterListModel> getVolumeParameterListProvider(ClientGinjector ginjector) {
        return new SearchableDetailTabModelProvider<GlusterVolumeOption, VolumeListModel, VolumeParameterListModel>(ginjector,
        		VolumeListModel.class,
        		VolumeParameterListModel.class) {
        	
        };
    }
	
	
	@Provides
    @Singleton
    public SearchableDetailModelProvider<permissions, VolumeListModel, PermissionListModel> getVolumePermissionListProvider(ClientGinjector ginjector,
            final Provider<PermissionsPopupPresenterWidget> popupProvider,
            final Provider<RemoveConfirmationPopupPresenterWidget> removeConfirmPopupProvider) {
        return new SearchableDetailTabModelProvider<permissions, VolumeListModel, PermissionListModel>(ginjector,
        		VolumeListModel.class,
                PermissionListModel.class) {
            @Override
            protected AbstractModelBoundPopupPresenterWidget<? extends Model, ?> getModelPopup(UICommand lastExecutedCommand) {
                PermissionListModel model = getModel();

                if (lastExecutedCommand == model.getAddCommand()) {
                    return popupProvider.get();
                } else {
                    return super.getModelPopup(lastExecutedCommand);
                }
            }

            @Override
            protected AbstractModelBoundPopupPresenterWidget<? extends ConfirmationModel, ?> getConfirmModelPopup(UICommand lastExecutedCommand) {
                if (lastExecutedCommand == getModel().getRemoveCommand()) {
                    return removeConfirmPopupProvider.get();
                } else {
                    return super.getConfirmModelPopup(lastExecutedCommand);
                }
            }
        };
    }

    @Provides
    @Singleton
    public SearchableDetailModelProvider<AuditLog, VolumeListModel, VolumeEventListModel> getHostEventListProvider(ClientGinjector ginjector) {
        return new SearchableDetailTabModelProvider<AuditLog, VolumeListModel, VolumeEventListModel>(ginjector,
        		VolumeListModel.class,
        		VolumeEventListModel.class);
    }
	
	
	@Override
	protected void configure() {
		
	}
}