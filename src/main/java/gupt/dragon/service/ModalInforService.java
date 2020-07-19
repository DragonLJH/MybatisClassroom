package gupt.dragon.service;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import gupt.dragon.access.AccessHelper;
import gupt.dragon.common.Management;
import gupt.dragon.dao.ModalInforMapper;

public class ModalInforService {

	public static final ModalInforService INSTANCE = new ModalInforService(); // 创建单例

	private SqlSessionFactory sqlSessionFactory = AccessHelper.SQL_SESSION_FACTORY;

	public ModalInforService() {

	}
	
	public Management selectAllByDateAndMor(Management management) {
		Management result = new Management();
		SqlSession session = sqlSessionFactory.openSession();
		ModalInforMapper ModalInforMapper = session.getMapper(ModalInforMapper.class);
		try {
			result = ModalInforMapper.selectAllByDateAndMor(management);
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
		return result;
	}
}
