package dk.signfluent.security.model.request;

import dk.signfluent.security.model.ContentBasedRequest;
import dk.signfluent.security.model.P12BasedRequest;
import lombok.Data;

@Data
public class SignatureRequest implements ContentBasedRequest, P12BasedRequest {
    private String p12Base64;
    private String p12Password;
    private String base64Content;
}
