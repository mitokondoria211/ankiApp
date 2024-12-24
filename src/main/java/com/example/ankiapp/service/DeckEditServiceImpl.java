package com.example.ankiapp.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.ankiapp.entitiy.DeckInfo;
import com.example.ankiapp.form.DeckUpdateForm;
import com.example.ankiapp.repository.DeckInfoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
//@Transactional
public class DeckEditServiceImpl implements DeckEditService{

    /** ログイン情報テーブルDIO*/
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
    public DeckInfo updateDeck(DeckInfo deckInfo, DeckUpdateForm form) throws IOException {
        if(form.getTitle() != null && !form.getTitle().trim().isEmpty()) {
            deckInfo.setTitle(form.getTitle());
        }
        if(form.getDescription() != null && !form.getDescription().trim().isEmpty()) {
            deckInfo.setDescription(form.getDescription());
        }
        if(!form.getImageFile().isEmpty()) {
            String searchFileName = searchFileName(form.getDeckId());
            Path imgFilePath = imageService.getDeckImagePath(deckInfo.getUserInfo().getLoginId(), form.getDeckId());
            Files.deleteIfExists(imgFilePath);
            Files.copy(form.getImageFile().getInputStream(), imgFilePath);
            deckInfo.setImagePath(searchFileName);
        }
        
        repository.save(deckInfo);
        return deckInfo;
    }

    private String searchFileName(Long deckId) {
        String searchFileName = "deck_" + deckId + imgExtract;
        return searchFileName;
    }
    
}
