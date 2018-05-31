package cn.com.cs.system.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.cs.common.json.AjaxJson;
import cn.com.cs.common.pojo.BasicType;
import cn.com.cs.common.pojo.BasicTypeGroup;
import cn.com.cs.common.service.BaseServiceImpl;

@Service
@Transactional
public class BasicCodeServiceSpringImpl extends BaseServiceImpl implements BasicCodeService {

	@Override
	public AjaxJson addGroup(String code, String codeName) {
		AjaxJson json = new AjaxJson();
		if (StringUtils.isBlank(code) || StringUtils.isBlank(codeName)) {
			json.setSuccess(false);
			return json;
		}
		try {
			BasicTypeGroup group = new BasicTypeGroup();
			group.setCode(code);
			group.setCodeName(codeName);
			
			super.save(group);
		} catch (Exception e) {
			e.printStackTrace();
			json.setSuccess(false);
			return json;
		}
		
		return json;
	}

	@Override
	public AjaxJson del(HttpServletRequest request) {
		AjaxJson json = new AjaxJson();
		try {
			String[] ids = request.getParameter("id").split(",");
			BasicType type = null;
			BasicTypeGroup group = null;
			for (String id : ids) {
				type = get(BasicType.class, id);
				if (type != null) {
					delete(type);
				}
				group = get(BasicTypeGroup.class, id);
				if (group != null) {
					delete(group);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.setSuccess(false);
			return json;
		}
		
		return json;
	}

	@Override
	public AjaxJson addType(String code, String codeName) {
		AjaxJson json = new AjaxJson();
		if (StringUtils.isBlank(code) || StringUtils.isBlank(codeName)) {
			json.setSuccess(false);
			return json;
		}
		try {
			BasicType type = new BasicType();
			type.setCode(code);
			type.setCodeName(codeName);
			
			super.save(type);
		} catch (Exception e) {
			e.printStackTrace();
			json.setSuccess(false);
			return json;
		}
		
		return json;
	}

	@Override
	public List<BasicTypeGroup> list() {
		String hql = "from " + BasicTypeGroup.class.getSimpleName() + " order by createTime desc";
		
		List<BasicTypeGroup> list = queryByHql(hql);
		
		return list;
	}

	@Override
	@Transactional(transactionManager="transactionManager")
	public AjaxJson update(HttpServletRequest request) {
		AjaxJson json = new AjaxJson();
		
		String id = request.getParameter("id");
		String code = request.getParameter("code");
		String codeName = request.getParameter("codeName");
		String parentId = request.getParameter("parentId");
		
		if (StringUtils.isBlank(codeName) || StringUtils.isBlank(code)) {
			json.setSuccess(false);
			return json;
		}
		try {
			//新增或修改类型值
			if (StringUtils.isNotBlank(parentId)) {
				BasicType type = super.get(BasicType.class, id);
				if (type == null) {
					type = new BasicType();
				}
				type.setCode(code);
				type.setCodeName(codeName);
				
				BasicTypeGroup group = get(BasicTypeGroup.class, parentId);
				type.setTypeGroup(group);
				
				try {
					saveOrUpdate(type);
				} catch (Exception e) {
					e.printStackTrace();
					
					json.setSuccess(false);
					json.setMsg("基础数据重复");
					return json;
				}
				return json;
			} else {
				BasicTypeGroup group = get(BasicTypeGroup.class, id);
				if (group != null) {
					group.setCode(code);
					group.setCodeName(codeName);
					
					update(group);
					return json;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		json.setSuccess(false);
		return json;
	}
	
}
