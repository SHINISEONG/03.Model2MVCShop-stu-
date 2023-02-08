package com.model2.mvc.common;


public class Page {
	
	///Field
	private int currentPage;		// ����������
	private int totalCount;		// �� �Խù� ��
	private int pageUnit;			// �ϴ� ������ ��ȣ ȭ�鿡 �������� ��
	private int pageSize;			// �� �������� �������� �Խù���
	private int maxPage;			// �ִ� ������ ��ȣ(��ü ������)
	private int beginUnitPage;	//ȭ�鿡 �������� ������ ��ȣ�� �ּҼ�
	private int endUnitPage;	//ȭ�鿡 �������� ������ ��ȣ�� �ִ��
	
	///Constructor
	public Page() {
	}
	
	public Page( int currentPage, int totalCount,	int pageUnit, int pageSize ) {
				//		1				14					2			3
		this.totalCount = totalCount;
		this.pageUnit = pageUnit;
		this.pageSize = pageSize;
		
		this.maxPage = (pageSize == 0) ? totalCount :  (totalCount-1)/pageSize +1;
			// 13				3				true		false 13/3 13
		this.currentPage = ( currentPage > maxPage) ? maxPage : currentPage;
				//1				1	> 13							false 1
		this.beginUnitPage = ( (currentPage-1) / pageUnit ) * pageUnit +1 ;
				//1				//		0					* 2 = 0 + 1 
		if( maxPage <= pageUnit ){
			// 13		2
			this.endUnitPage = maxPage;
					//	13		
		}else{
			this.endUnitPage = beginUnitPage + (pageUnit -1);
					// 2     =			1		+   1
			if( maxPage <= endUnitPage){
				//13 	<=    2
				this.endUnitPage = maxPage;
			}
		}
	}
	
	///Mehtod
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getPageUnit() {
		return pageUnit;
	}
	public void setPageUnit(int pageUnit) {
		this.pageUnit = pageUnit;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getMaxPage() {
		return maxPage;
	}
	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
	}
	public int getBeginUnitPage() {
		return beginUnitPage;
	}
	public void setBeginUnitPage(int beginUnitPage) {
		this.beginUnitPage = beginUnitPage;
	}
	public int getEndUnitPage() {
		return endUnitPage;
	}
	public void setEndUnitPage(int endUnitPage) {
		this.endUnitPage = endUnitPage;
	}
	@Override
	public String toString() {
		return "Page [currentPage=" + currentPage + ", totalCount="
				+ totalCount + ", pageUnit=" + pageUnit + ", pageSize="
				+ pageSize + ", maxPage=" + maxPage + ", beginUnitPage="
				+ beginUnitPage + ", endUnitPage=" + endUnitPage + "]";
	}
}