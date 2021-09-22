package dk.signfluent.security.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class VerificationResponse extends ResponseBase {
    private boolean isCorrectSignature;

    public VerificationResponse(boolean isCorrectSignature) {
        this.isCorrectSignature = isCorrectSignature;
        setSuccessfulResponse();
    }
}
