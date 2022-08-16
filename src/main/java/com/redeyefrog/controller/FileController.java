package com.redeyefrog.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redeyefrog.model.FileModel;
import com.redeyefrog.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/file")
@RestController
public class FileController {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public String upload(@RequestBody FileModel fileModel) throws JsonProcessingException {
        log.info("Json->{}", objectMapper.writeValueAsString(fileModel));
        log.info("FileModel->{}", fileModel);

        return fileService.mockFileUpload(fileModel);
    }

}
