package cn.com.cs.system.pojo.base.menu;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sys_role_menu")
public class SystemRoleMenu implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	String menuId;

	String roleId;

	@Id
	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	@Id
	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		
		if (obj instanceof SystemRoleMenu) {
			SystemRoleMenu pk = (SystemRoleMenu) obj;
			if (this.menuId.equals(pk.getMenuId()) && this.getRoleId().equals(pk.getRoleId())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
