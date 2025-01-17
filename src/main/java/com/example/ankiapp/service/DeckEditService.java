package com.example.ankiapp.service;

import java.io.IOException;
import com.example.ankiapp.constant.UpdateDeckResult;
import com.example.ankiapp.entitiy.DeckInfo;
import com.example.ankiapp.form.DeckUpdateForm;


public interface DeckEditService {
    
     UpdateDeckResult updateDeck(DeckInfo deckInfo ,DeckUpdateForm form) throws IOException;
    
}
