package com.example.ankiapp.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.ankiapp.constant.CardUpadateResult;
import com.example.ankiapp.constant.DeckUpdateResult;
import com.example.ankiapp.entitiy.DeckInfo;
import com.example.ankiapp.entitiy.UserInfo;
import com.example.ankiapp.form.DeckUpdateForm;
import com.example.ankiapp.repository.DeckInfoRepository;
import com.example.ankiapp.repository.UserInfoRepository;
import com.example.ankiapp.utilty.AppUtility;
import lombok.RequiredArgsConstructor;
import lombok.var;

@Service
@RequiredArgsConstructor
//@Transactional
public class DeckEditServiceImpl implements DeckEditService{

    /** ログイン情報テーブルDIO*/
    private final UserInfoRepository userInfoRepository;
    
    private final DeckInfoRepository repository;
    
    private final CloudinaryService cloudinaryService;

    @Transactional
    @Override
    public DeckUpdateResult updateDeck(DeckInfo deckInfo, DeckUpdateForm form) throws IOException {
        deckInfo.setTitle(form.getTitle());
        deckInfo.setDescription(form.getDescription());
        
        try {
            if(!form.getImageFile().isEmpty()) {
                String fileUrl = cloudinaryService.uploadDeckImageFile(form.getImageFile(), AppUtility.getUsername(), form.getDeckId());
                deckInfo.setImageUrl(fileUrl);
            }
            repository.save(deckInfo);
        }catch(DataIntegrityViolationException e) {
            return DeckUpdateResult.FAILURE_BY_DB_ERROR;
        }catch (RuntimeException e) {
            return DeckUpdateResult.FAILURE_BY_IMAGE_ERROR;
        }
        return DeckUpdateResult.SUCCEED;
    }    
}
