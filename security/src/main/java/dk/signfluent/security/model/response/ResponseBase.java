package dk.signfluent.security.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseBase {
    private boolean successful;
    private String message;

    public void setSuccessfulResponse() {
        this.successful = true;
        this.message = "Success";
    }

    public static ResponseBase failResponse() {
        return new ResponseBase(false, "Failed");
    }
}
