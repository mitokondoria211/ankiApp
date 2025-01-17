package com.example.ankiapp.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 *データをソートするタイプ種別
 */
@Getter
@AllArgsConstructor
public enum SortType {
   
    /**並び替えなし*/
    UNSORTED("unsorted","並び替えなし"),
    
    /**更新日時(新しい順)*/
    UPDATED_AT_DESC("updatedAtDesc", "更新日時(新しい順)"),
    
    /**更新日時(古い順)*/
    UPDATED_AT_ASC("updatedAtAsc", "更新日時(古い順)"),
    
    /**作成日時(新しい順)*/
    CREATED_AT_DESC("createdAtDesc", "作成日時(新しい順)"),
    
    /**作成日時(古い順)*/
    CREATED_AT_ASC("createdAtAsc","作成日時(古い順)");
  
    /**ソートタイプキー*/
    private String sortType;
    
    /**ソートタイプ名*/
    private String sortTypeDisplay;
    
}
