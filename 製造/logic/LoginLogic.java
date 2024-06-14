// 吉新
// 2024/06/14
package logic;

import beans.UsersBean;
import dao.UsersDAO;

public class LoginLogic {
	public UsersBean findLoginCheck(String login_id, String password) {
		UsersDAO usersDAO = new UsersDAO();
		return usersDAO.findLoginCheck(login_id, password);
	}
}
