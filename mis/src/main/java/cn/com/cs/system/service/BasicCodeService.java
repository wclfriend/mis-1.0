package cn.com.cs.system.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.com.cs.common.json.AjaxJson;
import cn.com.cs.common.pojo.BasicTypeGroup;
import cn.com.cs.common.service.BaseService;

public interface BasicCodeService extends BaseService {

	AjaxJson addGroup(String code, String codeName);

	AjaxJson del(HttpServletRequest request);

	AjaxJson addType(String code, String codeName);

	List<BasicTypeGroup> list();

	AjaxJson update(HttpServletRequest request);

//	AjaxJson sssss(HttpServletRequest request);


}
