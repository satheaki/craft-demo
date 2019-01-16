package com.intuit.craftdemo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@JsonIgnoreProperties(ignoreUnknown = true)
@Document(collection = "tweet")
public class Tweet {
    @Id
    private String id;
    private String createdByUser;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private String timestamp;

    @NotBlank(message = "message cannot be empty")
    @Size(max = 140)
    private String message;

    Tweet() {

    }

    @JsonIgnore
    @JsonProperty(value = "id")
    public String getId() {
        return id;
    }

    public String getCreatedByUser() {
        return createdByUser;
    }

    public void setCreatedByUser(String createdByUser) {
        this.createdByUser = createdByUser;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
