package service.secretary;

import java.util.List;

import dao.DbjszDaoImpl;
import dao.DbzlDaoImpl;
import dao.TeacherDao;
import dao.YuanxiDao;
import domain.Dbjsz;
import domain.Dbzl;
import domain.Teacher;
import factory.JWCFactory;
import web.dbjs.Dbz;

public class SecretaryService {

	private DbzlDaoImpl dbzlDao=new DbzlDaoImpl();
	DbjszDaoImpl dbjszDao=new DbjszDaoImpl();
	 TeacherDao teacherDao=JWCFactory.geTeacherDao();
	 YuanxiDao yuanxiDao=JWCFactory.getYuanxiDaoImpl();
//答辩组***************************************答辩组***************************************答辩组***************************************
	/**
	 * 保存答辩组
	 * @param dbzl
	 */
	public void add(Dbzl dbzl){
		dbzlDao.add(dbzl);
	}
	/**
	 * 删除答辩组
	 * @param dbzl
	 */
	public void deleteDbzl(Dbzl dbzl){
		dbzlDao.delete(dbzl);
	}
	
	/**
	 * 按id查答辩组
	 * @param id
	 * @return
	 */
	public Dbzl findById(int id){
		return dbzlDao.findById(id);
	}
	
	/**
	 * 按名字查答辩组
	 * @param name
	 * @return
	 */
	public Dbzl findByName(String  name){
		return dbzlDao.findByName(name);
	}
	/**
	 * 根据院系Id找某个院系的答辩组
	 * @param yuanxiId
	 * @return
	 */
	public List<Dbzl> findDbzlByYuanxiId(int yuanxiId){
		return dbzlDao.findByYuanxiId(yuanxiId);
	}
	/**
	 * 查所有答辩组
	 * @return
	 */
	public List<Dbzl> findAllDbzl(){
		return dbzlDao.findAll();
	}
	//答辩教师组*****************************************************************************************
	/**
	 * 添加答辩教师
	 * @param dbjsz
	 */
	public void add(Dbjsz dbjsz){
		dbjszDao.add(dbjsz);
	}
	
	/**
	 * 根据dbzl的id，查找同一组的教师
	 * @param dbzId
	 * @return
	 */
	public List<Dbjsz> findByDbzId(int dbzId){
		return dbjszDao.findByDbzId(dbzId);
	}
	/**
	 * 删除答辩教师组
	 * @param dbjsz
	 */
	public void delete(Dbjsz dbjsz){
		dbjszDao.delete(dbjsz);
	}
	/**
	 * 更新答辩教师组信息
	 * @param dbjsz
	 */
	public void updateDbjsz(Dbjsz dbjsz){
		dbjszDao.update(dbjsz);
	}

	//教师管理*****************************************************************************************************************
	public Teacher findTeacherByNumber(String number) {
		return teacherDao.findTeacherByNum(number);
	}
	
	
	
	
}
