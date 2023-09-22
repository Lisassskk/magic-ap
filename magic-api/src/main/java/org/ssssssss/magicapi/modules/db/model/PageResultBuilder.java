package org.ssssssss.magicapi.modules.db.model;

import java.util.List;

/**
 * 分页执行结果
 *
 * @author mxd
 */
public class PageResultBuilder<T> {

	/**
	 * 分页请求参数
	 */
	private Page page;

	/**
	 * 总条数
	 */
	private long total;

	/**
	 * 数据项
	 */
	private List<T> list;

	public PageResultBuilder(Page page, long total, List<T> list) {
		this.page = page;
		this.total = total;
		this.list = list;
	}

	public PageResultBuilder() {
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}
}
