package com.example.ankiapp.service;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.example.ankiapp.entitiy.DeckInfo;
import com.example.ankiapp.form.DeckUpdateForm;
import com.example.ankiapp.repository.DeckInfoRepository;
import com.example.ankiapp.repository.UserInfoRepository;
import com.github.dozermapper.core.Mapper;
//import jakarta.persistence.criteria.Path;
import lombok.RequiredArgsConstructor;




@Service
@RequiredArgsConstructor
public class DeckEditServiceImpl implements DeckEditService{


    private final UserInfoRepository userInfoRepository;
    
    /** ログイン情報テーブルDIO*/
    private final DeckInfoRepository repository;

    /**Dozer Mapper*/
    private final Mapper mapper;
    
    private final ImageStorageService imageService;
    
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
    public DeckInfo updateDeck(DeckInfo deckInfo, DeckUpdateForm form) throws IOException {
        if(form.getTitle() != null && !form.getTitle().trim().isEmpty()) {
            deckInfo.setTitle(form.getTitle());
        }
        if(form.getDescription() != null && !form.getDescription().trim().isEmpty()) {
            deckInfo.setDescription(form.getDescription());
        }
        if(!form.getImageFile().isEmpty()) {
            String searchFileName = searchFileName(form.getDeckId());
            Path imgFilePath = imageService.getDeckImagePath(deckInfo.getUserInfo().getLoginId(), form.getDeckId());
            Files.deleteIfExists(imgFilePath);
            Files.copy(form.getImageFile().getInputStream(), imgFilePath);
            deckInfo.setImagePath(searchFileName);
        }
        
        repository.save(deckInfo);
        return deckInfo;
    }


    
    private String searchFileName(Long deckId) {
        String searchFileName = "deck_" + deckId + imgExtract;
        return searchFileName;
    }

//    @Override
//    public void createDeck(DeckForm form) throws IOException{
//
//        var userInfo = getUserInfo();
//        var deckInfo = mapper.map(form, DeckInfo.class);
//        if(!form.getImageFile().isEmpty()) {
//            /**保存する画像ファイルの設定*/
//
//            Long nowId = repository.findMaxDeckId() +1;
//            String saveFileName = AppUtility.getUsername() + "Deck" + nowId + imgExtract;
//            Path imgFilePath = Path.of(imgFolder, saveFileName);
//            
//            Files.copy(form.getImageFile().getInputStream(), imgFilePath);
////            deckInfo.setImagePath("data:image/jpg;base64," + outputImage(nowId));
//            deckInfo.setImagePath(saveFileName);
//        }else {
//            deckInfo.setImagePath(imgdefault + imgExtract);
//        }
//       
//        deckInfo.setUserInfo(userInfo);
//        deckInfo.setCreatedAt(LocalDateTime.now());
//        deckInfo.setUpdatedAt(LocalDateTime.now());
//        repository.save(deckInfo);
//    }
//
//    @Override
//    public List<DeckInfo> findDeckInfo() {
//        return repository.findByUserInfoOrderByDeckId(getUserInfo());
//    }
//    
//    public UserInfo getUserInfo() {
//        return userInfoRepository.findByLoginId(AppUtility.getUsername());
//    }
//
//    @Override
//    public DeckInfo findDeckInfoByDeckId(Long deckId) {
//        return repository.findByDeckId(deckId);
//    }
//
//    @Override
//    public String deckImage(Long deckId) throws IOException {
//        // TODO 自動生成されたメソッド・スタブ
//        return null;
//    }

    

}
