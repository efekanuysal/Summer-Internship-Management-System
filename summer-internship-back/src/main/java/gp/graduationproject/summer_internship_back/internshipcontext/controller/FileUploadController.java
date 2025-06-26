package gp.graduationproject.summer_internship_back.internshipcontext.controller;

import gp.graduationproject.summer_internship_back.internshipcontext.service.FileStorageService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/files")
public class FileUploadController {

    private static final Logger logger = Logger.getLogger(FileUploadController.class.getName());

    private final FileStorageService fileStorageService;

    @Autowired
    public FileUploadController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    /**
     * 📌 Dosya yükleme endpoint'i
     */
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("⚠File is not Empty!");
            }
            String fileName = fileStorageService.storeFile(file);
            return ResponseEntity.ok("✅ File Uploaded: " + fileName);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("❌ Error Occured.");
        }
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        try {
            Resource file = fileStorageService.loadFile(fileName);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .body(file);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @PostMapping("/upload-cv/{username}")
    public ResponseEntity<?> uploadStudentCv(@PathVariable String username, @RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("❌ Error: No file uploaded.");
            }

            String fileName = fileStorageService.storeStudentCv(file, username);
            return ResponseEntity.ok("✅ CV Successfully Uploaded: " + fileName);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("❌ Upload Failed: " + e.getMessage());
        }
    }

    /**
     * 📌 CV dosyasını getirme endpoint’i
     */
    @GetMapping("/get-cv/{username}")
    public ResponseEntity<Resource> getStudentCv(@PathVariable String username) {
        Resource file = fileStorageService.loadStudentCv(username);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

}
