package com.example.ankiapp.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import com.example.ankiapp.constant.db.CardAnswerResult;
import com.example.ankiapp.entitiy.CardInfo;
import com.example.ankiapp.entitiy.UserInfo;
import com.example.ankiapp.form.CardEditorForm;
import com.example.ankiapp.form.CardUpdateForm;
import com.example.ankiapp.repository.CardInfoRepository;
import com.example.ankiapp.repository.DeckInfoRepository;
import com.example.ankiapp.repository.UserInfoRepository;
import com.example.ankiapp.utilty.AppUtility;
import com.github.dozermapper.core.Mapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class CardEditServiceImpl implements CardEditService{

    /** ログイン情報テーブルDIO*/
    private final CardInfoRepository repository;
    
    private final UserInfoRepository userInfoRepository;
    
    private final DeckInfoRepository deckInfoRepository;
    
    private final ImageStorageService imageService;
 
    /**Dozer Mapper*/
    private final Mapper mapper;
    
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
    public void createCardInfo(@Valid CardEditorForm form) throws IOException {
        var userInfo = getUserInfo();
        var deckId = form.getDeckId();
        var deckInfo = deckInfoRepository.findByDeckId(deckId);
        var cardInfo = mapper.map(form, CardInfo.class);
        cardInfo.setCardResult(CardAnswerResult.UNRATED);
        cardInfo.setUserInfo(userInfo);
        cardInfo.setDeckInfo(deckInfo);
        cardInfo.setCreatedAt(LocalDateTime.now());
        cardInfo.setUpdatedAt(LocalDateTime.now());
        try {
            cardInfo = repository.save(cardInfo);
        }catch(DataIntegrityViolationException e) {
            System.out.println("重複しています");
        }
        
        Long nowId = cardInfo.getCardId();
        if(!form.getQuestionImageFile().isEmpty()) {
            /**保存する画像ファイルの設定*/
            String saveFileName = imageService.saveQuestionCardImage(form.getQuestionImageFile(), AppUtility.getUsername(), deckId, nowId);
            cardInfo.setQuestionImagePath(saveFileName);
        }else {
            cardInfo.setQuestionImagePath(imgdefault + imgExtract);
        }
        
        if(!form.getAnswerImageFile().isEmpty()) {
            /**保存する画像ファイルの設定*/
            String saveFileName = imageService.saveAnswerCardImage(form.getAnswerImageFile(), AppUtility.getUsername(), deckId, nowId);
            cardInfo.setAnswerImagePath(saveFileName);
        }else {
            cardInfo.setAnswerImagePath(imgdefault + imgExtract);
        }

        try {
            repository.save(cardInfo);
        }catch(DataIntegrityViolationException e) {
            System.out.println("重複しています");
        }
    }
    
    public UserInfo getUserInfo() {
        return userInfoRepository.findByLoginId(AppUtility.getUsername());
    }

    @Override
    public CardInfo findCardInfoByCardId(Long cardId) {
        return repository.findByCardId(cardId);
    }

    @Override
    public CardInfo updateCardEditorInfo(CardInfo cardInfo, CardUpdateForm form) throws IOException {
        if(form.getCardName() != null && !form.getCardName().trim().isEmpty()) {
            cardInfo.setCardName(form.getCardName());
        }
        
        if(form.getQuestion() != null && !form.getQuestion().trim().isEmpty()) {
            cardInfo.setQuestion(form.getQuestion());
        }
        
        if(form.getAnswer() != null && !form.getAnswer().trim().isEmpty()) {
            cardInfo.setAnswer(form.getAnswer());
        }
        
        if(form.getCardResult() != null) {
            cardInfo.setCardResult(form.getCardResult());
        }
        if(!form.getQuestionImageFile().isEmpty()) {
            
            String searchFileName = searchQuestionFileName(form.getCardId());
            Path imgFolderPath =imageService.getDeckCardsDirectory(AppUtility.getUsername(), cardInfo.getDeckInfo().getDeckId());
            Path imgFilePath = imgFolderPath.resolve(searchFileName);
            Files.createDirectories(imgFolderPath);
            Files.deleteIfExists(imgFilePath);
            Files.copy(form.getQuestionImageFile().getInputStream(), imgFilePath);
            cardInfo.setQuestionImagePath(searchFileName);
        }
        
        if(!form.getAnswerImageFile().isEmpty()) {
            String searchFileName = searchAnswerFileName(form.getCardId());
            Path imgFolderPath =imageService.getDeckCardsDirectory(AppUtility.getUsername(), cardInfo.getDeckInfo().getDeckId());
            Path imgFilePath = imgFolderPath.resolve(searchFileName);
            Files.createDirectories(imgFolderPath);
            Files.deleteIfExists(imgFilePath);
            Files.copy(form.getAnswerImageFile().getInputStream(), imgFilePath);
            cardInfo.setAnswerImagePath(searchFileName);
        }
        repository.save(cardInfo);
        
        return cardInfo;
    }
    
    private String searchQuestionFileName(Long cardId) {
        String searchFileName =  "card_" + cardId + "_question"+ imgExtract;
        return searchFileName;
    }
    
    private String searchAnswerFileName(Long cardId) {
        String searchFileName =  "card_" + cardId + "_answer"+ imgExtract;
        return searchFileName;
    }

}
