package cn.com.cs.system.pojo.base.role;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import cn.com.cs.system.pojo.base.user.SystemUser;

@Entity
@Table(name = "sys_role_user")
public class SystemRoleUser implements Serializable {

	private static final long serialVersionUID = 1L;
  
	private String id;

	private SystemUser user;

	private SystemRole role;

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

	@ManyToOne(cascade = CascadeType.ALL, targetEntity = SystemUser.class)
	@JoinColumn(name = "userId")
	public SystemUser getUser() {
		return user;
	}

	public void setUser(SystemUser User) {
		this.user = User;
	}

	@ManyToOne(cascade = CascadeType.ALL, targetEntity = SystemRole.class)
	@JoinColumn(name = "roleId")
	public SystemRole getRole() {
		return role;
	}

	public void setRole(SystemRole role) {
		this.role = role;
	}

}
