package com.example.ankiapp.service;

import java.util.List;
import com.example.ankiapp.constant.CardCsvImportResult;
import com.example.ankiapp.dto.CardCsvInfo;
import com.example.ankiapp.entitiy.CardInfo;
import com.example.ankiapp.form.CardCsvForm;

public interface CardFromCsvService {
    
    List<CardCsvInfo> getCardFromCsv(CardCsvForm form);
    
    CardCsvImportResult registerCardsFromCsv(List<CardCsvInfo>cardCsvInfos);
}
