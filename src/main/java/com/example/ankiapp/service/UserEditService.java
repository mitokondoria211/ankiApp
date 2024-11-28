package com.example.ankiapp.service;

import java.util.Optional;
import com.example.ankiapp.dto.UserEditResult;
import com.example.ankiapp.dto.UserUpdateInfo;
import com.example.ankiapp.entitiy.UserInfo;

public interface UserEditService {

//    Object editUserListByParam(UserSearchInfo searchDto);

    public Optional<UserInfo> searchUserInfo(String loginId);
    
    public UserEditResult updateUserInfo(UserUpdateInfo userUpdateInfo);

}
