package dk.signfluent.security.model.response;

import dk.signfluent.security.model.ContentBasedRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SignatureResponse extends ResponseBase implements ContentBasedRequest {
    private String base64Content;

    public SignatureResponse(String base64Content) {
        this.base64Content = base64Content;
        setSuccessfulResponse();
    }
}
