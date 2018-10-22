package service;

import java.util.List;

import domain.Year;

public interface YearService {
	/**
	 * 获取当届年份
	 * @return
	 */
	public Year getNowYear();
	
	/**
	 * 保存年份
	 * @param year
	 */
	public void saveYear(Year year);
	/**
	 * 设定当前届的年份
	 */
	public void setNowYear(Year year);
	
	/**
	 * 初始化年份信息
	 * 保持年份数量为4个以内，一个是当年，三个是历史年；当年份数量大于4个，就删除最小的一个
	 */
	public void initialYear();
	
	/**
	 * 获取历史年份，不能包含本届年份
	 * @return
	 */
	public List<Year> getHistoryYear();
	/**
	 * 查询所有年份
	 * @return
	 */
	public List<Year> getAlllYear();
}
