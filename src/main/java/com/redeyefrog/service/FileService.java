package com.redeyefrog.service;

import com.redeyefrog.factory.RestFactory;
import com.redeyefrog.model.FileModel;
import com.redeyefrog.telegram.mock.MockFileRequest;
import com.redeyefrog.telegram.mock.MockFileResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class FileService {

    @Value("${mock.file.upload.url}")
    private String url;

    @Autowired
    private RestFactory restFactory;

    public String mockFileUpload(FileModel fileModel) {
        MockFileRequest mockFileRequest = new MockFileRequest();
        BeanUtils.copyProperties(fileModel, mockFileRequest);
        ResponseEntity<MockFileResponse> responseEntity = restFactory.callTelegram(url, mockFileRequest, MockFileResponse.class);
        return StringUtils.equals("0000", responseEntity.getBody().getReturnCode()) ? "SUCCESS" : "FAIL";
    }

}
