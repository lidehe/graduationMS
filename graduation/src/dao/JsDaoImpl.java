package dao;

import java.util.List;

import domain.Js;

public class JsDaoImpl extends BaseDAO<Js> implements JsDao{
	
	@Override
	public void save(Js js){
		super.save(js);
	}

	@Override
	public List<Js> findAllJs() {
		String hql="from Js";
		return super.find(hql);
	}

	@Override
	public Js findJsById(int id) {
		String hql="from Js where id=? ";
		Object[] param=new Object[]{id};
		return super.get(hql, param);
	}

}
