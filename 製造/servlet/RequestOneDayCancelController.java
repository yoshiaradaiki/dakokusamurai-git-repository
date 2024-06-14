//申請一覧画面：キャンセルボタン（打刻修正申請）
//機能：打刻修正申請をキャンセルし、申請画面再描画する
//作成者：湯
//作成日：2024/6/14
package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import logic.RequestListLogic;

/**
 * Servlet implementation class RequestOneDayCancelController
 */
@WebServlet("/RequestOneDayCancelController")
public class RequestOneDayCancelController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RequestOneDayCancelController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
request.setCharacterEncoding("UTF-8");
		
		//セッションを取得
		HttpSession session = request.getSession();

		//セッションスコープから利用者IDを取得
		int sessionUsersBean = (int) session.getAttribute("sessionUsersBean");
		//打刻修正申請IDを取得
		int stamp_rev_req_id = Integer.parseInt(request.getParameter("stamp_rev_req_id"));

		//取得した打刻修正申請IDによって、打刻修正申請をキャンセルする（打刻修正申請.ステータスを3に更新、更新者を利用者IDに更新する）
		// 打刻修正申請をキャンセルする操作を行うためのロジッククラスのインスタンスを作成
		RequestListLogic requestListLogic = new RequestListLogic();
		// 打刻修正申請のキャンセル操作を実行し、その結果を取得
		Boolean isCancelled = requestListLogic.updateReqCancelByOneDay(stamp_rev_req_id, 3, null, sessionUsersBean);
		// キャンセル操作の結果をリクエストスコープに設定
		request.setAttribute("isCancelled", isCancelled);

		
		request.setAttribute("resultMsg", "打刻修正申請をキャンセルしました。");

		//"attendanceStatus.jsp"へ転送する
		request.getRequestDispatcher("WEB-INF/jsp/requestList.jsp").forward(request, response);
	}

}
