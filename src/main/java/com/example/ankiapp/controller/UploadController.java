package com.example.ankiapp.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.example.ankiapp.service.CloudinaryService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UploadController {
    
    public final CloudinaryService cloudinaryService;
    
    @GetMapping("/upload")
    public String showUploadView(Model model) {
        List<String> images = cloudinaryService.getImagesFromFolder("/");
        model.addAttribute("imageUrls", images);
        return "upload";
    }
    
    @PostMapping("/upload")
    public String uploadImage(
            @RequestParam("file") MultipartFile file, 
            @RequestParam("text") String text  ,Model model) {
        String imageUrl = cloudinaryService.uploadFile(file, text);
        model.addAttribute("imageUrl", imageUrl);
        return "upload";
    }
}
