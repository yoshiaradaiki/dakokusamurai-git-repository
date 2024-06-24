//申請一覧画面：変更詳細ボタン（勤怠状況詳細画面）
//機能：部下から提出した打刻修正申請詳細画面（勤務状況詳細画面）へ
//作成者：湯
//作成日：2024/6/14
package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.RequestListBean;
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
		
		int stamp_rev_id=Integer.parseInt(request.getParameter("stamp_rev_id"));
		int stamp_rev_req_id = Integer.parseInt(request.getParameter("stamp_rev_req_id"));
		
		
//		String dateString = request.getParameter("stamp_date");
//	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//	    Date stamp_date = dateFormat.parse(dateString);
		//Date stamp_date = request.getParameter("stamp_date");
		
		RequestListLogic requestListLogic = new RequestListLogic();
		StampBean stampBean = new StampBean();
		RequestListBean requestListBean = new RequestListBean();
		//users_idを取得する
		UsersBean usersBean = new RequestListLogic().findUsersStampRevId( stamp_rev_id);
		//勤怠状況表IDで部下の利用者IDを取得（年月を持つUsersBean）
		int users_id = usersBean.getUsers_id();
		//Date year_and_month = usersBean.getYear_and_month();
				
		//------------------------------------------------------------------------------------//
		//DBから再提出ボタンを押下時の勤怠状況表を取得する処理
		//↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
		//打刻日時を取得する
		StampBean attDetailBean = requestListLogic.findAttDetailStamp( stamp_rev_id);
		//------------------------------------------------------------------------------------//

		//JSPから取得するためにセットする
		request.setAttribute("date", usersBean.getYear_and_month());
		request.setAttribute("usersBean", usersBean);
		request.setAttribute("stampBean",  attDetailBean);
		request.setAttribute("requestListBean", requestListBean);


		//"attendanceStatus.jsp"へ転送する
		request.getRequestDispatcher("WEB-INF/jsp/attendanceStatusDetail.jsp").forward(request, response);
	}

}
