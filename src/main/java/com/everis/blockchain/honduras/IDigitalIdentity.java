package com.everis.blockchain.honduras;

import java.io.IOException;
import java.math.BigInteger;

public interface IDigitalIdentity {

	/**
	 * This method is used to deploy a new Identity Manager contract.
	 * 
	 * @return This returns the contract address.
	 */
	String deployIM() throws IOException, Exception;

	/**
	 * This method is used to create a new identity.
	 * 
	 * @param keyMnemonic Key where the salt and username are stored in contract Id.
	 * @param keyProfile  Key where the public profile is stored in contract Id.
	 * @param urlProfile  Url where the user's public profile is stored.
	 * @param username    Username with which you can search for an identity through
	 *                    events.
	 * @param salt        It's used as part of the private key recovery text string.
	 *                    Note: Username can be optional, if so the salt has no
	 *                    relevance.
	 * @return This returns the contract address.
	 */
	String createIdentity(String keyMnemonic, String keyProfile, String urlProfile, String username, String salt)
			throws Exception;

	/**
	 * This method is used to check the capability of a device over an identity.
	 * 
	 * @param identity Proxy address of the user.
	 * @param device   Address to validate capability in IM.
	 * @param cap      Capability to validate.
	 * @return Boolean
	 */
	Boolean hasCap(String identity, String device, String cap) throws Exception;

	/**
	 * This method is used to set Capability of device over an identity
	 * @param identity Proxy address of the user
	 * @param device  Address to validate capability in IM.
	 * @param cap  Capability to validate.
	 * @param startDate 
	 * @param endDate
	 * @return tx
	 */
	public String setCap(String identity, String device, String cap, BigInteger startDate, BigInteger endDate)
			throws Exception;

	/**
	 * This method is used to forward a transaction through IM using user's
	 * identity.
	 * 
	 * @param identity    Proxy address of the user.
	 * @param destination Address of the contract where the transaction will be
	 *                    execute.
	 * @param value       Value sent to a function if payable.
	 * @param data        Bytecode representation of the function to execute in the
	 *                    destination contract.
	 * @return Transaction hash.
	 */
	String forwardTo(String identity, String destination, BigInteger value, byte[] data) throws Exception;

	/**
	 * validate if a Proxy Address exists in this contract identity manager
	 * @param address : Proxy Address
	 * @return true if exists Proxy Address
	 */
	boolean existsProxyAddress(String address);

}