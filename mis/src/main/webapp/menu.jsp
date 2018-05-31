<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="security"  
    uri="http://www.springframework.org/security/tags"%>  
<%@ include file="/common/taglibs.jsp"%>
<aside class="Hui-aside">
		<c:forEach items="${menus }" var="menu">
			<div class="menu_dropdown bk_2">
				<dl id="menu-article">
				<dt><i class="Hui-iconfont">&#xe616;</i> ${menu.key.menuName }<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
					<dd>
						<ul>
							<li>
								<c:forEach items="${menu.value }" var="m">
									<a data-href="${ctx }${m.url}" data-title="${m.menuName }" href="javascript:void(0)">${m.menuName }</a>
								</c:forEach>
							</li>
						</ul>
					</dd>
				</dl>
			</div>
		</c:forEach>
<!-- 		<div class="menu_dropdown bk_2"> -->
<!-- 			<dl id="menu-article"> -->
<!-- 			<dt><i class="Hui-iconfont">&#xe616;</i>ssss<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt> -->
<!-- 				<dd> -->
<!-- 					<ul> -->
<!-- 						<li> -->
<!-- 							<a data-href="http://xtmall-dat.sinatay.com/wechat/qmeb/detail.do?state=H5" data-title="123" href="javascript:void(0)">sdf</a> -->
<!-- 						</li> -->
<!-- 					</ul> -->
<!-- 				</dd> -->
<!-- 			</dl> -->
<!-- 		</div> -->
</aside>