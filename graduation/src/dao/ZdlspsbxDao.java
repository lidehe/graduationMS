package dao;

import java.util.List;

import domain.Zdlspsbx;

public class ZdlspsbxDao extends BaseDAO<Zdlspsbx>{
	public void save(Zdlspsbx Zdlspsbx){
		super.save(Zdlspsbx);
	}
	
	public void update(Zdlspsbx Zdlspsbx){
		super.update(Zdlspsbx);
	}
	
	public Zdlspsbx findByName(String name){
		String hql="from Zdlspsbx where text = ?";
		Object [] param={name};
		return super.get(hql, param);
	}
	
	public void delete(Zdlspsbx Zdlspsbx){
		super.delete(Zdlspsbx);
	}
	public List<Zdlspsbx> findAll(){
		String hql="from Zdlspsbx ";
		return super.find(hql);
	}
}
