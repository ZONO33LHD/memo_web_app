package constants;

/**
 * DB関連の項目値を定義するインターフェース
 * ※インターフェイスに定義した変数は public static final 修飾子がついているとみなされる
 */
public interface JpaConst {

    //persistence-unit名
    String PERSISTENCE_UNIT_NAME = "memo_web_app";

    //データ取得件数の最大値
    int ROW_PER_PAGE = 15; //1ページに表示するレコードの数

    //ユーザーテーブル
    String TABLE_USR = "users"; //テーブル名
    //ユーザーテーブルカラム
    String USR_COL_ID = "id"; //id
    String USR_COL_CODE = "code"; //ユーザー番号
    String USR_COL_NAME = "name"; //氏名
    String USR_COL_PASS = "password"; //パスワード

    String USR_COL_CREATED_AT = "created_at"; //登録日時
    String USR_COL_UPDATED_AT = "updated_at"; //更新日時
    String USR_COL_DELETE_FLAG = "delete_flag"; //削除フラグ


    int USR_DEL_TRUE = 1; //削除フラグON(削除済み)
    int USR_DEL_FALSE = 0; //削除フラグOFF(現役)

    //メモテーブル
    String TABLE_MEM = "memos"; //テーブル名
    //メモテーブルカラム
    String MEM_COL_ID = "id"; //id
    String MEM_COL_USR = "user_id"; //メモを作成したユーザーのid
    String MEM_COL_MEM_DATE = "report_date"; //いつのメモかを示す日付
    String MEM_COL_TITLE = "title"; //メモのタイトル
    String MEM_COL_CONTENT = "content"; //メモの内容
    String MEM_COL_CREATED_AT = "created_at"; //登録日時
    String MEM_COL_UPDATED_AT = "updated_at"; //更新日時
    String MEM_COL_DELETE = "delete"; //メモ削除

    //Entity名
    String ENTITY_MEM = "memo"; //ユーザー
    String ENTITY_USR = "user"; //メモ

    //JPQL内パラメータ
    String JPQL_PARM_CODE = "code"; //ユーザー番号
    String JPQL_PARM_PASSWORD = "password"; //パスワード
    String JPQL_PARM_USER = "user"; //ユーザー

    //NamedQueryの nameとquery
    //全てのユーザーをidの降順に取得する
    String Q_USR_GET_ALL = ENTITY_USR + ".getAll"; //name
    String Q_USR_GET_ALL_DEF = "SELECT u FROM User AS u ORDER BY u.id DESC"; //query
    //全てのユーザーの件数を取得する
    String Q_USR_COUNT = ENTITY_USR + ".count";
    String Q_USR_COUNT_DEF = "SELECT COUNT(u) FROM User AS u";
    //ユーザー番号とハッシュ化済パスワードを条件に未削除のユーザーを取得する
    String Q_USR_GET_BY_CODE_AND_PASS = ENTITY_USR + ".getByCodeAndPass";
    String Q_USR_GET_BY_CODE_AND_PASS_DEF = "SELECT u FROM User AS u WHERE u.deleteFlag = 0 AND u.code = :" + JPQL_PARM_CODE + " AND u.password = :" + JPQL_PARM_PASSWORD;
    //指定したユーザー番号を保持するユーザーの件数を取得する
    String Q_USR_COUNT_REGISTERED_BY_CODE = ENTITY_USR + ".countRegisteredByCode";
    String Q_USR_COUNT_REGISTERED_BY_CODE_DEF = "SELECT COUNT(u) FROM User AS u WHERE u.code = :" + JPQL_PARM_CODE;
    //全てのメモをidの降順に取得する
    String Q_MEM_GET_ALL = ENTITY_MEM + ".getAll";
    String Q_MEM_GET_ALL_DEF = "SELECT m FROM Memo AS m ORDER BY m.id DESC";
    //全てのメモの件数を取得する
    String Q_MEM_COUNT = ENTITY_MEM + ".count";
    String Q_MEM_COUNT_DEF = "SELECT COUNT(m) FROM Memo AS m";
    //指定したユーザーが作成したメモを全件idの降順で取得する
    String Q_MEM_GET_ALL_MINE = ENTITY_MEM + ".getAllMine";
    String Q_MEM_GET_ALL_MINE_DEF = "SELECT m FROM Memo AS m WHERE m.user = :" + JPQL_PARM_USER + " ORDER BY m.id DESC";
    //指定したユーザーが作成したメモの件数を取得する
    String Q_MEM_COUNT_ALL_MINE = ENTITY_MEM + ".countAllMine";
    String Q_MEM_COUNT_ALL_MINE_DEF = "SELECT COUNT(m) FROM Memo AS m WHERE m.user = :" + JPQL_PARM_USER;

}