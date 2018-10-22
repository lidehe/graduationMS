package jwc;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import domain.Yuanxi;
import history.DBManage;
import history.HistorySearch;

public class HistoryTest {

	@Test
	public void test() {
		List<Yuanxi> yuanxis = new ArrayList<>();
		// HistorySearch historySearch=new HistorySearch();
		// historySearch.setConfigFile("hibernate2.cfg.xml");
		// yuanxis=historySearch.findAllYuanxi();
		// if (yuanxis.size()>0) {
		// for(Yuanxi yuanxi:yuanxis)
		// System.out.println(yuanxi.getName());
		// }else {
		// System.out.println("查不到数据 啊哥");
		// }

		//boolean dbResult;

		// DBManage.makeNewDb("graduation_1");
		// DBManage.makeNewDb("graduation_2");
		// DBManage.makeNewDb("graduation_3");
		// DBManage.copyDB("graduation_2", "graduation_3");//成功
		// DBManage.copyDB("graduation_1", "graduation_2");//失败
		// DBManage.copyDB("graduation_db","graduation_1");//成功
		 DBManage.copyDB("graduation_1","graduation_db");//成功
		// DBManage.clearDb("graduation_1");
		// DBManage.clearDb("graduation_2");
		 //DBManage.clearDb();

	}

}
