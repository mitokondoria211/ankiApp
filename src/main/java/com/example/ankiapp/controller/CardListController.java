package com.example.ankiapp.controller;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.ankiapp.constant.CardDeleteResult;
import com.example.ankiapp.constant.SortType;
import com.example.ankiapp.constant.UrlConst;
import com.example.ankiapp.constant.ViewNameConst;
import com.example.ankiapp.constant.db.CardAnswerResult;
import com.example.ankiapp.dto.CardSearchInfo;
import com.example.ankiapp.form.CardListForm;
import com.example.ankiapp.service.CardListService;
import com.example.ankiapp.service.DeckInfoService;
import com.example.ankiapp.utilty.AppUtility;
import com.github.dozermapper.core.Mapper;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CardListController {
    
    private final DeckInfoService deckInfoService;
    
    private final CardListService cardListService;
    
    /**メッセージソース*/
    private final MessageSource messageSource;
    
    /**Dozer Mapper*/
    private final Mapper mapper;
    /**
     * デッキリスト画面の初期表示を行います。
     * 
     * @param model モデル
     * @return デッキメニュー画面
     */
    @GetMapping(UrlConst.CARD_LIST)
    public String cardListView(Model model) {
        var cardList =  cardListService.editCardInfos();
        var deckInfos = deckInfoService.findDeckInfo();
        model.addAttribute("cardList", cardList);
        model.addAttribute("deckInfos", deckInfos);
        model.addAttribute("cardListForm", new CardListForm());  // この行を追加
        model.addAttribute("cardResults", CardAnswerResult.values());
        model.addAttribute("sortTypes", SortType.values());
//        model.addAttribute("deckForm", new DeckForm());
        return ViewNameConst.CARD_LIST;
    }
    
    @PostMapping(value = UrlConst.CARD_LIST, params = "search")
    public String searchCard(Model model, @ModelAttribute CardListForm form) {
        var deckInfos = deckInfoService.findDeckInfo();
//        var searchDto = mapper.map(form, CardSearchInfo.class);
//        var cardList = cardListService.editCardListByParam(searchDto);
        
        var searchDto = mapper.map(form, CardSearchInfo.class);
        searchDto.setDeckId(form.getDeckId());
        
        var cardList = cardListService.editCardListByParam(searchDto);
        model.addAttribute("cardList", cardList);
        model.addAttribute("deckInfos", deckInfos);
        model.addAttribute("sortTypes", SortType.values());
        model.addAttribute("cardResults", CardAnswerResult.values());
//        model.addAttribute("sortTypes", SortType.values());
        // フォームを初期化
        model.addAttribute("cardListForm", new CardListForm());
        
        return ViewNameConst.CARD_LIST;
    }
    
    @PostMapping(value=UrlConst.CARD_LIST, params = "edit")
    public String updateCard(@ModelAttribute CardListForm form) {
//        var deckUpdateInfo = new DeckUpdateInfo();
//        deckUpdateInfo.setDeckId(form.getSelectedDeckId());
        return AppUtility.doRedirect(UrlConst.UPDATE_CARD +"/" + form.getSelectedCardId());
    }
    
    @PostMapping(value=UrlConst.CARD_LIST, params = "delete")
    public String deleteCard(Model model ,CardListForm form) {
//        var deckUpdateInfo = new DeckUpdateInfo();
        var executeResult = cardListService.deleteCardEditorInfoByCardId(form.getSelectedCardId());
        
        model.addAttribute("isError", executeResult == CardDeleteResult.ERROR);
        model.addAttribute("message", AppUtility.getMessage(messageSource, executeResult.getMessageId()));
        return searchCard(model, form.clearSelectedCardId());
    }

    
    
}
