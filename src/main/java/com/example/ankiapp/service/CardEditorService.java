package com.example.ankiapp.service;

import java.io.IOException;
import com.example.ankiapp.entitiy.CardEditorInfo;
import com.example.ankiapp.form.CardEditorForm;
import com.example.ankiapp.form.CardUpdateForm;

public interface CardEditorService {
    
    CardEditorInfo createCardEditorInfo(CardEditorForm form) throws IOException;
    
    CardEditorInfo updateCardEditorInfo(CardEditorInfo cardInfo, CardUpdateForm form) throws IOException;
    
    CardEditorInfo findCardInfoByCardId(Long cardId);
}
