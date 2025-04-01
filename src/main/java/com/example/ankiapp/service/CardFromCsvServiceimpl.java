package com.example.ankiapp.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ankiapp.constant.CardCsvImportResult;
import com.example.ankiapp.constant.db.CardAnswerResult;
import com.example.ankiapp.dto.CardCsvInfo;
import com.example.ankiapp.entitiy.CardInfo;
import com.example.ankiapp.entitiy.UserInfo;
import com.example.ankiapp.form.CardCsvForm;
import com.example.ankiapp.repository.CardInfoRepository;
import com.example.ankiapp.repository.DeckInfoRepository;
import com.example.ankiapp.repository.UserInfoRepository;
import com.example.ankiapp.utilty.AppUtility;
import com.github.dozermapper.core.Mapper;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class CardFromCsvServiceimpl implements CardFromCsvService{

    private final CardInfoRepository repository;
    
    private final UserInfoRepository userInfoRepository;
    
    private final DeckInfoRepository deckInfoRepository;
    
    /**Dozer Mapper*/
    private final Mapper mapper;
    
    @Override
    public List<CardCsvInfo> getCardFromCsv(CardCsvForm form) {
        StringBuilder sbError = new StringBuilder();
        var cardCsvInfos = new ArrayList<CardCsvInfo>();
        
        var csvFile = form.getCsvFile();
        var deckId = form.getDeckId();
        
        if(csvFile.isEmpty()) {
            return new ArrayList<>();
        }
        
        String filename = csvFile.getOriginalFilename();
        if(filename == null || !filename.toLowerCase().endsWith(".csv")) {
            return new ArrayList<>();
        }
        
        try(BufferedReader reader = new BufferedReader(
                new InputStreamReader(form.getCsvFile().getInputStream(),"UTF-8"))) {
            try(CSVReader csvReader = new CSVReader(reader)) {
                String [] record;
                int len = 0;
                while((record = csvReader.readNext()) != null) {
                    len++;
                    if(record.length > 3) {
                        continue;
                    }
                    
                    var cardCsvInfo = new CardCsvInfo();
                    String cardName = record[0];
                    String question = record[1];
                    String answer = record[2];
                    
                    boolean hasError = false;
                    if(cardName.length() < 4 || 25 < cardName.length()) {
                        sbError.append("name error : " + len + "\n");
                        hasError = true;
                    }
                    
                    if(question.length() < 4 || 1000 < question.length()) {
                        sbError.append("question error : " + len + "\n");
                        hasError = true;
                    }
                    
                    if(answer.length() < 1 || 1000 < answer.length()) {
                        sbError.append("answer error : " + len + "\n");
                        hasError = true;
                    }
                    if(!hasError) {
                        cardCsvInfo.setDeckId(deckId);
                        cardCsvInfo.setCardName(cardName);
                        cardCsvInfo.setQuestion(question);
                        cardCsvInfo.setAnswer(answer);
                        cardCsvInfos.add(cardCsvInfo);
                    }

                }
            }
        }catch(IOException | CsvException e) {
            return new ArrayList<>();
        }catch(Exception e) {
            return new ArrayList<>();
        }
        return cardCsvInfos;
    }

    @Override
    public CardCsvImportResult registerCardsFromCsv(List<CardCsvInfo> cardCsvInfos) {
        if(cardCsvInfos.size() == 0) {
            return CardCsvImportResult.ERROR;
        }
        
        for(CardCsvInfo card: cardCsvInfos) {
            var cardInfo = mapper.map(card, CardInfo.class) ;
            var userInfo = getUserInfo();
            var deckInfo = deckInfoRepository.findByDeckId(card.getDeckId());
            cardInfo.setCardResult(CardAnswerResult.UNRATED);
            cardInfo.setUserInfo(userInfo);
            cardInfo.setDeckInfo(deckInfo);
            cardInfo.setCreatedAt(LocalDateTime.now());
            cardInfo.setUpdatedAt(LocalDateTime.now()); 
            
            repository.save(cardInfo);
        }
        
        return CardCsvImportResult.SUCCEED;
    }
    
    private UserInfo getUserInfo() {
        return userInfoRepository.findByLoginId(AppUtility.getUsername());
    }

 
    

}
