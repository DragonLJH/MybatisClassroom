package gupt.dragon.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import gupt.dragon.access.AccessHelper;
import gupt.dragon.common.Management;
import gupt.dragon.dao.ManagementMapper;

public class ManagementService {
	public static final ManagementService INSTANCE = new ManagementService(); // 创建单例

	private SqlSessionFactory sqlSessionFactory = AccessHelper.SQL_SESSION_FACTORY;

	public ManagementService() {

	}
	
	
	//根据日期查询所有的数据
	public List<Management> selectAllByDate(String date) {
		List<Management> listManagement = new ArrayList<Management>();
		SqlSession session = sqlSessionFactory.openSession();
		ManagementMapper managementMapper = session.getMapper(ManagementMapper.class);
		try {
			listManagement = managementMapper.selectAllByDate(date);
			// 提交事务。
			session.commit();
		} catch (PersistenceException e) {
			// 若抛出异常，需回滚事务，最好如下显式地回滚。
			session.rollback();
			// 继续往上抛，不需转化为RuntimeException，因PersistenceException就是RuntimeException
			throw e;

		} finally {
			// 关闭连接，以释放占用的资源。
			session.close();
		}
		return listManagement;
	}
	
	
	//在数据库创建一条新的management数据
	public void creationManagement(Management management) {
		SqlSession session = sqlSessionFactory.openSession();
		ManagementMapper managementMapper = session.getMapper(ManagementMapper.class);
		try {
			managementMapper.creationManagement(management);;
			// 提交事务。
			session.commit();
		} catch (PersistenceException e) {
			// 若抛出异常，需回滚事务，最好如下显式地回滚。
			session.rollback();
			// 继续往上抛，不需转化为RuntimeException，因PersistenceException就是RuntimeException
			throw e;

		} finally {
			// 关闭连接，以释放占用的资源。
			session.close();
		}
	}
	
	//在数据库更新一条management数据
	public void updateManagement(Management management) {
		SqlSession session = sqlSessionFactory.openSession();
		ManagementMapper managementMapper = session.getMapper(ManagementMapper.class);
		try {
			managementMapper.updateManagement(management);
			// 提交事务。
			session.commit();
		} catch (PersistenceException e) {
			// 若抛出异常，需回滚事务，最好如下显式地回滚。
			session.rollback();
			// 继续往上抛，不需转化为RuntimeException，因PersistenceException就是RuntimeException
			throw e;

		} finally {
			// 关闭连接，以释放占用的资源。
			session.close();
		}
	}
}
