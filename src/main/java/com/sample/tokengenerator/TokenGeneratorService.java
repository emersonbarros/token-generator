package com.sample.tokengenerator;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import com.groupon.uuid.UUID;

public class TokenGeneratorService {

  private static final String CHARSET_NAME = "UTF-8";
  private static final String HMACSHA256 = "HmacSHA256";

  public static String generateUUID() {
    UUID generated = new UUID();

    return generated.toString();
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
}
