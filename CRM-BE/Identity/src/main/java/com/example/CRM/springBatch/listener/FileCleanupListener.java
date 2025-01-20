package com.example.CRM.springBatch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class FileCleanupListener implements JobExecutionListener {


    private static final Logger log = LoggerFactory.getLogger(FileCleanupListener.class);

    @Override
    public void beforeJob(JobExecution jobExecution) {
        // Không cần làm gì trước khi job bắt đầu

    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        // Xóa file tạm sau khi job hoàn thành
        String inputFilePath = jobExecution.getJobParameters().getString("inputFile");
//        File tempFile = new File(inputFilePath);
//        if (tempFile.exists()) {
//            tempFile.delete();
//        }
        log.info("Input file path: " + inputFilePath);
    }


}
