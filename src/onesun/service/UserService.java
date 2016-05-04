/**
 * 
 */
package onesun.service;

import onesun.model.Hra;
import onesun.model.User;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Administrator 2016年4月21日
 *
 */
@Service("userService")
@Transactional
public class UserService extends BaseService {

	private static final Logger logger = Logger.getLogger(UserService.class);

	public User login(User user_p) {
		String hql = "from User u where u.name=? and u.password=?";
		logger.info("用户姓名：------" + user_p.getName());
		logger.info("用户密码：------" + user_p.getPassword());
		User user = (User) super.getBaseDao().get(hql, user_p.getName(),
				user_p.getPassword());
		return user;
	}

	public void hra(Hra hra) {
		super.getBaseDao().saveOrUpdate(hra);
	}
}
