package com.sample.tokengenerator;

import static org.junit.Assert.assertTrue;

import java.util.HashSet;

import org.junit.Test;

public class TokenGeneratorTest {

  @Test
  public void testUUIDUnicity() throws Exception {
    HashSet<String> tokens = new HashSet<>();

    for (int i = 0; i < 1000000; i++) {
      new TokenGeneratorService();
      String generatedToken = TokenGeneratorService.generateUUID();
      if (tokens.contains(generatedToken)) {
        throw new Exception();
      }
      tokens.add(generatedToken);
    }
    assertTrue(true);
  }
  
  @Test
  public void testHashUnicity() throws Exception {
    HashSet<String> tokens = new HashSet<>();

    for (int i = 0; i < 1000000; i++) {
      new TokenGeneratorService();
      String generatedToken = TokenGeneratorService.generateHashedUUID();
      if (tokens.contains(generatedToken)) {
        throw new Exception();
      }
      tokens.add(generatedToken);
    }
    assertTrue(true);
  }
}
