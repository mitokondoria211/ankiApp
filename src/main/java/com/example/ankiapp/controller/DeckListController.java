package com.example.ankiapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.ankiapp.constant.SortType;
import com.example.ankiapp.constant.UrlConst;
import com.example.ankiapp.constant.ViewNameConst;
import com.example.ankiapp.dto.DeckSearchInfo;
import com.example.ankiapp.form.DeckListForm;
import com.example.ankiapp.service.DeckInfoService;
import com.example.ankiapp.service.DeckListService;
import com.example.ankiapp.utilty.AppUtility;
import com.github.dozermapper.core.Mapper;
import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class DeckListController {
    
    private final DeckInfoService deckInfoService;
    
    private final DeckListService deckListService;
    
    /**Dozer Mapper*/
    private final Mapper mapper;
    /**
     * デッキリスト画面の初期表示を行います。
     * 
     * @param model モデル
     * @return デッキメニュー画面
     */
    @GetMapping(UrlConst.DECK_LIST)
    public String deckListView(Model model) {
    
        var deckList =  deckListService.editDeckList();
        model.addAttribute("deckList", deckList);
        model.addAttribute("deckListForm", new DeckListForm());  // この行を追加
        model.addAttribute("sortTypes", SortType.values());
//        model.addAttribute("deckForm", new DeckForm());
        return ViewNameConst.DECK_LIST;
    }
    
    /**
     * 検索条件に合致するユーザー情報を画面に表示します。
     * 
     * @param model モデル
     * @return 表示画面
     */    
    @PostMapping(value = UrlConst.DECK_LIST, params = "search")
    public String searchDeck(Model model, DeckListForm form) {
        var searchDto = mapper.map(form, DeckSearchInfo.class);
        var deckList = deckListService.editDeckListByParam(searchDto);
        
        model.addAttribute("deckList", deckList);
        model.addAttribute("sortTypes", SortType.values());
        // フォームを初期化
        model.addAttribute("deckListForm", form);
        return ViewNameConst.DECK_LIST;
    }
    
    /**
     * 選択行のユーザー情報を削除して、最新情報で画面を再表示します
     * 
     * @param form 入力情報
     * @return リダイレクトURL
     */ 
    @PostMapping(value=UrlConst.DECK_LIST, params = "edit")
    public String updateDeck(DeckListForm form) {
//        var deckUpdateInfo = new DeckUpdateInfo();
//        deckUpdateInfo.setDeckId(form.getSelectedDeckId());
        return AppUtility.doRedirect(UrlConst.UPDATE_DECK +"/" + form.getSelectedDeckId());
    }
}
