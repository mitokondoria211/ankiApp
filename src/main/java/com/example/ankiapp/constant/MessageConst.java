package com.example.ankiapp.constant;


/**
 * エラーメッセージIDクラス
 */
public class MessageConst {
	
    /** 共通：入力内容誤り */
    public static final String FORM_ERROR = "common.formError";

    /** ログイン画面：入力内容誤り */
    public static final String LOGIN_WRONG_INPUT = "login.wrongInput";

    /** ユーザー登録画面：既に本登録済み */
    public static final String SIGNUP_ALREADY_COMPLETED = "signup.alreadyCompleted";

    /** ユーザー登録画面：仮登録状態のログインID */
    public static final String SIGNUP_SIGNUP_PROCEEDING = "signup.signupProceeding";

    /** ユーザー登録画面：DB更新時エラー */
    public static final String SIGNUP_DB_ERROR = "signup.dbError";

    /** ユーザー登録画面：メール送信失敗 */
    public static final String SIGNUP_MAIL_SEND_ERROR = "signup.mailSendError";

    /** ユーザー登録画面：送信メール件名 */
    public static final String SIGNUP_MAIL_SUBJECT = "singup.mailSubject";

    /** ユーザー登録画面：送信メール本文 */
    public static final String SIGNUP_MAIL_TEXT = "singup.mailText";

    /** ユーザー登録画面：ユーザー仮登録完了 */
    public static final String SIGNUP_TENTATIVE_SUCCEED = "signup.tentativeSucceed";

    /** ユーザー登録情報確認画面：既に本登録済み */
    public static final String SIGNUP_CONFIRM_ALREADY_COMPLETED = "signupConfirm.alreadyCompleted";

    /** ユーザー登録情報確認画面：不正な画面操作 */
    public static final String SIGNUP_CONFIRM_INVALID_OPERATION = "signupConfirm.invalidOperation";

    /** ユーザー登録情報確認画面：仮登録されていない */
    public static final String SIGNUP_CONFIRM_NOT_EXISTS_TENTATIVE_USER = "signupConfirm.notExistsTentativeUser";

    /** ユーザー登録情報確認画面：ワンタイムコード誤り */
    public static final String SIGNUP_CONFIRM_WRONG_ONE_TIME_CODE = "signupConfirm.wrongOneTimeCode";

    /** ユーザー登録情報確認画面：ワンタイムコード有効期限切れ */
    public static final String SIGNUP_CONFIRM_EXPIRED_ONE_TIME_CODE = "signupConfirm.expiredOneTimeCode";

    /** ユーザー登録情報確認画面：DB更新時エラー */
    public static final String SIGNUP_CONFIRM_DB_ERROR = "signupConfirm.dbError";

    /** ユーザー登録情報確認画面：ユーザー本登録完了 */
    public static final String SIGNUP_CONFIRM_COMPLETE = "signupConfirm.complete";

    /** ユーザー一覧画面：存在しないログインID */
    public static final String USERLIST_NON_EXISTED_LOGIN_ID = "userList.nonExistedLoginId";

    /** ユーザー一覧画面：ユーザー削除完了 */
    public static final String USERLIST_DELETE_SUCCEED = "userList.deleteSucceed";

    /** ユーザー情報編集画面：存在しないログインID */
    public static final String USEREDIT_NON_EXISTED_LOGIN_ID = "userEdit.nonExistedLoginId";

    /** ユーザー情報編集画面：ユーザー更新失敗 */
    public static final String USEREDIT_UPDATE_FAILED = "userEdit.updateFailed";

    /** ユーザー情報編集画面：ユーザー更新完了 */
    public static final String USEREDIT_UPDATE_SUCCEED = "userEdit.updateSucceed";
    
    /** デッキ作成画面：デッキ作成完了 */
    public static final String DECKEDIT_CREATE_SUCCEED = "deckEdit.createSucceed";
    
    /** デッキ作成画面：デッキ作成DB失敗 */
    public static final String DECKEDIT_DB_FAILED = "deckEdit.dbFailed";
    
    /** デッキ作成画面：デッキ作成画像処理失敗 */
    public static final String DECKEDIT_IMAGE_FAILED = "deckEdit.imageFailed";
    
    /** デッキ作成画面：デッキ作成画像サイズ失敗 */
    public static final String DECKEDIT_IMAGE_SIZE_FAILED = "deckEdit.imageSizeFailed";
    
    /** デッキ更新画面：デッキ作成完了 */
    public static final String UPDATEDECK_UPDATE_SUCCEED = "updateDeck.updateSucceed";

    /** デッキ更新画面：デッキ作成DB失敗 */
    public static final String UPDATEDECK_DB_FAILED = "updateDeck.dbFailed";

    /** デッキ更新画面：デッキ作成画像サイズ失敗 */
    public static final String UPDATEDECK_IMAGE_SIZE_FAILED = "updateDeck.imageSizeFailed";

    /** デッキ更新画面：デッキ作成画像処理失敗 */
    public static final String UPDATEDECK_IMAGE_FAILED = "updateDeck.imageFailed";
    
    /** カード一覧画面：存在しないカードID */
    public static final String CARDLIST_NON_EXISTED_CARD_ID = "cardList.nonExistedCardId";

    /** カード一覧画面：カード削除完了 */
    public static final String CARDLIST_DELETE_SUCCEED = "cardList.deleteSucceed";
    
    /** カード作成画面：カード作成完了 */
    public static final String CARDINFO_CREATE_SUCCEED = "cardInfo.createSucceed";
    
    /** カード作成画面：カード作成DB失敗 */
    public static final String CARDINFO_DB_FAILED = "cardInfo.dbFailed";
    
    /** カード作成画面：カード作成画像処理失敗 */
    public static final String CARDINFO_IMAGE_FAILED = "cardInfo.imageFailed";
    
    /** カード作成画面：カード作成画像サイズ失敗 */
    public static final String CARDINFO_IMAGE_SIZE_FAILED = "cardInfo.imageSizeFailed";

    /** カード更新画面：カード更新完了 */
    public static final String UPDATECARD_UPDATE_SUCCEED = "updateCard.updateSucceed";
    
    /** カード更新画面：カード更新DB失敗 */
    public static final String UPDATECARD_DB_FAILED = "updateCard.dbFailed";

    /** カード更新画面：カード作成画像処理失敗 */
    public static final String UPDATECARD_IMAGE_SIZE_FAILED = "updateCard.imageSizeFailed";

    /** カード更新画面：カード作成画像サイズ失敗 */
    public static final String UPDATECARD_IMAGE_FAILED = "updateCard.imageFailed";

    public static final String CARDFROMCSV_IMPORT_SUCCEED = "cardFromCsv.importSucceed";

    public static final String CARDFROMCSV_IMPORT_FAILED = "cardFromCsv.importFailed";

    public static final String DECKLIST_DELETE_SUCCEED = "deckList_deleteSucceed";

    public static final String DECKLIST_NON_EXISTED_DECK_ID = "deckList_nonExistedDeckId";


}   

