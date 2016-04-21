/**
 * 
 */
package onesun.service;

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
		logger.debug("------" + user_p.getName());
		String hql = "from User u where u.name=? and u.password=?";
		User user = null;
		user = (User) super.getBaseDao().get(hql, user_p.getName(),
				user_p.getPassword());
		return user;
	}
}
