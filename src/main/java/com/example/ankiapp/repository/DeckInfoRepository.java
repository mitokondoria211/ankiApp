package com.example.ankiapp.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.example.ankiapp.entitiy.DeckInfo;
import com.example.ankiapp.entitiy.UserInfo;

/**
 * デッキ情報テーブルRepositoryクラス
 */
public interface DeckInfoRepository extends  JpaRepository<DeckInfo, Long>{
    
    /**
     * デッキIDが完全一致した場合のデッキ情報を取得する
     * @param deckId
     * @return deckInfo デッキの情報
     */
    DeckInfo findByDeckId(Long deckId);
    
    
    /**
     * ユーザーが作成したデッキ情報一覧を取得する
     * @param userInfo　ユーザー情報
     * @return ユーザーごとのデッキリスト
     */
    List<DeckInfo>findByUserInfo(UserInfo userInfo);
    
    /**
     * ユーザーが作成したデッキ情報一覧を取得する(deckIdの昇順)
     * @param userInfo　ユーザー情報
     * @return ユーザーごとのデッキリスト
     */
    List <DeckInfo> findByUserInfoOrderByDeckId(UserInfo userInfo);
    
    /**
     * ユーザーが作成かつデッキのタイトルと部分一致するデッキ情報一覧を取得する(deckIdの昇順)
     * @param userInfo　ユーザー情報
     * @param title　デッキタイトル
     * @return　ユーザ情報とデッキタイトルの一部が一致したデッキリスト
     */
    List <DeckInfo> findByUserInfoAndTitleLikeOrderByDeckId(UserInfo userInfo, String title);

    /**
     * ユーザーが作成かつデッキのタイトルと部分一致するデッキ情報一覧を取得する(更新日(新しい順))
     * @param userInfo　ユーザー情報
     * @param title　デッキタイトル
     * @return　ユーザ情報とデッキタイトルの一部が一致したデッキリスト
     */
    List <DeckInfo> findByUserInfoAndTitleLikeOrderByUpdatedAtDesc(UserInfo userInfo, String title);
    
    /**
     * ユーザーが作成かつデッキのタイトルと部分一致するデッキ情報一覧を取得する(更新日(古い順))
     * @param userInfo　ユーザー情報
     * @param title　デッキタイトル
     * @return　ユーザ情報とデッキタイトルの一部が一致したデッキリスト
     */
    List <DeckInfo> findByUserInfoAndTitleLikeOrderByUpdatedAt(UserInfo userInfo, String title);
    
    /**
     * ユーザーが作成かつデッキのタイトルと部分一致するデッキ情報一覧を取得する(作成日(新しい順))
     * @param userInfo　ユーザー情報
     * @param title　デッキタイトル
     * @return　ユーザ情報とデッキタイトルの一部が一致したデッキリスト
     */
    List <DeckInfo> findByUserInfoAndTitleLikeOrderByCreatedAtDesc(UserInfo userInfo, String title);
    
    /**
     * ユーザーが作成かつデッキのタイトルと部分一致するデッキ情報一覧を取得する(作成日(古い順))
     * @param userInfo　ユーザー情報
     * @param title　デッキタイトル
     * @return　ユーザ情報とデッキタイトルの一部が一致したデッキリスト
     */
    List <DeckInfo> findByUserInfoAndTitleLikeOrderByCreatedAt(UserInfo userInfo, String title);
//    Long findTopByOrderBydeckIdDesc();
    
    @Query("SELECT MAX(d.deckId) FROM DeckInfo d")
    Long findMaxDeckId();
}
