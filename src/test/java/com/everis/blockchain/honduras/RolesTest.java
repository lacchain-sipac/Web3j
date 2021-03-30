package com.everis.blockchain.honduras;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.everis.blockchain.honduras.impl.RolesImpl;
import com.everis.blockchain.honduras.main.model.Constants;
import com.everis.blockchain.honduras.util.EthCoreParams;

public class RolesTest {

    EthCoreParams ethCoreParams = new EthCoreParams("http://eth-lacchain.mytrust.id", "0xd5eeee8b07df8174fdfbb9728036ba9278552a9e3b03cc8fae0c0ba444c22541", null, BigInteger.valueOf(0), BigInteger.valueOf(4000000));
    private RolesImpl  rolesContract; 
    private String identitManagerAddress = "0xb79e51f09d216d6b70278464e5dedd6164d0793b";
    private static final Logger log = LoggerFactory.getLogger(RolesTest.class);

    public RolesTest() throws Exception {
    	rolesContract = new RolesImpl("0x612fbf4660416c50b284d83082860f8e325ff005", Constants.ROLE_ADMIN, Constants.ROLE_COO_TEC, ethCoreParams);
    	   	
    }
    // @Test
    public void deployRoles() throws Exception {
        String contractAddress = rolesContract.deployRoles("ownerRole", "projectOwnerRole");
        log.info("contractAddress: " + contractAddress);
        assertThat(contractAddress, containsString("0x"));
    }

    // @Test
    public void setRoleUser() throws Exception {
        String txHash = rolesContract.setRoleUser("role1", "0x1577c234ed9129298731af44e79f66145c3ca87b");
        assertThat(txHash, containsString("0x"));
    }
    
    // @Test
    public void setRoleUserIM() throws Exception {
        String txHash = rolesContract.setRoleUserIM("role1", "0x0afc013721b6d1d5b7b08a32eb2908becd212862", "0x4e4b1308f311d0e144843892d43216b51da949d0", identitManagerAddress);
        assertThat(txHash, containsString("0x"));
    }
 
    // @Test
    public void removeRoleUser() throws Exception {
        String txHash = rolesContract.removeRoleUser("role1", "0xCA35b7d915458EF540aDe6068dFe2F44E8fa733c");
        assertThat(txHash, containsString("0x"));
    }

    // @Test
    public void removeRoleUserIM() throws Exception {
        String txHash = rolesContract.removeRoleUserIM("role1", "0x0afc013721b6d1d5b7b08a32eb2908becd212862", "0x4e4b1308f311d0e144843892d43216b51da949d0", identitManagerAddress);
        assertThat(txHash, containsString("0x"));
    }

    // @Test
    public void hasRoleUser() throws Exception {
        Boolean hasRole = rolesContract.hasRoleUser("role1", "0x1577c234ed9129298731af44e79f66145c3ca87b");
        assertTrue(hasRole);
    }
    
    //@Test
    public void checkhasRoleUser() throws Exception {
    	String proyectId="0xcad94117bb23d9254690f190bc0691fe1c02495a93408b9718e893780576f3ad";
    	String user = "0x1ef8c9ebe2e94cb1a0777d982a2efc1438a8e591";
    	String proxySession = "0x05281bc91a2ce82d2986f9a13765731c84507d65";
    	
    	 Boolean hasRole = rolesContract.hasRoleUser("ROLE_CONT",user,proyectId);
         assertTrue(hasRole);
         
    	String txHash = rolesContract.setRoleUserIM("ROLE_CONT", user,proyectId, proxySession, identitManagerAddress);
        assertThat(txHash, containsString("0x"));
        
       
    }

}