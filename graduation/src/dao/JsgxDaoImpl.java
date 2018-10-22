package dao;

import java.util.List;

import domain.Jsgx;

public class JsgxDaoImpl extends BaseDAO<Jsgx> implements JsgxDao {

	@Override
	public void addJsgx(Jsgx jsgx) {
      super.save(jsgx);
	}

	@Override
	public void updateJsgx(Jsgx jsgx) {
      super.update(jsgx);
	}

	@Override
	public void deleteJsgx(Jsgx jsgx) {
      super.delete(jsgx);
	}

	
	@Override
	public List<Jsgx> findJsgxByTeacherNumber(String number) {
		String hql="from Jsgx where gonghao =? ";
		Object[] param ={number};
		return super.find(hql, param);
	}

	@Override
	public Jsgx findJsgxByNumberAndJsId(String number, int JsId) {
		String hql="from Jsgx where gonghao = ? and js = ? ";
		Object[] param ={number,JsId};
		Jsgx jsgx=null;
		jsgx=super.get(hql, param);
		return super.get(hql, param);
	}

	@Override
	public List<Jsgx> findAllJsgx() {
		String hql="from Jsgx ";
		return super.find(hql);
	}

	@Override
	public List<Jsgx> findJsgxByJsID(int JsId) {
		String hql="from Jsgx where js = ? ";
		Object[] param ={JsId};
		return super.find(hql, param);
	}


}
