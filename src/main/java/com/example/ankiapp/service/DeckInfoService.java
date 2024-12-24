package com.example.ankiapp.service;

import java.io.IOException;
import java.util.List;
import com.example.ankiapp.constant.CreateDeckResult;
import com.example.ankiapp.entitiy.DeckInfo;
import com.example.ankiapp.form.DeckForm;


public interface DeckInfoService {
    
    CreateDeckResult createDeck(DeckForm form) throws IOException;
    
    List <DeckInfo> findDeckInfo();
    
    List<DeckInfo> filterDeckListByCardInfos();
    
    DeckInfo findDeckInfoByDeckId(Long deckId);
//    public Deck resistDeck(DeckForm form);
    
    
}
