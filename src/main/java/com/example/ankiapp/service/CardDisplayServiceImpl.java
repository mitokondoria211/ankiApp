package com.example.ankiapp.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.example.ankiapp.constant.db.CardAnswerResult;
import com.example.ankiapp.entitiy.CardInfo;
import com.example.ankiapp.entitiy.DeckInfo;
import com.example.ankiapp.repository.CardInfoRepository;
import com.example.ankiapp.repository.DeckInfoRepository;
import com.example.ankiapp.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;




@Service
@RequiredArgsConstructor
//@Transactional
public class CardDisplayServiceImpl implements CardDisplayService{

    /** ログイン情報テーブルDIO*/
    private final CardInfoRepository repository;
    
    /** ログイン情報テーブルDIO*/
    private final UserInfoRepository userRepository;
    
    /** ログイン情報テーブルDIO*/
    private final DeckInfoRepository deckRepository;
    
    /**プロフィール画像の保存先のフォルダ*/
    @Value("${image.folder}")
    private String imgFolder;
    
    /**プロフィール画像の保管拡張子*/
    @Value("${image.extract}")
    private String imgExtract;
    
    /**プロフィール画像の保管拡張子*/
    @Value("${image.default}")
    private String imgdefault;
    
    @Override
    public CardInfo findCardInfoByCardId(Long cardId) {
        var userInfo = userRepository.findByLoginId(getUserName());
        return repository.findByUserInfoAndCardId(userInfo, cardId);
    }

    @Override
    public List<CardInfo> findCardInfos() {
        var userInfo = userRepository.findByLoginId(getUserName());
        return repository.findByUserInfo(userInfo);
    }

    @Override
    public CardInfo findCardInfoByCardName(String name) {
        var userInfo = userRepository.findByLoginId(getUserName());
        return repository.findByUserInfoAndCardName(userInfo, name);
    }
    
    private String getUserName() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        return userName;
    }

    @Override
    public List<CardInfo> findCardInfoByDeckId(Long deckId) {
        var userInfo = userRepository.findByLoginId(getUserName());
        var deckInfo = deckRepository.findByDeckId(deckId);
        var cardInfos = repository.findByUserInfoAndDeckInfoOrderByCardId(userInfo, deckInfo);
        return cardInfos;
    }

    
    
    @Override
    public void saveCardInfo(CardInfo cardEditorInfo) {
        repository.save(cardEditorInfo);
    }

    @Override
    public Integer getCardCount(Long deckId) {
        var userInfo = userRepository.findByLoginId(getUserName());
        var deckInfo = deckRepository.findByDeckId(deckId);
        Integer count = repository.findByUserInfoAndDeckInfoOrderByCardId(userInfo, deckInfo).size();
        return count;
    }

    @Override
    public Integer getCardCountByCardResult(Long deckId, String result) {
//        var userInfo = userRepository.findByLoginId(getUserName());
//        var deckInfo = deckRepository.findByDeckId(deckId);
//        Integer count  = 0;
//        if(result.equals("未評価")) {
//            count += repository.findByUserInfoAndDeckInfoAndCardResult(userInfo, deckInfo,CardAnswerResult.UNRATED).size();
//            return count;
//        }else if(result.equals("不正解")) {
//            count += repository.findByUserInfoAndDeckInfoAndCardResult(userInfo, deckInfo,CardAnswerResult.AGAIN).size();
//            count += repository.findByUserInfoAndDeckInfoAndCardResult(userInfo, deckInfo,CardAnswerResult.DIFFICULT).size();
//            count += repository.findByUserInfoAndDeckInfoAndCardResult(userInfo, deckInfo,CardAnswerResult.INCORRECT).size();
//            return count;
//        }else if(result.equals("正解")) {
//            count += repository.findByUserInfoAndDeckInfoAndCardResult(userInfo, deckInfo,CardAnswerResult.EASY).size();
//            count += repository.findByUserInfoAndDeckInfoAndCardResult(userInfo, deckInfo,CardAnswerResult.CORRECT).size();
//            return count;
//        }
        
        return findCardInfoByDeckIdAndCardResult(deckId, result).size();
    }

    @Override
    public List<CardInfo> findCardInfoByDeckIdAndCardResult(Long deckId, String cardResult) {
        var userInfo = userRepository.findByLoginId(getUserName());
        var deckInfo = deckRepository.findByDeckId(deckId);
        var cardList = new ArrayList<CardInfo>();
        if(cardResult.equals("未評価")) {
            return repository.findByUserInfoAndDeckInfoAndCardResult(userInfo, deckInfo,CardAnswerResult.UNRATED);
        }else if(cardResult.equals("不正解")) {
            cardList.addAll(repository.findByUserInfoAndDeckInfoAndCardResult(userInfo, deckInfo,CardAnswerResult.AGAIN));
            cardList.addAll(repository.findByUserInfoAndDeckInfoAndCardResult(userInfo, deckInfo,CardAnswerResult.DIFFICULT));
            cardList.addAll(repository.findByUserInfoAndDeckInfoAndCardResult(userInfo, deckInfo,CardAnswerResult.INCORRECT));
            return cardList;
        }else if(cardResult.equals("正解")) {
            cardList.addAll(repository.findByUserInfoAndDeckInfoAndCardResult(userInfo, deckInfo,CardAnswerResult.EASY));
            cardList.addAll(repository.findByUserInfoAndDeckInfoAndCardResult(userInfo, deckInfo,CardAnswerResult.CORRECT));
            return cardList;
        }
        
        return findCardInfoByDeckId(deckId);
    }

    @Override
    public List<DeckInfo> filterEmptyDecks(List<DeckInfo> decks) {
        Iterator<DeckInfo> iterator = decks.iterator();
        while(iterator.hasNext()) {
            DeckInfo deckInfo = iterator.next();
            var cards = findCardInfoByDeckId(deckInfo.getDeckId());
            if(cards.size() == 0) {
                iterator.remove();
            }
        }
        return decks;
    }
}
