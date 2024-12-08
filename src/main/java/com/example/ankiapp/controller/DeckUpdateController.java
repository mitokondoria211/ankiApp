package com.example.ankiapp.controller;

import java.io.IOException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.ankiapp.constant.UrlConst;
import com.example.ankiapp.dto.DeckUpdateInfo;
import com.example.ankiapp.entitiy.DeckInfo;
import com.example.ankiapp.form.DeckUpdateForm;
import com.example.ankiapp.service.DeckEditService;
import com.example.ankiapp.service.DeckInfoService;
import com.example.ankiapp.service.ImageStorageService;
import com.example.ankiapp.utilty.AppUtility;
import com.github.dozermapper.core.Mapper;
import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class DeckUpdateController {
    
    private final DeckInfoService deckInfoService;
    
//    private final DeckListService deckListService;
    
    private final ImageStorageService imageStorageService;
    
    private final DeckEditService deckEditService;
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
//    public String deckListView(@RequestParam Long deckId, Model model) {
    public String deckUpdateView(@PathVariable Long deckId, Model model) throws IOException {
//        var deckList =  deckListService.editDeckList();
        
        DeckInfo deckInfo = deckInfoService.findDeckInfoByDeckId(deckId);
        DeckUpdateInfo updateInfo = mapper.map(deckInfo, DeckUpdateInfo.class);
        var form = new DeckUpdateForm();
        form.setDeckId(deckId);
        form.setTitle(deckInfo.getTitle());
        form.setDescription(deckInfo.getDescription());
        
        String deckImage = imageStorageService.displayDeckImage(AppUtility.getUsername(), deckId);
        
        
        model.addAttribute("title", deckInfo.getTitle());
        model.addAttribute("deckImage", deckImage);
        model.addAttribute("updateDeck", updateInfo);
        model.addAttribute("updateDeckForm", form);
//        model.addAttribute("deckListForm", new DeckListForm());  // この行を追加
//        model.addAttribute("deckForm", new DeckForm());
        return UrlConst.UPDATE_DECK;
    }
    
    @PostMapping(UrlConst.UPDATE_DECK)
    public String updateDeck(@ModelAttribute DeckUpdateForm form, Model model, RedirectAttributes attributes) throws IOException {
        DeckInfo deckInfo = deckInfoService.findDeckInfoByDeckId(form.getDeckId());
        deckInfo = deckEditService.updateDeck(deckInfo, form);
        DeckUpdateInfo updateInfo = mapper.map(deckInfo, DeckUpdateInfo.class);
        String deckImage = imageStorageService.displayDeckImage(AppUtility.getUsername(), form.getDeckId());
        
//        DeckUpdateInfo updatedInfo = mapper.map(updateInfo, DeckUpdateInfo.class);
//        attributes.addFlashAttribute("updateDeck", updatedInfo);
//        attributes.addFlashAttribute("updateDeckForm", form);
        model.addAttribute("title", deckInfo.getTitle());
        model.addAttribute("deckImage", deckImage);
        model.addAttribute("updateDeck", updateInfo);
        model.addAttribute("updateDeckForm", form);
//        return AppUtility.doRedirect(UrlConst.UPDATE_DECK);
        return UrlConst.UPDATE_DECK;
    }
    
}
