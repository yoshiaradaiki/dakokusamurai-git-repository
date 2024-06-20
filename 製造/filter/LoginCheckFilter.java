/*----------------------------------------------------------------------------*/
/* ログインチェックフィルター
/* ログインしていない場合は、強制的にログイン画面に戻る
/*----------------------------------------------------------------------------*/
package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter("/*")
public class LoginCheckFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		String s = req.getServletPath();
		if(!s.equals("/LoginController")) {
			if(req.getSession().getAttribute("sessionUsersBean") == null) {
				request.getRequestDispatcher("index.jsp").forward(req, res);
			}else {
				chain.doFilter(req, res);
			}
		}else {
			chain.doFilter(req, res);
		}
	}

}
