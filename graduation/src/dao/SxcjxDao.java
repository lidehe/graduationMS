package dao;

import java.util.List;

import domain.Sxcjx;

public class SxcjxDao  extends BaseDAO<Sxcjx>{
	public void save(Sxcjx Sxcjx){
		super.save(Sxcjx);
	}
	
	public void update(Sxcjx Sxcjx){
		super.update(Sxcjx);
	}
	
	public Sxcjx findByName(String name){
		String hql="from Sxcjx where text = ?";
		Object [] param={name};
		return super.get(hql, param);
	}
	
	public void delete(Sxcjx Sxcjx){
		super.delete(Sxcjx);
	}
	
	public List<Sxcjx> findAll(){
		String hql="from Sxcjx ";
		return super.find(hql);
	}
	
}
