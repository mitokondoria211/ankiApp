package com.example.ankiapp.service;

import java.io.IOException;
import com.example.ankiapp.entitiy.CardInfo;
import com.example.ankiapp.form.CardEditorForm;
import com.example.ankiapp.form.CardUpdateForm;

public interface CardEditService {
    
    void createCardInfo(CardEditorForm form) throws IOException;
    
    CardInfo updateCardEditorInfo(CardInfo cardInfo, CardUpdateForm form) throws IOException;
    
    CardInfo findCardInfoByCardId(Long cardId);
}
