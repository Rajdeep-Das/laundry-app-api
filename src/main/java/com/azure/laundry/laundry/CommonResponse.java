package com.azure.laundry.laundry;

import java.util.Date;

public class CommonResponse {
    private boolean error = false; // default
    private int status;
    private Date timestamp = new Date();
    private String message;
    private String description;
    private Object data;

    

    public CommonResponse() {
    }

    public CommonResponse(boolean error,int statusCode, Date timestamp, String message, String description, Object data) {
        this.error = error;
        this.status = statusCode;
        this.timestamp = timestamp;
        this.message = message;
        this.description = description;
        this.data = data;
    }

    

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    

}
