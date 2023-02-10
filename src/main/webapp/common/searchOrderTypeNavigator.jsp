<%@ page contentType="text/html; charset=euc-kr" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<c:choose>
		<c:when test = "${pageType eq 'user' }">
			<c:set var = "fncName" value =  "fncGetUserList"/>
		</c:when>
		
		<c:when test = "${pageType eq 'product' }">
			<c:set var = "fncName" value =  "fncGetProductList"/>
			<c:set var = "menu" value = ",'${param.menu}'"/>
		</c:when>
		
		<c:when test = "${pageType eq 'purchase'}">
			<c:set var = "fncName" value =  "fncGetPurchaseList"/>
		</c:when>
	</c:choose>	
		
	
		<td colspan="10" align="right">
			<c:if test="${pageType eq 'user' }">
				<a href="javascript:${fncName }('1', 'orderByUserIdASC'${menu })">
				ID순&nbsp;&nbsp;&nbsp;&nbsp;
				</a>
			</c:if>
			<a href="javascript:${fncName }('1', 'orderByDateDESC'${menu })">
			최근등록순&nbsp;&nbsp;&nbsp;&nbsp;
			</a>
			<a href="javascript:${fncName }('1', 'orderByDateASC'${menu })">
			오래된등록순&nbsp;&nbsp;&nbsp;&nbsp;
			</a>
			<c:if test="${pageType eq 'product' }">
				<a href="javascript:${fncName }('1', 'orderByPriceDESC'${menu })">
				가격높은순&nbsp;&nbsp;&nbsp;&nbsp;
				</a>
				<a href="javascript:${fncName }('1', 'orderByPriceASC'${menu })">
				가격낮은순&nbsp;&nbsp;
				</a>
			</c:if>
		</td>
	