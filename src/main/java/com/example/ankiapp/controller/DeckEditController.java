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
import com.example.ankiapp.form.DeckForm;
import com.example.ankiapp.service.DeckInfoService;
import com.example.ankiapp.utilty.AppUtility;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class DeckEditController {
    
    private final DeckInfoService service;
    
    /**
     * デッキ作成画面の初期表示を行います。
     * 
     * @param model モデル
     * @return デッキメニュー画面
     */
    @GetMapping(UrlConst.DECK_MENU)
    public String menuView(Model model) {
//        model.addAttribute("deckForm", new DeckForm());
        return ViewNameConst.DECK_MENU;
    }
    
    /**
     * デッキ作成画面の初期表示を行います。
     * 
     * @param model モデル
     * @param form 入力情報
     * @return デッキ作成画面
     */
    @GetMapping(UrlConst.DECK_INFO)
    public String editView(Model model) {
        model.addAttribute("deckForm", new DeckForm());
        return ViewNameConst.CREATE_DECK;
    }
    
    /**
     * デッキを作成し、再表示を行います。
     * 
     * @param model モデル
     * @param form 入力情報
     * @return デッキ作成画面
     */
    @PostMapping(UrlConst.DECK_INFO)
    public String resistCard(Model model,@Valid @ModelAttribute DeckForm form,
            BindingResult bindingResult) throws IOException{
        if(bindingResult.hasErrors()) {
            return ViewNameConst.CREATE_DECK;
        }
        
        service.createDeck(form);
        //formを再表示
        model.addAttribute("deckForm", new DeckForm());
        
        return AppUtility.doRedirect(ViewNameConst.CREATE_DECK);
    }
    
//    @GetMapping(UrlConst.UPDATE_DECK)
//    public String updateView(Model model) {
//        model.addAttribute("deckForm", new DeckForm());
//        return ViewNameConst.UPDATE_DECK;
//    }
}
