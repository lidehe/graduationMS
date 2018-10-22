package dao;

import java.util.List;

import domain.Notice;

public class NoticeDao extends BaseDAO<Notice>{

	public void save(Notice notice){
		super.save(notice);
	}
	
	public void delete(Notice notice){
		super.delete(notice);
	}
	
	/**
	 * 查找所有校级公告
	 * @return
	 */
	public List<Notice> findAllXiaoNotice(){
		String hql="from Notice where type = 3 ";
		return find(hql);
	}
	
	/**
	 * 根据ID查找公告
	 * @param id
	 * @return
	 */
	public Notice findById(int id){
		String hql="from Notice where id = ? ";
		Object [] param ={id};
		return super.get(hql, param);
	}
	
	/**
	 * 根据院系Id找到某个院系的所有公告
	 * @param yuanxiId
	 * @return
	 */
	public List<Notice> findAllYuanxiNoticeByYuanxiId(int yuanxiId){
		String hql="from Notice where yx = ? ";
		Object [] param ={yuanxiId};
		return find(hql,param);
	}
	
}
