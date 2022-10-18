package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.MemoView;
import actions.views.UserView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import services.MemoService;

/**
 * トップページに関する処理を行うActionクラス
 *
 */
public class TopAction extends ActionBase {

    private MemoService service;

    /**
     * indexメソッドを実行する
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
     */
    public void index() throws ServletException, IOException {



        //セッションからログイン中のユーザー情報を取得
        UserView loginUser = (UserView) getSessionScope(AttributeConst.LOGIN_USR);

        //ログイン中のユーザーが作成したメモデータを、指定されたページ数の一覧画面に表示する分取得する
        int page = getPage();
        List<MemoView> memos = service.getMinePerPage(loginUser, page);

        //ログイン中のユーザーが作成したメモデータの件数を取得
        long myMomosCount = service.countAllMine(loginUser);

        putRequestScope(AttributeConst.MEMOS, momos); //取得したメモデータ
        putRequestScope(AttributeConst.MEM_COUNT, myMemosCount); //ログイン中のユーザーが作成したメモの数
        putRequestScope(AttributeConst.PAGE, page); //ページ数
        putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE); //1ページに表示するレコードの数



        //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
        String flush = getSessionScope(AttributeConst.FLUSH);
        if (flush != null) {
            putRequestScope(AttributeConst.FLUSH, flush);
            removeSessionScope(AttributeConst.FLUSH);
        }

        //一覧画面を表示
        forward(ForwardConst.FW_TOP_INDEX);
    }

}