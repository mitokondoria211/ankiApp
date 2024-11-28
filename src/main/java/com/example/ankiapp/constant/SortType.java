package com.example.ankiapp.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SortType {
   
    UNSORTED("unsorted","並び替えなし"),
    UPDATED_AT_DESC("updatedAtDesc", "更新日時(新しい順)"),
    UPDATED_AT_ASC("updatedAtAsc", "更新日時(古い順)"),
    CREATED_AT_DESC("createdAtDesc", "作成日時(新しい順)"),
    CREATED_AT_ASC("createdAtAsc","作成日時(古い順)");
  
    
    private String sortType;
    
    private String sortTypeDisplay;
    
}
