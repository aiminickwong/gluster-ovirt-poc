/*******************************************************************************
 * Copyright (c) 2006-2011 Gluster, Inc. <http://www.gluster.com>
 * This file is part of Gluster Management Console.
 *
 * Gluster Management Console is free software; you can redistribute
 * it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * Gluster Management Console is distributed in the hope that it
 * will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE.  See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see
 * <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package org.ovirt.engine.core.common.businessentities;

import javax.xml.bind.annotation.XmlTransient;

import org.ovirt.engine.core.common.utils.StringUtil;

public class GlusterBrickEntity extends GlusterEntity {
    public enum BRICK_STATUS {
        ONLINE,
        OFFLINE
    };

    private String[] BRICK_STATUS_STR = { "Online", "Offline" };

    private String serverName;
    // private String deviceName;
    private String brickDirectory;
    private BRICK_STATUS status;

    public GlusterBrickEntity() {
    }

    private void initBrick(String serverName, String brickDirectory) {
        setServerName(serverName);
        setBrickDirectory(brickDirectory);
        setStatus(BRICK_STATUS.ONLINE);
    }

    public GlusterBrickEntity(String serverName, String brickDirectory) {
        super();
        initBrick(serverName, brickDirectory);
    }

    public GlusterBrickEntity(String qualifiedName) {
        String brickInfo[] = qualifiedName.split(":", -1);
        initBrick(brickInfo[0], brickInfo[1]);
    }

    @Override
    @XmlTransient
    public String getName() {
        return getQualifiedName();
    }

    public BRICK_STATUS getStatus() {
        return status;
    }

    public String getStatusStr() {
        return BRICK_STATUS_STR[getStatus().ordinal()];
    }

    public void setStatus(BRICK_STATUS status) {
        this.status = status;
    }

    public GlusterBrickEntity(String serverName, BRICK_STATUS brickStatus, String brickDirectory) {
        setServerName(serverName);
        setStatus(brickStatus);
        // setDeviceName(deviceName);
        setBrickDirectory(brickDirectory);
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getServerName() {
        return serverName;
    }

    public void setBrickDirectory(String brickDirectory) {
        this.brickDirectory = brickDirectory;
    }

    public String getBrickDirectory() {
        return brickDirectory;
    }

    // public void setDeviceName(String deviceName) {
    // this.deviceName = deviceName;
    // }

    // public String getDeviceName() {
    // return deviceName;
    // }

    public String getQualifiedName() {
        return serverName + ":" + brickDirectory;
    }

    @Override
    public boolean filter(String filterString, boolean caseSensitive) {
        return StringUtil.filterString(getServerName() + getBrickDirectory() + getStatusStr(), filterString,
                caseSensitive);
    }

    @Override
    public String toString() {
        return getQualifiedName();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof GlusterBrickEntity)) {
            return false;
        }

        GlusterBrickEntity brick = (GlusterBrickEntity) obj;
        if (getQualifiedName().equals(brick.getQualifiedName()) && getStatus() == brick.getStatus()) {
            return true;
        }

        return false;
    }

    public void copyFrom(GlusterBrickEntity newBrick) {
        setServerName(newBrick.getServerName());
        setBrickDirectory(newBrick.getBrickDirectory());
        // setDeviceName(newBrick.getDeviceName());
        setStatus(newBrick.getStatus());
    }

    @Override
    public Object getQueryableId() {
        return getId();
    }
}
