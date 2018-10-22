package dao;

import java.util.List;

import domain.Jsgx;
/**
 * 角色关系
 * @author DaMoTou
 *
 */
public interface JsgxDao {

	/**
	 * 添加角色关系
	 * @param jsgx
	 */
	public void addJsgx(Jsgx jsgx);
	
	/**
	 * 更新角色关系
	 * @param jsgx
	 */
	public void updateJsgx(Jsgx jsgx);
	
	/**
	 * 删除角色关系
	 * @param jsgx
	 */
	public void deleteJsgx(Jsgx jsgx);
	
	/**
	 * 根据工号查找一个老师的所有角色关系，可能不止一个
	 * @param number
	 * @return
	 */
	public List<Jsgx> findJsgxByTeacherNumber(String number);
	/**
	 * 根据教师工号和角色id找到角色关系
	 * @param number
	 * @param JsId
	 * @return
	 */
	public Jsgx  findJsgxByNumberAndJsId(String number,int JsId);
	/**
	 * 找出所有角色关系
	 * @return
	 */
	public List<Jsgx> findAllJsgx();
	
	/**
	 * 根据角色id找角色关系
	 * @param JsId  角色ID
	 * @return
	 */
	public List<Jsgx> findJsgxByJsID(int JsId);

}
