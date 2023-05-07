package com.example.mytech.service.impl;

import com.example.mytech.entity.Document;
import com.example.mytech.repository.DocumentRepository;
import com.example.mytech.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DocumentServiceImpl implements DocumentService {
    @Autowired
    private DocumentRepository documentRepository;

    @Override
    public List<Document> findAll() {
        return documentRepository.findAll();
    }

    @Override
    public Document findById(String id) {
        return documentRepository.findById(id).orElse(null);
    }

    @Override
    public Document save(Document document) {
        return documentRepository.save(document);
    }

    @Override
    public void deleteById(String id) {
        documentRepository.deleteById(id);
    }
}