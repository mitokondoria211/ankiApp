package com.example.ankiapp.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import com.example.ankiapp.constant.CreateCardResult;
import com.example.ankiapp.constant.UpdateCardResult;
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
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.var;


@Service
@RequiredArgsConstructor
//@Transactional
public class CardEditServiceImpl implements CardEditService{

    /** ログイン情報テーブルDIO*/
    private final CardInfoRepository repository;
    
    private final UserInfoRepository userInfoRepository;
    
    private final DeckInfoRepository deckInfoRepository;
    
    private final ImageStorageService imageService;
 
    /**Dozer Mapper*/
    private final Mapper mapper;
    
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
    public CreateCardResult createCardInfo(CardEditorForm form) throws IOException {
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
            try {
                
                //カードの問題画像の処理
                if(!form.getQuestionImageFile().isEmpty()) {
                    
                    String saveFileName = imageService.saveQuestionCardImage(form.getQuestionImageFile(), AppUtility.getUsername(), deckId, nowId);
                    cardInfo.setQuestionImagePath(saveFileName);
                }else {
                    cardInfo.setQuestionImagePath(imgdefault + imgExtract);
                }
                
                //カードの解答画像の処理
                if(!form.getAnswerImageFile().isEmpty()) {
                    String saveFileName = imageService.saveAnswerCardImage(form.getAnswerImageFile(), AppUtility.getUsername(), deckId, nowId);
                    cardInfo.setAnswerImagePath(saveFileName);
                }else {
                     cardInfo.setAnswerImagePath(imgdefault + imgExtract);
                }
                  
            } catch(IOException e) {
                throw new RuntimeException();
            }
            //再度レポジトリに保存
            repository.save(cardInfo);
            
        } catch(DataIntegrityViolationException e) {
            return CreateCardResult.FAILURE_BY_DB_ERROR;
        } catch(MaxUploadSizeExceededException e) {
            return CreateCardResult.FAILURE_BY_IMAGE_SIZE_ERROR;
        }catch (RuntimeException e) {
            return CreateCardResult.FAILURE_BY_IMAGE_ERROR;
        }

        
        return CreateCardResult.SUCCEED;
    }
    
    private UserInfo getUserInfo() {
        return userInfoRepository.findByLoginId(AppUtility.getUsername());
    }

    @Override
    public CardInfo findCardInfoByCardId(Long cardId) {
        return repository.findByCardId(cardId);
    }

    @Override
    public UpdateCardResult updateCardEditorInfo(CardInfo cardInfo, CardUpdateForm form) throws IOException {
        
        
        cardInfo.setCardName(form.getCardName());
        cardInfo.setQuestion(form.getQuestion());
        cardInfo.setAnswer(form.getAnswer());
        var deckId = cardInfo.getDeckInfo().getDeckId();

        
        if(form.getCardResult() != null) {
            cardInfo.setCardResult(form.getCardResult());
        }
        try {
            try {
                if(!form.getQuestionImageFile().isEmpty()) {
                    String questionFileName = imageService.saveQuestionCardImage(
                            form.getQuestionImageFile(),AppUtility.getUsername(),deckId,cardInfo.getCardId());
                    cardInfo.setQuestionImagePath(questionFileName);
                }
                
                if(!form.getAnswerImageFile().isEmpty()) {
                    String answerFileName = imageService.saveAnswerCardImage(
                            form.getAnswerImageFile(),AppUtility.getUsername(),deckId,cardInfo.getCardId());
                    cardInfo.setAnswerImagePath(answerFileName);
                }
            }catch(IOException e) {
                throw new RuntimeException();
            }
            //再度レポジトリに保存
            repository.save(cardInfo);
        }catch(DataIntegrityViolationException e) {
            return UpdateCardResult.FAILURE_BY_DB_ERROR;
        }catch(RuntimeException e) {
            return UpdateCardResult.FAILURE_BY_IMAGE_ERROR;
        }
        


        
        

        
//        try {
//            repository.save(cardInfo);
//        }catch(DataIntegrityViolationException e) {
//            return UpdateCardResult.FAILURE_BY_DB_ERROR;
//        }
        
        
        return UpdateCardResult.SUCCEED;
    }
    
    private String searchQuestionFileName(Long cardId) {
        String searchFileName =  "card_" + cardId + "_question"+ imgExtract;
        return searchFileName;
    }
    
    private String searchAnswerFileName(Long cardId) {
        String searchFileName =  "card_" + cardId + "_answer"+ imgExtract;
        return searchFileName;
    }

}
