package com.example.ankiapp.service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import com.example.ankiapp.entitiy.CardInfo;
import com.example.ankiapp.entitiy.DeckInfo;
import com.example.ankiapp.entitiy.UserInfo;

public interface CloudinaryService {
    
    String uploadFile(MultipartFile file, String costomName);
    
    String uploadDeckImageFile(MultipartFile file, String username, Long deckId);

    List<String> displayDeckImages(List<DeckInfo> deckInfos);

    void deleteDeckImage(DeckInfo deckInfo) throws IOException;

    String uploadQuestionCardImage(MultipartFile file, String username, Long deckId, Long cardId);

    String uploadAnswerCardImage(MultipartFile file, String username, Long deckId, Long cardId);

    void deleteQuestionImage(CardInfo cardInfo) throws IOException;

    void deleteAnswerImage(CardInfo cardInfo) throws IOException;

    void deleteAllDeckCardImages(UserInfo userInfo, DeckInfo deckInfo) throws IOException;

    List<String> getImagesFromFolder(String folderName);
}
