package org.ovirt.engine.core.common.businessentities;

import java.util.ArrayList;

import org.ovirt.engine.core.compat.Guid;

/**
 * The VmDevice represents a managed or unmanaged VM device.<br>
 * The VmDevice data consists of this entity which holds the immutable fields of the device.<br>
 * Each device have a link to its VM id , an address and optional boot order and additional special device parameters. <br>
 * This BE holds both managed (disk, network interface etc.) and unmanaged (sound, video etc.) devices.
 */

public class VmDevice extends IVdcQueryable implements BusinessEntity<VmDeviceId> {

    /**
     * Needed for java serialization/deserialization mechanism.
     */
    private static final long serialVersionUID = 826297167224396854L;

    /**
     * Device id and Vm id pair as a compound key
     */
    private VmDeviceId id;
    /**
     * The VmDevice ID uniquely identifies a VM device in the system.
     */
    private Guid deviceId;

    /**
     * The VM id associated with the device
     */
    private Guid vmId;

    /**
     * The device name.
     */
    private String device;

    /**
     * The device name.
     */
    private String type;

    /**
     * The device address.
     */
    private String address;

    /**
     * The device boot order (if applicable).
     */
    private int bootOrder;

    /**
     * The device special parameters.
     */
    private String specParams;

    /**
     * The device managed/unmanaged flag
     */
    private boolean isManaged = false;

    /**
     * The device plugged flag
     */
    private boolean isPlugged = false;

    /**
     * The device shared flag
     */

    private boolean isShared = false;

    /**
     * The device read-only flag
     */

    private boolean isReadOnly = false;

    public VmDevice() {
    }

    public VmDevice(VmDeviceId id, String type, String device, String address,
            int bootOrder,
            String specParams,
            boolean isManaged,
            boolean isPlugged,
            boolean isShared,
            boolean isReadOnly) {
        super();
        this.id = id;
        this.deviceId = id.getDeviceId();
        this.vmId = id.getVmId();
        this.type = type;
        this.device = device;
        this.address = address;
        this.bootOrder = bootOrder;
        this.specParams = specParams;
        this.isManaged = isManaged;
        this.isPlugged = isPlugged;
        this.isShared = isShared;
        this.isReadOnly = isReadOnly;
    }

    @Override
    public Object getQueryableId() {
        return getId();
    }

    @Override
    public ArrayList<String> getChangeablePropertiesList() {
        return null;
    }

    @Override
    public VmDeviceId getId() {
        return id;
    }

    @Override
    public void setId(VmDeviceId id) {
        this.id = id;
    }

    public Guid getDeviceId() {
        return id.getDeviceId();
    }

    public void setDeviceId(Guid deviceId) {
        id.setDeviceId(deviceId);
    }
    public Guid getVmId() {
        return id.getVmId();
    }

    public void setVmId(Guid vmId) {
        this.id.setVmId(vmId);
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getBootOrder() {
        return bootOrder;
    }

    public void setBootOrder(int bootOrder) {
        this.bootOrder = bootOrder;
    }

    public String getSpecParams() {
        return specParams;
    }

    public void setSpecParams(String specParams) {
        this.specParams = specParams;
    }

    public boolean getIsManaged() {
        return isManaged;
    }

    public void setIsManaged(boolean isManaged) {
        this.isManaged = isManaged;
    }

    public boolean getIsPlugged() {
        return isPlugged;
    }

    public void setIsPlugged(boolean isPlugged) {
        this.isPlugged = isPlugged;
    }

    public boolean getIsShared() {
        return isShared;
    }

    public void setIsShared(boolean isShared) {
        this.isShared = isShared;
    }

    public boolean getIsReadOnly() {
        return isReadOnly;
    }

    public void setIsReadOnly(boolean isReadOnly) {
        this.isReadOnly = isReadOnly;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id.hashCode();
        result = prime * result + device.hashCode();
        result = prime * result + type.hashCode();
        result = prime * result + address.hashCode();
        result = prime * result + bootOrder;
        result = prime * result
                + ((specParams == null) ? 0 : specParams.hashCode());
        result = prime * result + (isManaged ? 1 : 0);
        result = prime * result + (isPlugged ? 1 : 0);
        result = prime * result + (isShared ? 1 : 0);
        result = prime * result + (isReadOnly ? 1 : 0);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof VmDevice)) {
            return false;
        }
        VmDevice other = (VmDevice) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (!device.equals(other.device)) {
            return false;
        }
        if (!type.equals(other.type)) {
            return false;
        }
        if (!address.equals(other.address)) {
            return false;
        }
        if (bootOrder != other.bootOrder) {
            return false;
        }
        if (!specParams.equals(other.specParams)) {
            return false;
        }
        if (isManaged != other.isManaged) {
            return false;
        }
        if (isPlugged != other.isPlugged) {
            return false;
        }
        if (isShared != other.isShared) {
            return false;
        }
        if (isReadOnly != other.isReadOnly) {
            return false;
        }

        return true;
    }
}