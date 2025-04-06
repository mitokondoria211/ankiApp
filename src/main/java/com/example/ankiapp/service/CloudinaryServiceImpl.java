package com.example.ankiapp.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.api.ApiResponse;
import com.cloudinary.utils.ObjectUtils;
import com.example.ankiapp.entitiy.CardInfo;
import com.example.ankiapp.entitiy.DeckInfo;
import com.example.ankiapp.entitiy.UserInfo;
import com.example.ankiapp.repository.CardInfoRepository;
import com.example.ankiapp.utilty.AppUtility;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class CloudinaryServiceImpl implements CloudinaryService{

    private final Cloudinary cloudinary;
    
    private final CardInfoRepository cardInfoRepository;
    
    @Value("${default.image}")
    private String defaultImg;
    
    @Override
    public String uploadFile(MultipartFile file, String customName) {
        try {
            Map uploadResult = cloudinary.uploader().upload(
                    file.getBytes(), 
                    ObjectUtils.asMap(
                            "public_id", "users/images/" + customName
                        )
                    );
            return uploadResult.get("secure_url").toString();
        }catch (IOException e) {
            throw new RuntimeException("ファイルのアップロードに失敗しました", e);
        }
        
    }
    
    @Override
    public List<String> getImagesFromFolder(String folderName) {
        List<String> imageUrls = new ArrayList<>();
        try {
            ApiResponse response = cloudinary.search()
                    .expression("folder:" + folderName + " AND resource_type:image") // フォルダ名と画像種別を指定
                    .maxResults(30)
                    .execute();

            List<Map> resources = (List<Map>) response.get("resources");
            for (Map resource : resources) {
                String url = (String) resource.get("secure_url");
                imageUrls.add(url);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageUrls;
    }

    @Override
    public String uploadDeckImageFile(MultipartFile file, String username, Long deckId) {
    	String publicId ="users/" + username + "/decks/deck_" + deckId;
//        try {
//            
//            Map uploadResult = cloudinary.uploader().upload(
//                file.getBytes(), 
//                ObjectUtils.asMap(
//                	"public_id", publicId,
//                	"trasformation", new Transformation().quality("auto").fetchFormat("auto")
//                )
//             );
//            return uploadResult.get("secure_url").toString();
//        }catch (IOException e) {
//            throw new RuntimeException("ファイルのアップロードに失敗しました", e);
//        }
    	
    	return uploadImage(file, publicId);

    }
    
    @Override
    public List<String> displayDeckImages(List<DeckInfo> deckInfos) {
        List<String> imageUrls = new ArrayList<>();
        for (DeckInfo deck : deckInfos) {
            if (deck.getImageUrl() != null) {
                imageUrls.add(deck.getImageUrl()); 
            } else {
                imageUrls.add(defaultImg);
            }
        }
        return imageUrls;
    }
    
    @Override
    public void deleteDeckImage(DeckInfo deckInfo) throws IOException {
        String publicId = "users/" + AppUtility.getUsername() + "/decks/deck_" + deckInfo.getDeckId();
        Map result = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
    }
    
    @Override
    public void deleteQuestionImage(CardInfo cardInfo) throws IOException {
        String publicId = "users/" + AppUtility.getUsername() + 
                "/decks/deck_" +cardInfo.getDeckInfo().getDeckId() + "/card_" + 
                cardInfo.getCardId() +"_question";
        Map result = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
    }
    
    @Override
    public void deleteAnswerImage(CardInfo cardInfo) throws IOException {
        String publicId = "users/" + AppUtility.getUsername() + 
                "/decks/deck_" +cardInfo.getDeckInfo().getDeckId() + "/card_" + 
                cardInfo.getCardId() +"_answer";
        Map result = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
    }
    
    @Override
    public void deleteAllDeckCardImages(UserInfo userInfo ,DeckInfo deckInfo) throws IOException {
        List<CardInfo> cardInfos = cardInfoRepository.findByUserInfoAndDeckInfo(userInfo, deckInfo);
        
        for (CardInfo card : cardInfos) {
            deleteQuestionImage(card);
            deleteAnswerImage(card);
        }
    }
    
    @Override
    public String uploadQuestionCardImage(MultipartFile file, String username, Long deckId, Long cardId) {
        String publicId = "users/" + username + "/decks/deck_" + deckId + "/card_" + cardId +"_question";
//        try {
//            Map uploadResult = cloudinary.uploader().upload(
//                    file.getBytes(), 
//                    ObjectUtils.asMap(
//                        "public_id", publicId,
//                        "trasformation", new Transformation().quality("auto").fetchFormat("auto")
//                    	)
//                    );
//            return uploadResult.get("secure_url").toString();
//        }catch (IOException e) {
//            throw new RuntimeException("ファイルのアップロードに失敗しました", e);
//        }
        return uploadImage(file, publicId);
    }
    
    @Override
    public String uploadAnswerCardImage(MultipartFile file, String username, Long deckId, Long cardId) {
        String publicId = "users/" + username + "/decks/deck_" + deckId + "/card_" + cardId +"_answer";
//        try {
//            Map uploadResult = cloudinary.uploader().upload(
//                    file.getBytes(), 
//                    ObjectUtils.asMap( "public_id", publicId)
//                    );
//            return uploadResult.get("secure_url").toString();
//        }catch (IOException e) {
//            throw new RuntimeException("ファイルのアップロードに失敗しました", e);
//        }
        return uploadImage(file, publicId);
    }
    
    private String uploadImage(MultipartFile file, String publicId) {
    	try {
            Map uploadResult = cloudinary.uploader().upload(
                file.getBytes(), 
                ObjectUtils.asMap( 
                	"public_id", publicId,
                	"trasformation", new Transformation().
                	quality("auto").
                	fetchFormat("auto")
                )
            );
            return uploadResult.get("secure_url").toString();
        }catch (IOException e) {
            throw new RuntimeException("ファイルのアップロードに失敗しました", e);
        }
    }
}
