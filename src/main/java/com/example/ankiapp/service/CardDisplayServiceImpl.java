package com.example.ankiapp.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.example.ankiapp.entitiy.CardEditorInfo;
import com.example.ankiapp.form.CardDisplayForm;
import com.example.ankiapp.repository.CardEditorInfoRepository;
import com.example.ankiapp.repository.DeckInfoRepository;
import com.example.ankiapp.repository.UserInfoRepository;
import com.example.ankiapp.utilty.AppUtility;
import lombok.RequiredArgsConstructor;
import lombok.var;



@Service
@RequiredArgsConstructor
public class CardDisplayServiceImpl implements CardDisplayService{

    /** ログイン情報テーブルDIO*/
    private final CardEditorInfoRepository repository;
    
    /** ログイン情報テーブルDIO*/
    private final UserInfoRepository userRepository;
    
    /** ログイン情報テーブルDIO*/
    private final DeckInfoRepository deckRepository;
    
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
    public CardEditorInfo findCardEditorByCardId(Long cardId) {
        var userInfo = userRepository.findByLoginId(getUserName());
        return repository.findByUserInfoAndCardId(userInfo, cardId);
    }

    @Override
    public List<CardEditorInfo> findCardEditor() {
        var userInfo = userRepository.findByLoginId(getUserName());
        return repository.findByUserInfo(userInfo);
    }

    @Override
    public CardEditorInfo findCardEditorByCardName(String name) {
        var userInfo = userRepository.findByLoginId(getUserName());
        return repository.findByUserInfoAndCardName(userInfo, name);
    }
    
    private String outputImage(Long deckId) throws IOException{
        var imgFilePath = searchImage(deckId);
        var byteImg = Files.readAllBytes(imgFilePath);
        return Base64.getEncoder().encodeToString(byteImg);
    }
    
    private Path searchImage(Long deckId) {
//        var deckInfo = deckRepository.findByDeckId(form.getDeckId());
        var searchFileName =  AppUtility.getUsername() + "Deck" +deckId + imgExtract;
        var imgFilePath = Path.of(imgFolder, searchFileName);
        
        return Files.exists(imgFilePath) ? imgFilePath : Path.of(imgFolder, imgdefault + imgExtract);
    }
    
    private String getUserName() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        return userName;
    }

    @Override
    public List<CardEditorInfo> findCardEditorByDeckId(Long deckId) {
        var userInfo = userRepository.findByLoginId(getUserName());
        var deckInfo = deckRepository.findByDeckId(deckId);
        var cardInfos = repository.findByUserInfoAndDeckInfoOrderByCardId(userInfo, deckInfo);
//        Collections.shuffle(cardInfos);
        return cardInfos;
    }

    @Override
    public List<CardEditorInfo> displayCards(CardDisplayForm form) {
        
        return null;
    }

    @Override
    public String deckImage(Long deckId) throws IOException{
       return "data:image/jpg;base64," + outputImage(deckId);
    }
    
    @Override
    public void saveCardEditorInfo(CardEditorInfo cardEditorInfo) {
        repository.save(cardEditorInfo);
    }

    @Override
    public Integer getCardCount(Long deckId) {
        var userInfo = userRepository.findByLoginId(getUserName());
        var deckInfo = deckRepository.findByDeckId(deckId);
        Integer count = repository.findByUserInfoAndDeckInfoOrderByCardId(userInfo, deckInfo).size();
        return count;
    }
}
