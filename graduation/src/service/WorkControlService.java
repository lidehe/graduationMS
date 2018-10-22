package service;

/**
 * 工作控制层，通过查询进度来控制某些操作能否开始
 * @author DaMoTou
 *
 */
public interface WorkControlService {

	/**
	 * 校级评优是否开始了。控制院系能否更改推荐的优秀论文
	 * 取决于 论文  校分数 字段是否有了值，如果有，说明校级评优活动已经开始
	 * @return
	 */
	public boolean isXiaoPingyouStart();
	
	/**
	 * 论文抽检能否开始
	 * 取决于进展最慢的学生是否提交了最终论文
	 * @return
	 */
	public boolean canPapercheckStart();
	
	/**
	 * 查看毕业方式是否已经被选用
	 * @param byfsId
	 * @return
	 */
	public boolean isByfsUsed(int byfsId);
}
