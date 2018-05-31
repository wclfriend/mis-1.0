package cn.com.cs.common.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;


@MappedSuperclass
public abstract class IdEntity  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
//	@Id
//	private String id;
//	
//	@Version
//	private int version;

	private Date createTime = new Date();

	private Date modifyTime = new Date();

	private String remark;

//	@Id
//	@GeneratedValue(generator = "paymentableGenerator")
//	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
//	@Column(name = "ID", nullable = false, length = 32)
//	public String getId() {
//		return id;
//	}
//
//	public void setId(String id) {
//		this.id = id;
//	}
//	
//	public int getVersion() {
//		return version;
//	}
//
//	public void setVersion(int version) {
//		this.version = version;
//	}
	
	@JsonFormat(pattern ="yyyy-MM-dd HH:mm")
	@JsonIgnore
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@JsonFormat(pattern ="yyyy-MM-dd HH:mm")
	@JsonIgnore
	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	@JsonIgnore
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
//	@Override
//	public String toString() {
//		StringBuffer result = new StringBuffer();
//		result.append(this.getClass().getSimpleName()).append("@").append(id);
//		return result.toString();
//	}

}
