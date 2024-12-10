package com.example.ankiapp.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.example.ankiapp.constant.db.CardAnswerResult;
import com.example.ankiapp.entitiy.CardInfo;
import com.example.ankiapp.entitiy.DeckInfo;
import com.example.ankiapp.entitiy.UserInfo;


public interface CardInfoRepository extends  JpaRepository<CardInfo, Long>{
    
    CardInfo findByCardId(Long cardId);
    
    CardInfo findByUserInfoAndCardId(UserInfo userInfo, Long cardId);
    
//    CardEditorInfo findByUserIdAndDeckIdAndCardId(String userId, Long deckId, Integer cardId); 
    
    CardInfo findByUserInfoAndCardName(UserInfo userInfo, String name);
    
    @Query("SELECT MAX(c.cardId) FROM CardInfo c")
    Long findMaxCardId();
    
//    List<CardEditorInfo> findByUserId(String userId);
    
    List<CardInfo> findByUserInfoAndCardNameLikeOrderByCardId(UserInfo userInfo, String name);
    
    List<CardInfo> findByUserInfoAndCardNameLikeOrderByCreatedAtAsc(UserInfo userInfo, String name);
    
    List<CardInfo> findByUserInfoAndCardNameLikeOrderByCreatedAtDesc(UserInfo userInfo, String name);
    
    List<CardInfo> findByUserInfoAndCardNameLikeOrderByUpdatedAtAsc(UserInfo userInfo, String name);
    
    List<CardInfo> findByUserInfoAndCardNameLikeOrderByUpdatedAtDesc(UserInfo userInfo, String name);
    
    List<CardInfo> findByUserInfoAndQuestionLikeOrderByCardId(UserInfo userInfo, String question);
    
    List<CardInfo> findByUserInfoAndQuestionLikeOrderByCreatedAtAsc(UserInfo userInfo, String question);
    
    List<CardInfo> findByUserInfoAndQuestionLikeOrderByCreatedAtDesc(UserInfo userInfo, String question);
    
    List<CardInfo> findByUserInfoAndQuestionLikeOrderByUpdatedAtAsc(UserInfo userInfo, String question);
    
    List<CardInfo> findByUserInfoAndQuestionLikeOrderByUpdatedAtDesc(UserInfo userInfo, String question);
    
    List<CardInfo> findByUserInfoAndAnswerLikeOrderByCardId(UserInfo userInfo, String answer);
    
    List<CardInfo> findByUserInfoAndAnswerLikeOrderByCreatedAtAsc(UserInfo userInfo, String answer);
    
    List<CardInfo> findByUserInfoAndAnswerLikeOrderByCreatedAtDesc(UserInfo userInfo, String answer);
    
    List<CardInfo> findByUserInfoAndAnswerLikeOrderByUpdatedAtAsc(UserInfo userInfo, String answer);
    
    List<CardInfo> findByUserInfoAndAnswerLikeOrderByUpdatedAtDesc(UserInfo userInfo, String answer);
    
    List<CardInfo> findByUserInfoAndDeckInfoOrderByCardId(UserInfo userInfo, DeckInfo deckInfo);

    List<CardInfo> findByUserInfoAndDeckInfoOrderByCreatedAtAsc(UserInfo userInfo, DeckInfo deckInfo);
    
    List<CardInfo> findByUserInfoAndDeckInfoOrderByCreatedAtDesc(UserInfo userInfo, DeckInfo deckInfo);
    
    List<CardInfo> findByUserInfoAndDeckInfoOrderByUpdatedAtAsc(UserInfo userInfo, DeckInfo deckInfo);
    
    List<CardInfo> findByUserInfoAndDeckInfoOrderByUpdatedAtDesc(UserInfo userInfo, DeckInfo deckInfo);
    
    List<CardInfo> findByUserInfoAndCardResultOrderByCardId(UserInfo userInfo, CardAnswerResult cardResult);
    
    List<CardInfo> findByUserInfoAndCardResultOrderByCreatedAtAsc(UserInfo userInfo, CardAnswerResult cardResult);
    
    List<CardInfo> findByUserInfoAndCardResultOrderByCreatedAtDesc(UserInfo userInfo, CardAnswerResult cardResult);
    
    List<CardInfo> findByUserInfoAndCardResultOrderByUpdatedAtAsc(UserInfo userInfo, CardAnswerResult cardResult);
    
    List<CardInfo> findByUserInfoAndCardResultOrderByUpdatedAtDesc(UserInfo userInfo, CardAnswerResult cardResult);
    
    List<CardInfo> findByUserInfo(UserInfo userInfo);
    
    List<CardInfo> findByUserInfoOrderByCardId(UserInfo userInfo);
    
    List<CardInfo> findByUserInfoOrderByCreatedAt(UserInfo userInfo);
    
    List<CardInfo> findByUserInfoOrderByCreatedAtDesc(UserInfo userInfo);
    
    List<CardInfo> findByUserInfoOrderByUpdatedAt(UserInfo userInfo);
    
    List<CardInfo> findByUserInfoOrderByUpdatedAtDesc(UserInfo userInfo);
    
    List<CardInfo> findByUserInfoAndDeckInfoAndCardResult(UserInfo userInfo, DeckInfo deckInfo, CardAnswerResult cardResult);
}

