package com.example.ankiapp.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.example.ankiapp.entitiy.DeckInfo;
import com.example.ankiapp.entitiy.UserInfo;


public interface DeckInfoRepository extends  JpaRepository<DeckInfo, Long>{
    DeckInfo findByDeckId(Long deckId);
    
    List<DeckInfo>findByUserInfo(UserInfo userInfo);
    
    List <DeckInfo> findByUserInfoOrderByDeckId(UserInfo userInfo);
    
    List <DeckInfo> findByUserInfoAndTitleLikeOrderByDeckId(UserInfo userInfo, String title);

    List <DeckInfo> findByUserInfoAndTitleLikeOrderByUpdatedAtDesc(UserInfo userInfo, String title);
    
    List <DeckInfo> findByUserInfoAndTitleLikeOrderByUpdatedAt(UserInfo userInfo, String title);
    
    List <DeckInfo> findByUserInfoAndTitleLikeOrderByCreatedAtDesc(UserInfo userInfo, String title);
    
    List <DeckInfo> findByUserInfoAndTitleLikeOrderByCreatedAt(UserInfo userInfo, String title);
//    Long findTopByOrderBydeckIdDesc();
    
    @Query("SELECT MAX(d.deckId) FROM DeckInfo d")
    Long findMaxDeckId();
}
