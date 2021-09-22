package dk.signfluent.security.controller;

import dk.signfluent.security.model.request.SignatureRequest;
import dk.signfluent.security.model.request.VerificationRequest;
import dk.signfluent.security.model.response.ResponseBase;
import dk.signfluent.security.service.CertificateService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignatureController {
    private final CertificateService certificateService;

    public SignatureController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @PostMapping("/verify")
    public ResponseBase verifySignature(@RequestBody VerificationRequest verificationRequest) {
        return certificateService.verify(verificationRequest);
    }

    @PostMapping("/sign")
    public ResponseBase applySignature(@RequestBody SignatureRequest signatureRequest) {
        return certificateService.sign(signatureRequest);
    }
}
