package dao;

import java.io.Serializable;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import utils.HibernateUtils;
import utils.IuimSesssionFactory;

@SuppressWarnings("all")
public class BaseDAO<T> {

	// 保存实体
	public void save(T o) {
		Session session = null;
		session = IuimSesssionFactory.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		session.save(o);
		transaction.commit();
		session.close();
	}

	// 删除实体
	public void delete(T o) {
		Session session = null;
		session = IuimSesssionFactory.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		session.delete(o);
		transaction.commit();
		session.close();
	}

	// 更新实体
	public void update(T o) {
		Session session = null;
		session = IuimSesssionFactory.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		session.update(o);
		transaction.commit();
		session.close();
	}

	// 根据实体的id获取实体
	public T get(Class<T> c, Serializable id) {
		Session session = null;
		session = IuimSesssionFactory.getSessionFactory().openSession();
		Transaction transaction=session.beginTransaction();
		T t = (T) session.get(c, id);
		transaction.commit();
		session.close();
		
		return t;
	}

	// 根据带参数的hql语句获取实体
	public T get(String hql, Object[] param) {
		Session session = null;
		session = IuimSesssionFactory.getSessionFactory().openSession();
		session.flush();
		Transaction transaction=session.beginTransaction();
		List<T> l = this.find(hql, param);
		if (l != null && l.size() > 0) {
			session.close();
			transaction.commit();
			return l.get(0);
		} else {
			session.close();
			transaction.commit();
			return null;
		}
	}

	// 根据hql语句，获得一个LIst
	public List<T> find(String hql) {
		List<T> list = null;
		Session session = null;
		session = IuimSesssionFactory.getSessionFactory().openSession();
		Transaction transaction=session.beginTransaction();
		Query query = session.createQuery(hql);
		list = query.list();
		transaction.commit();
		session.close();
		return list;
	}

	// 根据带参数的hql获得实体
	public List<T> find(String hql, Object[] param) {
		List<T> list = null;
		Session session = null;
		session = IuimSesssionFactory.getSessionFactory().openSession();
		Transaction transaction=session.beginTransaction();
		Query q = session.createQuery(hql);
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				q.setParameter(i, param[i]);
			}
		}
		list = q.list();
		transaction.commit();
		session.close();
		return list;
	}

	// 计算总数
	public int count(String hql) {
		int count = 0;
		Session session = null;
		session = IuimSesssionFactory.getSessionFactory().openSession();
		Object object =  session.createQuery(hql).uniqueResult();
		if(object == null)
			return 0;
		count = ((Number) object).intValue();
		session.close();
		return count;
	}

	// 根据带参数的hql计算数量
	public int count(String hql, Object[] param) {
		int count = 0;
		Session session = null;
		session = IuimSesssionFactory.getSessionFactory().openSession();
		Query q = session.createQuery(hql);
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				q.setParameter(i, param[i]);
			}
		}
		Object object = q.uniqueResult();
		if (object == null)
			count = 0;
		else {
			count = ((Number) object).intValue();
		}
		session.close();
		return count;
	}

	// 分页查询
	public List<T> findInPage(String hql, int total, int pageNum, int rowCount) {
		// 起始地行号
		int startRow = 0;
		startRow = (pageNum - 1) * rowCount;

		List<T> list = null;
		Session session = null;
		session = IuimSesssionFactory.getSessionFactory().openSession();
		Query q = session.createQuery(hql);
		q.setFirstResult(startRow);// 设置起始行
		q.setMaxResults(rowCount);// 设置每页条数
		list = q.list();
		session.close();
		return list;
	}
	
	//按条件分页查询
		public List<T> findInPageByParam(String hql,int total,int pageNum,int rowCount,Object[] param){
			//起始地行号
			int startRow=0;
			startRow=(pageNum-1)*rowCount;  
			
			List<T> list = null;
			Session session = null;
			session = IuimSesssionFactory.getSessionFactory().openSession();
			Query q = session.createQuery(hql);
			q.setFirstResult(startRow);//设置起始行
			q.setMaxResults(rowCount);//设置每页条数
			if (param != null && param.length > 0) {
				for (int i = 0; i < param.length; i++) {
					q.setParameter(i, param[i]);
				}
			}
			list = q.list();
			session.close();
			return list;
		}

}