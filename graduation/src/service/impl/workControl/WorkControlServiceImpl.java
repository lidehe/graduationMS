package service.impl.workControl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import domain.Yxlw;
import service.WorkControlService;
import utils.IuimSesssionFactory;

public class WorkControlServiceImpl implements WorkControlService {
	@SuppressWarnings("unchecked")
	@Override
	public boolean isXiaoPingyouStart() {
		List<Yxlw> yxlws=new ArrayList<>();
		String sql = "SELECT * FROM tb_yxlw WHERE tb_yxlw.va_xfs IS NOT NULL";
		Session session = null;
		session = IuimSesssionFactory.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		System.out.println(sql);
		Query query = session.createSQLQuery(sql).addEntity(Yxlw.class);
		yxlws=query.list();
		transaction.commit();
		session.close();
		if (yxlws.size()>0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean canPapercheckStart() {
		String sql ="SELECT COUNT(*) FROM tb_student WHERE tb_student.va_stage < 14";
		Session session = null;
		session = IuimSesssionFactory.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		Query query = session.createSQLQuery(sql);
		Object object=query.uniqueResult();
		transaction.commit();
		session.close();
		BigInteger b=(BigInteger)object;
		int n=b.intValue();
		if (n==0) {//如果进度小于14的人数为0，则说明所有人提交了最终论文，抽检工作可以开始
			return true;
		}
		return false;
	}

	@Override
	public boolean isByfsUsed(int byfsId) {
		String sql ="SELECT COUNT(*) FROM tb_student AS stu WHERE stu.va_type="+byfsId+"  ";
		Session session = null;
		session = IuimSesssionFactory.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		Query query = session.createSQLQuery(sql);
		Object object=query.uniqueResult();
		transaction.commit();
		session.close();
		BigInteger b=(BigInteger)object;
		int n=b.intValue();
		if (n==0) {//如果选用人数为0，则说明毕设方式无人用，可以删除
			return false;
		}
		return true;
	}

}
