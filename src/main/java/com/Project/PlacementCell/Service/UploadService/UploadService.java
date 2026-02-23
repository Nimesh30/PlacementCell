package com.Project.PlacementCell.Service.UploadService;

import com.Project.PlacementCell.DTO.ResumeUploadDTO.UploadResponse;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;


@Service
public class UploadService {

    @Autowired
    private Cloudinary cloudinary;

    public UploadResponse uploadImage(@Nonnull MultipartFile file) throws IOException {

        if (file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }
        Map<String, Object> uploadResult = cloudinary.uploader().upload(
                file.getBytes(),
                ObjectUtils.emptyMap()
        );

        return new UploadResponse(
                uploadResult.get("secure_url").toString(),
                uploadResult.get("public_id").toString()
        );
    }
}




