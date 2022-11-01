package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.UserView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import constants.MessageConst;
import constants.PropertyConst;
import services.UserService;

/**
 * ユーザーに関わる処理を行うActionクラス
 *
 */
public class UserAction extends ActionBase {

    private UserService service;

    /**
     * メソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException {

        service = new UserService();

        //メソッドを実行
        invoke();

        service.close();
    }

    /**
     * 一覧画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void index() throws ServletException, IOException {



            //指定されたページ数の一覧画面に表示するデータを取得
            int page = getPage();
            List<UserView> users = service.getPerPage(page);

            //全てのユーザーデータの件数を取得
            long userCount = service.countAll();

            putRequestScope(AttributeConst.USERS, users); //取得したユーザーデータ
            putRequestScope(AttributeConst.USR_COUNT, userCount); //全てのユーザーデータの件数
            putRequestScope(AttributeConst.PAGE, page); //ページ数
            putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE); //1ページに表示するレコードの数

            //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
            String flush = getSessionScope(AttributeConst.FLUSH);
            if (flush != null) {
                putRequestScope(AttributeConst.FLUSH, flush);
                removeSessionScope(AttributeConst.FLUSH);
            }

            //一覧画面を表示
            forward(ForwardConst.FW_USR_INDEX);

        }

    /**
     * 新規登録画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void entryNew() throws ServletException, IOException {

            putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
            putRequestScope(AttributeConst.USER, new UserView()); //空のユーザーインスタンス

            //新規登録画面を表示
            forward(ForwardConst.FW_USR_NEW);
        }

    /**
     * 新規登録を行う
     * @throws ServletException
     * @throws IOException
     */
    public void create() throws ServletException, IOException {

        //CSRF対策 tokenのチェック
        if (checkToken()) {

            //パラメータの値を元にユーザー情報のインスタンスを作成する
            UserView uv = new UserView(
                    null,
                    getRequestParam(AttributeConst.USR_CODE),
                    getRequestParam(AttributeConst.USR_NAME),
                    getRequestParam(AttributeConst.USR_PASS),
                    //toNumber(getRequestParam(AttributeConst.EMP_ADMIN_FLG)),
                    null,
                    null,
                    AttributeConst.DEL_FLAG_FALSE.getIntegerValue());

            //アプリケーションスコープからpepper文字列を取得
            String pepper = getContextScope(PropertyConst.PEPPER);

            //ユーザー情報登録
            List<String> errors = service.create(uv, pepper);

            if (errors.size() > 0) {
                //登録中にエラーがあった場合

                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                putRequestScope(AttributeConst.USER, uv); //入力されたユーザー情報
                putRequestScope(AttributeConst.ERR, errors); //エラーのリスト

                //新規登録画面を再表示
                forward(ForwardConst.FW_USR_NEW);

            } else {
                //登録中にエラーがなかった場合

                //セッションに登録完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_REGISTERED.getMessage());

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_USR, ForwardConst.CMD_INDEX);
            }

        }
    }

    /**
     * 詳細画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void show() throws ServletException, IOException {



            //idを条件にユーザーデータを取得する
           UserView uv = service.findOne(toNumber(getRequestParam(AttributeConst.USR_ID)));

            if (uv == null || uv.getDeleteFlag() == AttributeConst.DEL_FLAG_TRUE.getIntegerValue()) {

                //データが取得できなかった、または論理削除されている場合はエラー画面を表示
                forward(ForwardConst.FW_ERR_UNKNOWN);
                return;
            }

            putRequestScope(AttributeConst.USER, uv); //取得したユーザー情報

            //詳細画面を表示
            forward(ForwardConst.FW_USR_SHOW);
        }


    /**
     * 編集画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void edit() throws ServletException, IOException {


            //idを条件にユーざーデータを取得する
            UserView uv = service.findOne(toNumber(getRequestParam(AttributeConst.USR_ID)));

            if (uv == null || uv.getDeleteFlag() == AttributeConst.DEL_FLAG_TRUE.getIntegerValue()) {

                //データが取得できなかった、または論理削除されている場合はエラー画面を表示
                forward(ForwardConst.FW_ERR_UNKNOWN);
                return;
            }

            putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
            putRequestScope(AttributeConst.USER, uv); //取得したユーザー情報

            //編集画面を表示する
            forward(ForwardConst.FW_USR_EDIT);

        }

    /**
     * 更新を行う
     * @throws ServletException
     * @throws IOException
     */
    public void update() throws ServletException, IOException {

        //CSRF対策 tokenのチェック
        if (checkToken()) {
            //パラメータの値を元にユーザー情報のインスタンスを作成する
            UserView uv = new UserView(
                    toNumber(getRequestParam(AttributeConst.USR_ID)),
                    getRequestParam(AttributeConst.USR_CODE),
                    getRequestParam(AttributeConst.USR_NAME),
                    getRequestParam(AttributeConst.USR_PASS),
                    //toNumber(getRequestParam(AttributeConst.EMP_ADMIN_FLG)),
                    null,
                    null,
                    AttributeConst.DEL_FLAG_FALSE.getIntegerValue());

            //アプリケーションスコープからpepper文字列を取得
            String pepper = getContextScope(PropertyConst.PEPPER);

            //ユーザー情報更新
            List<String> errors = service.update(uv, pepper);

            if (errors.size() > 0) {
                //更新中にエラーが発生した場合

                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                putRequestScope(AttributeConst.USER, uv); //入力されたユーザー情報
                putRequestScope(AttributeConst.ERR, errors); //エラーのリスト

                //編集画面を再表示
                forward(ForwardConst.FW_USR_EDIT);
            } else {
                //更新中にエラーがなかった場合

                //セッションに更新完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_UPDATED.getMessage());

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_USR, ForwardConst.CMD_INDEX);
            }
        }
    }

    /**
     * 論理削除を行う
     * @throws ServletException
     * @throws IOException
     */
    public void destroy() throws ServletException, IOException {

        //CSRF対策 tokenのチェック
        if (checkToken()) {

            //idを条件にユーザーデータを論理削除する
            service.destroy(toNumber(getRequestParam(AttributeConst.USR_ID)));

            //セッションに削除完了のフラッシュメッセージを設定
            putSessionScope(AttributeConst.FLUSH, MessageConst.I_DELETED.getMessage());

            //一覧画面にリダイレクト
            redirect(ForwardConst.ACT_USR, ForwardConst.CMD_INDEX);
        }
    }


}