package com.example.ankiapp.service;

import java.io.IOException;
import java.util.List;
import com.example.ankiapp.constant.DeckCreateResult;
import com.example.ankiapp.entitiy.DeckInfo;
import com.example.ankiapp.form.DeckForm;


public interface DeckInfoService {
    
    DeckCreateResult createDeck(DeckForm form) throws IOException;
    
    List <DeckInfo> findDeckInfo();
    
    List<DeckInfo> filterDeckListByCardInfos();
    
    DeckInfo findDeckInfoByDeckId(Long deckId);
//    public Deck resistDeck(DeckForm form);
    
    
}
