package com.example.ankiapp.service;

import java.io.IOException;
import java.util.List;
import com.example.ankiapp.entitiy.DeckInfo;
import com.example.ankiapp.form.DeckUpdateForm;


public interface DeckEditService {
    
     DeckInfo updateDeck(DeckInfo deckInfo ,DeckUpdateForm form) throws IOException;
    
  
}
