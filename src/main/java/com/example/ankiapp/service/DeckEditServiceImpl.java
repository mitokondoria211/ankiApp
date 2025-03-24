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

    private final ImageStorageService imageService;
    
    /**プロフィール画像の保存先のフォルダ*/
    @Value("${image.folder}")
    private String imgFolder;
    
    /**プロフィール画像の保管拡張子*/
    @Value("${image.extract}")
    private String imgExtract;
    
    /**プロフィール画像の保管拡張子*/
    @Value("${image.default}")
    private String imgdefault;

    @Override
    public DeckUpdateResult updateDeck(DeckInfo deckInfo, DeckUpdateForm form) throws IOException {
        deckInfo.setTitle(form.getTitle());
        deckInfo.setDescription(form.getDescription());
        
        try {
            try {
                if(!form.getImageFile().isEmpty()) {
                    String fileName = imageService.saveDeckImage(form.getImageFile(), AppUtility.getUsername(), form.getDeckId());
//                    String searchFileName = searchFileName(form.getDeckId());
//                    Path imgFilePath =imageService.getDeckImagePath(AppUtility.getUsername(), form.getDeckId());
//                    Files.deleteIfExists(imgFilePath);
//                    Files.copy(form.getImageFile().getInputStream(), imgFilePath);
                    deckInfo.setImagePath(fileName);
                }
            }catch(IOException e) {
                throw new RuntimeException();
            }
            repository.save(deckInfo);
        }catch(DataIntegrityViolationException e) {
            return DeckUpdateResult.FAILURE_BY_DB_ERROR;
        }catch (RuntimeException e) {
            return DeckUpdateResult.FAILURE_BY_IMAGE_ERROR;
        }
        
        
//        if(form.getTitle() != null && !form.getTitle().trim().isEmpty()) {
//            deckInfo.setTitle(form.getTitle());
//        }
//        if(form.getDescription() != null && !form.getDescription().trim().isEmpty()) {
//            deckInfo.setDescription(form.getDescription());
//        }
//        if(!form.getImageFile().isEmpty()) {
//            String searchFileName = searchFileName(form.getDeckId());
//            Path imgFilePath = imageService.getDeckImagePath(deckInfo.getUserInfo().getLoginId(), form.getDeckId());
//            Files.deleteIfExists(imgFilePath);
//            Files.copy(form.getImageFile().getInputStream(), imgFilePath);
//            deckInfo.setImagePath(searchFileName);
//        }
//        
//        repository.save(deckInfo);
        
        return DeckUpdateResult.SUCCEED;
    }

    private String searchFileName(Long deckId) {
        String searchFileName = "deck_" + deckId + imgExtract;
        return searchFileName;
    }
    
    private UserInfo getUserInfo() {
        return userInfoRepository.findByLoginId(AppUtility.getUsername()); 
    }
    
    
    
    
}
