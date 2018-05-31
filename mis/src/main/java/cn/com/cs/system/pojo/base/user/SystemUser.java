package cn.com.cs.system.pojo.base.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import cn.com.cs.common.pojo.IdEntity;

/**
 * 系统用户表
 */
@Entity
@Table(name = "sys_user")
@Inheritance(strategy = InheritanceType.JOINED)
//public class SystemUser {
public class SystemUser extends IdEntity {
	
	public static final long serialVersionUID = 1L;

	private String id;
	
	private int version;

	private String userName;// 用户名
	
	private String realName;// 真实姓名
	
	private String password;// 用户密码
	
	private Short status;// 状态1：在线,2：离线,0：禁用
	
	private String headImage;//头像地址
	
	/**
	 * Y：正常，可用
	 * N：已锁定
	 */
	private boolean lockFlag;
	
	private boolean admin;
	
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

	@Column(name = "status")
	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	@Column(name = "password", length = 100)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "username", nullable = false, length = 10)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRealName() {
		return this.realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	@Type(type="yes_no")
	public boolean isLockFlag() {
		return lockFlag;
	}

	public void setLockFlag(boolean lock) {
		this.lockFlag = lock;
	}

	@Type(type="yes_no")
	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public String getHeadImage() {
		return headImage;
	}

	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}
	
	@Override
	public String toString() {
		StringBuffer result = new StringBuffer();
		result.append(this.getClass().getSimpleName()).append("@").append(getId());
		return result.toString();
	}

}