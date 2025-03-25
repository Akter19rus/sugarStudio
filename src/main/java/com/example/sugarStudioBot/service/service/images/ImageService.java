package com.example.sugarStudioBot.service.service.images;

import com.example.sugarStudioBot.service.model.Images;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageService {
    void saveImageInFile(MultipartFile multipartFile, String name) throws IOException;

    List<Images> fullImage();

    void uploadImageFileId(String nameImg, String fileId);
}
