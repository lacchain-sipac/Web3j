package com.everis.blockchain.honduras;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.everis.blockchain.honduras.impl.Authorization;
import com.everis.blockchain.honduras.impl.FlowImpl;
import com.everis.blockchain.honduras.util.EthCoreParams;

public class FlowTest {

    EthCoreParams ethCoreParams = new EthCoreParams("http://localhost:8545",
            "0xdb32769684b7ede7c9adb56cf47e5acd008ebaf87fd5e63e9ef92e9401c8e825", null, BigInteger.valueOf(0),
            BigInteger.valueOf(4000000));
    private FlowImpl flowContract;
    private static final Logger log = LoggerFactory.getLogger(FlowTest.class);

    public FlowTest() throws Exception {
    	flowContract = new FlowImpl("0x8ddb9e75c871bc481ab883b69c79f2fca0cc5375", ethCoreParams);;
    }
    // @Test
    public void deployFlow() throws Exception {
        String contractAddress = flowContract.deployFlow();
        log.info("contractAddress: " + contractAddress);
        assertThat(contractAddress, containsString("0x"));
    }

    // @Test
    public void addStep() throws Exception {
        String txHash = flowContract.addStep("step1", new String[] {}, new String[] {},
                new String[] { "role1" }, new String[] {}, new String[] {}, false, true);
        assertThat(txHash, containsString("0x"));
    }

    // @Test
    public void setStatusAuthorizedRolesStep() throws Exception {
        String txHash = flowContract.setStatusAuthorizedRolesStep("step1", new String[] { "role3" }, true);
        assertThat(txHash, containsString("0x"));
    }

    // @Test
    public void setPreviousValidStep() throws Exception {
        String txHash = flowContract.setPreviousValidStep("step1", new String[] { "initial" }, false);
        assertThat(txHash, containsString("0x"));
    }

    // @Test
    public void setProjectSpecificRoles() throws Exception {
        String txHash = flowContract.setProjectSpecificRoles("step1", new String[] { "role1" }, false);
        assertThat(txHash, containsString("0x"));
    }

    // @Test
    public void setStatusAuthorizedDocumentUser() throws Exception {
        String txHash = flowContract.setStatusAuthorizedDocumentRole("step1", "key2", new String[] { "role1" }, true);
        assertThat(txHash, containsString("0x"));
    }

    // @Test
    public void getDocumentKeysStep() throws Exception {
        ArrayList<String> documentsKey = flowContract.getMandatorydocumentKeysStep("step1");
        assertEquals(documentsKey, new ArrayList<String>() {
            {
                add("key1");
            }
        });
    }

    // @Test
    public void documentRoleAuthorization() throws Exception {
        Authorization hasRole = flowContract.documentRoleAuthorization("step1", "key2", "role1");
        assertTrue(hasRole.getIsAuthorized());
    }
    
    // @Test
    public void isRoleAuthorized() throws Exception {
        Authorization isRoleAuthorized = flowContract.roleAuthorization("step1", "role1");
        assertTrue(isRoleAuthorized.getIsAuthorized());
    }
    
    // @Test
    public void isFinalStep() throws Exception {
        Boolean isFinalStep = flowContract.isFinalStep("step1");
        assertTrue(isFinalStep);
    }
    
    // @Test
    public void isValidStepChange() throws Exception {
        Boolean isValidStepChange = flowContract.isValidStepChange("step1", "step2");
        assertFalse(isValidStepChange);
    }
    
    // @Test
    public void existsDocumentKeyInStep() throws Exception {
        Boolean existsDocumentKeyInStep = flowContract.existsDocumentKeyInStep("step1", "key5");
        assertFalse(existsDocumentKeyInStep);
    }

}