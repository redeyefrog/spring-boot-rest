package com.redeyefrog.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FileModel {

    @JsonProperty("FILE_NAME")
    private String fileName;

    @JsonProperty(value = "FILE_CONTENT", access = JsonProperty.Access.WRITE_ONLY)
    private String fileContent;

    @JsonProperty("FILE_SIZE")
    private String fileSize;

}
