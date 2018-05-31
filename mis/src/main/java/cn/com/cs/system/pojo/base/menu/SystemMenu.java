package cn.com.cs.system.pojo.base.menu;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import cn.com.cs.common.pojo.IdEntity;

@Entity
@Table(name = "sys_menu")
@XmlRootElement
public class SystemMenu extends IdEntity {

	private static final long serialVersionUID = 1L;
	
	private String id;
	
	private int version;

	public String menuName;

	private String url;

	private String txt;
	
	private String level;

	private Integer sortOrder;

	private SystemMenu parentMenu;

	private List<SystemMenu> children = new ArrayList<SystemMenu>();

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTxt() {
		return txt;
	}

	public void setTxt(String txt) {
		this.txt = txt;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "parentId")
	@JsonIgnore
	public SystemMenu getParentMenu() {
		return parentMenu;
	}

	public void setParentMenu(SystemMenu parentMenu) {
		this.parentMenu = parentMenu;
	}
	
	@Transient
	public String getParentId() {
		if (parentMenu != null)
			return parentMenu.getId();
		
		return null;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parentMenu")
	@JsonIgnore
	public List<SystemMenu> getChildren() {
		return children;
	}

	public void setChildren(List<SystemMenu> children) {
		this.children = children;
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	@Override
	public String toString() {
		StringBuffer result = new StringBuffer();
		result.append(getMenuName()).append("@").append(getId());
		return result.toString();
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name = "ID", nullable = false, length = 32)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@Version
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
	
}
