package com.example.ankiapp.controller;



import org.springframework.context.MessageSource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.ankiapp.constant.UserEditMessage;
import com.example.ankiapp.constant.MessageConst;
import com.example.ankiapp.constant.SessionKeyConst;
import com.example.ankiapp.constant.UrlConst;
import com.example.ankiapp.constant.ViewNameConst;
import com.example.ankiapp.constant.db.AuthorityKind;
import com.example.ankiapp.constant.db.UserStatusKind;
import com.example.ankiapp.dto.UserEditInfo;
import com.example.ankiapp.dto.UserUpdateInfo;
import com.example.ankiapp.entitiy.UserInfo;
import com.example.ankiapp.form.UserEditForm;
import com.example.ankiapp.service.UserEditService;
import com.example.ankiapp.utilty.AppUtility;
import com.github.dozermapper.core.Mapper;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

/**
 *ユーザー一覧画面Controllerクラス 
 */
@Controller
@RequiredArgsConstructor
public class UserEditController {
    
    /**セッション情報*/
    private final HttpSession session;
    
    /**Dozer Mapper*/
    private final Mapper mapper;
    
    /** ユーザー一覧画面Serviceクラス */
    private final UserEditService service;
    
    /**メッセージソース*/
    private final MessageSource messageSource;
    
    /**
     * 前画面で選択されたログインIDに紐づくユーザー情報を画面に表示する
     *  
     * @param model モデル
     * @return 表示画面
     * @throws Exception
     */
    @GetMapping(UrlConst.USER_EDIT)
    public String view(Model model, UserEditForm form) throws Exception {
        var loginId = (String)session.getAttribute(SessionKeyConst.SELECTED_LOGIN_ID);
        var userInfoOpt = service.searchUserInfo(loginId);
        if(userInfoOpt.isEmpty()) {
            model.addAttribute("message",
                    AppUtility.getMessage(messageSource, MessageConst.USEREDIT_NON_EXISTED_LOGIN_ID));
            return ViewNameConst.USER_EDIT_ERROR;
        }
        
        setupCommonInfo(model, userInfoOpt.get());
        
        return ViewNameConst.USER_EDIT;
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
    @PostMapping(value = UrlConst.USER_EDIT, params="update")
    public String updateUser(Model model, UserEditForm form, @AuthenticationPrincipal User user) {
        var updateDto = mapper.map(form, UserUpdateInfo.class);
        updateDto.setLoginId((String) session.getAttribute(SessionKeyConst.SELECTED_LOGIN_ID));
        updateDto.setUpdateUserId(user.getUsername());
        
        var updateResult = service.updateUserInfo(updateDto);
        var updateMessage = updateResult.getUpdateMessage();
        if (updateMessage == UserEditMessage.FAILED) {
            model.addAttribute("message", AppUtility.getMessage(messageSource, updateMessage.getMessageId()));
            return ViewNameConst.USER_EDIT_ERROR;
        }
        setupCommonInfo(model, updateResult.getUpdateUserInfo());
    
        model.addAttribute("isError", false);
        model.addAttribute("message", AppUtility.getMessage(messageSource, updateMessage.getMessageId()));
        //削除後、フォーム情報の「選択されたログインID」は不要になるため、クリアします。
        return ViewNameConst.USER_EDIT;
    }

    
    /**
     * 画面表示に必要な共通項目の設定を行います
     * @param model モデル
     * @param userInfo 入力済みのフォーム情報
     */
    private void setupCommonInfo(Model model, UserInfo userInfo) {
        model.addAttribute("userEditForm", mapper.map(userInfo, UserEditForm.class));
        model.addAttribute("userEditInfo", mapper.map(userInfo, UserEditInfo.class));
        
        model.addAttribute("userStatusKindOptions", UserStatusKind.values());
        model.addAttribute("authorityKindOptions", AuthorityKind.values());
    }

}
