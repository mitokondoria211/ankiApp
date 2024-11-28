package com.example.ankiapp.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.ankiapp.constant.UrlConst;
import com.example.ankiapp.constant.ViewNameConst;
import com.example.ankiapp.constant.db.CardAnswerResult;
import com.example.ankiapp.entitiy.CardEditorInfo;
import com.example.ankiapp.entitiy.DeckInfo;
import com.example.ankiapp.form.CardDisplayForm;
import com.example.ankiapp.service.CardDisplayService;
import com.example.ankiapp.service.DeckInfoService;
import com.example.ankiapp.service.ImageStorageService;
import com.example.ankiapp.utilty.AppUtility;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
/**
 * メニュー画面Controllerクラス
 * 
 * @author ys-fj
 *
 */
@RequiredArgsConstructor
@Controller
public class CardDisplayController {

    private final CardDisplayService cardDisplayService;
    
    private final DeckInfoService deckInfoService;
    
    private final ImageStorageService imageStorageService;
    
    @Value("${image.folder}")
    private String baseImageFolder;
    
    @GetMapping("/selectDeck")
    public String selectDeck(Model model) throws IOException {
        var deckInfos = deckInfoService.findDeckInfo();
        List<Integer> deckCardSizes = new ArrayList<>();
        for(DeckInfo deck: deckInfos) {
            deckCardSizes.add(cardDisplayService.getCardCount(deck.getDeckId()));
        }
        List <String> deckImages = deckInfos.stream()
                .map(deck -> {
                    try {
//                        if(deck.getImagePath().equals("default.jpg") ) {
//                            return  imageStorageService.displayDefaultImage();
//                        }
                        return imageStorageService.displayDeckImage(deck.getUserInfo().getLoginId(), deck.getDeckId());
                    } catch (IOException e) {
                        // TODO 自動生成された catch ブロック
                       return "" + deck.getDeckId();
//                        return  imageStorageService.displayDefaultImage();
                    }
                })
                .collect(Collectors.toList());
        model.addAttribute("deckCardSizes", deckCardSizes);
        model.addAttribute("deckInfos", deckInfos);
        model.addAttribute("deckImages", deckImages);
//        model.addAttribute("deckImage", cardDisplayService.deckImage(null))
        return "selectDeck";
    }

    
    /**
     * 画面の初期表示を行います。
     * 
     * <p>その際、ユーザー情報から権限を確認し、ユーザー管理が可能かどうかを判定した結果を画面に渡します。
     * 
     * @param user 認証済みユーザー情報
     * @param model モデル
     * @return メニュー画面
     */

    
    @GetMapping(UrlConst.CARD_DISPLAY)
    public String view(Model model,@Valid CardDisplayForm form) {
        var deckInfos = deckInfoService.findDeckInfo();
        model.addAttribute("deckInfos", deckInfos);
        
     // デッキが選択されている場合、そのデッキに属するカードを取得
        if(form.getDeckId() != null) {
            var cardInfos = cardDisplayService.findCardEditorByDeckId(form.getDeckId());
            model.addAttribute("cardInfos", cardInfos);
        } // デッキが選択されていない場合、すべてのカードを取得
        else {
          var cardInfos = cardDisplayService.findCardEditor();
          model.addAttribute("cardInfos", cardInfos);
        }
        
        if (form.getCardId() != null) {
            // カードが選択されている場合、そのカードの情報を表示
            var cardInfo = cardDisplayService.findCardEditorByCardId(form.getCardId());
            var deckInfo = deckInfoService.findDeckInfoByDeckId(form.getDeckId());
            model.addAttribute("deckName", deckInfo.getTitle());
            model.addAttribute("question", cardInfo.getQuestion());
            model.addAttribute("answer", cardInfo.getAnswer());
        }
        model.addAttribute("cardDisplayForm", form);
        return ViewNameConst.CARD_DISPLAY;
    }
    
    @GetMapping("/deck/{deckId}")
    public String viewDeck(@PathVariable Long deckId, Model model) {
        var deckInfo = deckInfoService.findDeckInfoByDeckId(deckId);
        var cardInfos = cardDisplayService.findCardEditorByDeckId(deckId);
        model.addAttribute("deckInfo", deckInfo);
        model.addAttribute("cardInfos", cardInfos);
        return "deckDisplay"; // 新しいHTMLページ名
    }
    
    @GetMapping("/cardChallenge/{deckId}")
    public String challenge(@PathVariable Long deckId, Model model, 
            CardDisplayForm form) throws IOException {
        var deckInfo = deckInfoService.findDeckInfoByDeckId(deckId);
        var cardInfos = cardDisplayService.findCardEditorByDeckId(deckId);
        var firstCard = cardInfos.get(0);
//        model.addAttribute("imageFile", cardDisplayService.deckImage(deckId));
        model.addAttribute("deck", deckInfo);
        model.addAttribute("cardIndex", 0);
        model.addAttribute("card", firstCard);
        String questionImage = imageStorageService.displayQuestionCardImage(AppUtility.getUsername(), 
                deckId, firstCard.getCardId());
        String answerImage = imageStorageService.displayAnswerCardImage(AppUtility.getUsername(), 
                deckId, firstCard.getCardId());
        if(firstCard.getQuestionImagePath() != null &&
                !firstCard.getQuestionImagePath().equals("default.jpg")) {
            model.addAttribute("questionImage", questionImage);
        }
        if(firstCard.getAnswerImagePath() != null &&
                !firstCard.getAnswerImagePath().equals("default.jpg")) {
            model.addAttribute("answerImage", answerImage);
        }
        model.addAttribute("cards", cardInfos);
        model.addAttribute("totalCards", cardInfos.size());
//        model.addAttribute("deckImage", deckInfoService.out
        return "cardChallenge";
    }
    
    @PostMapping("/cardChallenge/{deckId}/{cardIndex}/result")
    public String submitCardResult(@PathVariable Long deckId, 
            @PathVariable int cardIndex, 
            @RequestParam String result,
            Model model) throws IOException {
        var deckInfo = deckInfoService.findDeckInfoByDeckId(deckId);
        var cardInfos = cardDisplayService.findCardEditorByDeckId(deckId);
        var currentCard = cardInfos.get(cardIndex);
        
        currentCard.setCardResult(CardAnswerResult.valueOf(result.toUpperCase()));
        cardDisplayService.saveCardEditorInfo(currentCard);
        
        cardIndex++;
        
        if (cardIndex < cardInfos.size()) {
            var nowCard = cardInfos.get(cardIndex);
            model.addAttribute("deck", deckInfo);
            model.addAttribute("cardIndex", cardIndex);
            model.addAttribute("card", nowCard);
            
            String questionImage = imageStorageService.displayQuestionCardImage(AppUtility.getUsername(), 
                    deckId, nowCard.getCardId());
            String answerImage = imageStorageService.displayAnswerCardImage(AppUtility.getUsername(), 
                    deckId, nowCard.getCardId());
            
            if(nowCard.getQuestionImagePath() != null &&
                    !nowCard.getQuestionImagePath().equals("default.jpg")) {
                model.addAttribute("questionImage", questionImage);
            }
            if(nowCard.getAnswerImagePath() != null &&
                    !nowCard.getAnswerImagePath().equals("default.jpg")) {
                model.addAttribute("answerImage", answerImage);
            }

//            model.addAttribute("questionImage", questionImage);
//            model.addAttribute("answerImage", answerImage);
            model.addAttribute("totalCards", cardInfos.size());
            return "cardChallenge";
        } else {
            return "redirect:/challengeComplete/" + deckId;
        }
    }
    @GetMapping("/challengeComplete/{deckId}")
    public String viewComplete(@PathVariable Long deckId, Model model) {
        var deckInfo = deckInfoService.findDeckInfoByDeckId(deckId);
        var cardInfos = cardDisplayService.findCardEditorByDeckId(deckId);
        int [] cardResults = new int[CardAnswerResult.values().length];
//        for(CardEditorInfo card: cardInfos) {
//            for(int i = 0; i < cardResults.length; i++) {
//                if(card.getCardResult().getCode() == i+1) {
//                    cardResults[i]++;
//                    break;
//                }
//            }
//        }
        
        for (CardEditorInfo card : cardInfos) {
            CardAnswerResult result = card.getCardResult();
            if (result != null) {
                int index = result.ordinal();
                if (index >= 0 && index < cardResults.length) {
                    cardResults[index]++;
                }
            }
        }
        
        Map<CardAnswerResult, Long> resultCounts = cardInfos.stream()
                .collect(Collectors.groupingBy(CardEditorInfo::getCardResult, Collectors.counting()));


       
        String[] ratings = Arrays.stream(CardAnswerResult.values())
                                 .map(CardAnswerResult::getRating)
                                 .toArray(String[]::new);

        
        model.addAttribute("deckInfo", deckInfo);
        model.addAttribute("cardInfos", cardInfos);
        model.addAttribute("totalCards", cardInfos.size());
        model.addAttribute("cardRating", ratings);
        model.addAttribute("cardResults", cardResults);
        
        return "challengeComplete"; // 新しいHTMLページ名
    }
    
//    @PostMapping(UrlConst.CARD_DISPLAY)
//    public String selectCard(Model model, CardDisplayForm form) {
//        
//        var deckId = form.getDeckId();
//        var cardId = form.getCardId();
//        var deckInfo = deckInfoService.findDeckInfoByDeckId(deckId);
//        var cardInfo = cardDisplayService.findCardEditorByCardId(cardId);
//        model.addAttribute("deckName", deckInfo.getTitle());
//        model.addAttribute("problem", cardInfo.getProblem());
//        model.addAttribute("answer", cardInfo.getAnswer());
//        model.addAttribute("deckInfos", deckInfoService.findDeckInfo());
//        model.addAttribute("cardInfos", cardDisplayService.findCardEditor()); //再表示する
//        model.addAttribute("cardDisplayForm", form);
//        return ViewNameConst.CARD_DISPLAY;
//    }

}