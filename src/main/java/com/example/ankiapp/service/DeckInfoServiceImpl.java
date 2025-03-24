package com.example.ankiapp.service;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.ankiapp.constant.DeckCreateResult;
import com.example.ankiapp.entitiy.DeckInfo;
import com.example.ankiapp.entitiy.UserInfo;
import com.example.ankiapp.form.DeckForm;
import com.example.ankiapp.repository.CardInfoRepository;
import com.example.ankiapp.repository.DeckInfoRepository;
import com.example.ankiapp.repository.UserInfoRepository;
import com.example.ankiapp.utilty.AppUtility;
import com.github.dozermapper.core.Mapper;
//import jakarta.persistence.criteria.Path;
import lombok.RequiredArgsConstructor;
import lombok.var;


@Service
@RequiredArgsConstructor
//@Transactional
public class DeckInfoServiceImpl implements DeckInfoService{


    private final UserInfoRepository userInfoRepository;
    
    /** ログイン情報テーブルDIO*/
    private final DeckInfoRepository repository;
    
    private final CardInfoRepository cardInfoRepository;
    
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
    public DeckCreateResult createDeck(DeckForm form) throws IOException{

        var userInfo = getUserInfo();
        var deckInfo = mapper.map(form, DeckInfo.class);
        
        deckInfo.setUserInfo(userInfo);
        deckInfo.setCreatedAt(LocalDateTime.now());
        deckInfo.setUpdatedAt(LocalDateTime.now());
        
        
        try {
            deckInfo = repository.save(deckInfo);
            Long deckId = deckInfo.getDeckId();
            try {
                //カードの問題画像の処理
                if(!form.getImageFile().isEmpty()) {
//                Files.createDirectories(Path.of(createImgFolder(AppUtility.getUsername())));
                    String saveFileName = imageService.saveDeckImage(form.getImageFile(), AppUtility.getUsername(), deckId);
                    deckInfo.setImagePath(saveFileName);
                }else {
                    deckInfo.setImagePath(imgdefault + imgExtract);
                }
            }catch(IOException e) {
                throw new RuntimeException();
            }
            repository.save(deckInfo);
        }catch(DataIntegrityViolationException e) {
            return DeckCreateResult.FAILURE_BY_DB_ERROR;
        }catch(RuntimeException e) {
            return DeckCreateResult.FAILURE_BY_IMAGE_ERROR;
        }
        
//        if(!form.getImageFile().isEmpty()) {
//            /**保存する画像ファイルの設定*/
////            String saveFileName = AppUtility.getUsername() + "Deck" + nowId + imgExtract;
//            String saveFileName = imageService.saveDeckImage(form.getImageFile(), AppUtility.getUsername(), deckId);
////            Path imgFilePath = Path.of(createImgFolder(AppUtility.getUsername()), saveFileName);
//         // ディレクトリが存在しない場合は作成する
////            Files.createDirectories(Path.of(createImgFolder(AppUtility.getUsername())));
////            Files.copy(form.getImageFile().getInputStream(), imgFilePath);
////            deckInfo.setImagePath("data:image/jpg;base64," + outputImage(nowId));
//            deckInfo.setImagePath(saveFileName);
//        }else {
//            deckInfo.setImagePath(imgdefault + imgExtract);
//        }
//        repository.save(deckInfo);
        
        return DeckCreateResult.SUCCEED;
    }

    @Override
    public List<DeckInfo> findDeckInfo() {
//        var deckInfos = repository.findByUserInfo(getUserInfo())
        return repository.findByUserInfoOrderByDeckId(getUserInfo());
    }
    
    private UserInfo getUserInfo() {
        return userInfoRepository.findByLoginId(AppUtility.getUsername());
    }

    @Override
    public DeckInfo findDeckInfoByDeckId(Long deckId) {
        return repository.findByDeckId(deckId);
    }

    @Override
    public List<DeckInfo> filterDeckListByCardInfos() {
        var deckInfos = findDeckInfo();
        var iterator = deckInfos.iterator();
        while(iterator.hasNext()) {
            var deck = iterator.next();
            var cardInfos = cardInfoRepository.findByUserInfoAndDeckInfoOrderByCardId(getUserInfo(), deck);
            if(cardInfos.isEmpty()) {
                iterator.remove();
            }
        }
        return deckInfos;
    }    

}
