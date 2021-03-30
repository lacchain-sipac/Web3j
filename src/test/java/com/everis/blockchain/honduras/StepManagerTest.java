package com.everis.blockchain.honduras;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.everis.blockchain.honduras.impl.StepManagerImpl;
import com.everis.blockchain.honduras.util.EthCoreParams;

public class StepManagerTest {

    EthCoreParams ethCoreParams = new EthCoreParams("http://localhost:8545", "0xdb32769684b7ede7c9adb56cf47e5acd008ebaf87fd5e63e9ef92e9401c8e825", null, BigInteger.valueOf(0), BigInteger.valueOf(4000000));
    private StepManagerImpl stepManagerContract ;
    //private String addressEthereumPk = "0x44464F28Ea47a0286881485Bd49A751C56106A8e";
    private static final Logger log = LoggerFactory.getLogger(StepManagerTest.class);
    private String addressIM = "0xd37Ea8FaaAb4A13cf8C6C9d403A5E90B7e28df0E";
    private String addressUser = "0x1577c234ed9129298731af44e79f66145c3ca87b";
    private static String flowContract = "0x8ddb9e75c871bc481ab883b69c79f2fca0cc5375";
    private static String rolesContract = "0xd9e5884d97356a227a36cdc2526d64a179e2bd3e";

    public StepManagerTest() throws Exception {
    	stepManagerContract = new StepManagerImpl("0x8d54f19d8a287d1e0d85d1cc99e2dfaa340480da", ethCoreParams);
    	   
    }
    
    // @Test
    public void deployStepManager() throws Exception {
        String contractAddress = stepManagerContract.deployStepManager();
        log.info("contractAddress: " + contractAddress);
        assertThat(contractAddress, containsString("0x"));
    }

    // @Test
    public void initProject() throws Exception {
        // Project code = project1 (0x721d13ef5958d4be6f19a03d9761e48dacbf2c0d712153d4af05d311f92118fb)
        String txHash = stepManagerContract.initProject("0x721d13ef5958d4be6f19a03d9761e48dacbf2c0d712153d4af05d311f92118fb", "step1", flowContract, rolesContract, "role1");
        assertThat(txHash, containsString("0x"));
    }
    
    // @Test
    public void initProjectIM() throws Exception {
        String txHash = stepManagerContract.initProjectIM("0xe2013eda4cfff7f6bca35fae70667132317bfe663a227f49aa867bc4cab146ea", "step1", flowContract, rolesContract, "role2", addressUser, addressIM);
        assertThat(txHash, containsString("0x"));
    }

    // @Test
    public void changeStep() throws Exception {
        String txHash = stepManagerContract.changeStep("0x721d13ef5958d4be6f19a03d9761e48dacbf2c0d712153d4af05d311f92118fb", "step2", "role1");
        assertThat(txHash, containsString("0x"));
    }
    
    // @Test
    public void changeStepIM() throws Exception {
        String txHash = stepManagerContract.changeStepIM("0x721d13ef5958d4be6f19a03d9761e48dacbf2c0d712153d4af05d311f92118fb", "step2", "role1", addressUser, addressIM);
        assertThat(txHash, containsString("0x"));
    }

    // @Test
    public void setDocumentType() throws Exception {
        String txHash = stepManagerContract.setDocumentType("0x721d13ef5958d4be6f19a03d9761e48dacbf2c0d712153d4af05d311f92118fb", "key1", "step1", "role1", "0x6b65793400000000000000000000000000000000000000000000000000000000", "0x0000000000000000000000000000000000000000000000000000000000000000", "0x0000000000000000000000000000000000000000000000000000000000000000", false);
        assertThat(txHash, containsString("0x"));
    }
    
    // @Test
    public void setDocumentTypeIM() throws Exception {
        String txHash = stepManagerContract.setDocumentTypeIM("0x721d13ef5958d4be6f19a03d9761e48dacbf2c0d712153d4af05d311f92118fb", "key1", "step1", "role1", "0x6b65793400000000000000000000000000000000000000000000000000000000", "0x0000000000000000000000000000000000000000000000000000000000000000", "0x0000000000000000000000000000000000000000000000000000000000000000", false, addressUser, addressIM);
        assertThat(txHash, containsString("0x"));
    }

    // @Test
    public void unfinalizeDocumentType() throws Exception {
        String txHash = stepManagerContract.unfinalizeDocumentType("0x721d13ef5958d4be6f19a03d9761e48dacbf2c0d712153d4af05d311f92118fb", "key1", "role1");
        assertThat(txHash, containsString("0x"));
    }
    
    // @Test
    public void unfinalizeDocumentTypeIM() throws Exception {
        String txHash = stepManagerContract.unfinalizeDocumentTypeIM("0x721d13ef5958d4be6f19a03d9761e48dacbf2c0d712153d4af05d311f92118fb", "key1", "role1", addressUser, addressIM);
        assertThat(txHash, containsString("0x"));
    }
    
    // @Test
    public void getDocumentTypeCount() throws Exception {
        BigInteger total = stepManagerContract.getDocumentTypeCount("0x721d13ef5958d4be6f19a03d9761e48dacbf2c0d712153d4af05d311f92118fb", "key1");
        assertTrue(total.intValue() > 0);
    }
    
    // @Test
    public void hasValidDocuments() throws Exception {
        Boolean hasValidDocuments = stepManagerContract.hasValidDocuments("0x721d13ef5958d4be6f19a03d9761e48dacbf2c0d712153d4af05d311f92118fb");
        assertFalse(hasValidDocuments);
    }
    
    // @Test
    public void projectStep() throws Exception {
        Boolean projectStep = stepManagerContract.isStepCompletedProject("0x721d13ef5958d4be6f19a03d9761e48dacbf2c0d712153d4af05d311f92118fb", "step1");
        assertFalse(projectStep);
    }

}