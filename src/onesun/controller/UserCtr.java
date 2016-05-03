/**
 * 
 */
package onesun.controller;

import javax.servlet.http.HttpSession;

import onesun.model.Answer;
import onesun.model.Hra;
import onesun.model.User;
import onesun.service.UserService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Administrator
 *
 */

@Controller
@RequestMapping("/userCtr")
public class UserCtr {
	private static final Logger logger = Logger.getLogger(UserCtr.class);

	private UserService userService;

	/**
	 * @return the userService
	 */
	public UserService getUserService() {
		return userService;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(params = "login")
	@ResponseBody
	public Answer login(User user, HttpSession session) {
		Answer answer = userService.login(user);
		// session.setAttribute("ifLogin", answer.getResult());
		return answer;
	}

	@RequestMapping(params = "hra")
	@ResponseBody
	public Answer hra(Hra hra) {
		Answer answer = userService.hra(hra);
		return answer;
	}
}
