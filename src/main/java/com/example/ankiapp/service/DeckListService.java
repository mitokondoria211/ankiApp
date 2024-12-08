package com.example.ankiapp.service;

import java.util.List;
import com.example.ankiapp.dto.DeckListInfo;
import com.example.ankiapp.dto.DeckSearchInfo;


public interface DeckListService {

    /**
     *ユーザーが作成したデッキをすべて取得、デッキ一覧画面に必要なデータに変換して、返却します
     *
     *@return デッキリストの全データ
     */
    public List<DeckListInfo> editDeckList();
    
    public List<DeckListInfo> editDeckListByParam(DeckSearchInfo dto);
    
}
