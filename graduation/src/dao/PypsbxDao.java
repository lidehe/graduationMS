package dao;

import java.util.List;

import domain.Dbpsbx;
import domain.Pypsbx;

public class PypsbxDao extends BaseDAO<Pypsbx>{
	public void save(Pypsbx Pypsbx){
		super.save(Pypsbx);
	}
	
	public void update(Pypsbx Pypsbx){
		super.update(Pypsbx);
	}
	
	public Pypsbx findByName(String name){
		String hql="from Pypsbx where text = ?";
		Object [] param={name};
		return super.get(hql, param);
	}
	
	public void delete(Pypsbx Pypsbx){
		super.delete(Pypsbx);
	}
	public List<Pypsbx> findAll(){
		String hql="from Pypsbx ";
		return super.find(hql);
	}
}
