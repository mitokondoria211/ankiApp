package com.example.ankiapp.controller;

import java.io.IOException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.ankiapp.constant.UrlConst;
import com.example.ankiapp.constant.ViewNameConst;
import com.example.ankiapp.form.CardEditorForm;
import com.example.ankiapp.service.CardEditService;
import com.example.ankiapp.service.DeckInfoService;
import com.example.ankiapp.utilty.AppUtility;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CardInfoController {
    
    private final CardEditService service;
    
    private final DeckInfoService deckService;
    
    @GetMapping(UrlConst.CARD_MENU)
    public String cardMenuView(Model model) {
        return ViewNameConst.CARD_MENU;
    }
    
    /**
     * 画面の初期表示を行います。
     * 
     * @param model モデル
     * @param form 入力情報
     * @return ログイン画面
     */
    @GetMapping(UrlConst.CARD_EDITOR)
    public String view(Model model, CardEditorForm form) {
        model.addAttribute("cardEditorForm", new CardEditorForm());
        var deckInfos = deckService.findDeckInfo();
        model.addAttribute("deckInfos", deckInfos);
        if (form.getDeckId() == null && !deckInfos.isEmpty()) {
            var deckInfo = deckInfos.get(0);
            form.setDeckId(1l);
            model.addAttribute("deckName", deckInfo.getTitle());
        }
        return ViewNameConst.CARD_EDITOR;
    }
    @PostMapping(UrlConst.CARD_EDITOR)
    public String resistCard(Model model,@Valid @ModelAttribute CardEditorForm form, BindingResult result) throws IOException {
        
//        var deckId = form.getDeckId();
//       
//        var deckInfo = deckInfoService.findByDeckInfoByDeckId(deckId);
//        var cardInfo = cardDisplayService.findCardEditorByCardId(cardId);
//        model.addAttribute("deckName", deckInfo.getTitle());
//        model.addAttribute("problem", cardInfo.getProblem());
//        model.addAttribute("answer", cardInfo.getAnswer());
        model.addAttribute("deckInfos", deckService.findDeckInfo());
        if(result.hasErrors()) {
            return ViewNameConst.CARD_EDITOR;
        }
        
//        model.addAttribute("cardInfos", cardDisplayService.findCardEditor()); //再表示する
//        model.addAttribute("cardEditorForm", form);
        
        service.createCardInfo(form);
        
        model.addAttribute("cardEditorForm", new CardEditorForm());
        
        return AppUtility.doRedirect(ViewNameConst.CARD_EDITOR);
    }
}
