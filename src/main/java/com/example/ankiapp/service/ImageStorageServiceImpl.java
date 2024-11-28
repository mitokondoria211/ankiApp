package com.example.ankiapp.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class ImageStorageServiceImpl implements ImageStorageService {
    
    @Value("${image.folder}")
    private String baseImageFolder;

    @Value("${image.extract}")
    private String imageExtract;
    
    /**プロフィール画像の保管拡張子*/
    @Value("${image.default}")
    private String imgdefault;
    
    @Override
    public Path getUserDirectory(String username) {
        return Path.of(baseImageFolder, "users", username);
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
    public String storeImage(MultipartFile file) throws IOException {
       return null;
    }

    @Override
    public String saveQuestionCardImage(MultipartFile file, String userName, Long deckId, Long cardId) throws IOException {
        String fileName =  "card_" + cardId + "_question"+ imageExtract;
        // ディレクトリが存在しない場合は作成する
        Path imgFolderPath =getDeckCardsDirectory(userName, deckId);
        Path imgFilePath = imgFolderPath.resolve(fileName);
        Files.createDirectories(imgFolderPath);
        Files.copy(file.getInputStream(), imgFilePath);
        return fileName;
    }
    
    @Override
    public String saveAnswerCardImage(MultipartFile file, String userName, Long deckId, Long cardId)
            throws IOException {
        String fileName =  "card_" + cardId + "_answer"+ imageExtract;
        // ディレクトリが存在しない場合は作成する
        Path imgFolderPath =getDeckCardsDirectory(userName, deckId);
        Path imgFilePath = imgFolderPath.resolve(fileName);
        Files.createDirectories(imgFolderPath);
        Files.copy(file.getInputStream(), imgFilePath);
        return fileName;
    }

    @Override
    public String saveDeckImage(MultipartFile file, String userName,Long deckId) throws IOException {
        // TODO 自動生成されたメソッド・スタブ
        
        String fileName =  "deck_" +deckId + imageExtract;
        // ディレクトリが存在しない場合は作成する
        Path imgFolderPath =getDeckDirectory(userName, deckId);
        Path imgFilePath = imgFolderPath.resolve(fileName);
        Files.createDirectories(imgFolderPath);
        Files.copy(file.getInputStream(), imgFilePath);
        
        return fileName;
    }
    
    private Path searchDeckImage(String userName,Long deckId) {
        Path imgFilePath = getDeckImagePath(userName, deckId);
        return Files.exists(imgFilePath) ? imgFilePath : Path.of(baseImageFolder, imgdefault + imageExtract);
    }
    
    private String outputDeckImage(String userName,Long deckId) throws IOException {
        var imgFilePath = searchDeckImage(userName,deckId);
        var byteImg = Files.readAllBytes(imgFilePath);
        return Base64.getEncoder().encodeToString(byteImg);
    }
    
    private Path searchQuestionCardImage(String userName,Long deckId, Long cardId) {
        Path imgFilePath = getQuestionCardImagePath(userName, deckId, cardId);
        return Files.exists(imgFilePath) ? imgFilePath : Path.of(baseImageFolder, imgdefault + imageExtract);
    }
    
    private String outputQuestionCardImage(String userName,Long deckId, Long cardId) throws IOException {
        var imgFilePath = searchQuestionCardImage(userName,deckId, cardId);
        var byteImg = Files.readAllBytes(imgFilePath);
        return Base64.getEncoder().encodeToString(byteImg);
    }
    
    private Path searchAnswerCardImage(String userName,Long deckId, Long cardId) {
        Path imgFilePath = getAnswerCardImagePath(userName, deckId, cardId);
        return Files.exists(imgFilePath) ? imgFilePath : Path.of(baseImageFolder, imgdefault + imageExtract);
    }
    
    private String outputAnswerCardImage(String userName,Long deckId, Long cardId) throws IOException {
        var imgFilePath = searchAnswerCardImage(userName,deckId, cardId);
        var byteImg = Files.readAllBytes(imgFilePath);
        return Base64.getEncoder().encodeToString(byteImg);
    }

    @Override
    public String displayDeckImage(String userName, Long deckId)
            throws IOException {
        
        return "data:image/jpg;base64," + outputDeckImage(userName, deckId);
    }
    private String defaultBaseImage() throws IOException {
        Path imgFilePath = Path.of(baseImageFolder, imgdefault + imageExtract);
        var byteImg = Files.readAllBytes(imgFilePath);
        return Base64.getEncoder().encodeToString(byteImg);
    }
    
    @Override
    public String displayDefaultImage() throws IOException {
        
        return "data:image/jpg;base64," + defaultBaseImage();
    }
    @Override
    public String displayQuestionCardImage(String userName, Long deckId, Long cardId)
            throws IOException {
        return "data:image/jpg;base64," + outputQuestionCardImage(userName, deckId, cardId);
    }
    @Override
    public String displayAnswerCardImage(String userName, Long deckId, Long cardId)
            throws IOException {
        return "data:image/jpg;base64," + outputAnswerCardImage(userName, deckId, cardId);
    }
    @Override
    public String updateQuestionImage(MultipartFile file, String userName, Long deckId, Long cardId)
            throws IOException {
        return null;
    }
    @Override
    public String updateAnswerImage(MultipartFile file, String userName, Long deckId, Long cardId)
            throws IOException {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }
    @Override
    public String updateDeckImage(MultipartFile file, String userName, Long deckId)
            throws IOException {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }
   
    

    

}
