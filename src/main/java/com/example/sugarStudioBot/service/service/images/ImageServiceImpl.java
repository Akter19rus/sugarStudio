package com.example.sugarStudioBot.service.service.images;

import com.example.sugarStudioBot.service.model.Images;
import com.example.sugarStudioBot.service.repositories.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.apache.commons.io.FilenameUtils.getExtension;

@Service
@RequiredArgsConstructor
@Transactional
public class ImageServiceImpl implements ImageService {

    @Value("${path.to.avatars.folder}")
    private String imagesDir;

    private final ImageRepository imageRepository;

    @Override
    public void saveImageInFile(MultipartFile multipartFile, String name) throws IOException {
        String originalFileName = multipartFile.getOriginalFilename();
        String fileName = UUID.randomUUID() + "." + getExtension(Objects.requireNonNull(originalFileName));
        Path filePath = Path.of(imagesDir, fileName);

        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        readAndWriteDirectory(multipartFile, filePath);

        Images images = new Images();
        images.setFileSize(multipartFile.getSize());
        images.setFilePath(imagesDir + "/" + fileName);
        images.setMediaType(multipartFile.getContentType());
        images.setData(multipartFile.getBytes());
        images.setName(name);

        imageRepository.save(images);
    }

    private static void readAndWriteDirectory(MultipartFile multipartFile, Path filePath) throws IOException {
        try (
                InputStream inputStream = multipartFile.getInputStream();
                OutputStream outputStream = Files.newOutputStream(filePath, StandardOpenOption.CREATE_NEW);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream, 1000);
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream, 1000)
        ) {
            bufferedInputStream.transferTo(bufferedOutputStream);
        }
    }

    public List<Images> fullImage() {
        return imageRepository.findAll();
    }

    public void uploadImageFileId(String nameImg, String fileId) {
        Images images = imageRepository.findImagesByName(nameImg);
        images.setFileId(fileId);
        imageRepository.save(images);
    }
}


