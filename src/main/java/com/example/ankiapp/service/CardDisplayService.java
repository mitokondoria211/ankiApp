package com.example.ankiapp.service;

import java.io.IOException;
import java.util.List;
import com.example.ankiapp.entitiy.CardInfo;
import com.example.ankiapp.entitiy.DeckInfo;


public interface CardDisplayService {
    
   CardInfo findCardEditorByCardId(Long cardId);
   
   CardInfo findCardEditorByCardName(String name);
   
   List<CardInfo> findCardEditor();
   
   List<CardInfo> findCardEditorByDeckId(Long deckId);
   
   String deckImage(Long deckId) throws IOException;

   void saveCardEditorInfo(CardInfo cardEditorInfo);
   
   Integer getCardCount(Long deckId);
   
   List<CardInfo> findCardInfoByDeckIdAndCardResult(Long deckId, String cardResult);
   
   Integer getCardCountByCardResult(Long deckId, String result);
   
   List<DeckInfo> filterEmptyDecks(List<DeckInfo> decks);
   
   
}
