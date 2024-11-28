package com.example.ankiapp.service;

import java.util.List;
import com.example.ankiapp.constant.CardDeleteResult;
import com.example.ankiapp.dto.CardListInfo;
import com.example.ankiapp.dto.CardSearchInfo;
;

public interface CardListService {

    /**
     *ユーザー情報を全件検索し、ユーザー一覧画面に必要な情報へ変換して返却します
     *
     *@return ユーザー情報テーブルの全登録情報
     */
    List<CardListInfo> editCardInfos();
    
    List<CardListInfo> editCardListByParam(CardSearchInfo dto);
    
//    /**
//     *ユーザー情報を条件検索した結果を画面の表示用に変換して返却します
//     *
//     *@param dto検索氏に使用するパラメーター
//     *@return 検索結果
//     */
//    public List<DeckListInfo> editDeckListByParam(DeckListInfo dto);
    
//    /**
//     * 指定されたIDのユーザー情報を削除します
//     * @param selectedLoginId
//     * @return 実行結果(エラー有無)
//     */
    CardDeleteResult deleteCardEditorInfoByCardId(Long selectedCardId);


}
