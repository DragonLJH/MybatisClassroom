/*
COPYRIGHT (C) 2017 BY MULDINI. ALL RIGHTS RESERVED.
 */

package gupt.dragon.service;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import gupt.dragon.access.AccessHelper;
import gupt.dragon.common.User;
import gupt.dragon.dao.UserMapper;

/**
 * 用户服务类。用户服务类的实例化采用单例模式
 */
public final class UserService {

	/**
	 * UserService实例，单例模式
	 */
	public static final UserService INSTANCE = new UserService(); // 创建单例

	
	
	private SqlSessionFactory sqlSessionFactory = AccessHelper.SQL_SESSION_FACTORY;
	

	public UserService() {

	}

	/**
	 * 用户登录，成功返回true，否则返回false。
	 * 
	 * @param jobNumber,userPwd
	 * 
	 * @return boolean
	 */
	public boolean loginUser(String jobNumber, String userPwd) {

		SqlSession session = sqlSessionFactory.openSession();
		UserMapper userMapper = session.getMapper(UserMapper.class);

		try {
			// 查询该用户是否存在，若无则返回false。
			User existingUser = userMapper.retrieveUserByUserId(jobNumber);
			if (existingUser != null) {
				if (userPwd.equals(existingUser.getPassword())) { // 校验密码是否正确
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} catch (PersistenceException e) {

			// 若抛出异常，需回滚事务，最好如下显式地回滚。
			session.rollback();

			throw new RuntimeException(e);

		} finally {
			// 关闭连接，以释放占用的资源。
			session.close();
		}

	}

	/**
	 * 修改用户密码。
	 * 
	 * @param manageID
	 * 
	 * 
	 * @return true表示成功；false表示失败。
	 */
	public boolean updateUser(String jobNumber, String password, String newPassword) {

		SqlSession session = sqlSessionFactory.openSession();
		UserMapper userMapper = session.getMapper(UserMapper.class);
		User user = new User();
		user.setJobNumber(jobNumber);
		user.setPassword(newPassword);
		try {
			// 查询该用户是否存在，若无则返回false。
			User existingUser = userMapper.retrieveUserByUserId(jobNumber);
			if (existingUser != null) {
				if (password.equals(existingUser.getPassword())) { // 校验旧密码是否正确
					userMapper.updateUser(user);
					// 提交事务。
					session.commit();
				} else {
					return false;
				}
			} else {
				return false;
			}
		} catch (PersistenceException e) {

			// 若抛出异常，需回滚事务，最好如下显式地回滚。
			session.rollback();

			// 将SQLException转化为RuntimeException，如此，该方法调用者就不需显式地处理该SQLException。
			throw new RuntimeException(e);

		} finally {
			// 关闭连接，以释放占用的资源。
			session.close();
		}
		return true;
	}

	/**
	 * 重置用户密码。
	 * 
	 * @param jobNumber
	 * 
	 * @return true表示成功；false表示失败。
	 */
	public boolean resetUserPassword(String jobNumber, String password) {

		SqlSession session = sqlSessionFactory.openSession();
		UserMapper userMapper = session.getMapper(UserMapper.class);
		User user = new User();
		user.setJobNumber(jobNumber);
		user.setPassword(password);
		try {
			// 查询该用户是否存在，若无则返回false。
			User existingUser = userMapper.retrieveUserByUserId(jobNumber);
			if (existingUser != null) {
				userMapper.resetUserPassword(user);
				// 提交事务。
				session.commit();
			} else {
				return false;
			}
		} catch (PersistenceException e) {

			// 若抛出异常，需回滚事务，最好如下显式地回滚。
			session.rollback();

			// 将SQLException转化为RuntimeException，如此，该方法调用者就不需显式地处理该SQLException。
			throw new RuntimeException(e);

		} finally {
			// 关闭连接，以释放占用的资源。
			session.close();
		}
		return false;
	}

	public String retrievePowerByJobNumber(String jobNumber) {
		String result = null;
		SqlSession session = sqlSessionFactory.openSession();
		UserMapper userMapper = session.getMapper(UserMapper.class);

		try {
			//返回该用户的权限
			result = userMapper.retrievePowerByJobNumber(jobNumber);
			// 提交事务。
			session.commit();
		} catch (PersistenceException e) {

			// 若抛出异常，需回滚事务，最好如下显式地回滚。
			session.rollback();

			// 将SQLException转化为RuntimeException，如此，该方法调用者就不需显式地处理该SQLException。
			throw new RuntimeException(e);

		} finally {
			// 关闭连接，以释放占用的资源。
			session.close();
		}
		return result;
	}

}
