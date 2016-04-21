/**
 * 
 */
package onesun.service;

import onesun.dao.BaseDao;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Administrator 2016年4月21日
 *
 */
public class BaseService {

	private BaseDao baseDao;

	public BaseDao getBaseDao() {
		return baseDao;
	}

	@Autowired
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

}
