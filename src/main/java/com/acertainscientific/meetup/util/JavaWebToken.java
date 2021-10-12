package com.acertainscientific.meetup.util;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.Map;


public class JavaWebToken {

    private static Logger log = LoggerFactory.getLogger(JavaWebToken.class);

    //generate key
    private static Key getKeyInstance() {
        //We will sign our JavaWebToken with our ApiKey secret
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary("MeetUpACertainScientific");        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        return signingKey;
    }

    /**generate token
     * @param claims  claims
     * @return  token
     */
    public static String createJavaWebToken(Map<String, Object> claims) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(nowMillis + 1000*60*60*24*7))
                .setIssuedAt(now)
                .setNotBefore(now)
                .signWith(SignatureAlgorithm.HS256, getKeyInstance())
                .compact();
    }

    /**analyz token
     * @param jwt  token
     * @return  map
     */
    public static Map<String, Object> parserJavaWebToken(String jwt) {
        try {
            Map<String, Object> jwtClaims =
                    Jwts.parser().setSigningKey(getKeyInstance()).parseClaimsJws(jwt).getBody();
            return jwtClaims;
        } catch (Exception e) {
            log.error("json web token verify failed : " + e.getMessage());
            return null;
        }
    }
}