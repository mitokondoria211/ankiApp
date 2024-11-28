package com.example.ankiapp.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.example.ankiapp.constant.db.CardAnswerResult;
import com.example.ankiapp.entitiy.CardEditorInfo;
import com.example.ankiapp.entitiy.DeckInfo;
import com.example.ankiapp.entitiy.UserInfo;


public interface CardEditorInfoRepository extends  JpaRepository<CardEditorInfo, Long>{
    
    CardEditorInfo findByCardId(Long cardId);
    
    CardEditorInfo findByUserInfoAndCardId(UserInfo userInfo, Long cardId);
    
//    CardEditorInfo findByUserIdAndDeckIdAndCardId(String userId, Long deckId, Integer cardId); 
    
    CardEditorInfo findByUserInfoAndCardName(UserInfo userInfo, String name);
    
    @Query("SELECT MAX(c.cardId) FROM CardEditorInfo c")
    Long findMaxCardId();
    
//    List<CardEditorInfo> findByUserId(String userId);
    
    List<CardEditorInfo> findByUserInfoAndCardNameLikeOrderByCardId(UserInfo userInfo, String name);
    
    List<CardEditorInfo> findByUserInfoAndCardNameLikeOrderByCreatedAtAsc(UserInfo userInfo, String name);
    
    List<CardEditorInfo> findByUserInfoAndCardNameLikeOrderByCreatedAtDesc(UserInfo userInfo, String name);
    
    List<CardEditorInfo> findByUserInfoAndCardNameLikeOrderByUpdatedAtAsc(UserInfo userInfo, String name);
    
    List<CardEditorInfo> findByUserInfoAndCardNameLikeOrderByUpdatedAtDesc(UserInfo userInfo, String name);
    
    List<CardEditorInfo> findByUserInfoAndQuestionLikeOrderByCardId(UserInfo userInfo, String question);
    
    List<CardEditorInfo> findByUserInfoAndQuestionLikeOrderByCreatedAtAsc(UserInfo userInfo, String question);
    
    List<CardEditorInfo> findByUserInfoAndQuestionLikeOrderByCreatedAtDesc(UserInfo userInfo, String question);
    
    List<CardEditorInfo> findByUserInfoAndQuestionLikeOrderByUpdatedAtAsc(UserInfo userInfo, String question);
    
    List<CardEditorInfo> findByUserInfoAndQuestionLikeOrderByUpdatedAtDesc(UserInfo userInfo, String question);
    
    List<CardEditorInfo> findByUserInfoAndAnswerLikeOrderByCardId(UserInfo userInfo, String answer);
    
    List<CardEditorInfo> findByUserInfoAndAnswerLikeOrderByCreatedAtAsc(UserInfo userInfo, String answer);
    
    List<CardEditorInfo> findByUserInfoAndAnswerLikeOrderByCreatedAtDesc(UserInfo userInfo, String answer);
    
    List<CardEditorInfo> findByUserInfoAndAnswerLikeOrderByUpdatedAtAsc(UserInfo userInfo, String answer);
    
    List<CardEditorInfo> findByUserInfoAndAnswerLikeOrderByUpdatedAtDesc(UserInfo userInfo, String answer);
    
    List<CardEditorInfo> findByUserInfoAndDeckInfoOrderByCardId(UserInfo userInfo, DeckInfo deckInfo);

    List<CardEditorInfo> findByUserInfoAndDeckInfoOrderByCreatedAtAsc(UserInfo userInfo, DeckInfo deckInfo);
    
    List<CardEditorInfo> findByUserInfoAndDeckInfoOrderByCreatedAtDesc(UserInfo userInfo, DeckInfo deckInfo);
    
    List<CardEditorInfo> findByUserInfoAndDeckInfoOrderByUpdatedAtAsc(UserInfo userInfo, DeckInfo deckInfo);
    
    List<CardEditorInfo> findByUserInfoAndDeckInfoOrderByUpdatedAtDesc(UserInfo userInfo, DeckInfo deckInfo);
    
    List<CardEditorInfo> findByUserInfoAndCardResultOrderByCardId(UserInfo userInfo, CardAnswerResult cardResult);
    
    List<CardEditorInfo> findByUserInfoAndCardResultOrderByCreatedAtAsc(UserInfo userInfo, CardAnswerResult cardResult);
    
    List<CardEditorInfo> findByUserInfoAndCardResultOrderByCreatedAtDesc(UserInfo userInfo, CardAnswerResult cardResult);
    
    List<CardEditorInfo> findByUserInfoAndCardResultOrderByUpdatedAtAsc(UserInfo userInfo, CardAnswerResult cardResult);
    
    List<CardEditorInfo> findByUserInfoAndCardResultOrderByUpdatedAtDesc(UserInfo userInfo, CardAnswerResult cardResult);
    
    List<CardEditorInfo> findByUserInfo(UserInfo userInfo);
    
    List<CardEditorInfo> findByUserInfoOrderByCardId(UserInfo userInfo);
    
    List<CardEditorInfo> findByUserInfoOrderByCreatedAt(UserInfo userInfo);
    
    List<CardEditorInfo> findByUserInfoOrderByCreatedAtDesc(UserInfo userInfo);
    
    List<CardEditorInfo> findByUserInfoOrderByUpdatedAt(UserInfo userInfo);
    
    List<CardEditorInfo> findByUserInfoOrderByUpdatedAtDesc(UserInfo userInfo);
}

