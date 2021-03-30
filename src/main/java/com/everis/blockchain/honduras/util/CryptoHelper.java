package com.everis.blockchain.honduras.util;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;

class CryptoHelper {

    private static final byte JWT_PART_SEPARATOR = (byte)46;


    /**
     * Verify signature for JWT header and payload.
     *
     * @param algorithm algorithm name.
     * @param publicKey algorithm public key.
     * @param header JWT header.
     * @param payload JWT payload.
     * @param signatureBytes JWT signature.
     * @return true if signature is valid.
     * @throws NoSuchAlgorithmException if the algorithm is not supported.
     * @throws InvalidKeyException if the given key is inappropriate for initializing the specified algorithm.
     */

    boolean verifySignatureFor(String algorithm, PublicKey publicKey, String header, String payload, byte[] signatureBytes)  throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        return verifySignatureFor(algorithm, publicKey, header.getBytes(StandardCharsets.UTF_8), payload.getBytes(StandardCharsets.UTF_8), signatureBytes);
    }

    /**
     * Verify signature for JWT header and payload using a public key.
     *
     * @param algorithm algorithm name.
     * @param publicKey the public key to use for verification.
     * @param headerBytes JWT header.
     * @param payloadBytes JWT payload.
     * @param signatureBytes JWT signature.
     * @return true if signature is valid.
     * @throws NoSuchAlgorithmException if the algorithm is not supported.
     * @throws InvalidKeyException if the given key is inappropriate for initializing the specified algorithm.
     */

    boolean verifySignatureFor(String algorithm, PublicKey publicKey, byte[] headerBytes, byte[] payloadBytes, byte[] signatureBytes) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
    	final Signature s = Signature.getInstance(algorithm);
        s.initVerify(publicKey);
        s.update(headerBytes);
        s.update(JWT_PART_SEPARATOR);
        s.update(payloadBytes);
        return s.verify(signatureBytes);
    }

    /**
     * Create signature for JWT header and payload using a private key.
     *
     * @param algorithm algorithm name.
     * @param privateKey the private key to use for signing.
     * @param headerBytes JWT header.
     * @param payloadBytes JWT payload.
     * @return the signature bytes.
     * @throws NoSuchAlgorithmException if the algorithm is not supported.
     * @throws InvalidKeyException if the given key is inappropriate for initializing the specified algorithm.
     * @throws SignatureException if this signature object is not initialized properly or if this signature algorithm is unable to process the input data provided.
     */

    byte[] createSignatureFor(String algorithm, PrivateKey privateKey, byte[] headerBytes, byte[] payloadBytes) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        final Signature s = Signature.getInstance(algorithm);
        s.initSign(privateKey);
        s.update(headerBytes);
        s.update(JWT_PART_SEPARATOR);
        s.update(payloadBytes);
        return s.sign();
    }




}