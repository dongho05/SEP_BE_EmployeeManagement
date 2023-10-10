package com.project.SEP_BE_EmployeeManagement.controller;

import com.project.SEP_BE_EmployeeManagement.model.User;
import com.project.SEP_BE_EmployeeManagement.repository.UserRepository;
import com.project.SEP_BE_EmployeeManagement.service.impl.FileManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("api/file")
@CrossOrigin(origins = "*", maxAge = 3600)
public class FileController {
    @Autowired
    FileManagerService fileManagerService;
    @Value("${upload.image.path}")
    private String fileUploadImage;

    @Value("${upload.contract.path}")
    private String fileUploadContract;
    @Autowired
    UserRepository userRepository;
    @GetMapping("info")
    public Map<String,String> download () {
        List<User> user = userRepository.findAll();
        Map<String,String> listInfo = new HashMap<>();
        for(User u : user){
        String pathFile = "http://localhost:2000/"+u.getUserImage();
        listInfo.put(pathFile,u.getUserCode());
        }
        return listInfo;
    }

    @GetMapping("/avatar/{imageName}")
    public ResponseEntity<Resource> getImage(@PathVariable String imageName) {
        try {
            Path imagePath = Paths.get(fileUploadImage +"/"+ imageName);
            Resource resource = new UrlResource(imagePath.toUri());

            if (resource.exists()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, "image/png") // Thay đổi kiểu MIME theo loại ảnh của bạn
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/contract/{contractFile}")
    public ResponseEntity<Resource> getContract(@PathVariable String contractFile) {
        try {
            Path contractPath = Paths.get(fileUploadContract +"/"+ contractFile);
            String pdfFilePath = fileUploadContract +"/"+ contractFile;
            // Đọc file PDF thành một InputStream
            FileInputStream pdfFileInputStream = new FileInputStream(pdfFilePath);

            // Tạo một InputStreamResource từ InputStream
            Resource resource = new InputStreamResource(pdfFileInputStream);

            // Trả về file PDF với HTTP header phù hợp
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+contractFile+".pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
