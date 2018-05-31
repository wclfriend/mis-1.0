package cn.com.cs.wx.user.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.cs.common.service.BaseServiceImpl;
import cn.com.cs.wx.user.pojo.WechatUniqueId;

@Service("wechatUniqueIdService")
@Transactional
public class WechatUniqueIdServiceImpl extends BaseServiceImpl implements WechatUniqueIdService {

	@Override
	public WechatUniqueId getWechatUniqueId(String id) {
		List<WechatUniqueId> list = super.findByProperty(WechatUniqueId.class, "uniqueId", id);
		
		return list.isEmpty() ? null : list.get(0);
	}

	@Override
	public String getUniqueId(String idType) {
		String result = null;
		String sql = "select UNIQUE_ID, ID from wx_uniqueId where id_type = ? and active = 'N' limit 1";
		
		List<Map<String, Object>> list = super.findForJdbc(sql, idType);
		if (!list.isEmpty()) {
			super.executeSql("update wx_uniqueId set active = 'Y' where id = ? ", list.get(0).get("id").toString());
			
			return list.get(0).get("UNIQUE_ID").toString();
		}
		
		return result;
	}
}