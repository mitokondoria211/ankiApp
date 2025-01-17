package com.example.ankiapp.controller;

import java.io.IOException;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.ankiapp.constant.UpdateCardResult;
import com.example.ankiapp.constant.UpdateDeckResult;
import com.example.ankiapp.constant.UrlConst;
import com.example.ankiapp.constant.ViewNameConst;
import com.example.ankiapp.dto.DeckUpdateInfo;
import com.example.ankiapp.entitiy.DeckInfo;
import com.example.ankiapp.form.CardUpdateForm;
import com.example.ankiapp.form.DeckUpdateForm;
import com.example.ankiapp.service.DeckEditService;
import com.example.ankiapp.service.DeckInfoService;
import com.example.ankiapp.service.ImageStorageService;
import com.example.ankiapp.utilty.AppUtility;
import com.github.dozermapper.core.Mapper;
import lombok.RequiredArgsConstructor;
import lombok.var;


@Controller
@RequiredArgsConstructor
public class DeckUpdateController {
    
    private final DeckInfoService deckInfoService;
    
    private final ImageStorageService imageStorageService;
    
    private final DeckEditService deckEditService;
    
    /**画面で使用するフォームクラス*/
    private static final String FORM_CLASS_NAME ="deckUpdateForm";
    
    /**メッセージソース*/
    private final MessageSource messageSource;
    
    /**Dozer Mapper*/
    private final Mapper mapper;
    /**
     * デッキリスト画面の初期表示を行います。
     * 
     * @param model モデル
     * @return デッキメニュー画面
     * @throws IOException 
     */
    @GetMapping(UrlConst.UPDATE_DECK + "/{deckId}")
    public String deckUpdateView(@PathVariable Long deckId, Model model, DeckUpdateForm form) throws IOException {
        
        DeckInfo deckInfo = deckInfoService.findDeckInfoByDeckId(deckId);
        
        DeckUpdateInfo updateInfo = mapper.map(deckInfo, DeckUpdateInfo.class);
        
        //formにデッキ情報をセット
        form.setDeckId(deckId);
        form.setTitle(deckInfo.getTitle());
        form.setImagePath(deckInfo.getImagePath());
        form.setDescription(deckInfo.getDescription());
        
        String deckImage = imageStorageService.displayDeckImage(AppUtility.getUsername(), deckId);
        
        model.addAttribute("title", deckInfo.getTitle());
        model.addAttribute("deckImagePath", deckInfo.getImagePath());
        model.addAttribute("deckImage", deckImage);
        model.addAttribute("updateDeck", updateInfo);
        model.addAttribute("deckUpdateForm", form);

        return UrlConst.UPDATE_DECK;
    }
    
    @PostMapping(UrlConst.UPDATE_DECK)
    public String updateDeck(@Validated DeckUpdateForm deckUpdateForm ,
                                              Model model, 
                                              BindingResult bdResult,
                                              RedirectAttributes redirectAttributes) throws IOException {
        DeckInfo deckInfo = deckInfoService.findDeckInfoByDeckId(deckUpdateForm.getDeckId());
        String deckImage = imageStorageService.displayDeckImage(AppUtility.getUsername(), deckUpdateForm.getDeckId());
        DeckUpdateInfo updateInfo = mapper.map(deckInfo, DeckUpdateInfo.class);
        
        if(bdResult.hasErrors()) {
            model.addAttribute("title", deckInfo.getTitle());
            model.addAttribute("deckImage", deckImage);
            model.addAttribute("updateDeck", updateInfo);
            model.addAttribute("isError", true);
            model.addAttribute("deckUpdateForm", deckUpdateForm);
            
            return ViewNameConst.UPDATE_DECK + "/" + deckUpdateForm.getDeckId();
        }
        
        var result = deckEditService.updateDeck(deckInfo, deckUpdateForm);
        boolean isError = result != UpdateDeckResult.SUCCEED;
        
        if(isError) {
            editGuideMessage(deckUpdateForm, bdResult, result.getMessageId(), redirectAttributes);
            return AppUtility.doRedirect(UrlConst.UPDATE_DECK + "/" + deckUpdateForm.getDeckId());     
        }
        
        deckInfo = deckInfoService.findDeckInfoByDeckId(deckUpdateForm.getDeckId());
        var updatedInfo = mapper.map(updateInfo, DeckUpdateInfo.class);
//        attributes.addFlashAttribute("updateDeck", updatedInfo);
//        attributes.addFlashAttribute("updateDeckForm", form);
        model.addAttribute("title", deckInfo.getTitle());
        model.addAttribute("deckImage", deckImage);
        model.addAttribute("updateDeck", updatedInfo);
        model.addAttribute("deckUpdateForm", deckUpdateForm);
        redirectAttributes.addFlashAttribute("message", AppUtility.getMessage(messageSource, result.getMessageId()));
        redirectAttributes.addFlashAttribute("isError", false);
//        return AppUtility.doRedirect(UrlConst.UPDATE_DECK);
        return AppUtility.doRedirect(UrlConst.UPDATE_DECK+ "/" + deckUpdateForm.getDeckId());
    }
    
    private void editGuideMessage(DeckUpdateForm form, BindingResult bdResult, String messageId, 
            RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("message", AppUtility.getMessage(messageSource, messageId));
        redirectAttributes.addFlashAttribute("isError", true);
        redirectAttributes.addFlashAttribute("deckUpdateForm",form);
        redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + FORM_CLASS_NAME, bdResult);
    }
    
}
