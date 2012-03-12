package org.ovirt.engine.core.common.glusteractions;

import org.ovirt.engine.core.compat.Guid;

public class GlusterVolumeParameters extends GlusterParametersBase {
    private String volumeName;

    public GlusterVolumeParameters(Guid vdsGroupId, String volumeName) {
        super(vdsGroupId);
        setVolumeName(volumeName);
    }

    public String getVolumeName() {
        return volumeName;
    }

    public void setVolumeName(String volumeName) {
        this.volumeName = volumeName;
    }
}
