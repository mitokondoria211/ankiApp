package com.example.ankiapp.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.cloudinary.Cloudinary;
import com.cloudinary.api.ApiResponse;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CloudinaryServiceImpl implements CloudinaryService{

    private final Cloudinary cloudinary;
    
    @Value("${image.folder}")
    private String baseImageFolder;

    @Value("${image.extract}")
    private String imageExtract;
    
    /**プロフィール画像の保管拡張子*/
    @Value("${image.default}")
    private String imgdefault;
    
    @Override
    public String uploadFile(MultipartFile file, String customName) {
        try {
            Map uploadResult = cloudinary.uploader().upload(
                    file.getBytes(), 
                    ObjectUtils.asMap(
//                            "upload_preset", "spring-upload",  
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
    public Path getUserDirectory(String username) {
        return Path.of("users", username);
    }
    @Override
    public Path getUserDecksDirectory(String username) {
        return getUserDirectory(username).resolve("decks");
    }
    @Override
    public Path getDeckDirectory(String username, Long deckId) {
        return getUserDecksDirectory(username).resolve("deck_" + deckId);
    }
    @Override
    public Path getDeckCardsDirectory(String username, Long deckId) {
        return getDeckDirectory(username, deckId).resolve("cards");
    }
    @Override
    public Path getDeckImagePath(String username, Long deckId) {
        return getDeckDirectory(username, deckId).resolve("deck_" +deckId + imageExtract);
    }
    @Override
    public Path getQuestionCardImagePath(String username, Long deckId, Long cardId) {
        return getDeckCardsDirectory(username, deckId).resolve("card_" + cardId +"_question" + imageExtract);
    }
    
    @Override
    public Path getAnswerCardImagePath(String username, Long deckId, Long cardId) {
        return getDeckCardsDirectory(username, deckId).resolve("card_" + cardId +"_answer" + imageExtract);
    }
    
    @Override
    public String saveQuestionCardImage(MultipartFile file, String userName, Long deckId, Long cardId) throws IOException {
        String fileName =  "card_" + cardId + "_question"+ imageExtract;
        // ディレクトリが存在しない場合は作成する
        Path imgFolderPath =getDeckCardsDirectory(userName, deckId);
        String imgFileUrl = imgFolderPath.resolve(fileName).toString();
        return imgFileUrl;
    }
    
    @Override
    public String uploadDeckImage(MultipartFile file, String userName,Long deckId) throws IOException {
        
        String fileName =  "deck_" + deckId + imageExtract;
        // ディレクトリが存在しない場合は作成する
        Path imgFolderPath = getDeckDirectory(userName, deckId);
        Path imgFilePath = imgFolderPath.resolve(fileName);
        Files.createDirectories(imgFolderPath);
        Files.deleteIfExists(imgFilePath);
        Files.copy(file.getInputStream(), imgFilePath);
        
        return fileName;
    }
    
   

}
