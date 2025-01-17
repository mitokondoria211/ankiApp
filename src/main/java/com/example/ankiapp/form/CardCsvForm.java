package com.example.ankiapp.form;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import com.example.ankiapp.dto.CardCsvInfo;
import com.example.ankiapp.entitiy.CardInfo;
import lombok.Data;


@Data
public class CardCsvForm {
    private Long deckId;
    
    private List<CardCsvInfo> cards;
    
    private MultipartFile csvFile;
}
