/**
 * 
 */
package onesun.controller;

import java.text.DecimalFormat;

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
		answer.setObj(user.getName());
		answer.setResult(true);
		sessionInfo.setIfLogin(true);
		sessionInfo.setUserName(user.getName());
		session.setAttribute("sessionInfo", sessionInfo);
		return answer;
	}

	@RequestMapping(params = "hraPage")
	@ResponseBody
	public Answer hraPage(HttpSession session) {
		Hra hra = userService.hraPage(((SessionInfo) session
				.getAttribute("sessionInfo")).getUserName());
		Answer answer = new Answer();
		answer.setResult(true);
		answer.setObj(hra);
		return answer;
	}

	// k1[10] b1[10]
	private final double[] k1 = new double[] { 0.05, 0.04, 0.03, 0.02, 0.28,
			0.12, 0.083, 0.067, 0.0083, 0.013 };
	private final double[] b1 = new double[] { 6.8, 5.0, 3.4, 2.0, 26.0, 9.98,
			6.5, 5.09, 0.83, 1.84 };
	// k2[11] b2[11]
	private final double[] k2 = new double[] { 0.06, 0.055, 0.03, 0.015, 0.26,
			0.04, 0.04, 0.02, 0.02, 0.013, 0.005 };
	private final double[] b2 = new double[] { 8.1, 7.2, 3.2, 1.1, 24.6, 2.6,
			2.6, 0.8, 0.8, 2.16, 0.4 };
	// k4[11] b4[11]
	private final double[] k4 = new double[] { 0.055, 0.04, 0.025, 0.015, 0.08,
			0.06, 0.06, 0.04, 0.02, 0.0083, 0.013 };
	private final double[] b4 = new double[] { 7.7, 5.0, 2.6, 1.2, 6.4, 4.4,
			4.4, 2.6, 0.9, 0.83, 1.84 };
	// typeTo1[23]
	private final double[] typeTo1 = new double[] { 3.5, 2.5, 1.0, 2.5, 1.0,
			0.6, 0.5, 1.0, 0.5, 1.4, 1.2, 1.0, 0.9, 1.5, 1.1, 1.0, 0.7, 0.5,
			2.5, 1.5, 1.0, 0.8, 1.0 };
	// typeTo2[20]
	private final double[] typeTo2 = new double[] { 5.4, 2.7, 1.0, 1.3, 1.1,
			0.9, 0.8, 1.6, 1.2, 0.8, 2.0, 1.5, 1.1, 0.8, 0.7, 1.4, 1.2, 1.1,
			1.0, 0.8 };
	// typeTo3[5]
	private final double[] typeTo3 = new double[] { 2.0, 1.9, 1.3, 0.8, 0.6 };
	// typeTo4[5]
	private final double[] typeTo4 = new double[] { 3.5, 2.5, 1.0, 1.2, 1.0 };

	@RequestMapping(params = "hra")
	@ResponseBody
	public Answer hra(Hra hra, HttpSession session) {
		hra.setName(((SessionInfo) session.getAttribute("sessionInfo"))
				.getUserName());
		userService.hra(hra);
		Answer answer = new Answer();
		DecimalFormat df = new DecimalFormat("#.00");
		double sbp, dbp, chole, dm, sport, family, smoke, weight, results = 1.0, resultb = 0.0;
		switch (hra.getType()) {
		case 1:
			if (hra.getSbp() >= 200) {
				sbp = 3.2;
			} else if (hra.getSbp() >= 180) {
				sbp = k1[0] * hra.getSbp() - b1[0];
			} else if (hra.getSbp() >= 160) {
				sbp = k1[1] * hra.getSbp() - b1[1];
			} else if (hra.getSbp() >= 140) {
				sbp = k1[2] * hra.getSbp() - b1[2];
			} else if (hra.getSbp() >= 120) {
				sbp = k1[3] * hra.getSbp() - b1[3];
			} else {
				sbp = 0.4;
			}
			if (hra.getDbp() >= 106) {
				dbp = 3.7;
			} else if (hra.getDbp() >= 100) {
				dbp = k1[4] * hra.getDbp() - b1[4];
			} else if (hra.getDbp() >= 94) {
				dbp = k1[5] * hra.getDbp() - b1[5];
			} else if (hra.getDbp() >= 88) {
				dbp = k1[6] * hra.getDbp() - b1[6];
			} else if (hra.getDbp() >= 82) {
				dbp = k1[7] * hra.getDbp() - b1[7];
			} else {
				dbp = 0.4;
			}
			if (hra.getChole() >= 280) {
				chole = 1.5;
			} else if (hra.getChole() >= 220) {
				chole = k1[8] * hra.getChole() - b1[8];
			} else if (hra.getChole() >= 180) {
				chole = k1[9] * hra.getChole() - b1[9];
			} else {
				chole = 0.5;
			}
			if (hra.getDm() == 1) {
				dm = typeTo1[0];
			} else if (hra.getDm() == 2) {
				dm = typeTo1[1];
			} else {
				dm = typeTo1[2];
			}
			if (hra.getSport() == 1) {
				sport = typeTo1[3];
			} else if (hra.getSport() == 2) {
				sport = typeTo1[4];
			} else if (hra.getSport() == 3) {
				sport = typeTo1[5];
			} else if (hra.getSport() == 4) {
				sport = typeTo1[6];
			} else if (hra.getSport() == 5) {
				sport = typeTo1[7];
			} else {
				sport = typeTo1[8];
			}
			if (hra.getFamily() == 1) {
				family = typeTo1[9];
			} else if (hra.getFamily() == 2) {
				family = typeTo1[10];
			} else if (hra.getFamily() == 3) {
				family = typeTo1[11];
			} else {
				family = typeTo1[12];
			}
			if (hra.getSmoke() == 1) {
				smoke = typeTo1[13];
			} else if (hra.getSmoke() == 2) {
				smoke = typeTo1[14];
			} else if (hra.getSmoke() == 3) {
				smoke = typeTo1[15];
			} else if (hra.getSmoke() == 4) {
				smoke = typeTo1[16];
			} else {
				smoke = typeTo1[17];
			}
			if (hra.getWeight() == 1) {
				weight = typeTo1[18];
			} else if (hra.getWeight() == 2) {
				weight = typeTo1[19];
			} else if (hra.getWeight() == 3) {
				weight = typeTo1[20];
			} else if (hra.getWeight() == 4) {
				weight = typeTo1[21];
			} else {
				weight = typeTo1[22];
			}
			if (sbp > 1) {
				resultb += (sbp - 1);
			} else {
				results = results * sbp;
			}
			if (dbp > 1) {
				resultb += (dbp - 1);
			} else {
				results = results * dbp;
			}
			if (chole > 1) {
				resultb += (chole - 1);
			} else {
				results = results * chole;
			}
			if (dm > 1) {
				resultb += (dm - 1);
			} else {
				results = results * dm;
			}
			if (sport > 1) {
				resultb += (sport - 1);
			} else {
				results = results * sport;
			}
			if (family > 1) {
				resultb += (family - 1);
			} else {
				results = results * family;
			}
			if (smoke > 1) {
				resultb += (smoke - 1);
			} else {
				results = results * smoke;
			}
			if (weight > 1) {
				resultb += (weight - 1);
			} else {
				results = results * weight;
			}
			answer.setObj(df.format(results + resultb));
			break;
		case 2:
			if (hra.getSbp() >= 200) {
				sbp = 3.9;
			} else if (hra.getSbp() >= 180) {
				sbp = k2[0] * hra.getSbp() - b2[0];
			} else if (hra.getSbp() >= 160) {
				sbp = k2[1] * hra.getSbp() - b2[1];
			} else if (hra.getSbp() >= 140) {
				sbp = k2[2] * hra.getSbp() - b2[2];
			} else if (hra.getSbp() >= 120) {
				sbp = k2[3] * hra.getSbp() - b2[3];
			} else {
				sbp = 0.7;
			}
			if (hra.getDbp() >= 105) {
				dbp = 2.7;
			} else if (hra.getDbp() >= 100) {
				dbp = k2[4] * hra.getDbp() - b2[4];
			} else if (hra.getDbp() >= 95) {
				dbp = k2[5] * hra.getDbp() - b2[5];
			} else if (hra.getDbp() >= 90) {
				dbp = k2[6] * hra.getDbp() - b2[6];
			} else if (hra.getDbp() >= 85) {
				dbp = k2[7] * hra.getDbp() - b2[7];
			} else if (hra.getDbp() >= 80) {
				dbp = k2[8] * hra.getDbp() - b2[8];
			} else {
				dbp = 0.8;
			}
			if (hra.getChole() >= 280) {
				chole = 1.5;
			} else if (hra.getChole() >= 220) {
				chole = k2[9] * hra.getChole() - b2[9];
			} else if (hra.getChole() >= 180) {
				chole = k2[10] * hra.getChole() - b2[10];
			} else {
				chole = 0.5;
			}
			if (hra.getDm() == 1) {
				dm = typeTo2[0];
			} else if (hra.getDm() == 2) {
				dm = typeTo2[1];
			} else {
				dm = typeTo2[2];
			}
			if (hra.getSport() == 1) {
				sport = typeTo2[3];
			} else if (hra.getSport() == 2) {
				sport = typeTo2[4];
			} else if (hra.getSport() == 3) {
				sport = typeTo2[5];
			} else {
				sport = typeTo2[6];
			}
			if (hra.getFamily() == 1) {
				family = typeTo2[7];
			} else if (hra.getFamily() == 2) {
				family = typeTo2[8];
			} else {
				family = typeTo2[9];
			}
			if (hra.getSmoke() == 1) {
				smoke = typeTo2[10];
			} else if (hra.getSmoke() == 2) {
				smoke = typeTo2[11];
			} else if (hra.getSmoke() == 3) {
				smoke = typeTo2[12];
			} else if (hra.getSmoke() == 4) {
				smoke = typeTo2[13];
			} else {
				smoke = typeTo2[14];
			}
			if (hra.getWeight() == 1) {
				weight = typeTo2[15];
			} else if (hra.getWeight() == 2) {
				weight = typeTo2[16];
			} else if (hra.getWeight() == 3) {
				weight = typeTo2[17];
			} else if (hra.getWeight() == 4) {
				weight = typeTo2[18];
			} else {
				weight = typeTo2[19];
			}
			if (sbp > 1) {
				resultb += (sbp - 1);
			} else {
				results = results * sbp;
			}
			if (dbp > 1) {
				resultb += (dbp - 1);
			} else {
				results = results * dbp;
			}
			if (chole > 1) {
				resultb += (chole - 1);
			} else {
				results = results * chole;
			}
			if (dm > 1) {
				resultb += (dm - 1);
			} else {
				results = results * dm;
			}
			if (sport > 1) {
				resultb += (sport - 1);
			} else {
				results = results * sport;
			}
			if (family > 1) {
				resultb += (family - 1);
			} else {
				results = results * family;
			}
			if (smoke > 1) {
				resultb += (smoke - 1);
			} else {
				results = results * smoke;
			}
			if (weight > 1) {
				resultb += (weight - 1);
			} else {
				results = results * weight;
			}
			answer.setObj(df.format(results + resultb));
			break;
		case 3:
			if (hra.getSmoke() == 1) {
				smoke = typeTo3[0];
			} else if (hra.getSmoke() == 2) {
				smoke = typeTo3[1];
			} else if (hra.getSmoke() == 3) {
				smoke = typeTo3[2];
			} else if (hra.getSmoke() == 4) {
				smoke = typeTo3[3];
			} else {
				smoke = typeTo3[4];
			}
			answer.setObj(smoke);
			break;
		case 4:
			if (hra.getSbp() >= 200) {
				sbp = 3.3;
			} else if (hra.getSbp() >= 180) {
				sbp = k4[0] * hra.getSbp() - b4[0];
			} else if (hra.getSbp() >= 160) {
				sbp = k4[1] * hra.getSbp() - b4[1];
			} else if (hra.getSbp() >= 140) {
				sbp = k4[2] * hra.getSbp() - b4[2];
			} else if (hra.getSbp() >= 120) {
				sbp = k4[3] * hra.getSbp() - b4[3];
			} else {
				sbp = 0.6;
			}
			if (hra.getDbp() >= 105) {
				dbp = 2.0;
			} else if (hra.getDbp() >= 100) {
				dbp = k4[4] * hra.getDbp() - b4[4];
			} else if (hra.getDbp() >= 95) {
				dbp = k4[5] * hra.getDbp() - b4[5];
			} else if (hra.getDbp() >= 90) {
				dbp = k4[6] * hra.getDbp() - b4[6];
			} else if (hra.getDbp() >= 85) {
				dbp = k4[7] * hra.getDbp() - b4[7];
			} else if (hra.getDbp() >= 80) {
				dbp = k4[8] * hra.getDbp() - b4[8];
			} else {
				dbp = 0.7;
			}
			if (hra.getChole() >= 280) {
				chole = 1.5;
			} else if (hra.getChole() >= 220) {
				chole = k4[9] * hra.getChole() - b4[9];
			} else if (hra.getChole() >= 180) {
				chole = k4[10] * hra.getChole() - b4[10];
			} else {
				chole = 0.5;
			}
			if (hra.getDm() == 1) {
				dm = typeTo4[0];
			} else if (hra.getDm() == 2) {
				dm = typeTo4[1];
			} else {
				dm = typeTo4[2];
			}
			if (hra.getSmoke() == 1) {
				smoke = typeTo4[3];
			} else {
				smoke = typeTo4[4];
			}
			if (sbp > 1) {
				resultb += (sbp - 1);
			} else {
				results = results * sbp;
			}
			if (dbp > 1) {
				resultb += (dbp - 1);
			} else {
				results = results * dbp;
			}
			if (chole > 1) {
				resultb += (chole - 1);
			} else {
				results = results * chole;
			}
			if (dm > 1) {
				resultb += (dm - 1);
			} else {
				results = results * dm;
			}
			if (smoke > 1) {
				resultb += (smoke - 1);
			} else {
				results = results * smoke;
			}
			answer.setObj(df.format(results + resultb));
			break;
		default:
			answer.setResult(false);
			answer.setMsg("疾病选择出错");
			return answer;
		}
		answer.setResult(true);
		return answer;
	}
}
