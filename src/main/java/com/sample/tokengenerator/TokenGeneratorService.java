package com.sample.tokengenerator;

import java.security.MessageDigest;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.codec.digest.DigestUtils;

import com.groupon.uuid.UUID;

public class TokenGeneratorService {

  private static final String CHARSET_NAME = "UTF-8";
  private static final String HMACSHA256 = "HmacSHA256";
  
  public static String generateUUID() {
    UUID generated = new UUID();

    return generated.toString();
  }

  public static String generateShaUUID() {
    String ts = String.valueOf(System.currentTimeMillis());
    UUID generated = new UUID();
    
    return DigestUtils.shaHex(ts + generated);
  }

  public static String generateHashedUUID() throws Exception {
    String secretKey = "secrety_key";
    UUID generated = new UUID();

    final SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), HMACSHA256);

    Mac mac = Mac.getInstance(HMACSHA256);
    mac.init(secretKeySpec);

    byte[] rawHmac = mac.doFinal(generated.toString().getBytes(CHARSET_NAME));

    return DatatypeConverter.printBase64Binary(rawHmac).replace("\\n", "");

  }

  public static String generatePKCUUID() throws Exception {
    String secretKey = "secrety_key";

    final SecretKeySpec secretKeySpec = new SecretKeySpec(generateAESKey(secretKey), "AES");

    UUID generated = new UUID();

    Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
    cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

    byte[] rawHmac = cipher.doFinal(generated.toString().getBytes(CHARSET_NAME));

    return DatatypeConverter.printBase64Binary(rawHmac).replace("\\n", "");

  }

  public static byte[] generateAESKey(String myKey) throws Exception {
    byte[] key;
    MessageDigest sha = null;
    try {
      key = myKey.getBytes("UTF-8");
      sha = MessageDigest.getInstance("SHA-1");
      key = sha.digest(key);
      key = Arrays.copyOf(key, 16);
      return key;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
}
