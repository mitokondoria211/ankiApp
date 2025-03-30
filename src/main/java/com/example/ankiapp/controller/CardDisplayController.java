package com.example.ankiapp.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import com.example.ankiapp.constant.UrlConst;
import com.example.ankiapp.constant.ViewNameConst;
import com.example.ankiapp.constant.db.CardAnswerResult;
import com.example.ankiapp.dto.CardPracticeInfo;
import com.example.ankiapp.dto.CardUpdateInfo;
import com.example.ankiapp.entitiy.CardInfo;
import com.example.ankiapp.entitiy.DeckInfo;
import com.example.ankiapp.form.CardDisplayForm;
import com.example.ankiapp.form.CardPracticeForm;
import com.example.ankiapp.form.ChallengeConfirmForm;
import com.example.ankiapp.service.CardDisplayService;
import com.example.ankiapp.service.CloudinaryService;
import com.example.ankiapp.service.DeckInfoService;
import com.example.ankiapp.service.ImageStorageService;
import com.example.ankiapp.utilty.AppUtility;
import com.github.dozermapper.core.Mapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.var;

/**
 * カード表示画面Controllerクラス
 */
@RequiredArgsConstructor
@Controller
@SessionAttributes("cardDisplayForm")  // セッションに保存する属性を指定
public class CardDisplayController {

    /**カード表示Service*/
    private final CardDisplayService cardDisplayService;
    
    /**デッキ情報Service*/
    private final DeckInfoService deckInfoService;
    
    private final CloudinaryService cloudinaryService;
    
    /**Dozer Mapper*/
    private final Mapper mapper;
    
    /**ルートディレクトリパス*/
    @Value("${default.image}")
    private String defaultImage;
    
    
    /**
     * カード練習画面を表示
     * デッキを選択させて画面に初期表示
     * @param model
     * @return カード練習画面
     */
    @GetMapping("/cardDisplay")
    public String practiceFirstForm(Model model) {
        var deckInfos = deckInfoService.findDeckInfo();
        deckInfos = cardDisplayService.filterEmptyDecks(deckInfos);
        model.addAttribute("deckInfos", deckInfos);
        model.addAttribute("practiceForm", new CardPracticeForm());
        return "cardDisplay";
    }
    
    /**
     * カード練習画面を表示
     * 選んだデッキの中からカードを選択させて表示
     * 
     * @param model
     * @param form
     * @return カード練習画面
     */
    @PostMapping("/cardDisplay/deck")
    public String setDeck(Model model, CardPracticeForm form) {
        var cardInfos = cardDisplayService.findCardInfoByDeckId(form.getDeckId());
        model.addAttribute("cardInfos", cardInfos);
        model.addAttribute("practiceForm", form);
        model.addAttribute("deckId", form.getDeckId());
        return ViewNameConst.CARD_DISPLAY;
    }
    
    /**
     * カード練習画面を表示
     * 選択したデッキとカードの情報(問題、解答、問題の画像、解答の画像)を表示させる
     * 
     * @param model
     * @param form
     * @return カード練習画面
     */
    @PostMapping("/cardDisplay/card")
    public String practiceCard(Model model, CardPracticeForm form) {
        model.addAttribute("practiceForm", form);
        var cardInfo = cardDisplayService.findCardInfoByCardId(form.getCardId());
        CardPracticeInfo updateInfo = mapper.map(cardInfo, CardPracticeInfo.class);
        model.addAttribute("cardInfo", updateInfo);
        return ViewNameConst.CARD_DISPLAY;
    }
    
    /**
     * カード挑戦するためにデッキを選択させる画面
     * カードのようなものにデッキに含まれている情報を表示
     * 
     * @param model
     * @return　デッキ選択画面
     * @throws IOException
     */
    @GetMapping(UrlConst.SELECT_DECK)
    public String selectDeck(Model model) {
        var deckInfos = deckInfoService.findDeckInfo();
        List<Integer> deckCardSizes = new ArrayList<>();
        for(DeckInfo deck: deckInfos) {
            deckCardSizes.add(cardDisplayService.getCardCount(deck.getDeckId()));
        }
        List<String> imageUrls = cloudinaryService.displayDeckImages(deckInfos);
        model.addAttribute("deckCardSizes", deckCardSizes);
        model.addAttribute("deckInfos", deckInfos);
        model.addAttribute("deckImageUrls", imageUrls);
        return ViewNameConst.SELECT_DECK;
    }
    

    /**
     * カード挑戦を確認する画面
     * デッキ内のカードをカード評価ごとに選択し、挑戦する問題数を選択させ、画面遷移する
     * 
     * @param deckId デッキId
     * @param model モデル
     * @param form
     * @return カード挑戦確認画面
     */
    @GetMapping("/challengeConfirm/{deckId}")
    public String challengeConfirm(@PathVariable Long deckId, Model model, ChallengeConfirmForm form) {
        var deckInfo = deckInfoService.findDeckInfoByDeckId(deckId);
        //デッキ内のカードの枚数
        var deckCardSize = cardDisplayService.getCardCount(deckId);
        //カードの評価のリスト
        List<String> cardResults = new ArrayList<>();
        //カードの評価値
        String [] resultArray = {"未選択","未評価","不正解","正解"};
        //デッキ内のカード評価ごとの個数をcardResultsに格納する
        for(String result: resultArray) {
          var resultSize = cardDisplayService.getCardCountByCardResult(deckId, result);
            if(resultSize > 0) {
                cardResults.add(result);
            }
        }
        model.addAttribute("deck", deckInfo);
        //カード評価が未選択のときすべての問題を表示し、formのカード評価を未選択にする
        if(form.getCardResult() == null) {
            form.setSize(deckCardSize);
            form.setCardResult(resultArray[0]);
            model.addAttribute("form", new ChallengeConfirmForm());
            model.addAttribute("cardSize", deckCardSize);
            model.addAttribute("cardResults", cardResults);
        }     
        //カード評価が未選択以外の場合、カード評価に付随する問題と問題数をformにセット
        else {
            deckCardSize = cardDisplayService.getCardCountByCardResult(deckId, form.getCardResult());
            form.setSize(deckCardSize);
            model.addAttribute("cardSize", deckCardSize);
            model.addAttribute("cardResult", form.getCardResult());
            model.addAttribute("cardResults", cardResults);
        }
       
        return ViewNameConst.CHALLENGE_CONFIRM; 
    }
    
    @ModelAttribute("cardDisplayForm")
    public CardDisplayForm setupForm() {
        return new CardDisplayForm();
    }
    
    /**
     * カードチャレンジ画面
     * 
     * @param deckId　デッキId
     * @param cardIndex　カード位置
     * @param confirmForm
     * @param model
     * @return カードチャレンジ画面
     * @throws IOException
     */
    @PostMapping("/cardChallenge/{deckId}/{cardIndex}")
    public String challenge(@PathVariable Long deckId, 
            @PathVariable Integer cardIndex,
            @ModelAttribute ChallengeConfirmForm confirmForm,
            Model model) throws IOException {
        //選択したカード情報を取得する
        var deckInfo = deckInfoService.findDeckInfoByDeckId(deckId);
        //選択したデッキIDとカード票が一致するカードリストを取得する
        var cardInfos = cardDisplayService.findCardInfoByDeckIdAndCardResult(deckId, confirmForm.getCardResult());
        //カードリストをシャッフルする
        Collections.shuffle(cardInfos);
        var setCards = new ArrayList<CardInfo>();
        for(int i = 0; i < confirmForm.getSize(); i++) {
            setCards.add(cardInfos.get(i));
        }
//        form.setCards(setCards);
        CardDisplayForm form = new CardDisplayForm();
        form.setCards(setCards);
        model.addAttribute("cardDisplayForm", form);
        
        
        var firstCard = cardInfos.get(0);
        model.addAttribute("deck", deckInfo);
        model.addAttribute("cardIndex", cardIndex);
        model.addAttribute("cards", cardInfos);
        model.addAttribute("card", firstCard);
        model.addAttribute("beforeResult", firstCard.getCardResult().getRating());
        model.addAttribute("cards", cardInfos);
        model.addAttribute("totalCards", cardInfos.size());
        return ViewNameConst.CARD_CHALLENGE;
    }
    
    /**
     * @param deckId
     * @param cardIndex
     * @param result
     * @param form
     * @param model
     * @return
     * @throws IOException
     */
    @PostMapping(UrlConst.CARD_CHALLENGE + "/{deckId}/{cardIndex}/result")
    public String submitCardResult(@PathVariable Long deckId, 
            @PathVariable int cardIndex, 
            @RequestParam String result,
            @SessionAttribute("cardDisplayForm") CardDisplayForm form,
            Model model) throws IOException {
        var deckInfo = deckInfoService.findDeckInfoByDeckId(deckId);
        var cardInfos = form.getCards();
        model.addAttribute("cards", cardInfos);
        var currentCard = cardInfos.get(cardIndex);
        
        currentCard.setCardResult(CardAnswerResult.valueOf(result.toUpperCase()));
        cardDisplayService.saveCardInfo(currentCard);
        
        cardIndex++;
        
        if (cardIndex < cardInfos.size()) {
            var nowCard = cardInfos.get(cardIndex);
            model.addAttribute("deck", deckInfo);
            model.addAttribute("cardIndex", cardIndex);
            model.addAttribute("card", nowCard);
            model.addAttribute("beforeResult", nowCard.getCardResult().getRating());
            model.addAttribute("totalCards", cardInfos.size());
            return ViewNameConst.CARD_CHALLENGE;
        } else {
            return AppUtility.doRedirect(UrlConst.CHALLENGE_COMPLETE) + "/" + deckId;
        }
    }
    @GetMapping(UrlConst.CHALLENGE_COMPLETE + "/{deckId}")
    public String viewComplete(@PathVariable Long deckId, @SessionAttribute("cardDisplayForm") CardDisplayForm form,SessionStatus sessionStatus,Model model) {
        var deckInfo = deckInfoService.findDeckInfoByDeckId(deckId);
        var cardInfos = form.getCards();
        int [] cardResults = new int[CardAnswerResult.values().length];
        
        for (CardInfo card : cardInfos) {
            CardAnswerResult result = card.getCardResult();
            if (result != null) {
                int index = result.ordinal();
                if (index >= 0 && index < cardResults.length) {
                    cardResults[index]++;
                }
            }
        }
        String[] ratings = Arrays.stream(CardAnswerResult.values())
                                 .map(CardAnswerResult::getRating)
                                 .toArray(String[]::new);
        
        model.addAttribute("deckInfo", deckInfo);
        model.addAttribute("cardInfos", cardInfos);
        model.addAttribute("totalCards", cardInfos.size());
        model.addAttribute("cardRating", ratings);
        model.addAttribute("cardResults", cardResults);
        
        sessionStatus.setComplete();
        
        return ViewNameConst.CHALLENGE_COMPLETE; 
    }
    

}
