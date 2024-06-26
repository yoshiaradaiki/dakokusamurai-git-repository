//担当:長江
//更新日：2024/06/17
//勤怠状況詳細画面の変更申請ボタン
package servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.RequestListBean;
import beans.StampBean;
import beans.StampRevBean;
import beans.UsersBean;
import logic.AttDetailLogic;
import logic.AttStatusLogic;
import logic.EmpLogic;

@WebServlet("/AttDetailRevRequestController")
public class AttDetailRevRequestController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// 文字列型をLocalTime型に変換
	private LocalTime stringToLocalTime(String s) {
		if (s != null) {
			return LocalTime.parse(s);
		} else {
			return null;
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		RequestDispatcher dispatcher=null;
//     セッションを取得
		HttpSession session = request.getSession();
//　　　取得した情報をUserBeanに渡す
		UsersBean sessionUsersBean = (UsersBean) session.getAttribute("sessionUsersBean");
		AttDetailLogic attDetailLogic = new AttDetailLogic();
		
//      申請日を取得
		Date requestDate = null;
		try {
		requestDate = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("requestDate"));
		} catch (ParseException e) {
		e.printStackTrace();
		}
		
		
		StampBean stampBean = new StampBean();//打刻データ
		
		int users_id = sessionUsersBean.getUsers_id();//利用者IDを取得
		
		//	打刻情報チェック
		//打刻チェックを行い、利用者ID,申請日,打刻情報の結果を取得
		attDetailLogic.findStampCheck(users_id, requestDate);
		
		stampBean.setUsers_id(users_id);//利用者IDを取得して保持
		
		stampBean.setStamp_date(requestDate);//申請日を保持

		int stamp_id = attDetailLogic.attCheck(users_id,requestDate).getStamp_id();//打刻IDを取得
		int work_status = Integer.parseInt(request.getParameter("work_status"));//勤怠状況
		LocalTime workIn_rev = stringToLocalTime(request.getParameter("workIn_rev"));//修正出勤時刻
		LocalTime workOut_rev = stringToLocalTime(request.getParameter("workOut_rev"));//修正退勤時刻
		LocalTime rest_time = stringToLocalTime(request.getParameter("rest_time"));//休憩
		String note = request.getParameter("note");//備考
		
		if (!note.isEmpty()) {
			
			//　打刻修正テーブルに登録
			StampRevBean stampRevBean = new StampRevBean();
			
	//		打刻修正データに値を設定
			stampRevBean.setStamp_id(stamp_id);
			stampRevBean.setWorkIn_rev(workIn_rev);
			stampRevBean.setWorkOut_rev(workOut_rev);
			stampRevBean.setRest_time(rest_time);
			stampRevBean.setWork_status(work_status);
			stampRevBean.setNote(note);
			
	       //  打刻修正追加を行い、打刻ID、修正出勤、修正退勤、休憩、勤怠状況、備考の結果を取得		
			int stamp_rev_id = attDetailLogic.insertStampRev(stampRevBean,users_id);
			System.out.println(stamp_rev_id);
	
			//　打刻修正申請テーブルに登録
			 RequestListBean reqListBean= new RequestListBean ();
			 reqListBean.setRequest_id(stamp_rev_id);
			 reqListBean.setStatus(1);
			 reqListBean.setCreated_users_id(users_id);
			 reqListBean.setUpdated_users_id(users_id);
			 
			 
			 //打刻修正申請追加を行い、理由、ステータス、申請者の結果を取得
			attDetailLogic.insertStampRevReq(reqListBean);
			int stamp_rev_req_id=-1;
			try {
				stamp_rev_req_id = Integer.parseInt(request.getParameter("stamp_rev_req_id"));
			} catch (NumberFormatException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			attDetailLogic.updateReqCancelByOneDay(stamp_rev_req_id,3,"",users_id);
		

		//********************　 ページング　********************//
				int page = 1;
				int recordsPerPage = 5; // 申請一覧の1ページあたりの表示件数
				if (request.getParameter("page") != null) {
					page = Integer.parseInt(request.getParameter("page"));
				}

				int page2 = 1;
				int recordsPerPage2 = 15; // 部下の申請一覧の1ページあたりの表示件数
				if (request.getParameter("page2") != null) {
					page2 = Integer.parseInt(request.getParameter("page2"));
				}
				//********************　 ページング　********************//

				
				//--------セッション利用者ID----------//

				EmpLogic empLogic = new EmpLogic();

				//自分が提出した申請を表示
				List<RequestListBean> requestListBean = empLogic.findMyRequest(users_id);

				//System.out.println("requestListBeanは" + requestListBean != null);

				//********************　 ページング　********************//
				if (requestListBean != null) {
					int listSize = requestListBean.size();

					int start = (page - 1) * recordsPerPage;
					int end = Math.min(start + recordsPerPage, listSize);
					int totalPages = (int) Math.ceil((double) requestListBean.size() / recordsPerPage);
					List<RequestListBean> displayedRequests = requestListBean.subList(start, end);//範囲指定

					//********************　 ページング　********************//
					//自分宛ての申請を表示
					List<RequestListBean> subrequestListBean = empLogic.findMySubRequest(users_id);

					//********************　 ページング　********************//
					int listSize2 = subrequestListBean.size();
					int start2 = (page2 - 1) * recordsPerPage2;
					int end2 = Math.min(start2 + recordsPerPage2, listSize2);
					int totalPages2 = (int) Math.ceil((double) subrequestListBean.size() / recordsPerPage2);
					List<RequestListBean> displayedRequests2 = subrequestListBean.subList(start2, end2);//範囲指定

					//********************　 ページング　********************//

					//JSPで取得する属性を入れる
					//		request.setAttribute("requestListBean", requestListBean);//自分が提出した申請
					//		request.setAttribute("subrequestListBean", subrequestListBean);//自分宛ての申請

					//********************　 ページング　********************//
					request.setAttribute("requestList", displayedRequests);
					request.setAttribute("currentPage", page);
					request.setAttribute("totalPages", totalPages);
					request.setAttribute("requestList2", displayedRequests2);
					request.setAttribute("currentPage2", page2);
					request.setAttribute("totalPages2", totalPages2);
					//********************　 ページング　********************//

				}
				dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/requestList.jsp");
		} else {
			AttStatusLogic attStatusLogic = new AttStatusLogic();
			
			// 受け取った文字列の日付をDate型に変換
			String dateString = request.getParameter("requestDate");
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		    Date date = null;
		    try {
				date = dateFormat.parse(dateString);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		    UsersBean attDetailUsers = attStatusLogic.findMyAttStatusUsers(users_id);
		    attDetailUsers.setYear_and_month(date);
		    StampBean attDetailStamp = attStatusLogic.findMyAttDetailStatusStamp(users_id,date);
		    RequestListBean attDetailUsersRequestList = attStatusLogic.findMyAttDetailStatusRequest(users_id);
		    
		    request.setAttribute("formstatus", 0);
		    request.setAttribute("date", date);
			request.setAttribute("usersBean", attDetailUsers);
			request.setAttribute("stampBean", attDetailStamp);
			request.setAttribute("requestListBean", attDetailUsersRequestList);
			request.setAttribute("errorMsg", "備考を入力してください");
			dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/attendanceStatusDetail.jsp");
		}
	
		//	JSPからサーブレットへ転送
		dispatcher.forward(request, response);

	}

}
