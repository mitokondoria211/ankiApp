package com.example.ankiapp.service;

import java.io.IOException;
import java.util.List;
import com.example.ankiapp.entitiy.CardEditorInfo;
import com.example.ankiapp.form.CardDisplayForm;


public interface CardDisplayService {
    
   CardEditorInfo findCardEditorByCardId(Long cardId);
   
   CardEditorInfo findCardEditorByCardName(String name);
   
   List<CardEditorInfo> findCardEditor();
   
   List<CardEditorInfo> findCardEditorByDeckId(Long deckId);
   
   List<CardEditorInfo> displayCards(CardDisplayForm form);
   
   String deckImage(Long deckId) throws IOException;

   void saveCardEditorInfo(CardEditorInfo cardEditorInfo);
   
   Integer getCardCount(Long deckId);
   
   
}
