package com.tbagrel1.lunch.api.security;

import java.security.Key;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import io.jsonwebtoken.SignatureAlgorithm;

public class SecurityConstants {
    // TODO: change
    private static final String SECRET_HEX = "c3f443f9ee809323efe00164aa90a4faf85fa340725337ca695309d76432cd2c";
    public static final long EXPIRATION_TIME = 86_400_000L * 365L;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String TOKEN_ISSUER = "lunch.tbagrel1.com";

    public static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;
    public static final byte[] SECRET_BYTES = DatatypeConverter.parseHexBinary(SECRET_HEX);
    public static Key SIGNING_KEY = new SecretKeySpec(SECRET_BYTES, SIGNATURE_ALGORITHM.getJcaName());
}
