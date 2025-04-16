package com.example.ankiapp.constant;

/**
 * URL定数クラス
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
    
    public static final String UPLOAD = "/upload";

    /** 認証不要画面 */
    public static final String[] NO_AUTHENTICATION = { LOGIN, SIGNUP, SIGNUP_CONFIRM, SIGNUP_COMPLETION,
            "/webjars/**", "/css/**" , "/js/**", "/upload/**",SIGNUP_CONFIRM + "/resend"};

    public static final String CARD_EDITOR = "/cardEditor";
    
    public static final String SOLVE_A_PROBLEM = "/solveAProblem";

    public static final String DECK = "/deck";
    
    /**デッキメニュー画面*/
    public static final String DECK_MENU = "/deckMenu";

    /**デッキ作成画面*/
    public static final String DECK_INFO = "/deckInfo";
    
    /**デッキ更新画面*/
    public static final String UPDATE_DECK = "/updateDeck";
    
    /**デッキリスト画面*/
    public static final String DECK_LIST = "/deckList";
    
    /**カードメニュー画面*/
    public static final String CARD_MENU = "/cardMenu";

    /**カード練習画面*/
    public static final String CARD_DISPLAY = "/cardDisplay";
    
    /**カードリスト画面*/
    public static final String CARD_LIST = "/cardList";
    
    /**カード作成画面*/
    public static final String CREATE_DECK = "/createDeck";
    
    /**カード更新画面*/
    public static final String UPDATE_CARD = "/updateCard";
    
    /**チャレンジ完了画面*/
    public static final String CHALLENGE_COMPLETE = "/challengeComplete";
    
    /**デッキ選択画面*/
    public static final String SELECT_DECK = "/selectDeck";
    
    /**カード挑戦画面*/
    public static final String CARD_CHALLENGE= "/cardChallenge";

    public static final String CARD_FROM_CSV = "/cardFromCsv";
    
    
    
    

}