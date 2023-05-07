package com.example.mytech.service;

import com.example.mytech.entity.Document;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface DocumentService {
    List<Document> findAll();
    Document findById(String id);
    Document save(Document document);
    void deleteById(String id);

    Document findByScheduleId(String scheduleId);
}