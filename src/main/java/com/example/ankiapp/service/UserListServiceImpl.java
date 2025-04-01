package com.example.ankiapp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ankiapp.constant.UserDeleteResult;
import com.example.ankiapp.dto.UserListInfo;
import com.example.ankiapp.dto.UserSearchInfo;
import com.example.ankiapp.entitiy.UserInfo;
import com.example.ankiapp.repository.UserInfoRepository;
import com.example.ankiapp.utilty.AppUtility;
import com.github.dozermapper.core.Mapper;

import lombok.RequiredArgsConstructor;



/**
 * ユーザー登録画面 Service実装クラス
 */

@Service
@RequiredArgsConstructor
//@Transactional
public class UserListServiceImpl implements UserListService {
    
	/** ユーザー情報テーブルDAO*/
	private final UserInfoRepository repository;
	
	/**Dozer Mapper*/
	private final Mapper mapper;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List <UserListInfo> editUserList() {
		return toUserListInfos(repository.findAll());
	}
	
    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserListInfo> editUserListByParam(UserSearchInfo dto) {
        return toUserListInfos(findUserInfoByParam(dto));
    }
    
    /**
     * ユーザー情報取得(条件付き)
     * 
     * ユーザー情報の条件検索をする
     * 
     * @param form 入力情報
     * @return 検索結果
     */

    private List<UserInfo> findUserInfoByParam(UserSearchInfo dto) {
        var loginIdParam = AppUtility.addWildcard(dto.getLoginId());
        if(dto.getUserStatusKind() != null && dto.getAuthorityKind() != null) {
            return repository.findByLoginIdLikeAndUserStatusKindAndAuthorityKind(loginIdParam,
                    dto.getUserStatusKind(), dto.getAuthorityKind());
        }else if(dto.getUserStatusKind() != null) {
            return repository.findByLoginIdLikeAndUserStatusKind(loginIdParam, dto.getUserStatusKind());
        }else if(dto.getAuthorityKind() != null) {
            return repository.findByLoginIdLikeAndAuthorityKind(loginIdParam, dto.getAuthorityKind());
        }else {
            return repository.findByLoginIdLike(loginIdParam);
        }
    }
    
    /**
     * ユーザー情報EntityのListをユーザー一覧情報DTOのListに変換します 
     * 
     * @param userInfos ユーザー情報のEntityのList
     * @return ユーザー一覧情報DTOのList
     */
    private List<UserListInfo> toUserListInfos(List<UserInfo> userInfos) {
        var userListInfos = new ArrayList<UserListInfo>();
        for (UserInfo userInfo: userInfos) {
            var userListInfo = mapper.map(userInfo, UserListInfo.class);
            userListInfo.setStatus(userInfo.getUserStatusKind().getDisplayValue());
            userListInfo.setAuthority(userInfo.getAuthorityKind().getDisplayValue());
            userListInfos.add(userListInfo);
        }
        return userListInfos;
    }

    @Override
    public UserDeleteResult deleteUserInfoById(String loginId) {
        var userInfo = repository.findById(loginId);
        if(userInfo.isEmpty()) {
            return UserDeleteResult.ERROR;
        }
        
        repository.deleteById(loginId);
        
        return UserDeleteResult.SUCCEED;
    }
	
//	public Optional<User> secondSearchUserByUserName(Long id) {
//		return userRepository.findById(id);
//	}
}
