package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import beans.UsersBean;

@WebFilter("/*")
public class SessionUsersFilter implements Filter {
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession session = httpRequest.getSession();

		// ダミーのsessionUsersBeanを作成
		UsersBean sessionUsersBean = new UsersBean();
		sessionUsersBean.setUsers_id(123); // ダミーのusers_idをセット

		// セッションスコープに保存
		session.setAttribute("sessionUsersBean", sessionUsersBean);

		// フィルターチェーンを実行
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}
}
