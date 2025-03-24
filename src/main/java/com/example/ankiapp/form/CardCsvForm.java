package com.example.ankiapp.form;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import com.example.ankiapp.dto.CardCsvInfo;
import com.example.ankiapp.entitiy.CardInfo;
import lombok.Data;


/**
 * CSVからカードの情報を取得するFormクラス
 */
@Data
public class CardCsvForm {
    
    /**デッキID*/
    private Long deckId;
    
    /**CSVから取得したカードのデータのリスト*/
    private List<CardCsvInfo> cards;
    
    /**CSVファイル*/
    private MultipartFile csvFile;
}
