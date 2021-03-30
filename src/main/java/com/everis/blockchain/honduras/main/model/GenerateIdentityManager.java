package com.everis.blockchain.honduras.main.model;

import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.everis.blockchain.honduras.IRoles;
import com.everis.blockchain.honduras.impl.DigitalIdentityImpl;
import com.everis.blockchain.honduras.impl.RolesImpl;
import com.everis.blockchain.honduras.util.EthCoreParams;
import com.everis.blockchain.honduras.util.Utils;

public class GenerateIdentityManager {

	private static final Logger log = LoggerFactory.getLogger("GenerateIdentityManager");

	private EthCoreParams ethCoreParams;
	private DigitalIdentityImpl DIM;
	// private String proxyAddressAduana ;
	private LinkedHashMap<String, String> map;
//	private RolesImpl roles;

	public GenerateIdentityManager(LinkedHashMap<String, String> map,  Map<String, String> headers) {
		this.map = map;

		ethCoreParams = new EthCoreParams(map.get(Constants.BLOCKCHAIN_SERVER), map.get(Constants.PRIVATE_KEY_BACKEND),
				null, BigInteger.valueOf(0), null);

		ethCoreParams.setHeaders(headers);
	}

	public void create() throws Exception {
	
		if (StringUtils.isEmpty(map.get(Constants.CONTRACT_ADDRESS_IDENTITY_MANAGER))) {
			deployIdentityManager();
		}else {
			DIM = new DigitalIdentityImpl(map.get(Constants.CONTRACT_ADDRESS_IDENTITY_MANAGER), ethCoreParams);
		}
		
		if (StringUtils.isEmpty(map.get(Constants.CONTRACT_ADDRESS_PROXY_FIRST_ADMINISTRATOR))) {
			createIdentity();
		}

		if (StringUtils.isEmpty(map.get(Constants.CONTRACT_ADDRESS_ROLES))) {
			deployRoles();
			setRoleUser();
		}

		

	}

	private void deployIdentityManager() throws Exception {
		DIM = new DigitalIdentityImpl(null, ethCoreParams);
		map.put(Constants.CONTRACT_ADDRESS_IDENTITY_MANAGER, DIM.getAddressIM());
		log.info("CONTRACT_ADDRESS_IDENTITY_MANAGER : " + map.get(Constants.CONTRACT_ADDRESS_IDENTITY_MANAGER));
	}

	private void createIdentity() throws Exception {

		String keyMnemonic = "admin";
		String keyProfile = "public";
		String urlProfile = "Qmxxx";
		String username = "administrator";
		String salt = "salt";

		String proxyAddress = DIM.createIdentity(keyMnemonic, keyProfile, urlProfile, username, salt);

		map.put(Constants.CONTRACT_ADDRESS_PROXY_FIRST_ADMINISTRATOR, proxyAddress);

		log.info("CONTRACT_ADDRESS_PROXY_FIRST_ADMINISTRATOR : "
				+ map.get(Constants.CONTRACT_ADDRESS_PROXY_FIRST_ADMINISTRATOR));

	}

	private void deployRoles() throws Exception {
		RolesImpl rolesT = new RolesImpl(Constants.DUMMY_ROLE, Constants.ROLE_ADMIN, Constants.ROLE_COO_TEC,
				ethCoreParams);

		String addressRoles = rolesT.deployRoles(Constants.ROLE_ADMIN, Constants.ROLE_COO_TEC);

		map.put(Constants.CONTRACT_ADDRESS_ROLES, addressRoles);

		log.info("CONTRACT_ADDRESS_ROLES : " + map.get(Constants.CONTRACT_ADDRESS_ROLES));

	}

	private void setRoleUser() throws Exception {
		try {
			IRoles iRoles = new RolesImpl(map.get(Constants.CONTRACT_ADDRESS_ROLES), Constants.ROLE_ADMIN,
					Constants.ROLE_COO_TEC, ethCoreParams);

			if (!iRoles.hasRoleUser(Constants.ROLE_ADMIN,
					map.get(Constants.CONTRACT_ADDRESS_PROXY_FIRST_ADMINISTRATOR))) {
				log.info("add " + Constants.ROLE_ADMIN + " user admin");
				iRoles.setRoleUser(Constants.ROLE_ADMIN, map.get(Constants.CONTRACT_ADDRESS_PROXY_FIRST_ADMINISTRATOR));
			}
		} catch (Exception e) {
		
			log.error(Utils.getRevertReason(e));

			throw e;
		}
	}

}
