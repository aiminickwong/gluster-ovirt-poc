package org.ovirt.engine.ui.webadmin.widget.table.column;

import org.ovirt.engine.core.common.businessentities.GlusterVolumeEntity;
import org.ovirt.engine.core.common.businessentities.GlusterVolumeEntity.VOLUME_STATUS;
import org.ovirt.engine.core.common.businessentities.VDS;
import org.ovirt.engine.core.common.businessentities.VDSStatus;
import org.ovirt.engine.ui.webadmin.ApplicationResources;
import org.ovirt.engine.ui.webadmin.gin.ClientGinjectorProvider;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.AbstractImagePrototype;

public class VolumeStatusCell extends AbstractCell<GlusterVolumeEntity> {

    @Override
    public void render(Context context, GlusterVolumeEntity volume, SafeHtmlBuilder sb) {
        // Nothing to render if no host is provided:
        if (volume == null) {
            return;
        }

        // Get a reference to the application resources:
        ApplicationResources resources = ClientGinjectorProvider.instance().getApplicationResources();

        // Find the image corresponding to the status of the host:
        VOLUME_STATUS status = volume.getStatus();
        ImageResource statusImage = null;
        switch (status) {
        case OFFLINE:
            statusImage = resources.downImage();
            break;
        case ONLINE:
            statusImage = resources.upImage();
            break;
        default:
            statusImage = resources.downImage();
        }

        // Generate the HTML for the image:
        SafeHtml statusImageHtml = SafeHtmlUtils.fromTrustedString(AbstractImagePrototype.create(statusImage).getHTML());
        sb.appendHtmlConstant("<div style=\"text-align: center; padding-top: 6px;\">");
        sb.append(statusImageHtml);
        sb.appendHtmlConstant("</div>");
    }

}
