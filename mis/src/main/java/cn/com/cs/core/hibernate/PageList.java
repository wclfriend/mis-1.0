package cn.com.cs.core.hibernate;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@XmlRootElement
public class PageList {
	public void setCount(int count) {
		this.count = count;
	}

	private int curPageNO;
	private int offset;
	private String toolBar;// 分页工具条
	private int count;
	private List result = null;// 结果集

	public PageList() {

	}

	/**
	 * 不使用分页标签的初始化构造方法
	 * 
	 * @param result
	 * @param toolBar
	 * @param offset
	 * @param curPageNO
	 * @param count
	 */
	public PageList(List result, String toolBar, int offset, int curPageNO, int count) {
		this.curPageNO = curPageNO;
		this.offset = offset;
		this.toolBar = toolBar;
		this.result = result;
		this.count = count;
	}

	/**
	 * 使用分页标签的初始化构造方法
	 * 
	 * @param result
	 * @param toolBar
	 * @param offset
	 * @param curPageNO
	 * @param count
	 */
	public PageList(CriteriaQuery cq, List result, int offset, int curPageNO, int count) {
		this.curPageNO = curPageNO;
		this.offset = offset;
		this.result = result;
		this.count = count;
	}

	public PageList(HqlQuery cq, List result, int offset, int curPageNO, int count) {
		this.curPageNO = curPageNO;
		this.offset = offset;
		this.result = result;
		this.count = count;
	}

	public List getResult() {
		return result;
	}

	public void setResult(List result) {
		this.result = result;
	}

	public String getToolBar() {
		return toolBar;
	}

	public int getCount() {
		return count;
	}

	public int getCurPageNO() {
		return curPageNO;
	}

	public void setCurPageNO(int curPageNO) {
		this.curPageNO = curPageNO;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}
	

}
