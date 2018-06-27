package test;

import java.util.ArrayList;
import java.util.List;

public class PageInfo {
	@SuppressWarnings("unused")
	private static final int PAGESIZE = 20;
	private int totalCount;
	private int totalPage;
	private int pageSize;
	private int currentPage;
	private String order;
	private String orderFlag;
	private int goPage;
	private int splitFlag;
	private List<Integer> displayPage;
	@SuppressWarnings("rawtypes")
	private List items;
	private int start;
	private int end;
	private String[] distFields;

	public int getEnd() {
		return this.end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public int getStart() {
		return this.start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public PageInfo() {
		this.setCurrentPage(1);
		this.setPageSize(20);
		this.setDisplayPage(new ArrayList());
		this.setGoPage(this.getCurrentPage());
		this.setSplitFlag(0);
		this.setOrder((String) null);
		this.setOrderFlag((String) null);
	}

	public int getCurrentPage() {
		return this.currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage <= this.totalPage ? currentPage : this.totalPage;
		this.start = currentPage * this.pageSize - this.pageSize;
	}

	public String getOrder() {
		return this.order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getOrderFlag() {
		return this.orderFlag;
	}

	public void setOrderFlag(String orderFlag) {
		this.orderFlag = orderFlag;
	}

	public int getGoPage() {
		return this.goPage;
	}

	public void setGoPage(int goPage) {
		this.goPage = goPage;
	}

	public int getSplitFlag() {
		return this.splitFlag;
	}

	public void setSplitFlag(int splitFlag) {
		this.splitFlag = splitFlag;
	}

	public int getPageSize() {
		return this.pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
		this.start = this.currentPage * this.pageSize - this.pageSize;
	}

	public int getTotalPage() {
		return this.totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
		int startPage = this.currentPage - 5 < 1 ? 1 : this.currentPage - 5;

		for (int endPage = this.currentPage + 5 > this.totalPage
				? this.totalPage
				: this.currentPage + 5; startPage <= endPage; ++startPage) {
			this.displayPage.add(Integer.valueOf(startPage));
		}

	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		int iPage = totalCount / this.pageSize;
		if (totalCount % this.pageSize != 0) {
			++iPage;
		}

		this.setTotalPage(iPage);
	}

	public int getTotalCount() {
		return this.totalCount;
	}

	public List<Integer> getDisplayPage() {
		return this.displayPage;
	}

	public void setDisplayPage(List<Integer> displayPage) {
		this.displayPage = displayPage;
	}

	public String[] getDistFields() {
		return this.distFields;
	}

	public void setDistFields(String[] distFields) {
		this.distFields = distFields;
	}

	@SuppressWarnings("rawtypes")
	public List getItems() {
		return this.items;
	}

	@SuppressWarnings("rawtypes")
	public void setItems(List items) {
		this.items = items;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static PageInfo getInstance() {
		PageInfo pageInfo = new PageInfo();
		pageInfo.setCurrentPage(1);
		pageInfo.setPageSize(20);
		pageInfo.setDisplayPage(new ArrayList());
		pageInfo.setGoPage(pageInfo.getCurrentPage());
		pageInfo.setSplitFlag(0);
		pageInfo.setOrder((String) null);
		pageInfo.setOrderFlag((String) null);
		return pageInfo;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static PageInfo getInstance(Integer pageSize) {
		PageInfo pageInfo = new PageInfo();
		pageInfo.setCurrentPage(1);
		pageInfo.setPageSize(pageSize.intValue());
		pageInfo.setDisplayPage(new ArrayList());
		pageInfo.setGoPage(pageInfo.getCurrentPage());
		pageInfo.setSplitFlag(0);
		pageInfo.setOrder((String) null);
		pageInfo.setOrderFlag((String) null);
		return pageInfo;
	}
}