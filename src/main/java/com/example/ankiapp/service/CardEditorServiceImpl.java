package com.example.ankiapp.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import com.example.ankiapp.constant.db.CardAnswerResult;
import com.example.ankiapp.entitiy.CardEditorInfo;
import com.example.ankiapp.entitiy.UserInfo;
import com.example.ankiapp.form.CardEditorForm;
import com.example.ankiapp.form.CardUpdateForm;
import com.example.ankiapp.repository.CardEditorInfoRepository;
import com.example.ankiapp.repository.DeckInfoRepository;
import com.example.ankiapp.repository.UserInfoRepository;
import com.example.ankiapp.utilty.AppUtility;
import com.github.dozermapper.core.Mapper;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class CardEditorServiceImpl implements CardEditorService{

    /** ログイン情報テーブルDIO*/
    private final CardEditorInfoRepository repository;
    
    private final UserInfoRepository userInfoRepository;
    
    private final DeckInfoRepository deckInfoRepository;
    
    private final HttpSession session;
    
    private String imageFolder;
    
    private final ImageStorageService imageService;
    /** ログイン情報テーブルDIO*/
//    private final UserInfoRepository userInfoRepository;
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
    public CardEditorInfo createCardEditorInfo(@Valid CardEditorForm form) throws IOException {
        var userInfo = getUserInfo();
        var deckId = form.getDeckId();
        var deckInfo = deckInfoRepository.findByDeckId(deckId);
        var cardEditorInfo = mapper.map(form, CardEditorInfo.class);
        cardEditorInfo.setCardResult(CardAnswerResult.UNRATED);
        cardEditorInfo.setUserInfo(userInfo);
        cardEditorInfo.setDeckInfo(deckInfo);
        try {
            cardEditorInfo = repository.save(cardEditorInfo);
        }catch(DataIntegrityViolationException e) {
            System.out.println("重複しています");
        }
        
        Long nowId = cardEditorInfo.getCardId();
        if(!form.getQuestionImageFile().isEmpty()) {
            /**保存する画像ファイルの設定*/
            String saveFileName = imageService.saveQuestionCardImage(form.getQuestionImageFile(), AppUtility.getUsername(), deckId, nowId);
            cardEditorInfo.setQuestionImagePath(saveFileName);
        }else {
            cardEditorInfo.setQuestionImagePath(imgdefault + imgExtract);
        }
        
        if(!form.getAnswerImageFile().isEmpty()) {
            /**保存する画像ファイルの設定*/
            String saveFileName = imageService.saveAnswerCardImage(form.getAnswerImageFile(), AppUtility.getUsername(), deckId, nowId);
            cardEditorInfo.setAnswerImagePath(saveFileName);
        }else {
            cardEditorInfo.setAnswerImagePath(imgdefault + imgExtract);
        }

        // TODO 自動生成されたメソッド・スタブ
       
        cardEditorInfo.setCreatedAt(LocalDateTime.now());
        cardEditorInfo.setUpdatedAt(LocalDateTime.now());
//        cardEditorInfo.setQuestion(form.getQuestion());
//        cardEditorInfo.setAnswer(form.getAnswer());
        try {
            return repository.save(cardEditorInfo);
        }catch(DataIntegrityViolationException e) {
            System.out.println("重複しています");
        }
        return null;
    }
    
    public UserInfo getUserInfo() {
        return userInfoRepository.findByLoginId(AppUtility.getUsername());
    }

    @Override
    public CardEditorInfo findCardInfoByCardId(Long cardId) {
        // TODO 自動生成されたメソッド・スタブ
        return repository.findByCardId(cardId);
    }

    @Override
    public CardEditorInfo updateCardEditorInfo(CardEditorInfo cardInfo, CardUpdateForm form) throws IOException {
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
