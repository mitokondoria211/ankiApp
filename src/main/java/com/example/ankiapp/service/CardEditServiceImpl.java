package com.example.ankiapp.service;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.example.ankiapp.constant.CardCreateResult;
import com.example.ankiapp.constant.CardUpadateResult;
import com.example.ankiapp.constant.db.CardAnswerResult;
import com.example.ankiapp.entitiy.CardInfo;
import com.example.ankiapp.entitiy.UserInfo;
import com.example.ankiapp.form.CardEditorForm;
import com.example.ankiapp.form.CardUpdateForm;
import com.example.ankiapp.repository.CardInfoRepository;
import com.example.ankiapp.repository.DeckInfoRepository;
import com.example.ankiapp.repository.UserInfoRepository;
import com.example.ankiapp.utilty.AppUtility;
import com.github.dozermapper.core.Mapper;

import lombok.RequiredArgsConstructor;


@Transactional
@Service
@RequiredArgsConstructor
public class CardEditServiceImpl implements CardEditService{

    /** ログイン情報テーブルDIO*/
    private final CardInfoRepository repository;
    
    private final UserInfoRepository userInfoRepository;
    
    private final DeckInfoRepository deckInfoRepository;
    
    private final CloudinaryService cloudinaryService;
    
    /**Dozer Mapper*/
    private final Mapper mapper;
    
    @Override
    public CardCreateResult createCardInfo(CardEditorForm form) throws IOException {
        var userInfo = getUserInfo();
        var deckId = form.getDeckId();
        var deckInfo = deckInfoRepository.findByDeckId(deckId);
        var cardInfo = mapper.map(form, CardInfo.class);
        cardInfo.setCardResult(CardAnswerResult.UNRATED);
        cardInfo.setUserInfo(userInfo);
        cardInfo.setDeckInfo(deckInfo);
        cardInfo.setCreatedAt(LocalDateTime.now());
        cardInfo.setUpdatedAt(LocalDateTime.now()); 
        
        try {
           cardInfo = repository.save(cardInfo);
           Long nowId = cardInfo.getCardId();
            //カードの問題画像の処理
            if(!form.getQuestionImageFile().isEmpty()) {
                String saveFileUrl = cloudinaryService.uploadQuestionCardImage(
                        form.getQuestionImageFile(), userInfo.getLoginId(), deckId, nowId);
                cardInfo.setQuestionImageUrl(saveFileUrl);
            }
            
            //カードの解答画像の処理
            if(!form.getAnswerImageFile().isEmpty()) {
                String saveFileUrl = cloudinaryService.uploadAnswerCardImage(
                        form.getAnswerImageFile(), userInfo.getLoginId(), deckId, nowId);
                cardInfo.setAnswerImageUrl(saveFileUrl);
            }
            //再度レポジトリに保存
            repository.save(cardInfo);
            
        } catch(DataIntegrityViolationException e) {
        	TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // ← これが重要
            return CardCreateResult.FAILURE_BY_DB_ERROR;
        } catch(MaxUploadSizeExceededException e) {
            return CardCreateResult.FAILURE_BY_IMAGE_SIZE_ERROR;
        }catch (RuntimeException e) {
            return CardCreateResult.FAILURE_BY_IMAGE_ERROR;
        }

        return CardCreateResult.SUCCEED;
    }
    
    private UserInfo getUserInfo() {
        return userInfoRepository.findByLoginId(AppUtility.getUsername());
    }

    @Override
    public CardInfo findCardInfoByCardId(Long cardId) {
        return repository.findByCardId(cardId);
    }

    
    @Override
    public CardUpadateResult updateCardEditorInfo(CardInfo cardInfo, CardUpdateForm form) throws IOException {
        
        
        cardInfo.setCardName(form.getCardName());
        cardInfo.setQuestion(form.getQuestion());
        cardInfo.setAnswer(form.getAnswer());
        var deckId = cardInfo.getDeckInfo().getDeckId();

        
        if(form.getCardResult() != null) {
            cardInfo.setCardResult(form.getCardResult());
        }
        try {
            if(!form.getQuestionImageFile().isEmpty()) {
                String saveFileUrl = cloudinaryService.uploadQuestionCardImage(
                        form.getQuestionImageFile(), AppUtility.getUsername(), deckId, cardInfo.getCardId());
                cardInfo.setQuestionImageUrl(saveFileUrl);
            }
            
            if(!form.getAnswerImageFile().isEmpty()) {
                String saveFileUrl = cloudinaryService.uploadAnswerCardImage(
                        form.getAnswerImageFile(), AppUtility.getUsername(), deckId, cardInfo.getCardId());
                cardInfo.setAnswerImageUrl(saveFileUrl);
            }
            //再度レポジトリに保存
            repository.save(cardInfo);
        }catch(DataIntegrityViolationException e) {
        	TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return CardUpadateResult.FAILURE_BY_DB_ERROR;
        }catch(RuntimeException e) {
            return CardUpadateResult.FAILURE_BY_IMAGE_ERROR;
        }
        
        return CardUpadateResult.SUCCEED;
    }
}
