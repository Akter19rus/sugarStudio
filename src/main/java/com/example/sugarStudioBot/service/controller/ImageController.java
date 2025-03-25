package com.example.sugarStudioBot.service.controller;

import com.example.sugarStudioBot.service.controller.swagger.ImageControllerSwagger;
import com.example.sugarStudioBot.service.model.Images;
import com.example.sugarStudioBot.service.service.images.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/image")
public class ImageController implements ImageControllerSwagger {
    private final ImageService imageService;

    @PostMapping(value = "/{name}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadImages(@RequestPart MultipartFile image, @PathVariable String name) throws IOException {
        imageService.saveImageInFile(image, name);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/full")
    public ResponseEntity<List<Images>> getAllImages() {
        return ResponseEntity.ok(imageService.fullImage());
    }
}
