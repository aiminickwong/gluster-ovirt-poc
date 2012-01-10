/**
 *
 */
package org.ovirt.engine.core.bll.gluster;

import java.util.Collections;
import java.util.Map;

import org.ovirt.engine.core.bll.Backend;
import org.ovirt.engine.core.common.VdcObjectType;
import org.ovirt.engine.core.common.action.VdsGroupParametersBase;
import org.ovirt.engine.core.common.glustercommands.GlusterBaseVDSCommandParameters;
import org.ovirt.engine.core.common.vdscommands.VDSCommandType;
import org.ovirt.engine.core.common.vdscommands.VDSReturnValue;
import org.ovirt.engine.core.dal.VdcBllMessages;

/**
 *
 */
public class ListGlusterVolumesCommand extends GlusterCommandBase<VdsGroupParametersBase> {

    public ListGlusterVolumesCommand(VdsGroupParametersBase params) {
        super(params);
    }

    @Override
    protected boolean canDoAction() {
        boolean canDoAction = super.canDoAction();
        if (!canDoAction) {
            addCanDoActionMessage(VdcBllMessages.VAR__ACTION__LIST);
        }
        return canDoAction;
    }

    /*
     * (non-Javadoc)
     * @see org.ovirt.engine.core.bll.CommandBase#executeCommand()
     */
    @Override
    protected void executeCommand() {
        VDSReturnValue returnValue = Backend
                .getInstance()
                .getResourceManager()
                .RunVdsCommand(
                        VDSCommandType.ListGlusterVolumes,
                        new GlusterBaseVDSCommandParameters(getVdsGroup().getstorage_pool_id().getValue()));

        // Get the return value from VDS command and put it into the return value of the BLL command
        getReturnValue().setActionReturnValue(returnValue.getReturnValue());

        setSucceeded(returnValue.getSucceeded());
    }

    /*
     * (non-Javadoc)
     * @see org.ovirt.engine.core.bll.CommandBase#getPermissionCheckSubjects()
     */
    @Override
    public Map getPermissionCheckSubjects() {
        return Collections.singletonMap(getVdsGroupId(), VdcObjectType.VdsGroups);
    }
}