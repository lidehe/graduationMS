package dao;

import java.util.List;

import domain.Dbpsbx;

public class DbpsbxDao extends BaseDAO<Dbpsbx>{

	public void save(Dbpsbx dbpsbx){
		super.save(dbpsbx);
	}
	
	public void update(Dbpsbx dbpsbx){
		super.update(dbpsbx);
	}
	
	public Dbpsbx findByName(String name){
		String hql="from Dbpsbx where text = ?";
		Object [] param={name};
		return super.get(hql, param);
	}
	
	public void delete(Dbpsbx dbpsbx){
		super.delete(dbpsbx);
	}
	
	public List<Dbpsbx> findAll(){
		String hql="from Dbpsbx ";
		return super.find(hql);
	}
	
}
