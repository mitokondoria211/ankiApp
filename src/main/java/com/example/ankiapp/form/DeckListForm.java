package com.example.ankiapp.form;


import com.example.ankiapp.constant.SortType;
import lombok.Data;

/**
 * ユーザー一覧画面Formクラス
 */
@Data
public class DeckListForm {
    
    /**デッキタイトル*/
    private Long selectedDeckId;
    
    /**デッキタイトル*/
    private String title;
    
    /**デッキソートタイプ*/
    private SortType sortType;
    
}
