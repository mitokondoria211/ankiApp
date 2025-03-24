package com.example.ankiapp.controller;

import java.io.IOException;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.ankiapp.constant.DeckCreateResult;
import com.example.ankiapp.constant.UrlConst;
import com.example.ankiapp.constant.ViewNameConst;
import com.example.ankiapp.form.DeckForm;
import com.example.ankiapp.service.DeckInfoService;
import com.example.ankiapp.utilty.AppUtility;
import lombok.RequiredArgsConstructor;
import lombok.var;

@Controller
@RequiredArgsConstructor
public class DeckEditController {
    
    /** ユーザー画面Serviceクラス */
    private final DeckInfoService service;
    
    private final MessageSource messageSource;
    
    /**画面で使用するフォームクラス*/
    private static final String FORM_CLASS_NAME ="deckForm";
    
    /**
     * デッキ作成画面の初期表示を行います。
     * 
     * @param model モデル
     * @return デッキメニュー画面
     */
    @GetMapping(UrlConst.DECK_MENU)
    public String menuView(Model model) {
        return ViewNameConst.DECK_MENU;
    }
    
    /**
     * デッキ作成画面の初期表示を行います。
     * 
     * @param model モデル
     * @param form 入力情報
     * @return デッキ作成画面
     */
    @GetMapping(UrlConst.CREATE_DECK)
    public String editView(Model model, DeckForm deckForm) {
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
    @PostMapping(UrlConst.CREATE_DECK)
    public String resisterCard(Model model,@Validated @ModelAttribute DeckForm deckForm,
            BindingResult bdResult, RedirectAttributes redirectAttributes) throws IOException{
        
        if(bdResult.hasErrors()) {
            return ViewNameConst.CREATE_DECK;
        }
        
        var result = service.createDeck(deckForm);
        boolean isError = result != DeckCreateResult.SUCCEED;
        
        if(isError) {
            editGuideMessage(deckForm, bdResult, result.getMessageId(), redirectAttributes);
            return AppUtility.doRedirect(ViewNameConst.CREATE_DECK);
        }
        
        //formを再表示
        model.addAttribute("deckForm", new DeckForm());
        redirectAttributes.addFlashAttribute("message", AppUtility.getMessage(messageSource, result.getMessageId()));
        redirectAttributes.addFlashAttribute("isError", false);
        
        return AppUtility.doRedirect(ViewNameConst.CREATE_DECK);
    }
    
    private void editGuideMessage(DeckForm form, BindingResult bdResult, String messageId, 
            RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("message", AppUtility.getMessage(messageSource, messageId));
        redirectAttributes.addFlashAttribute("isError", true);
        redirectAttributes.addFlashAttribute(form);
        redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + FORM_CLASS_NAME, bdResult);
    }
    
//    @GetMapping(UrlConst.UPDATE_DECK)
//    public String updateView(Model model) {
//        model.addAttribute("deckForm", new DeckForm());
//        return ViewNameConst.UPDATE_DECK;
//    }
}
