package com.example.ankiapp.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.ankiapp.constant.CardDeleteResult;
import com.example.ankiapp.constant.SortType;
import com.example.ankiapp.constant.db.CardAnswerResult;
import com.example.ankiapp.dto.CardListInfo;
import com.example.ankiapp.dto.CardSearchInfo;
import com.example.ankiapp.entitiy.CardInfo;
import com.example.ankiapp.entitiy.DeckInfo;
import com.example.ankiapp.entitiy.UserInfo;
import com.example.ankiapp.repository.CardInfoRepository;
import com.example.ankiapp.repository.DeckInfoRepository;
import com.example.ankiapp.repository.UserInfoRepository;
import com.example.ankiapp.utilty.AppUtility;
import com.github.dozermapper.core.Mapper;
import lombok.RequiredArgsConstructor;



/**
 * ユーザー登録画面 Service実装クラス
 */

@Service
@RequiredArgsConstructor
//@Transactional
public class CardListServiceImpl implements CardListService {
    
    private final UserInfoRepository userInfoRepository;
    private final DeckInfoRepository deckInfoRepository;
    private final CardInfoRepository repository;
    private final CloudinaryService cloudinaryService;
    
    private final Mapper mapper;
    
    @Override
    public List<CardListInfo> editCardInfos() {
        UserInfo userInfo = userInfoRepository.findByLoginId(AppUtility.getUsername());
        return toCardListInfos(repository.findByUserInfoOrderByCardId(userInfo));
    }
    
    private List<CardListInfo> toCardListInfos(List<CardInfo> cardInfos) {
        List<CardListInfo> cardListInfos = new ArrayList<>();
        
        for(CardInfo cardInfo: cardInfos) {
            var cardListInfo = mapper.map(cardInfo, CardListInfo.class);
            cardListInfos.add(cardListInfo);
        }
        
        return cardListInfos;
    }
    
    private List<CardInfo> findCardInfoByParam(CardSearchInfo dto) {
        
        String userName = AppUtility.getUsername();
        UserInfo userInfo = userInfoRepository.findByLoginId(userName);
        String cardNameParam = AppUtility.addWildcard(dto.getCardName());
        String questionParam = AppUtility.addWildcard(dto.getQuestion());
        String answerParam = AppUtility.addWildcard(dto.getAnswer());
        CardAnswerResult cardResult = dto.getCardResult();
        Long deckId = dto.getDeckId();
        DeckInfo deckInfo = null;
        if(deckId != null) {
            deckInfo = deckInfoRepository.findByDeckId(deckId);
        }
        
        
        SortType sortType = dto.getSortType();
        
        if(cardNameParam != null && !dto.getCardName().isBlank()) {
            return cardNameOrderBySortType(userInfo, cardNameParam, sortType);
        }
        if(questionParam != null && !dto.getQuestion().isBlank()) {
            return questionOrderBySortType(userInfo, questionParam, sortType);
        }
        if(answerParam != null && !dto.getAnswer().isBlank()) {
            return answerOrderBySortType(userInfo, answerParam, sortType);
        }
        if(cardResult != null) {
            return cardResultOrderBySortType(userInfo, cardResult, sortType);
        }
        if(deckInfo != null) {
            return deckInfoOrderBySortType(userInfo, deckInfo, sortType);
        }
        return normalOrderBySortType(userInfo, sortType);
    }
    private List<CardInfo> normalOrderBySortType(UserInfo userInfo,SortType sortType) {
        switch(sortType) {
            case CREATED_AT_ASC:
                return repository.findByUserInfoOrderByCreatedAt(userInfo);
            case CREATED_AT_DESC:
                return repository.findByUserInfoOrderByCreatedAtDesc(userInfo);
            case UPDATED_AT_ASC:
                return repository.findByUserInfoOrderByUpdatedAt(userInfo);
            case UPDATED_AT_DESC:
                return repository.findByUserInfoOrderByUpdatedAtDesc(userInfo);
            default:
                return repository.findByUserInfoOrderByCardId(userInfo);
        }
        
    }
    private List<CardInfo> cardNameOrderBySortType(
            UserInfo userInfo, String cardName ,SortType sortType) {
        switch(sortType) {
            case CREATED_AT_ASC:
                return repository.findByUserInfoAndCardNameLikeOrderByCreatedAtAsc(userInfo, cardName);
            case CREATED_AT_DESC:
                return repository.findByUserInfoAndCardNameLikeOrderByCreatedAtDesc(userInfo, cardName);
            case UPDATED_AT_ASC:
                return repository.findByUserInfoAndCardNameLikeOrderByUpdatedAtAsc(userInfo, cardName);
            case UPDATED_AT_DESC:
                return repository.findByUserInfoAndCardNameLikeOrderByUpdatedAtDesc(userInfo, cardName);
            case UNSORTED:
                return repository.findByUserInfoAndCardNameLikeOrderByCardId(userInfo, cardName);
        }
        return repository.findByUserInfoAndCardNameLikeOrderByCardId(userInfo, cardName);
    }
    
    private List<CardInfo> questionOrderBySortType(
            UserInfo userInfo, String question ,SortType sortType) {
        switch(sortType) {
            case CREATED_AT_ASC:
                return repository.findByUserInfoAndQuestionLikeOrderByCreatedAtAsc(userInfo, question);
            case CREATED_AT_DESC:
                return repository.findByUserInfoAndQuestionLikeOrderByCreatedAtDesc(userInfo, question);
            case UPDATED_AT_ASC:
                return repository.findByUserInfoAndQuestionLikeOrderByUpdatedAtAsc(userInfo, question);
            case UPDATED_AT_DESC:
                return repository.findByUserInfoAndQuestionLikeOrderByUpdatedAtDesc(userInfo, question);
            case UNSORTED:
                return repository.findByUserInfoAndQuestionLikeOrderByCardId(userInfo, question);
        }
        return repository.findByUserInfoAndQuestionLikeOrderByCardId(userInfo, question);
    }
    
    private List<CardInfo> answerOrderBySortType(
            UserInfo userInfo, String answer ,SortType sortType) {
        switch(sortType) {
            case CREATED_AT_ASC:
                return repository.findByUserInfoAndAnswerLikeOrderByCreatedAtAsc(userInfo, answer);
            case CREATED_AT_DESC:
                return repository.findByUserInfoAndAnswerLikeOrderByCreatedAtDesc(userInfo, answer);
            case UPDATED_AT_ASC:
                return repository.findByUserInfoAndAnswerLikeOrderByUpdatedAtAsc(userInfo, answer);
            case UPDATED_AT_DESC:
                return repository.findByUserInfoAndAnswerLikeOrderByUpdatedAtDesc(userInfo, answer);
            case UNSORTED:
                return repository.findByUserInfoAndAnswerLikeOrderByCardId(userInfo, answer);
        }
        return repository.findByUserInfoAndAnswerLikeOrderByCardId(userInfo, answer);
    }
    
    private List<CardInfo> cardResultOrderBySortType(
            UserInfo userInfo, CardAnswerResult cardResult ,SortType sortType) {
        switch(sortType) {
            case CREATED_AT_ASC:
                return repository.findByUserInfoAndCardResultOrderByCardId(userInfo, cardResult);
            case CREATED_AT_DESC:
                return repository.findByUserInfoAndCardResultOrderByCreatedAtDesc(userInfo, cardResult);
            case UPDATED_AT_ASC:
                return repository.findByUserInfoAndCardResultOrderByUpdatedAtAsc(userInfo, cardResult);
            case UPDATED_AT_DESC:
                return repository.findByUserInfoAndCardResultOrderByUpdatedAtDesc(userInfo, cardResult);
            case UNSORTED:
                return repository.findByUserInfoAndCardResultOrderByCardId(userInfo, cardResult);
        }
        return repository.findByUserInfoAndCardResultOrderByCardId(userInfo, cardResult);
    }
    
    private List<CardInfo> deckInfoOrderBySortType(
            UserInfo userInfo, DeckInfo deckInfo ,SortType sortType) {
        switch(sortType) {
            case CREATED_AT_ASC:
                return repository.findByUserInfoAndDeckInfoOrderByCreatedAtAsc(userInfo, deckInfo);
            case CREATED_AT_DESC:
                return repository.findByUserInfoAndDeckInfoOrderByCreatedAtDesc(userInfo, deckInfo);
            case UPDATED_AT_ASC:
                return repository.findByUserInfoAndDeckInfoOrderByUpdatedAtAsc(userInfo, deckInfo);
            case UPDATED_AT_DESC:
                return repository.findByUserInfoAndDeckInfoOrderByUpdatedAtDesc(userInfo, deckInfo);
            case UNSORTED:
                return repository.findByUserInfoAndDeckInfoOrderByCardId(userInfo, deckInfo);
        }
        return repository.findByUserInfoAndDeckInfoOrderByCardId(userInfo, deckInfo);
    }
    
    @Override
    public List<CardListInfo> editCardListByParam(CardSearchInfo dto) {
        return toCardListInfos(findCardInfoByParam(dto));
    }

    @Transactional
    @Override
    public CardDeleteResult deleteCardInfoByCardId(Long selectedCardId) {
        var cardInfo = repository.findByCardId(selectedCardId);
        if(cardInfo == null) {
            return CardDeleteResult.ERROR;
        }
        try {
            cloudinaryService.deleteQuestionImage(cardInfo);
            cloudinaryService.deleteAnswerImage(cardInfo);
        } catch (IOException e) {
            return CardDeleteResult.IMAGE_DELETE_ERROR;
        }
        repository.deleteById(selectedCardId);
        return CardDeleteResult.SUCCEED;
    }
    
    @Override
     public List<CardInfo> getDeckCards(UserInfo userInfo, DeckInfo deckInfo) {
         return repository.findByUserInfoAndDeckInfo(userInfo, deckInfo);
    }

    

}
