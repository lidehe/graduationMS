package history;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;

import utils.IuimSesssionFactory;

/**
 * 数据库管理，包括，新建数据库、删除数据库、数据库迁移
 * 教务处管理员在开始新的一年工作的时候，要把老的数据库改名为“graduation_上一年的年份”，同时新建一个数据库“graduation”
 * 
 * @author DaMoTou
 *
 */
public class DBManage {

	/**
	 * 迁移数据库
	 * 
	 * @param sourceDB源数据库
	 * @param targetDB目的数据库
	 * @return
	 */
	public static boolean copyDB(String sourceDB, String targetDB) {
		System.out.println(sourceDB+":"+targetDB);
		boolean result = true;
		Runtime runtime = Runtime.getRuntime();
		try {
			String commond = "cmd.exe /c  mysqldump -uroot -proot " + sourceDB + " | mysql -uroot -proot " + targetDB
					+ "  ";
			runtime.exec(commond);
			Thread.sleep(3000);
		} catch (Exception e) {
			result = false;
			e.printStackTrace();
		} 
		return result;
	}

	/**
	 * 清空数据库数据 思路是先删除再新建，这个方法不可行，因为正在使用的数据库被锁定，无法删除 
	 * 思路2，读取所有的表名字，拼接delete from tableName，这样来删除数据
	 * 
	 * @param dbNewName
	 */
	public static void clearDb() {
		List<String> tables=new ArrayList<>();
		tables=getTableNames();
		if (tables.size()>0) {
			for(String table:tables){
				String sql = "delete from  "+table+" ";
				Session session = null;
				session = IuimSesssionFactory.getSessionFactory().openSession();
				Transaction transaction = session.beginTransaction();
				Query query = session.createSQLQuery(sql);
				query.executeUpdate();
				transaction.commit();
				session.close();
			}
		}
	}
	/**
	 * 获取数据库中所有表的名字
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static  List<String> getTableNames(){
		List<String> tables=new ArrayList<>();
		List<Object> objects = null;
		String sql = "SELECT table_name AS tableName FROM infoRmation_schema.tables WHERE table_schema = 'graduation_db' ";
		Session session = null;
		session = IuimSesssionFactory.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		objects = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		transaction.commit();
		session.close();
		if (objects.size() > 0) {
			for (Object object : objects) {
				Map map = (Map) object;
				if ("tb_js".equals(map.get("tableName"))||"tb_year".equals(map.get("tableName"))||"tb_byfs".equals(map.get("tableName"))) {
					// 保存角色和年份、毕业方式信息，不删除
				} else {
					tables.add(map.get("tableName").toString());
				}
			}
		}
		return tables;
	}

	/**
	 * 新建数据库
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static boolean makeNewDb(String dbName) {
		boolean result = true;
		int i = 0;
		List<Object> dbs = null;
		// 查询所有数据库名字
		String isExist = "SELECT SCHEMA_NAME AS dbName FROM information_schema.SCHEMATA WHERE SCHEMA_NAME LIKE  '%graduation%'  ";
		Session session = null;
		session = IuimSesssionFactory.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		dbs = session.createSQLQuery(isExist).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		transaction.commit();
		session.close();
		boolean exist = false;
		System.out.println("数据库总数是"+dbs.size());
		if (dbs.size() > 0) {
			// 便利数据库名字，如果待新建的数据库已经存在了，就不新建了
			for (Object object : dbs) {
				Map map = (Map) object;
				System.out.println("便利中的数据库"+map.get("dbName"));
				if (dbName.equals(map.get("dbName"))) {
					System.out.println("数据库"+map.get("dbName")+"已经存在");
					exist = true;
					break;
				} 
			}
		}
		if (!exist) {
			System.out.println("开始创建数据库"+exist);
			String sql = "CREATE DATABASE " + dbName;
			session = null;
			session = IuimSesssionFactory.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			Query query = session.createSQLQuery(sql);
			transaction.commit();
			i = query.executeUpdate();
			session.close();
			if (i == 0) {
				result = false;
			} else {
				result = true;
			}
		}
		return result;
	}

	/**
	 * 删除数据库
	 * 
	 * @param dbName
	 * @return
	 */
	public static boolean deleteDB(String dbName) {
		boolean result = true;
		int i = 0;
		String sql = "DROP DATABASE " + dbName;
		Session session = null;
		session = IuimSesssionFactory.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		Query query = session.createSQLQuery(sql);
		try {
			transaction.commit();
			i = query.executeUpdate();
		} catch (Exception e) {
			throw new RuntimeException("删除   数据库失败");
		} finally {
			session.close();
		}
		if (i == 0) {
			result = false;
		} else {
			result = true;
		}
		return result;
	}
}
