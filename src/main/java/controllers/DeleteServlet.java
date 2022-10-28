package controllers;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Memo;
import utils.DBUtil;



/**
 * Servlet implementation class DeleteServlet
 */
@WebServlet("/delete")
public class DeleteServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /*
    * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
    */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String _token = request.getParameter("_token");
        if (_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();

            // セッションスコープからメッセージのIDを取得して
            // 該当のIDのメッセージ1件のみをデータベースから取得
            Memo m = em.find(Memo.class, (Integer) (request.getSession().getAttribute("memo_id")));

            em.getTransaction().begin();
            em.remove(m); // データ削除
            em.getTransaction().commit();
            request.getSession().setAttribute("flush", "削除が完了しました。");
            em.close();

            // セッションスコープ上の不要になったデータを削除
            request.getSession().removeAttribute("memo_id");

            // indexページへリダイレクト
            response.sendRedirect(request.getContextPath() + "/index");

        }
    }




}