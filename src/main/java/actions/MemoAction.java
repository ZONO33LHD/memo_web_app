package actions;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.MemoView;
import actions.views.UserView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import constants.MessageConst;
import services.MemoService;

/**
 * メモに関する処理を行うActionクラス
 *
 */
public class MemoAction extends ActionBase {

    private MemoService service;

    /**
     * メソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException {

        service = new MemoService();

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

        //指定されたページ数の一覧画面に表示するメモデータを取得
        int page = getPage();
        List<MemoView> memos = service.getAllPerPage(page);

        //全メモデータの件数を取得
        long memosCount = service.countAll();

        putRequestScope(AttributeConst.MEMOS, memos); //取得したメモデータ
        putRequestScope(AttributeConst.MEM_COUNT, memosCount); //全てのメモデータの件数
        putRequestScope(AttributeConst.PAGE, page); //ページ数
        putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE); //1ページに表示するレコードの数

        //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
        String flush = getSessionScope(AttributeConst.FLUSH);
        if (flush != null) {
            putRequestScope(AttributeConst.FLUSH, flush);
            removeSessionScope(AttributeConst.FLUSH);
        }

        //一覧画面を表示
        forward(ForwardConst.FW_MEM_INDEX);
    }

    /**
     * 新規登録画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void entryNew() throws ServletException, IOException {

        putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン

        //メモ情報の空インスタンスに、メモの日付＝今日の日付を設定する
        MemoView mv = new MemoView();
        mv.setReportDate(LocalDate.now());
        putRequestScope(AttributeConst.MEMO, mv); //日付のみ設定済みのメモインスタンス

        //新規登録画面を表示
        forward(ForwardConst.FW_MEM_NEW);

    }

    /**
     * 新規登録を行う
     * @throws ServletException
     * @throws IOException
     */
    public void create() throws ServletException, IOException {

        //CSRF対策 tokenのチェック
        if (checkToken()) {

            //メモの日付が入力されていなければ、今日の日付を設定
            LocalDate day = null;
            if (getRequestParam(AttributeConst.MEM_DATE) == null
                    || getRequestParam(AttributeConst.MEM_DATE).equals("")) {
                day = LocalDate.now();
            } else {
                day = LocalDate.parse(getRequestParam(AttributeConst.MEM_DATE));
            }

            //セッションからログイン中のユーザー情報を取得
            UserView uv = (UserView) getSessionScope(AttributeConst.LOGIN_USR);

            //パラメータの値をもとにメモ情報のインスタンスを作成する
            MemoView mv = new MemoView(
                    null,
                    uv, //ログインしているユーザーを、メモ作成者として登録する
                    day,
                    getRequestParam(AttributeConst.MEM_TITLE),
                    getRequestParam(AttributeConst.MEM_CONTENT),
                    null,
                    null);

            //メモ情報登録
            List<String> errors = service.create(mv);

            if (errors.size() > 0) {
                //登録中にエラーがあった場合

                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                putRequestScope(AttributeConst.MEMO, mv);//入力されたメモ情報
                putRequestScope(AttributeConst.ERR, errors);//エラーのリスト

                //新規登録画面を再表示
                forward(ForwardConst.FW_MEM_NEW);

            } else {
                //登録中にエラーがなかった場合

                //セッションに登録完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_REGISTERED.getMessage());

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_MEM, ForwardConst.CMD_INDEX);
            }
        }
    }

    /**
     * 詳細画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void show() throws ServletException, IOException {

        //idを条件にメモデータを取得する
        MemoView mv = service.findOne(toNumber(getRequestParam(AttributeConst.MEM_ID)));

        if (mv == null) {
            //該当のメモデータが存在しない場合はエラー画面を表示
            forward(ForwardConst.FW_ERR_UNKNOWN);

        } else {

            putRequestScope(AttributeConst.MEMO, mv); //取得しためも報データ

            //詳細画面を表示
            forward(ForwardConst.FW_MEM_SHOW);
        }
    }

    /**
     * 編集画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void edit() throws ServletException, IOException {

        //idを条件にメモデータを取得する
        MemoView mv = service.findOne(toNumber(getRequestParam(AttributeConst.MEM_ID)));

        //セッションからログイン中のユーザー情報を取得
        UserView uv = (UserView) getSessionScope(AttributeConst.LOGIN_USR);

        if (mv == null || uv.getId() != mv.getUser().getId()) {
            //該当のメモデータが存在しない、または
            //ログインしているユーザーがメモの作成者でない場合はエラー画面を表示
            forward(ForwardConst.FW_ERR_UNKNOWN);

        } else {

            putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
            putRequestScope(AttributeConst.MEMO, mv); //取得したメモデータ

            //編集画面を表示
            forward(ForwardConst.FW_MEM_EDIT);
        }

    }

    /**
     * 更新を行う
     * @throws ServletException
     * @throws IOException
     */
    public void update() throws ServletException, IOException {

        //CSRF対策 tokenのチェック
        if (checkToken()) {

            //idを条件にメモデータを取得する
            MemoView mv = service.findOne(toNumber(getRequestParam(AttributeConst.MEM_ID)));

            //入力されたメモ内容を設定する
            mv.setReportDate(toLocalDate(getRequestParam(AttributeConst.MEM_DATE)));
            mv.setTitle(getRequestParam(AttributeConst.MEM_TITLE));
            mv.setContent(getRequestParam(AttributeConst.MEM_CONTENT));

            //メモデータを更新する
            List<String> errors = service.update(mv);

            if (errors.size() > 0) {
                //更新中にエラーが発生した場合

                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                putRequestScope(AttributeConst.MEMO, mv); //入力されたメモ情報
                putRequestScope(AttributeConst.ERR, errors); //エラーのリスト

                //編集画面を再表示
                forward(ForwardConst.FW_MEM_EDIT);
            } else {
                //更新中にエラーがなかった場合

                //セッションに更新完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_UPDATED.getMessage());

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_MEM, ForwardConst.CMD_INDEX);

            }
        }
    }

    /**
     * 削除を行う
     * @throws ServletException
     * @throws IOException
     */
    public void delete() throws ServletException, IOException {

        //CSRF対策 tokenのチェック
        if (checkToken()) {

            //
            MemoView mv = new MemoView(toNumber(getRequestParam(AttributeConst.MEM_ID)), null, null, null, null, null,
                    null);

            //削除
            service.delete(mv);

            //一覧画面にリダイレクト
            redirect(ForwardConst.ACT_MEM, ForwardConst.CMD_INDEX);

        }
    }
}
