/**
 * 
 */
package onesun.controller;

import javax.servlet.http.HttpSession;

import onesun.model.Answer;
import onesun.model.Hra;
import onesun.model.SessionInfo;
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
	public Answer login(User user_p, HttpSession session) {
		User user = userService.login(user_p);
		Answer answer = new Answer();
		SessionInfo sessionInfo = new SessionInfo();
		if (user == null) {
			answer.setMsg("用户名或密码错误");
			answer.setResult(false);
			sessionInfo.setIfLogin(false);
			return answer;
		}
		answer.setMsg("登录成功");
		answer.setResult(true);
		sessionInfo.setIfLogin(true);
		sessionInfo.setUserName(user.getName());
		session.setAttribute("sessionInfo", sessionInfo);
		return answer;
	}

	@RequestMapping(params = "hraPage")
	@ResponseBody
	public Answer hraPage() {
		Answer answer = new Answer();
		answer.setResult(true);
		return answer;
	}

	@RequestMapping(params = "hra")
	@ResponseBody
	public Answer hra(Hra hra, HttpSession session) {
		hra.setName(((SessionInfo) session.getAttribute("sessionInfo"))
				.getUserName());
		userService.hra(hra);
		Answer answer = new Answer();
		answer.setResult(true);
		return answer;
	}
}
