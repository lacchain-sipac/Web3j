package com.everis.blockchain.honduras;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.everis.blockchain.honduras.impl.OwnerImpl;
import com.everis.blockchain.honduras.util.EthCoreParams;

public class OwnerTest {

    EthCoreParams ethCoreParams = new EthCoreParams("http://localhost:8545", "0xdb32769684b7ede7c9adb56cf47e5acd008ebaf87fd5e63e9ef92e9401c8e825", null, BigInteger.valueOf(0), BigInteger.valueOf(4000000));
    private OwnerImpl owner = new OwnerImpl(ethCoreParams);
    private static final Logger log = LoggerFactory.getLogger(OwnerTest.class);
    private static String flowContract = "0xF21204E1d7fD90Df54e0EaeFFB2df115898c90ac";

    // @Test
    public void addOwnerFlow() throws Exception {
        String txHash = owner.addOwner(flowContract, "0x4e4b1308f311d0e144843892d43216b51da949d0", false, null, null);
        assertThat(txHash, containsString("0x"));
    }
    
    // @Test
    public void renounceFlow() throws Exception {
        String txHash = owner.renounce(flowContract, false, null, null);
        assertThat(txHash, containsString("0x"));
    }
    
    // @Test
    public void isOwner() throws Exception {
        Boolean isOwnerFlow = owner.isOwner(flowContract, "0x189cf6D846793613aE8Af2F11190F28FDBAEd905");
        assertTrue(isOwnerFlow);
        isOwnerFlow = owner.isOwner(flowContract, "0x44464F28Ea47a0286881485Bd49A751C56106A8e");
        assertFalse(isOwnerFlow);
    }

}