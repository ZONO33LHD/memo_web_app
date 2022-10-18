package constants;

/**
 * 画面の項目値等を定義するEnumクラス
 *
 */
public enum AttributeConst {

    //フラッシュメッセージ
    FLUSH("flush"),

    //一覧画面共通
    MAX_ROW("maxRow"),
    PAGE("page"),

    //入力フォーム共通
    TOKEN("_token"),
    ERR("errors"),

    //ログイン中のユーザー
    LOGIN_USR("login_user"),

    //ログイン画面
    LOGIN_ERR("loginError"),

    //ユーザー管理
    USER("user"),
    USERS("users"),
    USR_COUNT("user_count"),
    USR_ID("id"),
    USR_CODE("code"),
    USR_PASS("password"),
    USR_NAME("name"),

    /*
    EMP_ADMIN_FLG("admin_flag"),
    //管理者フラグ
    ROLE_ADMIN(1),
    ROLE_GENERAL(0),
     */

    //削除フラグ
    DEL_FLAG_TRUE(1),
    DEL_FLAG_FALSE(0),

    //メモ管理
    MEMO("memo"),
    MEMOS("memos"),
    MEM_COUNT("memos_count"),
    MEM_ID("id"),
    MEM_DATE("memo_date"),
    MEM_TITLE("title"),
    MEM_CONTENT("content_msg");

    private final String text;
    private final Integer i;

    private AttributeConst(final String text) {
        this.text = text;
        this.i = null;
    }

    private AttributeConst(final Integer i) {
        this.text = null;
        this.i = i;
    }

    public String getValue() {
        return this.text;
    }

    public Integer getIntegerValue() {
        return this.i;
    }

}