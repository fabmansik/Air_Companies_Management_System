package milansomyk.springboothw.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class ResponseContainer {
    private Object result;
    private String errorMessage;
    private boolean isError;
    @JsonIgnore
    private int statusCode;

    public ResponseContainer setErrorMessageAndStatusCode(String errorMessage, int statusCode) {
        this.isError = true;
        this.errorMessage = errorMessage;
        this.statusCode = statusCode;
        return this;
    }

    public ResponseContainer setSuccessResult(Object result){
        this.result = result;
        this.statusCode = HttpStatus.OK.value();
        return this;
    }
    public ResponseContainer setCreatedResult(Object result){
        this.result = result;
        this.statusCode = HttpStatus.CREATED.value();
        return this;
    }

}



