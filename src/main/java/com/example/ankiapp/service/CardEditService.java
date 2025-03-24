package com.example.ankiapp.service;

import java.io.IOException;
import com.example.ankiapp.constant.CardCreateResult;
import com.example.ankiapp.constant.CardUpadateResult;
import com.example.ankiapp.entitiy.CardInfo;
import com.example.ankiapp.form.CardEditorForm;
import com.example.ankiapp.form.CardUpdateForm;

public interface CardEditService {
    
    CardCreateResult createCardInfo(CardEditorForm form) throws IOException;
    
    CardUpadateResult updateCardEditorInfo(CardInfo cardInfo, CardUpdateForm form) throws IOException;
    
    CardInfo findCardInfoByCardId(Long cardId);
}
