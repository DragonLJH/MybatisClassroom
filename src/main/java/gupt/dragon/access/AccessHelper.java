/*
COPYRIGHT (C) 2014-2017 BY MULDINI. ALL RIGHTS RESERVED.
 */

package gupt.dragon.access;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * 资源访问的帮助类（工具类），用单例设计模式。
 * 
 * @author Paolo Weng
 * @since 4.0
 */
public final class AccessHelper {

	public static final AccessHelper INSTANCE = new AccessHelper(); // 单例

	// 在装载该类时就创建SessionFactory，并缓存之
	public static final SqlSessionFactory SQL_SESSION_FACTORY = getSqlSessionFactory();

	/*
	 * 私有构造器，以便实现单例模式，即该类只有1个对象（实例）供其他类使用。
	 */
	private AccessHelper() {
	}

	/*
	 * 获取MyBatis的SQL会话工厂。
	 */
	private static SqlSessionFactory getSqlSessionFactory() {

		Reader reader = null;
		try {
			// 返回ByteArrayInputStream，用完不需调用close()释放资源。而FileInputStream需要close()。
			reader = Resources.getResourceAsReader("conf.xml");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);

		return sqlSessionFactory;
	}

}
