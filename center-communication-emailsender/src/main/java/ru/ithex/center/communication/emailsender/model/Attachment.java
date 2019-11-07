package ru.ithex.center.communication.emailsender.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.core.io.InputStreamSource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Attachment implements InputStreamSource {
    @JsonProperty("type")
    private final String type;

    @JsonProperty("fileName")
    private final String fileName;

    @JsonProperty("data")
    private final byte[] data;

    public Attachment(
            @JsonProperty("type") String type,
            @JsonProperty("fileName") String fileName,
            @JsonProperty("data") byte[] data) {
        this.type = type;
        this.fileName = fileName;
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public String getFileName() {
        return fileName;
    }

    public byte[] getData() {
        return data;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(data);
    }
}
