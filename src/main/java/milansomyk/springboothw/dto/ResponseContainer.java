package milansomyk.springboothw.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.http.HttpStatus;


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
    public ResponseContainer setResultAndStatusCode(Object result, int statusCode){
        this.result = result;
        this.statusCode = statusCode;
        return this;
    }

    public ResponseContainer() {
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setErrorMessage(String errorMessage) {
        this.isError = true;
        this.errorMessage = errorMessage;
    }
    public ResponseContainer setSuccessResult(Object result){
        this.result = result;
        this.statusCode = HttpStatus.OK.value();
        return this;
    }
    public void setCreatedResult(Object result){
        this.result = result;
        this.statusCode = HttpStatus.CREATED.value();
    }

    public ResponseContainer(Object result, int statusCode){
        this.statusCode = statusCode;
        this.result = result;
    }
}



