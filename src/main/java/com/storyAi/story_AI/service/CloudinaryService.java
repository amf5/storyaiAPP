package com.storyAi.story_AI.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.buffer.DataBuffer;

import reactor.core.publisher.Flux;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Map;


@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;

    @Autowired
    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    // ✅ رفع صور بسرعة وكفاءة
    public String uploadFileFromStream(InputStream inputStream) throws IOException {
        try {
            Map<String, Object> uploadResult = cloudinary.uploader().uploadLarge(
                    inputStream,
                    ObjectUtils.asMap(
                            "resource_type", "image",
                            "chunk_size", 6_000_000, 
                            "folder", "images",       
                            "use_filename", true,
                            "unique_filename", true
                    )
            );
            return uploadResult.get("secure_url").toString(); // نستخدم secure_url
        } catch (Exception e) {
            throw new RuntimeException("Cloudinary image upload failed: " + e.getMessage(), e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    System.out.println("Error closing InputStream: " + e.getMessage());
                }
            }
        }
    }

    // ✅ رفع فيديوهات بكفاءة وسرعة
    public String uploadVideoFileFromDataBuffer(Flux<DataBuffer> videoDataBuffer) throws IOException {
        // إعداد أنابيب للتدفق من Flux<DataBuffer> إلى InputStream
        PipedOutputStream pos = new PipedOutputStream();
        PipedInputStream pis = new PipedInputStream(pos);

        // اشترك في Flux وادفع كل DataBuffer فور وصوله إلى PipedOutputStream
        videoDataBuffer.subscribe(buffer -> {
            try {
                byte[] bytes = new byte[buffer.readableByteCount()];
                buffer.read(bytes);
                pos.write(bytes);
            } catch (IOException e) {
                throw new RuntimeException("Error writing buffer to stream: " + e.getMessage(), e);
            }
        }, err -> {
            try { pos.close(); } catch (IOException ignored) {}
        }, () -> {
            try { pos.close(); } catch (IOException ignored) {}
        });

        // الآن ارفع الفيديو بشكل تدفقي chunk-by-chunk إلى Cloudinary
        try (InputStream in = pis) {
            Map<String, Object> uploadResult = cloudinary.uploader().uploadLarge(
                in,
                ObjectUtils.asMap(
                    "resource_type", "video",
                    "chunk_size", 10_000_000,
                    "folder", "videos",
                    "use_filename", true,
                    "unique_filename", true
                )
            );
            return uploadResult.get("secure_url").toString();
        } catch (Exception e) {
            throw new IOException("Cloudinary video upload failed: " + e.getMessage(), e);
        }
    }
    public String uploadVideoFile(MultipartFile videoFile) throws IOException {
        try (InputStream inputStream = videoFile.getInputStream()) {
            Map<String, Object> uploadResult = cloudinary.uploader().uploadLarge(
                inputStream,
                ObjectUtils.asMap(
                    "resource_type", "video",
                    "chunk_size", 10_000_000,
                    "folder", "videos",
                    "use_filename", true,
                    "unique_filename", true
                )
            );
            return uploadResult.get("secure_url").toString();
        } catch (Exception e) {
            throw new IOException("Cloudinary video upload failed: " + e.getMessage(), e);
        }
    }
    public String uploadVideoFile(File videoFile) throws IOException {
        try (InputStream inputStream = new FileInputStream(videoFile)) {
            Map<String, Object> uploadResult = cloudinary.uploader().uploadLarge(
                inputStream,
                ObjectUtils.asMap(
                    "resource_type", "video",
                    "chunk_size", 10_000_000,
                    "folder", "videos",
                    "use_filename", true,
                    "unique_filename", true
                )
            );
            return uploadResult.get("secure_url").toString();
        } catch (Exception e) {
            throw new IOException("Cloudinary video upload failed: " + e.getMessage(), e);
        }
    }
    
    public String uploadVideoToCloudinary(byte[] videoBytes) throws IOException {
        Map<String, Object> uploadResult = cloudinary.uploader()
                .upload(videoBytes, ObjectUtils.asMap("resource_type", "video"));
        return (String) uploadResult.get("url");
    }

    
    
    
    

}
