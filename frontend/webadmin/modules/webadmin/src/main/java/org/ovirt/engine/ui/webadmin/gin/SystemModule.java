package org.ovirt.engine.ui.webadmin.gin;

import org.ovirt.engine.ui.common.gin.BaseSystemModule;
import org.ovirt.engine.ui.webadmin.ApplicationConstants;
import org.ovirt.engine.ui.webadmin.ApplicationMessages;
import org.ovirt.engine.ui.webadmin.ApplicationResources;
import org.ovirt.engine.ui.webadmin.ApplicationTemplates;
import org.ovirt.engine.ui.webadmin.place.ApplicationPlaces;
import org.ovirt.engine.ui.webadmin.system.ApplicationInit;
import org.ovirt.engine.ui.webadmin.system.InternalConfiguration;

/**
 * GIN module containing WebAdmin infrastructure and configuration bindings.
 */
public class SystemModule extends BaseSystemModule {

    @Override
    protected void configure() {
        bindInfrastructure();
        bindConfiguration();
    }

    void bindInfrastructure() {
        bindCommonInfrastructure();
        bind(ApplicationInit.class).asEagerSingleton();
        bind(InternalConfiguration.class).asEagerSingleton();
    }

    void bindConfiguration() {
        bindConstant().annotatedWith(DefaultLoginSectionPlace.class).to(ApplicationPlaces.loginPlace);
        bindConstant().annotatedWith(DefaultMainSectionPlace.class).to(ApplicationPlaces.volumeMainTabPlace);
        bind(ApplicationConstants.class).in(Singleton.class);
        bind(ApplicationMessages.class).in(Singleton.class);
        bind(ApplicationResources.class).in(Singleton.class);
        bind(ApplicationTemplates.class).in(Singleton.class);
    }
}
