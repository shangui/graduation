/**
 * 
 */
package onesun.model;

import java.io.Serializable;

/**
 * @author Administrator 2016年5月4日
 *
 */
public class SessionInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean ifLogin;
	private String userName;

	/**
	 * @return the ifLogin
	 */
	public boolean getIfLogin() {
		return ifLogin;
	}

	/**
	 * @param ifLogin
	 *            the ifLogin to set
	 */
	public void setIfLogin(boolean ifLogin) {
		this.ifLogin = ifLogin;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
