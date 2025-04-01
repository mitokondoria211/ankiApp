package com.example.ankiapp.service;

import java.util.List;
import com.example.ankiapp.entitiy.CardInfo;
import com.example.ankiapp.entitiy.DeckInfo;


public interface CardDisplayService {
    /**
     * カードIDからカード情報を取得する
     * @param cardId
     * @return カード情報
     */
    CardInfo findCardInfoByCardId(Long cardId);
   
    /**
     * カード名からカード情報を取得する
     * @param cardName カード名
     * @return カード情報
     */
    CardInfo findCardInfoByCardName(String cardName);
   
    /**
     * ユーザー内のカードリストすべてを取得する
     * @return カード情報リスト
     */
    List<CardInfo> findCardInfos();

    /**
     * カード情報を登録する
     * @param cardInfo　カード情報
     */
    void saveCardInfo(CardInfo cardInfo);
   
    /**
     * デッキ内のカードの枚数をカウントする
     * @param deckId
     * @return デッキ内のカードの枚数
     */
    Integer getCardCount(Long deckId);
   
    List<CardInfo> findCardInfoByDeckIdAndCardResult(Long deckId, String cardResult);
   
    Integer getCardCountByCardResult(Long deckId, String result);
   
    List<DeckInfo> filterEmptyDecks(List<DeckInfo> decks);

    List<CardInfo> findCardInfoByDeckId(Long deckId);
   
   
}
