package com.example.ankiapp.service;

import java.util.List;
import com.example.ankiapp.constant.UserDeleteResult;
import com.example.ankiapp.dto.UserListInfo;
import com.example.ankiapp.dto.UserSearchInfo;
;

public interface UserListService {

    /**
     *ユーザー情報を全件検索し、ユーザー一覧画面に必要な情報へ変換して返却します
     *
     *@return ユーザー情報テーブルの全登録情報
     */
    public List<UserListInfo> editUserList();
    
    /**
     *ユーザー情報を条件検索した結果を画面の表示用に変換して返却します
     *
     *@param dto検索氏に使用するパラメーター
     *@return 検索結果
     */
    public List<UserListInfo> editUserListByParam(UserSearchInfo dto);
    
    /**
     * 指定されたIDのユーザー情報を削除します
     * @param selectedLoginId
     * @return 実行結果(エラー有無)
     */
    public UserDeleteResult deleteUserInfoById(String selectedLoginId);


}
