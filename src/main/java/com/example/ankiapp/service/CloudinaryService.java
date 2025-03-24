package com.example.ankiapp.service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {
    
    String uploadFile(MultipartFile file, String costomName);

    Path getUserDirectory(String username);

    Path getUserDecksDirectory(String username);

    Path getDeckDirectory(String username, Long deckId);

    Path getDeckCardsDirectory(String username, Long deckId);

    Path getDeckImagePath(String username, Long deckId);

    Path getQuestionCardImagePath(String username, Long deckId, Long cardId);

    Path getAnswerCardImagePath(String username, Long deckId, Long cardId);

    String saveQuestionCardImage(MultipartFile file, String userName, Long deckId, Long cardId)
            throws IOException;

    String uploadDeckImage(MultipartFile file, String userName, Long deckId) throws IOException;
    
    public List<String> getImagesFromFolder(String folderName);
}
