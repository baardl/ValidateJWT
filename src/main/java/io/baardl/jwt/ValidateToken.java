package io.baardl.jwt;

import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jwk.HttpsJwks;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.ErrorCodes;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.keys.resolvers.HttpsJwksVerificationKeyResolver;

import io.baardl.jwt.utils.FileUtils;

public class ValidateToken {

	public static boolean validateToken(String publicKeyUrl, String jwt) throws MalformedClaimException {
		HttpsJwks httpsJkws = new HttpsJwks(publicKeyUrl);

		HttpsJwksVerificationKeyResolver httpsJwksKeyResolver = new HttpsJwksVerificationKeyResolver(httpsJkws);

		JwtConsumer jwtConsumer = new JwtConsumerBuilder()
										  .setVerificationKeyResolver(httpsJwksKeyResolver)
//										  .setRequireExpirationTime() // the JWT must have an expiration time
//										  .setAllowedClockSkewInSeconds(30) // allow some leeway in validating time based claims to account for clock skew
//										  .setRequireSubject() // the JWT must have a subject claim
//										  .setExpectedIssuer("Issuer") // whom the JWT needs to have been issued by
//										  .setExpectedAudience("Audience") // to whom the JWT is intended for
//										  .setVerificationKey(rsaJsonWebKey.getKey()) // verify the signature with the public key
										  .setJwsAlgorithmConstraints( // only allow the expected signature algorithm(s) in the given context
																	   new AlgorithmConstraints(AlgorithmConstraints.ConstraintType.WHITELIST, // which is only RS256 here
																								AlgorithmIdentifiers.RSA_USING_SHA256))
										  .build();

		try {
			//  Validate the JWT and process it to the Claims
			JwtClaims jwtClaims = jwtConsumer.processToClaims(jwt);
			System.out.println("JWT validation succeeded! " + jwtClaims);
			return true;
		} catch (InvalidJwtException e) {
			// InvalidJwtException will be thrown, if the JWT failed processing or validation in anyway.
			// Hopefully with meaningful explanations(s) about what went wrong.
			System.out.println("Invalid JWT! " + e);

			// Programmatic access to (some) specific reasons for JWT invalidity is also possible
			// should you want different error handling behavior for certain conditions.

			// Whether or not the JWT has expired being one common reason for invalidity
			if (e.hasExpired()) {
				System.out.println("JWT expired at " + e.getJwtContext().getJwtClaims().getExpirationTime());
			}

			// Or maybe the audience was invalid
			if (e.hasErrorCode(ErrorCodes.AUDIENCE_INVALID)) {
				System.out.println("JWT had wrong audience: " + e.getJwtContext().getJwtClaims().getAudience());
			}
			return false;
		}

	}

	public static void main(String[] args) {
		String publicKeyUrl = System.getProperty("publicKeyUrl");
		System.out.println("publicKeyUrl: " + publicKeyUrl);
		String jwt = System.getProperty("jwt");
        if (jwt == null || jwt.isEmpty()) {
			FileUtils fileUtils = new FileUtils();
			jwt = fileUtils.readFileAsString("./jwt.txt");
		}
		try {
			ValidateToken.validateToken(publicKeyUrl, jwt);
		} catch (MalformedClaimException e) {
			System.out.println("Failed to validate jwt. Reason: " + e.getMessage());
		}
	}
}
