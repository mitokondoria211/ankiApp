package com.example.ankiapp.constant;

/**
 * URL定数クラス
 * 
 * @author ys-fj
 *
 */
public class UrlConst {

    /** ログイン画面 */
    public static final String LOGIN = "/login";

    /** ユーザー登録画面 */
    public static final String SIGNUP = "/signup";

    /** ユーザー登録情報確認画面 */
    public static final String SIGNUP_CONFIRM = "/signupConfirm";

    /** ユーザー登録情報確認結果画面 */
    public static final String SIGNUP_COMPLETION = "/signupCompletion";

    /** メニュー画面 */
    public static final String MENU = "/menu";

    /** ユーザー一覧画面 */
    public static final String USER_LIST = "/userList";

    /** ユーザー編集画面 */
    public static final String USER_EDIT = "/userEdit";

    /** 認証不要画面 */
    public static final String[] NO_AUTHENTICATION = { LOGIN, SIGNUP, SIGNUP_CONFIRM, SIGNUP_COMPLETION,
            "/webjars/**", "/css/**" , "/js/**"};

    public static final String CARD_EDITOR = "/cardEditor";
    
    public static final String SOLVE_A_PROBLEM = "/solveAProblem";

    public static final String DECK = "/deck";
    
    public static final String DECK_MENU = "/deckMenu";

    public static final String DECK_INFO = "/deckInfo";
    
    public static final String UPDATE_DECK = "/updateDeck";
    
    public static final String DECK_LIST = "/deckList";
    
    public static final String CARD_MENU = "/cardMenu";

    public static final String CARD_DISPLAY = "/cardDisplay";
    
    public static final String CARD_LIST = "/cardList";
    
    public static final String UPDATE_CARD = "/updateCard";
}