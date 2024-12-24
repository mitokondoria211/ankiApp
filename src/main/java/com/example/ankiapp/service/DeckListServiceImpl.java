package com.example.ankiapp.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.ankiapp.constant.SortType;
import com.example.ankiapp.dto.DeckListInfo;
import com.example.ankiapp.dto.DeckSearchInfo;
import com.example.ankiapp.entitiy.DeckInfo;
import com.example.ankiapp.entitiy.UserInfo;
import com.example.ankiapp.repository.DeckInfoRepository;
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
public class DeckListServiceImpl implements DeckListService {
    
    private final UserInfoRepository userInfoRepository;
    
	/** ユーザー情報テーブルDAO*/
	private final DeckInfoRepository repository;
	
	/**Dozer Mapper*/
	private final Mapper mapper;

    @Override
    public List<DeckListInfo> editDeckList() {
        return toDeckListInfos(repository.findByUserInfoOrderByDeckId(getUserInfo()));
    }
    
    /**
     * ユーザー情報EntityのListをユーザー一覧情報DTOのListに変換します 
     * 
     * @param userInfos ユーザー情報のEntityのList
     * @return ユーザー一覧情報DTOのList
     */
    private List<DeckListInfo> toDeckListInfos(List<DeckInfo> deckInfos) {
        var deckListInfos = new ArrayList<DeckListInfo>();
//        var deckInfos = repository.findByUserInfoOrderByDeckId(getUserInfo());
        for(DeckInfo deckInfo: deckInfos) {
            var deckListInfo = mapper.map(deckInfo, DeckListInfo.class);
            deckListInfos.add(deckListInfo);
        }
        return deckListInfos;
    }
    
    private UserInfo getUserInfo() {
        return userInfoRepository.findByLoginId(AppUtility.getUsername());
    }

    @Override
    public List<DeckListInfo> editDeckListByParam(DeckSearchInfo dto) {
        
        return toDeckListInfos(findDeckInfoByParam(dto));
    }
    
    /**
     * ユーザー情報取得(条件付き)
     * 
     * ユーザー情報の条件検索をする
     * 
     * @param form 入力情報
     * @return 検索結果
     */

    private List<DeckInfo> findDeckInfoByParam(DeckSearchInfo dto) {
        String titleParam = AppUtility.addWildcard(dto.getTitle());
        SortType sortType = dto.getSortType();
        switch (sortType){
            case UPDATED_AT_ASC: 
                return repository.findByUserInfoAndTitleLikeOrderByUpdatedAt(getUserInfo(), titleParam);
            case UPDATED_AT_DESC:
                return repository.findByUserInfoAndTitleLikeOrderByUpdatedAtDesc(getUserInfo(), titleParam);
            case CREATED_AT_ASC:
                return repository.findByUserInfoAndTitleLikeOrderByCreatedAt(getUserInfo(), titleParam);
            case CREATED_AT_DESC:
                return repository.findByUserInfoAndTitleLikeOrderByCreatedAtDesc(getUserInfo(), titleParam);
            case UNSORTED:
                return repository.findByUserInfoAndTitleLikeOrderByDeckId(getUserInfo(), titleParam);
                
        }
        return repository.findByUserInfoAndTitleLikeOrderByDeckId(getUserInfo(), titleParam);
        
    }

}
