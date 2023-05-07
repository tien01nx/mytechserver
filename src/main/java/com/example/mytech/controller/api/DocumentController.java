package com.example.mytech.controller.api;

import com.example.mytech.entity.Document;
import com.example.mytech.entity.Schedule;
import com.example.mytech.service.DocumentService;
import com.example.mytech.service.FileStorageService;
import com.example.mytech.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping
    public ResponseEntity<List<Document>> getAllDocuments() {
        return ResponseEntity.ok(documentService.findAll());
    }

    // lấy dữ liệu theo ngày học nhưng đang truyền nhầm id chưa fix đang lấy theo id Document chứ k phải idSchedule
    @GetMapping("/schedule/{scheduleId}")
    public ResponseEntity<Document> getDocumentByScheduleId(@PathVariable String scheduleId) {
        Document document = documentService.findByScheduleId(scheduleId);
        if (document == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(document);
    }

    // tạo tài liệu cho buổi học
    @PostMapping("/upload/{scheduleId}")
    public ResponseEntity<Document> uploadFile(@RequestParam("file") MultipartFile file,
                                               @PathVariable("scheduleId") String scheduleId,
                                               @RequestParam("date") String date,
                                               @RequestParam("description") String description) {
        Schedule schedule = scheduleService.getScheduleById(scheduleId);
        if (schedule == null) {
            return ResponseEntity.notFound().build();
        }

        String fileName = fileStorageService.storeFile(file);
        Document document = new Document();
        document.setSchedule(schedule);
        document.setDate(date);
        document.setFilePath(fileName);
        document.setDescription(description);

        return ResponseEntity.status(HttpStatus.CREATED).body(documentService.save(document));
    }


    @PostMapping
    public ResponseEntity<Document> createDocument(@RequestBody Document document) {
        return ResponseEntity.status(HttpStatus.CREATED).body(documentService.save(document));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Document> updateDocument(@PathVariable String id, @RequestBody Document document) {
        Document existingDocument = documentService.findById(id);
        if (existingDocument == null) {
            return ResponseEntity.notFound().build();
        }
        document.setId(id);
        return ResponseEntity.ok(documentService.save(document));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable String id) {
        Document document = documentService.findById(id);
        if (document == null) {
            return ResponseEntity.notFound().build();
        }
        documentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
