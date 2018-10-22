package dao;

import java.util.List;

import domain.Js;

public interface JsDao {
	
	/**
	 * 保存一个角色信息
	 * @param js
	 */
	public void save(Js js);
	
	/**
	 * 查找所有角色
	 * @return
	 */
	public List<Js> findAllJs();
	
	/**
	 * 根据角色ID找角色
	 * @param id
	 * @return
	 */
	public Js findJsById(int id);
}
