package com.example.ankiapp.controller;

import java.io.IOException;
import javax.naming.spi.DirStateFactory.Result;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.ankiapp.constant.CreateCardResult;
import com.example.ankiapp.constant.UrlConst;
import com.example.ankiapp.constant.ViewNameConst;
import com.example.ankiapp.form.CardEditorForm;
import com.example.ankiapp.form.GroupOrder;
import com.example.ankiapp.service.CardEditService;
import com.example.ankiapp.service.DeckInfoService;
import com.example.ankiapp.utilty.AppUtility;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.var;

@Controller
@RequiredArgsConstructor
public class CardInfoController {
    
    private final CardEditService service;
    
    private final DeckInfoService deckService;
    
    /**メッセージソース*/
    private final MessageSource messageSource;
    
    /**画面で使用するフォームクラス*/
    private static final String FORM_CLASS_NAME ="cardEditorForm";
    
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
    public String createCard(Model model,@Validated @ModelAttribute CardEditorForm form, BindingResult bdResult ,RedirectAttributes redirectAttributes)  throws IOException {
        
        model.addAttribute("deckInfos", deckService.findDeckInfo());
        if(bdResult.hasErrors()) {
            return ViewNameConst.CARD_EDITOR;
        }
        
        var result = service.createCardInfo(form);
        boolean isError = result != CreateCardResult.SUCCEED;
        
        if(isError) {
            editGuideMessage(form, bdResult, result.getMessageId(), redirectAttributes);
            return AppUtility.doRedirect(ViewNameConst.CARD_EDITOR);
        }
        
        model.addAttribute("cardEditorForm", new CardEditorForm());
        redirectAttributes.addFlashAttribute("message", AppUtility.getMessage(messageSource, result.getMessageId()));
        redirectAttributes.addFlashAttribute("isError", false);
        return AppUtility.doRedirect(ViewNameConst.CARD_EDITOR);
    }
    
    private void editGuideMessage(CardEditorForm form, BindingResult bdResult, String messageId, 
            RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("message", AppUtility.getMessage(messageSource, messageId));
        redirectAttributes.addFlashAttribute("isError", true);
        redirectAttributes.addFlashAttribute(form);
        redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + FORM_CLASS_NAME, bdResult);
    }
}
