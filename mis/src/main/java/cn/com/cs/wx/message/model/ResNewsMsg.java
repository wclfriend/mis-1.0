package cn.com.cs.wx.message.model;

import java.util.List;

public class ResNewsMsg extends ResBaseMsg {

	/**
	 * 图文消息个数，限制为10条以内
	 */
	private String ArticleCount;

	/**
	 * 多条图文消息信息，默认第一个item为大图,注意，如果图文数超过10，则将会无响应
	 */
	private List<ResArticle> Articles;
	
	public String getArticleCount() {
		return ArticleCount;
	}

	public void setArticleCount(String articleCount) {
		this.ArticleCount = articleCount;
	}

	public List<ResArticle> getArticles() {
		return Articles;
	}

	public void setArticles(List<ResArticle> articles) {
		this.Articles = articles;
	}

	

	

}
