package org.ovirt.engine.core.bll.storage;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.when;

import org.junit.Before;
import org.junit.Test;
import org.ovirt.engine.core.common.action.StorageDomainParametersBase;
import org.ovirt.engine.core.common.businessentities.StorageDomainStatus;
import org.ovirt.engine.core.common.businessentities.storage_domains;
import org.ovirt.engine.core.compat.Guid;
import org.ovirt.engine.core.dal.VdcBllMessages;

public class StorageDomainCommandBaseTest {

    public StorageDomainCommandBase<StorageDomainParametersBase> cmd;

    @Before
    public void setUp() {
        createTestCommand();
    }

    @Test
    public void statusMatches() {
        storageDomainIsInactive();
        assertTrue(cmd.checkStorageDomainStatus(StorageDomainStatus.InActive));
        assertFalse(commandHasInvalidStatusMessage());
    }

    @Test
    public void statusNotMatch() {
        storageDomainIsInactive();
        assertFalse(cmd.checkStorageDomainStatus(StorageDomainStatus.Active));
        assertTrue(commandHasInvalidStatusMessage());
    }

    @Test
    public void statusInList() {
        storageDomainIsInactive();
        assertTrue(cmd.checkStorageDomainStatus(StorageDomainStatus.Locked, StorageDomainStatus.InActive,
                StorageDomainStatus.Unknown));
        assertFalse(commandHasInvalidStatusMessage());
    }

    @Test
    public void statusNotInList() {
        storageDomainIsInactive();
        assertFalse(cmd.checkStorageDomainStatus(StorageDomainStatus.Locked, StorageDomainStatus.Active,
                StorageDomainStatus.Unknown));
        assertTrue(commandHasInvalidStatusMessage());
    }

    private boolean commandHasInvalidStatusMessage() {
        return cmd.getReturnValue().getCanDoActionMessages().contains(
                VdcBllMessages.ACTION_TYPE_FAILED_STORAGE_DOMAIN_STATUS_ILLEGAL.toString());
    }

    private void storageDomainIsInactive() {
        storage_domains domain = new storage_domains();
        domain.setstatus(StorageDomainStatus.InActive);
        when(cmd.getStorageDomain()).thenReturn(domain);
    }

    private void createTestCommand() {
        StorageDomainParametersBase parameters = new StorageDomainParametersBase(Guid.NewGuid());
        cmd = spy(new TestStorageCommandBase(new StorageDomainParametersBase()));
    }

    class TestStorageCommandBase extends StorageDomainCommandBase<StorageDomainParametersBase> {

        public TestStorageCommandBase(StorageDomainParametersBase parameters) {
            super(parameters);
        }



        @Override
        protected void executeCommand() {

        }
    }
}