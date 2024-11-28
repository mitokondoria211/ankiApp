package com.example.ankiapp.form;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;
import lombok.Data;

@Data
public class DeckForm {
    
    private Long deckId;
    
    @Length(min = 5, max = 30)
    private String title;
    
    private String description;
    
    private MultipartFile imageFile;
}
