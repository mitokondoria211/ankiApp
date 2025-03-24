package com.example.ankiapp.service;

import java.io.IOException;
import java.nio.file.Path;
import org.springframework.web.multipart.MultipartFile;

public interface ImageStorageService {
    Path getUserDirectory(String username);
    
    Path getUserDecksDirectory(String username);
    
    Path getDeckDirectory(String username, Long deckId);
    
    Path getDeckCardsDirectory(String username, Long deckId);
    
    Path getDeckImagePath(String username, Long deckId);
    
    Path getQuestionCardImagePath(String username, Long deckId, Long cardId);
    
    Path getAnswerCardImagePath(String username, Long deckId, Long cardId);

    String storeImage(MultipartFile file) throws IOException;
    
    String saveQuestionCardImage(MultipartFile file, String userName, Long deckId, Long cardId) throws IOException;
    
    String saveAnswerCardImage(MultipartFile file, String userName, Long deckId, Long cardId) throws IOException;
    
    String updateQuestionImage(MultipartFile file,String userName,Long deckId, Long cardId) throws IOException;
    
    String updateAnswerImage(MultipartFile file,String userName,Long deckId, Long cardId) throws IOException;
    
    String updateDeckImage(MultipartFile file,String userName,Long deckId) throws IOException;

    String saveDeckImage(MultipartFile file,String userName,Long deckId) throws IOException;
    
    String displayDeckImage(String userName,Long deckId) throws IOException;
    
    String displayQuestionCardImage(String userName,Long deckId, Long cardId) throws IOException;
    
    String displayAnswerCardImage(String userName,Long deckId, Long cardId) throws IOException;
    
    String displayDefaultImage() throws IOException;
    
    void deleteDeckDirectory(String username, Long deckId);
}
