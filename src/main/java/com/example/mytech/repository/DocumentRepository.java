package com.example.mytech.repository;

import com.example.mytech.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository<Document, String> {

    Document findBySchedule_Id(String scheduleId);
}