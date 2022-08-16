package com.redeyefrog.telegram.mock;

import lombok.Data;

@Data
public class MockFileRequest {

    private String fileName;

    private String fileContent;

    private String fileSize;

}
