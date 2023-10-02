package com.project.SEP_BE_EmployeeManagement.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileManagerService {
    @Autowired
    ServletContext app;
    @Value("${upload.image.path}")
    private String fileUploadImage;

    @Value("${upload.contract.path}")
    private String fileUploadContract;
    private Path getImagePath(String filename) {
        File dir = Paths.get(fileUploadImage).toFile();
        if(!dir.exists()) {
            dir.mkdirs();
        }
        return Paths.get(dir.getAbsolutePath(), filename);
    }

    private Path getContractPath(String filename) {
        File dir = Paths.get(fileUploadContract).toFile();
        if(!dir.exists()) {
            dir.mkdirs();
        }
        return Paths.get(dir.getAbsolutePath(), filename);
    }



    public void delete( String filename) {
        Path path = this.getImagePath(filename);
        path.toFile().delete();
    }

    public List<String> list(String folder) {
        List<String> filenames = new ArrayList<>();
        File dir = Paths.get(app.getRealPath("/files/"), folder).toFile();
        if(dir.exists()) {
            File[] files = dir.listFiles();
            for (File file : files) {
                filenames.add(file.getName());
            }
        }
        return filenames;
    }

    public String saveUserImage(MultipartFile file) {
        try {
            String name = System.currentTimeMillis() + file.getOriginalFilename();
            String filename = Integer.toHexString(name.hashCode()) + name.substring(name.lastIndexOf("."));
            Path path = this.getImagePath(filename);
                file.transferTo(path);
                return  filename;
            } catch (Exception e) {
                e.printStackTrace();
                return "default.png";
            }
    }

    public String saveUserContract(MultipartFile file) {
        try {
            String name = System.currentTimeMillis() + file.getOriginalFilename();
            String filename = Integer.toHexString(name.hashCode()) + name.substring(name.lastIndexOf("."));
            Path path = this.getContractPath(filename);
            file.transferTo(path);
            return  filename;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
