package com.everis.blockchain.honduras;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.everis.blockchain.honduras.impl.DigitalIdentityImpl;
import com.everis.blockchain.honduras.impl.RolesImpl;
import com.everis.blockchain.honduras.main.model.Constants;
import com.everis.blockchain.honduras.util.EthCoreParams;
import com.everis.blockchain.honduras.util.UtilYaml;
import com.everis.blockchain.honduras.util.Utils;

public class IdentityTest {

	private static final Logger log = LoggerFactory.getLogger("IdentityTest");
	private RolesImpl rolesContract;
	private EthCoreParams ethCoreParams;
//	LinkedHashMap<String, String> map;

	@Before
	public void before() throws Exception {
		// map = UtilYaml.readFile(UtilYaml.NAME_FILE);

		ethCoreParams = new EthCoreParams("https://lacchain.eth.kaytrust.id",
				"0xd5eeee8b07df8174fdfbb9728036ba9278552a9e3b03cc8fae0c0ba444c22541", null, null, null);

		try {
			Map<String, String> header = new HashMap<>();

			String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJleHAiOjE5MTUyMjUyMDksInBlcm1pc3Npb25zIjpbImV0aDoqIiwibmV0OnZlcnNpb24iXX0.UMZYDEkdVoS4x-fVjJ6YD6Qll6Wy1O9x020UdGIcqofxsKlkAoyTLOKnY8QdLQ7Y3Vb9YmiYH81O3iIxXC_2lt7IQgY3TO7EjdaeRs8gDgFk1T_HR7wVMtX-8N7jLDF1MLPOKvSdDk6Qkj-tMvDb999TVZrzJpReD4SJ2c6mQRyqCO7OcCzxxH4JJ8ZFvx7AaCB7VHFwSg_hWFmPFuEc6hXOvz_02QRARWlzrEflOUKxWqQyyRhGqezyzcC2x4_ydAQvkpUlw0boLzCgFVB1itOy6DgYTUzyARP2VLZVfzHdw2IdQuOkG9ozmIXjIg9fOH0t2JJxp74UmCu1dIeoHA";
			header.put("Authorization", "Bearer " + token);
			ethCoreParams.setHeaders(header);
			//DIM = new DigitalIdentityImpl("0xb79e51f09d216d6b70278464e5dedd6164d0793b", ethCoreParams);
		} catch (Exception e) {
			e.printStackTrace();

		}

		rolesContract = new RolesImpl("0x612fbf4660416c50b284d83082860f8e325ff005", Constants.ROLE_ADMIN,
				Constants.ROLE_COO_TEC, ethCoreParams);

		// map.get(Constants.CONTRACT_ADDRESS_PROXY_FIRST_ADMINISTRATOR)
	}

	@Test
	public void processsRole() {
		try {
			String addressAdmin = "0x8b42de1a6e497613d911b03e9826a9d90c02c89a";

//			String role = "ROLE_COO_TEC";
//			String proxyAddress = "0x96a50b248a4c2d11dc1915f1a8cfba073153813e";

			Map<String, String> map = new HashMap<>();

			map.put("0x8b42de1a6e497613d911b03e9826a9d90c02c89a", "ROLE_1");
			map.put("0x1b18b844a541750d25c4c2b83fb332bfb786d3eb", "ROLE_1");
			map.put("0x96a50b248a4c2d11dc1915f1a8cfba073153813e", "ROLE_COO_TEC");
			map.put("0x3534d506c874a4695fbcd8c9374fb1be53810b77", "ROLE_COO_TEC");
			map.put("0x2b6c448a8cf763424b52d761212602f5c55b9e0c", "ROLE_DIR_ADM_FIN");
			map.put("0xa592dbdd4fb61ff4ee3b3d009e07ff114f742751", "ROLE_DIR_ADJ");
			map.put("0xa592dbdd4fb61ff4ee3b3d009e07ff114f742751", "ROLE_DIR_EJE");
			map.put("0xfe50bbbd7738a722e7d116f0c813711cf5bcb64b", "ROLE_DIR_ADQ");
			map.put("0x292163058da399742350d6f206aa26cdcb0cab00", "ROLE_ASI_DIR_EJE");
			map.put("0x40df6d4e9aed9e9a19889314dbbe57f7b14d54b1", "ROLE_DIR_ADJ");
			map.put("0x40df6d4e9aed9e9a19889314dbbe57f7b14d54b1", "ROLE_DIR_TRA");
			map.put("0xa5f02bada69ebe5589e1e8f830aa6376255cb508", "ROLE_ESP_ADQ");
			map.put("0xe105accd49a9283ab727acb143cd5bd8c20871c8", "ROLE_ASI_DIR_EJE");
			map.put("0xe105accd49a9283ab727acb143cd5bd8c20871c8", "ROLE_BID");
			map.put("0xe105accd49a9283ab727acb143cd5bd8c20871c8", "ROLE_CONT");
			map.put("0xe105accd49a9283ab727acb143cd5bd8c20871c8", "ROLE_COO_TEC");
			map.put("0xe105accd49a9283ab727acb143cd5bd8c20871c8", "ROLE_DIR_ADJ");
			map.put("0xe105accd49a9283ab727acb143cd5bd8c20871c8", "ROLE_DIR_ADM_FIN");
			map.put("0xe105accd49a9283ab727acb143cd5bd8c20871c8", "ROLE_DIR_ADQ");
			map.put("0xe105accd49a9283ab727acb143cd5bd8c20871c8", "ROLE_DIR_EJE");
			map.put("0xe105accd49a9283ab727acb143cd5bd8c20871c8", "ROLE_DIR_LEG");
			map.put("0xe105accd49a9283ab727acb143cd5bd8c20871c8", "ROLE_DIR_TRA");
			map.put("0xe105accd49a9283ab727acb143cd5bd8c20871c8", "ROLE_ESP_ADQ");
			map.put("0xe105accd49a9283ab727acb143cd5bd8c20871c8", "ROLE_SUP");
			map.put("0x86bb27313f75cdf9f88d399442774fb35e61808e", "ROLE_COO_TEC");
			map.put("0x86bb27313f75cdf9f88d399442774fb35e61808e", "ROLE_DIR_ADJ");
			map.put("0x86bb27313f75cdf9f88d399442774fb35e61808e", "ROLE_DIR_ADM_FIN");
			map.put("0xb9f899794187d05fd0fd7d56d14ccfe371c5af22", "ROLE_ASI_DIR_EJE");
			map.put("0xb9f899794187d05fd0fd7d56d14ccfe371c5af22", "ROLE_CONT");
			map.put("0xb9f899794187d05fd0fd7d56d14ccfe371c5af22", "ROLE_COO_TEC");
			map.put("0xb9f899794187d05fd0fd7d56d14ccfe371c5af22", "ROLE_DIR_ADJ");
			map.put("0xb9f899794187d05fd0fd7d56d14ccfe371c5af22", "ROLE_DIR_ADM_FIN");
			map.put("0xb9f899794187d05fd0fd7d56d14ccfe371c5af22", "ROLE_DIR_ADQ");
			map.put("0xb9f899794187d05fd0fd7d56d14ccfe371c5af22", "ROLE_DIR_EJE");
			map.put("0xb9f899794187d05fd0fd7d56d14ccfe371c5af22", "ROLE_DIR_LEG");
			map.put("0xb9f899794187d05fd0fd7d56d14ccfe371c5af22", "ROLE_DIR_TRA");
			map.put("0xb9f899794187d05fd0fd7d56d14ccfe371c5af22", "ROLE_ESP_ADQ");
			map.put("0xb9f899794187d05fd0fd7d56d14ccfe371c5af22", "ROLE_SUP");
			map.put("0xf83a127752986ed84b784623da992c5a4d1e3f82", "ROLE_1");
			map.put("0xf83a127752986ed84b784623da992c5a4d1e3f82", "ROLE_DIR_TRA");
			map.put("0x0e7e1474acd16b51f1c65aa6af78d2da0366d30d", "ROLE_DIR_EJE");
			map.put("0x0e7e1474acd16b51f1c65aa6af78d2da0366d30d", "ROLE_SUP");
			map.put("0xcc4775dca38fd50c24b7eb397247525331836a98", "ROLE_DIR_ADM_FIN");
			map.put("0x85a4cad13e70621e38e848509d2f1d748ad79578", "ROLE_ASI_DIR_EJE");
			map.put("0xce07de26be21a8c9b59e86eeb123192aefe7c8d7", "ROLE_CONT");
			map.put("0x7af081cf474dce93335bbf2382889670d1147dd4", "ROLE_1");
			map.put("0x7af081cf474dce93335bbf2382889670d1147dd4", "ROLE_ASI_DIR_EJE");
			map.put("0x7af081cf474dce93335bbf2382889670d1147dd4", "ROLE_BID");
			map.put("0x7af081cf474dce93335bbf2382889670d1147dd4", "ROLE_COO_TEC");
			map.put("0x7af081cf474dce93335bbf2382889670d1147dd4", "ROLE_DIR_ADJ");
			map.put("0x7af081cf474dce93335bbf2382889670d1147dd4", "ROLE_DIR_ADM_FIN");
			map.put("0x7af081cf474dce93335bbf2382889670d1147dd4", "ROLE_DIR_ADQ");
			map.put("0x7af081cf474dce93335bbf2382889670d1147dd4", "ROLE_DIR_EJE");
			map.put("0x7af081cf474dce93335bbf2382889670d1147dd4", "ROLE_DIR_LEG");
			map.put("0x7af081cf474dce93335bbf2382889670d1147dd4", "ROLE_DIR_TRA");
			map.put("0x7af081cf474dce93335bbf2382889670d1147dd4", "ROLE_ESP_ADQ");
			map.put("0x9f407125bfb5eb45a27cd637cc85cc6f34c1b4ac", "ROLE_ASI_DIR_EJE");
			map.put("0x9f407125bfb5eb45a27cd637cc85cc6f34c1b4ac", "ROLE_SUP");
			map.put("0x3a3f9578aaccb4506fd3e4339c12ac21ba2e78b0", "ROLE_CONT");
			map.put("0xd74e166c99311b28061245001b4868ca34bd1cdf", "ROLE_COO_TEC");
			map.put("0xff55b8c680109278e9d5f31357677439c1c26b81", "ROLE_ASI_DIR_EJE");
			map.put("0xff55b8c680109278e9d5f31357677439c1c26b81", "ROLE_COO_TEC");
			map.put("0xff55b8c680109278e9d5f31357677439c1c26b81", "ROLE_DIR_ADJ");
			map.put("0xff55b8c680109278e9d5f31357677439c1c26b81", "ROLE_DIR_ADM_FIN");
			map.put("0xff55b8c680109278e9d5f31357677439c1c26b81", "ROLE_DIR_ADQ");
			map.put("0xff55b8c680109278e9d5f31357677439c1c26b81", "ROLE_DIR_EJE");
			map.put("0xff55b8c680109278e9d5f31357677439c1c26b81", "ROLE_ESP_ADQ");
			map.put("0xbe1a0191dbe1c639437d5ac9d2da515900eb7e23", "ROLE_DIR_ADJ");
			map.put("0xbe1a0191dbe1c639437d5ac9d2da515900eb7e23", "ROLE_DIR_ADQ");
			map.put("0xbe1a0191dbe1c639437d5ac9d2da515900eb7e23", "ROLE_DIR_EJE");
			map.put("0x48fe6c688df10b4d4f2a1da7d886ca0b5779e335", "ROLE_CONT");
			map.put("0xc62d8962d04429cda1b424b910e22d0822c28ed3", "ROLE_DIR_ADM_FIN");
			map.put("0x57d3905bd4e5ae78d88a448186d6b699c2b75665", "ROLE_COO_TEC");
			map.put("0x97c4d873028fa1f8953075f25e03e33d0f0c5d8e", "ROLE_DIR_ADM_FIN");
			map.put("0x155ce27ff3f0e651d90a5382d56cf16b59d57512", "ROLE_COO_TEC");
			map.put("0x155ce27ff3f0e651d90a5382d56cf16b59d57512", "ROLE_DIR_ADJ");
			map.put("0x155ce27ff3f0e651d90a5382d56cf16b59d57512", "ROLE_DIR_ADM_FIN");
			map.put("0x155ce27ff3f0e651d90a5382d56cf16b59d57512", "ROLE_DIR_ADQ");
			map.put("0x155ce27ff3f0e651d90a5382d56cf16b59d57512", "ROLE_DIR_EJE");
			map.put("0x8a120565a86ff33adc40bdc5c611206e3079b5d4", "ROLE_ASI_DIR_EJE");
			map.put("0x8a120565a86ff33adc40bdc5c611206e3079b5d4", "ROLE_BID");
			map.put("0x8a120565a86ff33adc40bdc5c611206e3079b5d4", "ROLE_CONT");
			map.put("0x8a120565a86ff33adc40bdc5c611206e3079b5d4", "ROLE_COO_TEC");
			map.put("0x8a120565a86ff33adc40bdc5c611206e3079b5d4", "ROLE_DIR_ADJ");
			map.put("0x8a120565a86ff33adc40bdc5c611206e3079b5d4", "ROLE_DIR_ADM_FIN");
			map.put("0x8a120565a86ff33adc40bdc5c611206e3079b5d4", "ROLE_DIR_ADQ");
			map.put("0x8a120565a86ff33adc40bdc5c611206e3079b5d4", "ROLE_DIR_EJE");
			map.put("0x8a120565a86ff33adc40bdc5c611206e3079b5d4", "ROLE_DIR_LEG");
			map.put("0x8a120565a86ff33adc40bdc5c611206e3079b5d4", "ROLE_DIR_TRA");
			map.put("0x8a120565a86ff33adc40bdc5c611206e3079b5d4", "ROLE_ESP_ADQ");
			map.put("0x8a120565a86ff33adc40bdc5c611206e3079b5d4", "ROLE_SUP");
			map.put("0x3b4e504b8c75f133950079b50b80d0f24ee11d3f", "ROLE_ASI_DIR_EJE");
			map.put("0x3b4e504b8c75f133950079b50b80d0f24ee11d3f", "ROLE_BID");
			map.put("0x3b4e504b8c75f133950079b50b80d0f24ee11d3f", "ROLE_CONT");
			map.put("0x3b4e504b8c75f133950079b50b80d0f24ee11d3f", "ROLE_COO_TEC");
			map.put("0x3b4e504b8c75f133950079b50b80d0f24ee11d3f", "ROLE_DIR_ADJ");
			map.put("0x3b4e504b8c75f133950079b50b80d0f24ee11d3f", "ROLE_DIR_ADM_FIN");
			map.put("0x3b4e504b8c75f133950079b50b80d0f24ee11d3f", "ROLE_DIR_ADQ");
			map.put("0x3b4e504b8c75f133950079b50b80d0f24ee11d3f", "ROLE_DIR_EJE");
			map.put("0x3b4e504b8c75f133950079b50b80d0f24ee11d3f", "ROLE_DIR_LEG");
			map.put("0x3b4e504b8c75f133950079b50b80d0f24ee11d3f", "ROLE_DIR_TRA");
			map.put("0x3b4e504b8c75f133950079b50b80d0f24ee11d3f", "ROLE_ESP_ADQ");
			map.put("0x3b4e504b8c75f133950079b50b80d0f24ee11d3f", "ROLE_SUP");

			for (Map.Entry<String, String> entry : map.entrySet()) {
				
				System.out.println("Key = " + entry.getKey() + 
                        ", Value = " + entry.getValue());
				
				String proxyAddress = entry.getKey();
				String role = entry.getValue();	

				if (!rolesContract.hasRoleUser(role, proxyAddress)) {
					log.info("NO TIENE ROL");

					log.info("addRoleUser role:{}, user:{}, admin:{}, im:{}", role, proxyAddress, addressAdmin,
							"0xb79e51f09d216d6b70278464e5dedd6164d0793b");

					String hash = rolesContract.setRoleUserIM(role, proxyAddress, addressAdmin,
							"0xb79e51f09d216d6b70278464e5dedd6164d0793b");

					log.info(" add ROLE = " + role + " - " + hash);
				} else {
					log.info("SI TIENE ROL");
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// @Test
	public void hasRole() throws Exception {

		String role = "ROLE_COO_TEC";
		String proxyAddress = "0x96a50b248a4c2d11dc1915f1a8cfba073153813e";

		if (!rolesContract.hasRoleUser(role, proxyAddress)) {
			log.info("NO TIENE ROL");

//						log.info("addRoleUser role:{}, user:{}, admin:{}, im:{}", role, proxyAddress, addressAdmin,
//								map.get(Constants.CONTRACT_ADDRESS_IDENTITY_MANAGER));
//
//						String hash = rolesContract.setRoleUserIM(role, proxyAddress, addressAdmin,
//								map.get(Constants.CONTRACT_ADDRESS_IDENTITY_MANAGER));
//
//						log.info(" add ROLE = " + role + " - " + hash);
		} else {
			log.info("SI TIENE ROL");
		}
	}

	// @Test
//	public void testIdentity() {
//		try {
//			String proxy = createIdentity("edgard3.espinoza@gmail.com");
//			asignRole("ROLE_DIR_ADM_FIN", map.get(Constants.CONTRACT_ADDRESS_PROXY_FIRST_ADMINISTRATOR), proxy);
//
//			asignRole(Constants.ROLE_ADMIN, proxy, proxy);
//
//		} catch (Exception e) {
//			System.out.println(Utils.getRevertReason(e));
//		}
//
//	}

//	private String createIdentity(String email) throws Exception {
//
//		return DIM.createIdentity("admin", "public", "", email, "");
//
//	}
//
//	private void asignRole(String role, String addressAdmin, String proxyAddress) throws Exception {
////"admin", "public", "", userqueue.getEmailUser(), ""
//
//		log.info("SECOND_ADMINISTRATOR : {}", proxyAddress);
//
//		if (!rolesContract.hasRoleUser(role, proxyAddress)) {
//
//			log.info("addRoleUser role:{}, user:{}, admin:{}, im:{}", role, proxyAddress, addressAdmin,
//					map.get(Constants.CONTRACT_ADDRESS_IDENTITY_MANAGER));
//
//			String hash = rolesContract.setRoleUserIM(role, proxyAddress, addressAdmin,
//					map.get(Constants.CONTRACT_ADDRESS_IDENTITY_MANAGER));
//
//			log.info(" add ROLE = " + role + " - " + hash);
//		}
//
//	}

}
