# Validate JWT

Simple tool to validate a JWT token.

# Usage

1. Create jwt.txt
2. Copy your JWT token to jwt.txt
3. `mvn clean package`
4. `java -jar target/ValidateJWT.jar -DpublicKeyUrl=https://.../adfs/discovery/keys`

# Attribution
Thank you to [Brian Campbell](https://bitbucket.org/b_c/jose4j/wiki/Home) from https://bitbucket.org/b_c/jose4j/wiki/JWT%20Examples