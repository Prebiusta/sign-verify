package dk.signfluent.security.model.request;

import dk.signfluent.security.model.ContentBasedRequest;
import lombok.Data;

@Data
public class VerificationRequest implements ContentBasedRequest {
    private String base64X509;
    private String base64Content;
    private String base64Signature;
}
