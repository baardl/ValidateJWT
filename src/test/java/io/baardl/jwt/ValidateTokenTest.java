package io.baardl.jwt;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ValidateTokenTest {

	@Test
	public void validateToken() throws Exception {
		String jwt = "<insert encoded jwt>";
		String publicKeyUrl = "https://....";
		assertTrue(ValidateToken.validateToken(publicKeyUrl, jwt));
	}
}