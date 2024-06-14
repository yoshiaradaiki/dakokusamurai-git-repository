//申請一覧画面：提出(申請)詳細ボタン（勤怠状況表）
//機能：部下から提出した勤務状況表画面へ
//作成者：湯
//作成日：2024/6/14
package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RequestDetailController
 */
@WebServlet("/RequestDetailController")
public class RequestDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RequestDetailController() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		//部下の変更申請（勤怠状況表ID）を取得
		int att_status_id = Integer.parseInt(request.getParameter("att_status_id"));
		//勤怠状況表IDで部下の利用者ID、勤怠状況表の年月を取得する
		int subUsers_id = 

		//------------------------------------------------------------------------------------//
		//DBから再提出ボタンを押下時の勤怠状況表を取得する処理
		//↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
		
		
		//------------------------------------------------------------------------------------//

		//JSPから取得するためにセットする
		

		//"attendanceStatus.jsp"へ転送する
		request.getRequestDispatcher("WEB-INF/jsp/attendanceStatus.jsp").forward(request, response);
	}

}
