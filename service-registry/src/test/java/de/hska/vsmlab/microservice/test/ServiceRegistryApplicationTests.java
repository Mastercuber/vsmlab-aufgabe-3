package de.hska.vsmlab.microservice.test;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

@SpringBootTest
class ServiceRegistryApplicationTests {

	@Test
	void contextLoads() {

	}

	@Test
	void generateKeystore() throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException {
		KeyStore pkcs12 = KeyStore.getInstance(KeyStore.getDefaultType());
		char[] chars = "Keystore-Password".toCharArray();
		pkcs12.load(null, chars);
		try (FileOutputStream fos = new FileOutputStream("cloud-keystore.pkcs12")) {
			pkcs12.store(fos, chars);
		}
	}

}
