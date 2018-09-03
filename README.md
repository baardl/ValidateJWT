# Validate JWT

Simple tool to validate a JWT token.

# Usage

1. Create jwt.txt
2. Copy your JWT token to jwt.txt
3. `mvn clean package`
4. `java -jar target/ValidateJWT.jar -DpublicKeyUrl=https://.../adfs/discovery/keys`