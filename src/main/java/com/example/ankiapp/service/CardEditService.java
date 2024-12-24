package com.example.ankiapp.service;

import java.io.IOException;
import com.example.ankiapp.constant.CreateCardResult;
import com.example.ankiapp.constant.UpdateCardResult;
import com.example.ankiapp.entitiy.CardInfo;
import com.example.ankiapp.form.CardEditorForm;
import com.example.ankiapp.form.CardUpdateForm;

public interface CardEditService {
    
    CreateCardResult createCardInfo(CardEditorForm form) throws IOException;
    
    UpdateCardResult updateCardEditorInfo(CardInfo cardInfo, CardUpdateForm form) throws IOException;
    
    CardInfo findCardInfoByCardId(Long cardId);
}
