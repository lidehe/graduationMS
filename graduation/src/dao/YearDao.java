package dao;

import java.util.List;

import domain.Year;

public class YearDao extends BaseDAO<Year>{

	public void save(Year year){
		super.save(year);
	}
	
	public void delete(Year year){
		super.delete(year);
	}
	
	public List<Year> findAll(){
		String hql="from Year ";
		return super.find(hql);
	}
	
}
