package org.multiverse.stms.gamma.integration.classic;

import org.junit.Test;
import org.multiverse.api.TxnExecutor;
import org.multiverse.api.LockMode;
import org.multiverse.stms.gamma.LeanGammaTxnExecutor;
import org.multiverse.stms.gamma.transactions.GammaTxnConfig;
import org.multiverse.stms.gamma.transactions.fat.FatMonoGammaTxnFactory;

public class ReadersWriters_FatMonoGammaTxn_StressTest extends ReadersWritersProblem_AbstractTest {

    private LockMode lockMode;

    @Test
    public void whenNoLock() {
        lockMode = LockMode.None;
        run();
    }

    @Test
    public void whenReadLock() {
        lockMode = LockMode.Read;
        run();
    }

    @Test
    public void whenWriteLock() {
        lockMode = LockMode.Write;
        run();
    }

    @Test
    public void whenExclusiveLock() {
        lockMode = LockMode.Exclusive;
        run();
    }

    @Override
    protected TxnExecutor newAcquiredBlock() {
        GammaTxnConfig config = new GammaTxnConfig(stm)
                .setMaxRetries(10000)
                .setReadLockMode(lockMode);
        return new LeanGammaTxnExecutor(new FatMonoGammaTxnFactory(config));
    }

    @Override
    protected TxnExecutor newAcquireBlock() {
        GammaTxnConfig config = new GammaTxnConfig(stm)
                .setMaxRetries(10000)
                .setReadLockMode(lockMode);
        return new LeanGammaTxnExecutor(new FatMonoGammaTxnFactory(config));
    }
}
