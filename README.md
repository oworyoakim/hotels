# Hotels Project
Create SSL certificates
The MicroProfile JWT RBAC specification requires JSON Web Tokens to be signed with the RSA-256 signature algorithm. To achieve this requirement, we will need to generate an RSA public key pair. Quarkus will generate a Token with the RSA private Key and verify its validity with the RSA public key generated from the RSA private key.
Execute the 3 following commands and copy the privateKey.pem and publicKey.pem generated files into src/main/resources/token

```
$ openssl genrsa -out rsaPrivateKey.pem 2048
$ openssl rsa -pubout -in rsaPrivateKey.pem -out publicKey.pem
$ openssl pkcs8 -topk8 -nocrypt -inform pem -in rsaPrivateKey.pem -outform pem -out privateKey.pem
```