package history;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;

import net.sf.json.JSONArray;

public class HistorySearch {
	String confirgFile = "";

	public void setConfigFile(String dbName) {
		switch (dbName) {
		case "graduation_1":
			confirgFile = "hibernate1.cfg.xml";
			break;
		case "graduation_2":
			confirgFile = "hibernate2.cfg.xml";
			break;
		case "graduation_3":
			confirgFile = "hibernate3.cfg.xml";
			break;

		default:
			break;
		}

	}

	/**
	 * 查找该年份的院系
	 * 
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public JSONArray findAllYuanxi() {
		JSONArray jsonArray = null;
		String sql = "SELECT yuanxi.id AS yuanxiId,yuanxi.va_name AS yuanxiName FROM tb_yuanxi AS yuanxi";
		List<Object> result = new ArrayList<>();
		Session session = null;
		session = HistoryIuimSesssionFactory.getSessionFactory(confirgFile).openSession();
		Transaction transaction = session.beginTransaction();
		// 下面这样写，可以使得从sql查询到的List<Object>中以键值对的形式获取查到的数据
		result = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		String jsonStr = "[";
		if (result.size() > 0) {
			for (Object o : result) {
				Map map = (Map) o;
				String tempStr = "{ yuanxiId:'" + map.get("yuanxiId") + "',yuanxiName:'" + map.get("yuanxiName")
						+ "'},";
				jsonStr += tempStr;
			}
			jsonStr = jsonStr.substring(0, jsonStr.length() - 1);
		}
		jsonStr += "]";
		jsonArray = JSONArray.fromObject(jsonStr);
		transaction.commit();
		session.close();
		return jsonArray;
	}

	/**
	 * 查询某个院系的学生总量
	 * 
	 * @param yuanxiId
	 * @return
	 */
	@SuppressWarnings({ "unused", "rawtypes" })
	public int countStudentByYuanxi(int yuanxiId) {
		int count = 0;
		String sql = "SELECT COUNT(*) AS stuCount FROM tb_student WHERE va_yxid =  " + yuanxiId + " ";
		List<Object> result = new ArrayList<>();
		Session session = null;
		session = HistoryIuimSesssionFactory.getSessionFactory(confirgFile).openSession();
		Transaction transaction = session.beginTransaction();
		Object object = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list()
				.get(0);
		transaction.commit();
		session.close();
		Map map = (Map) object;
		String countstr = map.get("stuCount").toString();
		count = Integer.valueOf(countstr);
		System.out.println("countStudentByYuanxi-数据总数是：" + count);
		return count;
	}

	/**
	 * 按照院系分页查询学生
	 * 
	 * @param nowPage
	 *            当前页码
	 * @param row
	 *            每页显示的行数
	 * @param yuanxiId
	 *            院系Id
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public JSONArray pageSearchStudent(int nowPage, int row, int yuanxiId, String preOrNext) {
		JSONArray jsonArray = null;
		int count = 0;// 该院系学生信息总行数
		count = countStudentByYuanxi(yuanxiId);
		int totalPage = 0;
		// 计算总页数***********************************************************************
		if (count % row == 0) {// 如果能整除，则总数除以每页行数就是总页数
			totalPage = count / row;
		} else {// 如果能整除，则总数除以每页行数就是总页数+1
			totalPage = count / row + 1;
		}
		// 设置页码**************************************************************************
		if (preOrNext.equals("initial")) {
			nowPage = 1;
		} else if (preOrNext.equals("next")) {// 如果是查询下一页
			if (nowPage < totalPage) {
				nowPage += 1;
			}
		} else if (preOrNext.equals("pre")) {// 如果是查询上一页
			if (nowPage > 1) {
				nowPage -= 1;
			}
		}
		String sql = "SELECT stu.id AS stuId, stu.va_number AS stuNumber , stu.va_name AS stuName, yuanxi.va_name AS yuanxiName ,zhuanye.va_name AS zhuanyeName, "
				+ " va_class AS className  FROM tb_student AS stu, tb_yuanxi AS yuanxi ,tb_zhuanye AS zhuanye WHERE stu.va_yxid = yuanxi.id AND  "
				+ " stu.va_zyid = zhuanye.id and stu.va_yxid = " + yuanxiId + "  LIMIT " + (--nowPage) * row + " , " + row
				+ " ";
		List<Object> result = new ArrayList<>();
		Session session = null;
		session = HistoryIuimSesssionFactory.getSessionFactory(confirgFile).openSession();
		Transaction transaction = session.beginTransaction();
		// 下面这样写，可以使得从sql查询到的List<Object>中以键值对的形式获取查到的数据
		result = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		String jsonStr = "[{nowPage:'" + (++nowPage) + "',totalPage:'" + totalPage + "'},";
		if (result.size() > 0) {
			for (Object o : result) {
				Map map = (Map) o;
				String tempStr = "{ stuId:'" + map.get("stuId") + "',stuNumber:'" + map.get("stuNumber") + "',stuName:'"
						+ map.get("stuName") + "',yuanxiName:'" + map.get("yuanxiName") + "',zhuanyeName:'"
						+ map.get("zhuanyeName") + "',className:'" + map.get("className") + "'},";
				jsonStr += tempStr;
			}
			jsonStr = jsonStr.substring(0, jsonStr.length() - 1);
		}
		jsonStr += "]";
		jsonArray = JSONArray.fromObject(jsonStr);
		transaction.commit();
		session.close();
		return jsonArray;
	}

	/**
	 * 毕设详细信息、如成绩、课题
	 * 
	 * @param studentId
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public JSONArray findgraduationDetail(int studentId) {
		JSONArray jsonArray = null;
		String sql = "SELECT kt.va_name AS ktName, ktbg.va_filename AS ktbgName,lw.va_filename AS lwName, teacher.va_name AS teacherName, dbcj.va_fs AS dbfs,sxcj.va_fs AS sxfs, zf.va_lw AS lwcj,zf.va_zf AS zfcj"
				+ " FROM tb_student AS stu,tb_kt AS kt,tb_ktbg AS ktbg ,tb_lw AS lw ,tb_teacher AS teacher,tb_dbpsb AS dbcj,tb_sxcj AS sxcj,tb_zf AS zf"
				+ " WHERE dbcj.va_xuehao = stu.va_number AND sxcj.va_xuehao=stu.va_number AND zf.va_xsxh=stu.va_number AND lw.va_xsxh=stu.va_number AND teacher.va_number=stu.va_teacher AND stu.va_ktid = kt.id AND stu.va_ktbgid=ktbg.id AND stu.va_number=lw.va_xsxh AND stu.id="
				+ studentId + " ";
		List<Object> result = new ArrayList<>();
		Session session = null;
		session = HistoryIuimSesssionFactory.getSessionFactory(confirgFile).openSession();
		Transaction transaction = session.beginTransaction();
		// 下面这样写，可以使得从sql查询到的List<Object>中以键值对的形式获取查到的数据
		result = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		String jsonStr = "[";
		System.out.println("学生ID：" + studentId + "  毕设详情记录查询结果数量：" + result.size());
		if (result.size() > 0) {
			Object obj = result.get(0);
			Map map = (Map) obj;
			jsonStr += "{ktName:'" + map.get("ktName") + "',ktbgName:'" + map.get("ktbgName") + "',lwName:'"
					+ map.get("lwName") + "',teacherName:'" + map.get("teacherName") + "',dbfs:'" + map.get("dbfs")
					+ "',sxfs:'" + map.get("sxfs") + "',lwcj:'" + map.get("lwcj") + "',zfcj:'" + map.get("zfcj") + "'}";
		}
		jsonStr += "]";
		transaction.commit();
		session.close();
		jsonArray = JSONArray.fromObject(jsonStr);
		return jsonArray;
	}

	/**
	 * 优秀论文数量院系分配信息
	 * 
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public JSONArray appraisingCount() {
		JSONArray jsonArray = null;
		String sql = "SELECT  yx.va_name AS yuanxiName, yxlwsl.va_sl AS grsl, (SELECT va_sl FROM tb_yxlwx WHERE va_yx = yxlwsl.va_yx AND va_gx=1 ) AS xzsl  FROM tb_yxlwx AS yxlwsl, tb_yuanxi AS yx WHERE yxlwsl.va_yx = yx.id AND yxlwsl.va_gx=0";
		List<Object> result = new ArrayList<>();
		Session session = null;
		session = HistoryIuimSesssionFactory.getSessionFactory(confirgFile).openSession();
		Transaction transaction = session.beginTransaction();
		// 下面这样写，可以使得从sql查询到的List<Object>中以键值对的形式获取查到的数据
		result = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		String jsonStr = "[";
		if (result.size() > 0) {
			for (Object o : result) {
				Map map = (Map) o;
				String tempStr = "{ yuanxiName:'" + map.get("yuanxiName") + "',grsl:'" + map.get("grsl") + "',xzsl:'"
						+ map.get("xzsl") + "'},";
				jsonStr += tempStr;
			}
			jsonStr = jsonStr.substring(0, jsonStr.length() - 1);
		}
		jsonStr += "]";
		jsonArray = JSONArray.fromObject(jsonStr);
		transaction.commit();
		session.close();
		return jsonArray;
	}

	/**
	 * 推送到省级优秀论文
	 * 
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public JSONArray appraisingShengji() {
		JSONArray jsonArray = null;
		String sql = "SELECT stu.id AS stuId,yxlw.va_xuehao AS stuNumber, stu.va_name AS stuName ,yx.va_name AS yuanxiName,zhuanye.va_name AS zhuanyeName,lw.va_filename AS lwName FROM tb_yxlw AS yxlw,tb_yuanxi AS yx,tb_lw AS lw,tb_zhuanye AS zhuanye,tb_student AS stu WHERE yx.id=yxlw.va_yx AND zhuanye.id=yxlw.id AND lw.id=yxlw.va_lw AND stu.va_number=yxlw.va_xuehao AND yxlw.va_status=3";
		List<Object> result = new ArrayList<>();
		Session session = null;
		session = HistoryIuimSesssionFactory.getSessionFactory(confirgFile).openSession();
		Transaction transaction = session.beginTransaction();
		// 下面这样写，可以使得从sql查询到的List<Object>中以键值对的形式获取查到的数据
		result = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		String jsonStr = "[";
		if (result.size() > 0) {
			for (Object o : result) {
				Map map = (Map) o;
				String tempStr = "{ stuId:'" + map.get("stuId") + "',stuNumber:'" + map.get("stuNumber") + "',stuName:'"
						+ map.get("stuName") + "',yuanxiName:'" + map.get("yuanxiName") + "',zhuanyeName:'"
						+ map.get("zhuanyeName") + "',lwName:'" + map.get("lwName") + "'},";
				jsonStr += tempStr;
			}
			jsonStr = jsonStr.substring(0, jsonStr.length() - 1);
		}
		jsonStr += "]";
		jsonArray = JSONArray.fromObject(jsonStr);
		transaction.commit();
		session.close();
		return jsonArray;
	}

	/**
	 * 获取抽检规则-----学生尾号
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String findCheckRule() {
		String rule = "";
		String sql = " SELECT DISTINCT RIGHT(va_xuehao,1) AS checkRule FROM tb_cjk  ORDER BY checkRule ASC  ";
		List<Object> result = new ArrayList<>();
		Session session = null;
		session = HistoryIuimSesssionFactory.getSessionFactory(confirgFile).openSession();
		Transaction transaction = session.beginTransaction();
		// 下面这样写，可以使得从sql查询到的List<Object>中以键值对的形式获取查到的数据
		result = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		if (result.size() > 0) {
			for(int i=0;i<result.size();i++){
				Object obj = result.get(i);
				Map map = (Map) obj;
				rule += (String) map.get("checkRule");
			}
		}
		transaction.commit();
		session.close();
		return rule;
	}

	/**
	 * 获取抽检结果
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public JSONArray findCheckResult() {
		JSONArray jsonArray = null;
		String sql = " SELECT  yuanxi.va_name AS yuanxiName,cjk.va_dj AS dj,COUNT(*) AS total FROM tb_cjk AS cjk,tb_yuanxi AS yuanxi WHERE cjk.va_yx=yuanxi.id GROUP BY cjk.va_yx,cjk.va_dj";
		List<Object> result = new ArrayList<>();
		Session session = null;
		session = HistoryIuimSesssionFactory.getSessionFactory(confirgFile).openSession();
		Transaction transaction = session.beginTransaction();
		// 下面这样写，可以使得从sql查询到的List<Object>中以键值对的形式获取查到的数据
		result = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		String jsonStr = "[";
		if (result.size() > 0) {
			for (Object o : result) {
				Map map = (Map) o;
				String tempStr = "{ yuanxiName:'" + map.get("yuanxiName") + "',dj:'" + map.get("dj") + "',total:'"+ map.get("total") + "'},";
				jsonStr += tempStr;
			}
			jsonStr = jsonStr.substring(0, jsonStr.length() - 1);
		}
		jsonStr += "]";
		jsonArray = JSONArray.fromObject(jsonStr);
		transaction.commit();
		session.close();
		return jsonArray;
	}
}
