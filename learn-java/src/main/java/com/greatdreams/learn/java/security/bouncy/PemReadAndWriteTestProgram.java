package com.greatdreams.learn.java.security.bouncy;

import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.bouncycastle.util.io.pem.PemWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class PemReadAndWriteTestProgram {
    private static Logger logger = LoggerFactory.getLogger(PemReadAndWriteTestProgram.class);

    private static final int KEY_SIZE = 2048;

    public static void main(String[] args) {
        Security.addProvider(new BouncyCastleProvider());
        logger.info("BouncyCastle provider added.");

        KeyPair keyPair = generateRSAKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey)keyPair.getPrivate();
        RSAPublicKey publicKey = (RSAPublicKey)keyPair.getPublic();

        X509CertificateHolder holder = null;

        try {
            holder = generateCA(keyPair);
        } catch (OperatorCreationException e) {
            e.printStackTrace();
        }

        try {
            logger.debug("private key encoding format " + privateKey.getFormat());
            writePemFile(privateKey, "RSA PRIVATE KEY", "/Users/greatdreams/tmp/id_rsa");
            logger.debug("public key encoding format " + publicKey.getFormat());
            writePemFile(publicKey, "RSA PUBLIC KEY", "/Users/greatdreams/tmp/id_rsa.pub");
            writePemFile(holder, "CERTIFICATE", "/Users/greatdreams/tmp/ca.pem");

            openPEMResource("/Users/greatdreams/tmp/id_rsa");
            openPEMResource("/Users/greatdreams/.ssh/id_rsa.pub");
            openPEMResource("/Users/greatdreams/tmp/ca.pem");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writePemFile(Key key, String description, String fileName) throws IOException {
        PemFile pemFile = new PemFile(key, description);
        pemFile.write(fileName);

        logger.info(String.format("%s successfully writen in file %s.", description, fileName));
    }

    private static void writePemFile(X509CertificateHolder holder, String description, String fileName) throws IOException {
        PemFile pemFile = new PemFile(holder, description);
        pemFile.write(fileName);

        logger.info(String.format("%s successfully writen in file %s.", description, fileName));
    }

    private static KeyPair generateRSAKeyPair() {
        String algorithm = "RSA";
        KeyPair keyPair = null;

        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(algorithm);
            keyPairGenerator.initialize(KEY_SIZE, new SecureRandom());
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return keyPair;
    }

    private static X509CertificateHolder generateCA(KeyPair keyPair) throws OperatorCreationException {
        LocalDateTime startDate = LocalDate.now().atStartOfDay();

        X509v3CertificateBuilder builder = new X509v3CertificateBuilder(
                new X500Name("CN=ca"),
                new BigInteger("0"),
                Date.from(startDate.atZone(ZoneId.systemDefault()).toInstant()),
                Date.from(startDate.plusDays(3650).atZone(ZoneId.systemDefault()).toInstant()),
                new X500Name("CN=ca"),
                SubjectPublicKeyInfo.getInstance(keyPair.getPublic().getEncoded()));

        JcaContentSignerBuilder csBuilder = new JcaContentSignerBuilder("SHA512WithRSAEncryption");
        ContentSigner signer = csBuilder.build(keyPair.getPrivate());
        X509CertificateHolder holder = builder.build(signer);

        return holder;
    }

    private static void openPEMResource(String fileName) {
        PEMParser pemParser = null;
        try {
            Reader fd = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileName))));
            pemParser = new PEMParser(fd);

            JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");
            Object o = null;

            while(( o = pemParser.readObject()) != null) {
                if(o instanceof PrivateKeyInfo) {
                    PrivateKey privateKey = converter.getPrivateKey((PrivateKeyInfo.getInstance(o)));
                }
                if(o instanceof SubjectPublicKeyInfo) {
                    PublicKey publicKey = converter.getPublicKey((SubjectPublicKeyInfo)o);
                }

               if(o instanceof PEMKeyPair) {
                   PEMKeyPair pemKeyPair = (PEMKeyPair)o;
                   PrivateKey privateKey = converter.getPrivateKey(pemKeyPair.getPrivateKeyInfo());
                   // PublicKey publicKey = converter.getPublicKey(pemKeyPair.getPublicKeyInfo());
               }

               if(o instanceof  X509CertificateHolder) {
                   X509CertificateHolder holder = (X509CertificateHolder) o;
               }
            }
        } catch (IOException e) {
            logger.error("error when loading " + fileName);
            e.printStackTrace();
        }
    }
}

class PemFile {
    private PemObject pemOject;
    public PemFile(Key key, String description) {
        this.pemOject = new PemObject(description, key.getEncoded());
    }
    public PemFile(X509CertificateHolder holder, String description) {
        try {
            this.pemOject = new PemObject(description, holder.toASN1Structure().getEncoded());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void write(String fileName) throws IOException {
        File file = new File(fileName);
        if(!file.exists()) {
            file.createNewFile();
        }
        PemWriter pemWriter = new PemWriter(new OutputStreamWriter(new FileOutputStream(fileName)));
        try {
            pemWriter.writeObject(this.pemOject);
        } finally {
            pemWriter.close();
        }
    }
}