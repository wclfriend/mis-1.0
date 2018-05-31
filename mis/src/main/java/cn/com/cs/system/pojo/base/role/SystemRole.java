package cn.com.cs.system.pojo.base.role;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;

import cn.com.cs.common.pojo.IdEntity;
import cn.com.cs.system.pojo.base.menu.SystemMenu;

@Entity
@Table(name = "sys_role")
public class SystemRole extends IdEntity implements Serializable {
	
	private String id;
	
	private int version;

	private static final long serialVersionUID = 1L;

	private String roleName;

	private String roleCode;
	
	private Set<SystemMenu> menus = new HashSet<SystemMenu>();
	
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	
	@ManyToMany
    @JoinTable(name="sys_role_menu",
            joinColumns={@JoinColumn(name="roleId")},
            inverseJoinColumns={@JoinColumn(name="menuId")})
	public Set<SystemMenu> getMenus() {
		return menus;
	}
	
	public void setMenus(Set<SystemMenu> menus) {
		this.menus = menus;
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
