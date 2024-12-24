package com.example.ankiapp.form;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DeckForm {
    
    private Long deckId;
    
    @NotBlank
    @Length(min = 5, max = 30, message = "{title.deckForm.length}")
    private String title;
    
    @Length(max = 50, message = "{description.deckForm.length}")
    private String description;
    
    private MultipartFile imageFile;
}
