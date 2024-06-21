//申請一覧画面：変更詳細ボタン（勤怠状況詳細画面）
//機能：部下から提出した打刻修正申請詳細画面（勤務状況詳細画面）へ
//作成者：湯
//作成日：2024/6/14
package servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.StampBean;
import beans.UsersBean;
import logic.RequestListLogic;

/**
 * Servlet implementation class RevDetailController
 */
@WebServlet("/RevDetailController")
public class RevDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RevDetailController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		//部下の変更申請（勤怠状況表ID）を取得
		int att_status_id = Integer.parseInt(request.getParameter("att_status_id"));
		//勤怠状況表IDで部下の利用者IDを取得（年月を持つUsersBean）
		UsersBean usersBean = new RequestListLogic().findMySubAttDetailUsers(att_status_id);
		int users_id = usersBean.getUsers_id();
		Date year_and_month = usersBean.getYear_and_month();
				
		//------------------------------------------------------------------------------------//
		//DBから再提出ボタンを押下時の勤怠状況表を取得する処理
		//↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
		RequestListLogic requestListLogic = new RequestListLogic();
		//RequestListBean reqListBeanReason = new RequestListLogic().findAttStatusMonthReason(att_status_id);
		StampBean attDetailBean = requestListLogic.findAttDetailStamp(users_id, year_and_month);		
		//------------------------------------------------------------------------------------//

		//JSPから取得するためにセットする
		request.setAttribute("usersBean", usersBean);
		request.setAttribute("attDetailBean", attDetailBean);


		//"attendanceStatus.jsp"へ転送する
		request.getRequestDispatcher("WEB-INF/jsp/attendanceStatusDetail.jsp").forward(request, response);
	}

}
