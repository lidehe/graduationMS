package factory;


import dao.AccountDao;
import dao.ByfsDaoImpl;
import dao.CjbdDao;
import dao.CjkDaoImpl;
import dao.CjrzDaoImpl;
import dao.DbpsbxDao;
import dao.JsDaoImpl;
import dao.JsgxDaoImpl;
import dao.KtDao;
import dao.LwDaoImpl;
import dao.PypsbxDao;
import dao.StudentDaoImpl;
import dao.SxcjxDao;
import dao.TeacherDaoImp;
import dao.YuanxiDaoImpl;
import dao.YxlwDaoImpl;
import dao.YxlwxDao;
import dao.YxlwzDao;
import dao.ZdlspsbxDao;
import dao.ZhuanyeDaoImpl;
import dao.ZqxjDaoImpl;
import service.impl.jwc.JWCServiceImpl;
public class JWCFactory {
	public static JWCServiceImpl getJwcService(){
	    return new JWCServiceImpl();
	}

	public static ByfsDaoImpl getByfsDao(){
		return new ByfsDaoImpl();
	}
	
	public static JsDaoImpl getJsDao(){
		return new JsDaoImpl();
	}
	public static JsgxDaoImpl getJsgxDao(){
		return new JsgxDaoImpl();
	}
	
	public static StudentDaoImpl getStudentDao(){
		return new StudentDaoImpl();
	}
	
	public static TeacherDaoImp geTeacherDao(){
		return new TeacherDaoImp();
	}
	
	public static YuanxiDaoImpl getYuanxiDaoImpl(){
		return new YuanxiDaoImpl();
	}
	
	public static ZhuanyeDaoImpl getZhuanyeDao(){
		return new ZhuanyeDaoImpl();
	}
	
	public static CjrzDaoImpl getCjrzDao(){
		return new CjrzDaoImpl();
	}
	
	public static LwDaoImpl getLwDao(){
		return new LwDaoImpl();
	}
	
	public static YxlwDaoImpl getYxlwDao(){
		return new YxlwDaoImpl();
	}
	
	public static CjkDaoImpl getCjkDao(){
		return new CjkDaoImpl();
	}
	
	public static KtDao getKtDao(){
		return new KtDao();
	}
	
	public static CjbdDao getCjbdDao(){
		return new CjbdDao();
	}
	
	public static YxlwxDao getYxlwxDao(){
		return new YxlwxDao();
	}
	
	public static YxlwzDao getYxlwzDao(){
		return new YxlwzDao();
	}
	
	public static ZqxjDaoImpl getZqxjDao(){
		return new ZqxjDaoImpl();
	}
	public static AccountDao getAccountDao(){
		return new AccountDao();
	}
	
	public static PypsbxDao getPypsbxDao(){
		return new PypsbxDao();
	}
	public static DbpsbxDao getDbpsbxDao(){
		return new DbpsbxDao();
	}
	public static ZdlspsbxDao getZdlspsbxDao(){
		return new ZdlspsbxDao();
	}
	public static SxcjxDao getSxcjxDao(){
		return new SxcjxDao();
	}
}
