package service.impl.common;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import dao.YearDao;
import domain.Year;
import service.YearService;
import utils.IuimSesssionFactory;

public class YearServiceImpl implements YearService {

	private YearDao yearDao = new YearDao();

	@SuppressWarnings("unchecked")
	@Override
	public Year getNowYear() {
		// TODO Auto-generated method stub
		List<Year> years = new ArrayList<Year>();
		String hql = "select * from tb_year y where y.va_year = ( select MAX(va_year) from tb_year ) ";
		Session session = null;
		session = IuimSesssionFactory.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		Query query = session.createSQLQuery(hql).addEntity(Year.class);
		transaction.commit();
		years = query.list();
		if (years == null || years.size() == 0) {
			Calendar c = Calendar.getInstance();
			return new Year(c.get(Calendar.YEAR));
		}
		session.close();
		return years.get(0);
	}

	@Override
	public void setNowYear(Year year) {
		yearDao.save(year);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Year> getHistoryYear() {
		List<Year> years = new ArrayList<>();
		List<Year> result = new ArrayList<>();
		// 从小到大地获取所有年份，如2017，2018，，，，那么历史数据就是除了最后一个的所有年份
		String hql = "select * from tb_year order by va_year asc";
		Session session = null;
		session = IuimSesssionFactory.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		Query query = session.createSQLQuery(hql).addEntity(Year.class);
		transaction.commit();
		years = query.list();
		if (years.size() == 1) {
			// 仅有一个年份的时候，就返回空的结果，因为没有历史数据
		} else if (years.size() > 1) {
			result = years.subList(0, years.size() - 1);
		}
		session.close();
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initialYear() {
		List<Year> years = new ArrayList<>();

		// 从小到大地获取所有年份，如2017，2018，，，，如果达到3个以上，就删除第一个
		String hql = "select * from tb_year order by va_year asc";
		Session session = null;
		session = IuimSesssionFactory.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		Query query = session.createSQLQuery(hql).addEntity(Year.class);
		transaction.commit();
		years = query.list();
		if (years.size() > 3) {
			yearDao.delete(years.get(0));
		}
		session.close();

	}

	@Override
	public void saveYear(Year year) {
		yearDao.save(year);
	}

	@Override
	public List<Year> getAlllYear() {
		return yearDao.findAll();
	}

}
