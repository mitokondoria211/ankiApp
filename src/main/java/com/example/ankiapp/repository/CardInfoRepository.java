package com.example.ankiapp.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.example.ankiapp.constant.db.CardAnswerResult;
import com.example.ankiapp.entitiy.CardInfo;
import com.example.ankiapp.entitiy.DeckInfo;
import com.example.ankiapp.entitiy.UserInfo;

/**
 * カード情報テーブルRepositoryクラス
 */
public interface CardInfoRepository extends  JpaRepository<CardInfo, Long>{
    
    /**
     * カードIDが完全一致した場合のカード情報を取得する
     * @param cardId カードID
     * @return cardInfo カードの情報
     */
    CardInfo findByCardId(Long cardId);
    
    
    /**
     * ユーザーとカードIDが完全一致した場合のカード情報を取得する
     * @param userInfo ユーザー情報
     * @param cardId カードID
     * @return cardInfo カードの情報
     */
    CardInfo findByUserInfoAndCardId(UserInfo userInfo, Long cardId);
    
    /**
     * ユーザーとカード名が完全一致した場合のカード情報を取得する
     * @param userInfo ユーザー情報
     * @param cardName カード名
     * @return cardInfo カードの情報
     */
    CardInfo findByUserInfoAndCardName(UserInfo userInfo, String cardName);
    
    @Query("SELECT MAX(c.cardId) FROM CardInfo c")
    Long findMaxCardId();
    
    
    /**
     * ユーザーかつカード名が部分一致するカード情報一覧を取得する(cardIdの昇順)
     * @param userInfo ユーザー情報
     * @param cardName カード名
     * @return ユーザ情報とカード名の一部が一致したカードリスト
     */
    List<CardInfo> findByUserInfoAndCardNameLikeOrderByCardId(UserInfo userInfo, String cardName);
    
    /**
     * ユーザーかつカード名が部分一致するカード情報一覧を取得する(作成日(古い順))
     * @param userInfo ユーザー情報
     * @param cardName カード名
     * @return ユーザ情報とカード名の一部が一致したカードリスト
     */
    List<CardInfo> findByUserInfoAndCardNameLikeOrderByCreatedAtAsc(UserInfo userInfo, String cardName);
    
    /**
     * ユーザーかつカード名が部分一致するカード情報一覧を取得する(作成日(新しい順))
     * @param userInfo ユーザー情報
     * @param cardName カード名
     * @return ユーザ情報とカード名の一部が一致したカードリスト
     */
    List<CardInfo> findByUserInfoAndCardNameLikeOrderByCreatedAtDesc(UserInfo userInfo, String cardName);
    
    /**
     * ユーザーかつカード名が部分一致するカード情報一覧を取得する(更新日(古い順))
     * @param userInfo ユーザー情報
     * @param cardName カード名
     * @return ユーザ情報とカード名の一部が一致したカードリスト
     */
    List<CardInfo> findByUserInfoAndCardNameLikeOrderByUpdatedAtAsc(UserInfo userInfo, String cardName);
    
    /**
     * ユーザーかつカード名が部分一致するカード情報一覧を取得する(更新日(新しい順))
     * @param userInfo ユーザー情報
     * @param cardName カード名
     * @return ユーザ情報とカード名の一部が一致したカードリスト
     */
    List<CardInfo> findByUserInfoAndCardNameLikeOrderByUpdatedAtDesc(UserInfo userInfo, String cardName);
    
    /**
     * ユーザーかつ問題が部分一致するカード情報一覧を取得する(cardIdの昇順)
     * @param userInfo ユーザー情報
     * @param question 問題
     * @return ユーザ情報と問題の一部が一致したカードリスト
     */
    List<CardInfo> findByUserInfoAndQuestionLikeOrderByCardId(UserInfo userInfo, String question);
    
    /**
     * ユーザーかつ問題が部分一致するカード情報一覧を取得する(作成日(古い順))
     * @param userInfo ユーザー情報
     * @param question 問題
     * @return ユーザ情報と問題の一部が一致したカードリスト
     */
    List<CardInfo> findByUserInfoAndQuestionLikeOrderByCreatedAtAsc(UserInfo userInfo, String question);
    
    /**
     * ユーザーかつ問題が部分一致するカード情報一覧を取得する(作成日(新しい順))
     * @param userInfo ユーザー情報
     * @param question 問題
     * @return ユーザ情報と問題の一部が一致したカードリスト
     */
    List<CardInfo> findByUserInfoAndQuestionLikeOrderByCreatedAtDesc(UserInfo userInfo, String question);
    
    /**
     * ユーザーかつ問題が部分一致するカード情報一覧を取得する(更新日(古い順))
     * @param userInfo ユーザー情報
     * @param question 問題
     * @return ユーザ情報と問題の一部が一致したカードリスト
     */
    List<CardInfo> findByUserInfoAndQuestionLikeOrderByUpdatedAtAsc(UserInfo userInfo, String question);
    
    /**
     * ユーザーかつ問題が部分一致するカード情報一覧を取得する(更新日(新しい順))
     * @param userInfo ユーザー情報
     * @param question 問題
     * @return ユーザ情報と問題の一部が一致したカードリスト
     */
    List<CardInfo> findByUserInfoAndQuestionLikeOrderByUpdatedAtDesc(UserInfo userInfo, String question);
    
    /**
     * ユーザーかつ解答が部分一致するカード情報一覧を取得する(cardId(昇順))
     * @param userInfo ユーザー情報
     * @param answer 解答
     * @return ユーザ情報と解答の一部が一致したカードリスト
     */
    List<CardInfo> findByUserInfoAndAnswerLikeOrderByCardId(UserInfo userInfo, String answer);
    
    /**
     * ユーザーかつ解答が部分一致するカード情報一覧を取得する(作成日(古い順))
     * @param userInfo ユーザー情報
     * @param answer 解答
     * @return ユーザ情報と解答の一部が一致したカードリスト
     */
    List<CardInfo> findByUserInfoAndAnswerLikeOrderByCreatedAtAsc(UserInfo userInfo, String answer);
    
    /**
     * ユーザーかつ解答が部分一致するカード情報一覧を取得する(作成日(新しい順))
     * @param userInfo ユーザー情報
     * @param answer 解答
     * @return ユーザ情報と解答の一部が一致したカードリスト
     */
    List<CardInfo> findByUserInfoAndAnswerLikeOrderByCreatedAtDesc(UserInfo userInfo, String answer);
    
    /**
     * ユーザーかつ解答が部分一致するカード情報一覧を取得する(更新日(古い順))
     * @param userInfo ユーザー情報
     * @param answer 解答
     * @return ユーザ情報と解答の一部が一致したカードリスト
     */
    List<CardInfo> findByUserInfoAndAnswerLikeOrderByUpdatedAtAsc(UserInfo userInfo, String answer);
    
    /**
     * ユーザーかつ解答が部分一致するカード情報一覧を取得する(更新日(新しい順))
     * @param userInfo ユーザー情報
     * @param answer 解答
     * @return ユーザ情報と解答の一部が一致したカードリスト
     */
    List<CardInfo> findByUserInfoAndAnswerLikeOrderByUpdatedAtDesc(UserInfo userInfo, String answer);
    
    /**
     * ユーザーかつデッキが完全一致するカード情報一覧を取得する(cardId(昇順))
     * @param userInfo ユーザー情報
     * @param deckInfo デッキ情報
     * @return ユーザ情報とデッキ情報が完全一致したカードリスト
     */
    List<CardInfo> findByUserInfoAndDeckInfoOrderByCardId(UserInfo userInfo, DeckInfo deckInfo);

    /**
     * ユーザーかつデッキが完全一致するカード情報一覧を取得する(作成日(古い順))
     * @param userInfo ユーザー情報
     * @param deckInfo デッキ情報
     * @return ユーザ情報とデッキ情報が完全一致したカードリスト
     */
    List<CardInfo> findByUserInfoAndDeckInfoOrderByCreatedAtAsc(UserInfo userInfo, DeckInfo deckInfo);
    
    /**
     * ユーザーかつデッキが完全一致するカード情報一覧を取得する(作成日(新しい順))
     * @param userInfo ユーザー情報
     * @param deckInfo デッキ情報
     * @return ユーザ情報とデッキ情報が完全一致したカードリスト
     */
    List<CardInfo> findByUserInfoAndDeckInfoOrderByCreatedAtDesc(UserInfo userInfo, DeckInfo deckInfo);
    
    /**
     * ユーザーかつデッキが完全一致するカード情報一覧を取得する(更新日(古い順))
     * @param userInfo ユーザー情報
     * @param deckInfo デッキ情報
     * @return ユーザ情報とデッキ情報が完全一致したカードリスト
     */
    List<CardInfo> findByUserInfoAndDeckInfoOrderByUpdatedAtAsc(UserInfo userInfo, DeckInfo deckInfo);
    
    /**
     * ユーザーかつデッキが完全一致するカード情報一覧を取得する(更新日(新しい順))
     * @param userInfo ユーザー情報
     * @param deckInfo デッキ情報
     * @return ユーザ情報とデッキ情報が完全一致したカードリスト
     */
    List<CardInfo> findByUserInfoAndDeckInfoOrderByUpdatedAtDesc(UserInfo userInfo, DeckInfo deckInfo);
    
    
    /**
     * ユーザーかつカード評価が完全一致するカード情報一覧を取得する(cardID(昇順))
     * @param userInfo ユーザー情報
     * @param cardResult カード評価
     * @return ユーザ情報とカード評価が完全一致したカードリスト
     */
    List<CardInfo> findByUserInfoAndCardResultOrderByCardId(UserInfo userInfo, CardAnswerResult cardResult);
    
    /**
     * ユーザーかつカード評価が完全一致するカード情報一覧を取得する(作成日(古い順))
     * @param userInfo ユーザー情報
     * @param cardResult カード評価
     * @return ユーザ情報とカード評価が完全一致したカードリスト
     */
    List<CardInfo> findByUserInfoAndCardResultOrderByCreatedAtAsc(UserInfo userInfo, CardAnswerResult cardResult);
    
    /**
     * ユーザーかつカード評価が完全一致するカード情報一覧を取得する(作成日(新しい順))
     * @param userInfo ユーザー情報
     * @param cardResult カード評価
     * @return ユーザ情報とカード評価が完全一致したカードリスト
     */
    List<CardInfo> findByUserInfoAndCardResultOrderByCreatedAtDesc(UserInfo userInfo, CardAnswerResult cardResult);
    
    /**
     * ユーザーかつカード評価が完全一致するカード情報一覧を取得する(更新日(古い順))
     * @param userInfo ユーザー情報
     * @param cardResult カード評価
     * @return ユーザ情報とカード評価が完全一致したカードリスト
     */
    List<CardInfo> findByUserInfoAndCardResultOrderByUpdatedAtAsc(UserInfo userInfo, CardAnswerResult cardResult);
    
    /**
     * ユーザーかつカード評価が完全一致するカード情報一覧を取得する(更新日(新しい順))
     * @param userInfo ユーザー情報
     * @param cardResult カード評価
     * @return ユーザ情報とカード評価が完全一致したカードリスト
     */
    List<CardInfo> findByUserInfoAndCardResultOrderByUpdatedAtDesc(UserInfo userInfo, CardAnswerResult cardResult);
    
    /**
     * ユーザが作成したすべてのカードを取得する
     * @param userInfo ユーザー情報
     * @return ユーザが作成したすべてのカード
     */
    List<CardInfo> findByUserInfo(UserInfo userInfo);
    
    /**
     * ユーザが作成したすべてのカードを取得する(cardID(昇順))
     * @param userInfo ユーザー情報
     * @return ユーザが作成したすべてのカード
     */
    List<CardInfo> findByUserInfoOrderByCardId(UserInfo userInfo);
    
    /**
     * ユーザが作成したすべてのカードを取得する(作成日(古い順))
     * @param userInfo ユーザー情報
     * @return ユーザが作成したすべてのカード
     */
    List<CardInfo> findByUserInfoOrderByCreatedAt(UserInfo userInfo);
    
    /**
     * ユーザが作成したすべてのカードを取得する(作成日(新しい順))
     * @param userInfo ユーザー情報
     * @return ユーザが作成したすべてのカード
     */
    List<CardInfo> findByUserInfoOrderByCreatedAtDesc(UserInfo userInfo);
    
    /**
     * ユーザが作成したすべてのカードを取得する(更新日(古い順))
     * @param userInfo ユーザー情報
     * @return ユーザが作成したすべてのカード
     */
    List<CardInfo> findByUserInfoOrderByUpdatedAt(UserInfo userInfo);
    
    /**
     * ユーザが作成したすべてのカードを取得する(更新日(新しい順))
     * @param userInfo ユーザー情報
     * @return ユーザが作成したすべてのカード
     */
    List<CardInfo> findByUserInfoOrderByUpdatedAtDesc(UserInfo userInfo);
    
    List<CardInfo> findByUserInfoAndDeckInfoAndCardResult(UserInfo userInfo, DeckInfo deckInfo, CardAnswerResult cardResult);

    List<CardInfo> findByUserInfoAndDeckInfo(UserInfo userInfo, DeckInfo deckInfo);

//    void deleteByDeckId(Long selectedDeckId);
}

