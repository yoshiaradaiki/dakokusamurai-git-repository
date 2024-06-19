package servlet;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*********************************************
これを使って戻ってください
<form action="BackController" method="get">
	<input type="submit" value="戻る">	
</form>
*********************************************/

/**
 * Servlet implementation class backController
 */
@WebServlet("/BackController")
public class BackController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		request.getRequestDispatcher("WEB-INF/jsp/usersMain.jsp").forward(request, response);
	}

}
