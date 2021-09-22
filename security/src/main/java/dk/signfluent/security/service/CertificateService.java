package dk.signfluent.security.service;

import dk.signfluent.security.model.P12BasedRequest;
import dk.signfluent.security.model.request.SignatureRequest;
import dk.signfluent.security.model.request.VerificationRequest;
import dk.signfluent.security.model.response.ResponseBase;
import dk.signfluent.security.model.response.SignatureResponse;
import dk.signfluent.security.model.response.VerificationResponse;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;

@Service
public class CertificateService {
    public ResponseBase sign(SignatureRequest signatureRequest) {
        try {
            Signature signature = getSignatureInstance();
            signature.initSign(getPrivateKey(signatureRequest));
            signature.update(getBytes(signatureRequest.getBase64Content()));
            byte[] digitalSignature = signature.sign();
            return new SignatureResponse(encodeBytes(digitalSignature));
        } catch (Exception e) {
            return SignatureResponse.failResponse();
        }
    }

    public ResponseBase verify(VerificationRequest verificationRequest) {
        try {
            Signature signature = getSignatureInstance();
            signature.initVerify(getPublicKey(verificationRequest));
            signature.update(getBytes(verificationRequest.getBase64Content()));
            boolean isCorrect = signature.verify(getBytes(verificationRequest.getBase64Signature()));
            return new VerificationResponse(isCorrect);
        } catch (Exception e) {
            return VerificationResponse.failResponse();
        }
    }

    private PublicKey getPublicKey(VerificationRequest verificationRequest) throws CertificateException {
        X509Certificate x509Certificate = parseX509FromByteArray(getBytes(verificationRequest.getBase64X509()));
        return x509Certificate.getPublicKey();
    }

    private X509Certificate parseX509FromByteArray(byte[] bytes) throws CertificateException {
        CertificateFactory instance = CertificateFactory.getInstance("X.509");
        return (X509Certificate) instance.generateCertificate(new ByteArrayInputStream(bytes));
    }

    private PrivateKey getPrivateKey(SignatureRequest signatureRequest) throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException, UnrecoverableKeyException {
        KeyStore keyStore = getKeyStore(signatureRequest);
        String alias = keyStore.aliases().nextElement();
        return (PrivateKey) keyStore.getKey(alias, signatureRequest.getP12Password().toCharArray());
    }

    private KeyStore getKeyStore(P12BasedRequest p12BasedRequest) throws KeyStoreException, CertificateException, IOException, NoSuchAlgorithmException {
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        keyStore.load(
                new ByteArrayInputStream(getBytes(p12BasedRequest.getP12Base64())),
                p12BasedRequest.getP12Password().toCharArray()
        );
        return keyStore;
    }

    private byte[] getBytes(String content) {
        return Base64.getDecoder().decode(content);
    }

    private String encodeBytes(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    private Signature getSignatureInstance() throws NoSuchAlgorithmException {
        return Signature.getInstance("SHA256withRSA");
    }
}
