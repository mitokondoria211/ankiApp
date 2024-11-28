package com.example.ankiapp.controller;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.ankiapp.constant.UserDeleteResult;
import com.example.ankiapp.constant.SessionKeyConst;
import com.example.ankiapp.constant.UrlConst;
import com.example.ankiapp.constant.ViewNameConst;
import com.example.ankiapp.constant.db.AuthorityKind;
import com.example.ankiapp.constant.db.UserStatusKind;
import com.example.ankiapp.dto.UserSearchInfo;
import com.example.ankiapp.form.UserListForm;
import com.example.ankiapp.service.UserListService;
import com.example.ankiapp.utilty.AppUtility;
import com.github.dozermapper.core.Mapper;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

/**
 *ユーザー一覧画面Controllerクラス 
 */
@Controller
@RequiredArgsConstructor
public class UserListController {
    
    /**セッション情報*/
    private final HttpSession session;
    
    /**Dozer Mapper*/
    private final Mapper mapper;
    
    /** ユーザー一覧画面Serviceクラス */
    private final UserListService service;
    
    /**メッセージソース*/
    private final MessageSource messageSource;
    
    /**モデルキー：ユーザー情報リスト*/
    private static final String KEY_USERLIST = "userList";

    /**モデルキー：ユーザー情報リスト*/
    private static final String KEY_USER_STATUS_KIND_OPTIONS = "userStatusKindOptions";
    
    /**モデルキー：ユーザー情報リスト*/
    private static final String KEY_AUTHORITY_KIND_OPTIONS = "authorityKindOptions";
    
    /**
     * 画面の初期表示を行います
     * 
     *  * <p>またその際、画面選択項目「アカウント状態」「所有権限」の選択肢を生成して画面に渡します。
     *  
     * @param model
     * @param form
     * @return 表示画面
     */
    @GetMapping(UrlConst.USER_LIST)
    public String view(Model model, UserListForm form) {
        session.removeAttribute(SessionKeyConst.SELECTED_LOGIN_ID);
        
        var userInfos = service.editUserList();
        model.addAttribute(KEY_USERLIST, userInfos);
        
        model.addAttribute(KEY_USER_STATUS_KIND_OPTIONS, UserStatusKind.values());
        model.addAttribute(KEY_AUTHORITY_KIND_OPTIONS, AuthorityKind.values());
        return ViewNameConst.USER_LIST;
    }
    
    /**
     * 検索条件に合致するユーザー情報を画面に表示します。
     * 
     * @param model モデル
     * @return 表示画面
     */    
    @PostMapping(value = UrlConst.USER_LIST, params = "search")
    public String searchUser(Model model, UserListForm form) {
        var searchDto = mapper.map(form, UserSearchInfo.class);
        var userInfos = service.editUserListByParam(searchDto);
        model.addAttribute(KEY_USERLIST, userInfos);
        
        model.addAttribute(KEY_USER_STATUS_KIND_OPTIONS, UserStatusKind.values());
        model.addAttribute(KEY_AUTHORITY_KIND_OPTIONS, AuthorityKind.values());
        
        return ViewNameConst.USER_LIST;
    }
    
    /**
     * 選択行のユーザー情報を削除して、最新情報で画面を再表示します
     * 
     * @param form 入力情報
     * @return リダイレクトURL
     */ 
    @PostMapping(value=UrlConst.USER_LIST, params = "edit")
    public String updateUser(UserListForm form) {
        session.setAttribute(SessionKeyConst.SELECTED_LOGIN_ID, form.getSelectedLoginId());
        return AppUtility.doRedirect(UrlConst.USER_EDIT);
    }
    
    /**
     *選択行のユーザー情報を削除して、最新情報で画面を再表示します
     * 
     * 検索条件に合致するユーザー情報を画面に表示する
     * 
     * @param model モデル
     * @param form 入力情報
     * @return 表示画面
     */
    @PostMapping(value = UrlConst.USER_LIST, params="delete")
    public String deleteUser(Model model, UserListForm form) {
        var executeResult = service.deleteUserInfoById(form.getSelectedLoginId());
        model.addAttribute("isError", executeResult == UserDeleteResult.ERROR);
        model.addAttribute("message", AppUtility.getMessage(messageSource, executeResult.getMessageId()));
        
        //削除後、フォーム情報の「選択されたログインID」は不要になるため、クリアします。
        return searchUser(model, form.clearSelectedLoginId());
    }

}
