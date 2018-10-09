package com.greatdreams.learn.java.security.bouncy;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameStyle;
import org.bouncycastle.asn1.x500.style.RFC4519Style;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class GeneratingCertificateTestProgram {
    Logger loggger = LoggerFactory.getLogger(GeneratingCertificateTestProgram.class);

    public static void main(String[] args) throws NoSuchAlgorithmException, OperatorCreationException, IOException {
        // generate a keypair using Elliptic Curve algorithm
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
        keyPairGenerator.initialize(512, new SecureRandom());
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        LocalDateTime startDate = LocalDate.now().atStartOfDay();
        //build X509v3 certificate
        X509v3CertificateBuilder builder = new X509v3CertificateBuilder(
                new X500Name(RFC4519Style.INSTANCE, "C=China ST=Beijing L=Beijing O=greatdreams CN=*.verygoods.cn"),
                new BigInteger("1291029201920120192031"),
                Date.from(startDate.atZone(ZoneId.systemDefault()).toInstant()),
                Date.from(startDate.plusDays(365).atZone(ZoneId.systemDefault()).toInstant()),
                new X500Name("C=China ST=Beijing L=Beijing O=greatdreams CN=*.verygoods.cn"),
                SubjectPublicKeyInfo.getInstance(keyPair.getPublic().getEncoded())
        );

        // sign the certificate
        JcaContentSignerBuilder jcaContentSignerBuilder = new JcaContentSignerBuilder("SHA256WITHECDSA");
        ContentSigner contentSigner = jcaContentSignerBuilder.build(keyPair.getPrivate());
        X509CertificateHolder holder = builder.build(contentSigner);

        //save the certificate to disk with pem file format
        String fileName = "/Users/greatdreams/tmp/certificate.pem";
        PemObject pemObject = new PemObject("CERTIFICATE", holder.toASN1Structure().getEncoded());
        PemWriter writer = new PemWriter(new OutputStreamWriter(new FileOutputStream(fileName)));
        writer.writeObject(pemObject);
        writer.close();
    }
}
