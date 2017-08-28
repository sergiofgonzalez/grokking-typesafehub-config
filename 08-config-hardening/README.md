# 08 &mdash; Config Hardening
> securely transmitting configuration with your code base

## Description
+ Decrypted configuration files should not be committed
+ Encryption keys should not be committed for the same reason
+ Encrypted secrets are OK to be committed within your code base
+ Different keys per environment (profiles), probably with opinionated names

+ Steps:
  1. Create a private key that won't be shared with anyone, nor committed in the repo
  2. Obtain an initialization vector, which does not have to be secret but must not be known in advance
  3. Use a helper tool/program to encrypt sensitive data
  4. In your configuration file, use `cipher(<encrypted>)`
  
  
### Useful commands

#### Generate key in the keystore
keytool -genseckey -alias jceksaes -keyalg AES -keysize 256 -storetype JCEKS -keypass mykeypass -storetype jceks -keystore config-keystore.jck -storepass mystorepass

#### List the Contents of an existing Keystore 
keytool.exe -list -keystore config-keystore.jck -storetype jceks -storepass mystorepass

  
C:\Users\sergio.f.gonzalez\git\grokking-typesafehub-config\08-config-hardening\src\main\resources>c:\dev-sw\jdk1.8.0_144\bin\keytool.exe -list -keystore config-keystore.jck -storetype jceks -storepass mystorepass  


### Information Reviewed (in credits order)
https://www.mkyong.com/java/java-symmetric-key-cryptography-example/
https://commons.apache.org/proper/commons-crypto/xref-test/org/apache/commons/crypto/examples/CipherByteArrayExample.html
https://docs.oracle.com/javase/8/docs/technotes/guides/security/crypto/CryptoSpec.html#KeystoreImplementation
https://github.com/mike-ensor/aes-256-encryption-utility/blob/master/src/main/java/com/acquitygroup/encryption/KeystoreUtil.java
http://www.ensor.cc/2014/02/aes-256-encryption-with-java-and-jceks.html