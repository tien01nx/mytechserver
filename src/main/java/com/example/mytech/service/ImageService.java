package com.example.mytech.service;

import com.example.mytech.entity.Image;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface ImageService {

    void createFolder (String path);

    String uploadFile (MultipartFile file) ;

    String getFileExtension(String  fileName);

    boolean checkFileExtension(String fileExtension );

    byte[] readFile(String fileId);

    void saveImage (Image image) ;

    public List<String> getListImageOfUser(String userId);
}
