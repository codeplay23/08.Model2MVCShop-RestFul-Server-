<%--<%@page import="com.model2.mvc.service.domain.Product"%>
<%@page import="java.util.List"%>
<%@page import="com.model2.mvc.service.domain.User"%>
<%@page import="com.model2.mvc.common.Search"%>
<%@page import="com.model2.mvc.common.Page"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.model2.mvc.common.util.CommonUtil" %>--%>
<%@page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%--
<%
	List<Product> list= (List<Product>)request.getAttribute("list");
	Page resultPage=(Page)request.getAttribute("resultPage");
	
	Search search = (Search)request.getAttribute("search");
	
	
	
	//==> null 을 ""(nullString)으로 변경
	String searchCondition = CommonUtil.null2str(search.getSearchCondition());
	String searchKeyword = CommonUtil.null2str(search.getSearchKeyword());




	String menu=null;
	if(request.getParameter("menu").equals("search")){
	menu = "search";
	}else{
    menu ="manage";
	}
%>--%>

<html>
<head>
<title>상품목록 조회</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">

	// 검색 / page 두가지 경우 모두 Form 전송을 위해 JavaScrpt 이용  
	function fncGetList(currentPage) {
		document.getElementById("currentPage").value = currentPage;
	   	document.detailForm.submit();		
	}
</script>
</head>



<body bgcolor="#ffffff" text="#000000">

<div style="width:98%; margin-left:10px;">

<form name="detailForm" action="/product/listProduct?menu=${param.menu}" method="post">


<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37">
			<img src="/images/ct_ttl_img01.gif" width="15" height="37"/>
		</td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left:10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">
					
					${param.menu=="search" ? "상품목록조회" : "상품관리"}
					
					
					</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37">
			<img src="/images/ct_ttl_img03.gif" width="12" height="37"/>
		</td>
	</tr>
</table>


<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
			<td align="right">
			<select name="searchCondition" class="ct_input_g" style="width:80px">
			<%-- 
	         <%  <option value="0" <%= (searchCondition.equals("0") ? "selected" : "")%>>상품번호</option>
				<option value="1" <%= (searchCondition.equals("1") ? "selected" : "")%>>상품명</option>
				<option value="2"  <%=(searchCondition.equals("2") ? "selected" : "")%>>상품가격</option>
			%>
			--%>
			
			    <option value="0" ${! empty search.searchCondition && search.searchCondition=='0' ? "selected" : ""}>상품번호</option>
				<option value="1" ${! empty search.searchCondition && search.searchCondition=='1' ? "selected" : ""}>상품명</option>
				<option value="2" ${! empty search.searchCondition && search.searchCondition=='2' ? "selected" : ""}>상품가격</option>
			
		</select>
			<input 	type="text" name="searchKeyword" value="${search.searchKeyword}"  class="ct_input_g" 
							style="width:200px; height:20px" >
		
		</td>
		
			
			
		<td align="right" width="70">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17" height="23">
						<img src="/images/ct_btnbg01.gif" width="17" height="23">
					</td>
					<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">
						<a href="javascript:fncGetList('1');">검색</a>
							
					</td> 
					<td width="14" height="23">
						<img src="/images/ct_btnbg03.gif" width="14" height="23">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>


<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td colspan="11" >전체 ${resultPage.totalCount}건수, 현재 ${resultPage.currentPage} 페이지</td>
		<!-- total page --> <!-- current page -->
	</tr>
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">상품명</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">가격</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">등록일</td>	
		<td class="ct_line02"></td>
		<td class="ct_list_b">현재상태</td>	

	
</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>
	<c:set var="i" value="0" />
	<c:forEach var="product" items="${list}">
		<c:set var="i" value="${ i+1 }" />
		<tr class="ct_list_pop">
			<td align="center">${ i }</td>
			<td></td>
			<td align="left">
			 	<c:choose>
					<c:when test="${param.menu == 'manage' }">
					
						<a href="/product/updateProduct?prodNo=${product.prodNo}">${product.prodName}</a>
				<input type="hidden" id="prodNo" name="prodNo" value="${product.prodNo }"/>
						
					</c:when>
					<c:when test="${param.menu == 'search' }"> 
						<a href="/product/getProduct?prodNo=${product.prodNo }">${product.prodName}</a> 
					<input type="hidden" id="prodNo" name="prodNo" value="${product.prodNo }"/>
					
			</c:when>
						<c:otherwise>
					${product.prodName}
					</c:otherwise>
				</c:choose> 
			</td>
			<td></td>
			<td align="left">${product.price}</td>
			<td></td>
			<td align="left">${product.regDate}</td>
			<td></td>
			<td align="left">
			
				<c:if test="${param.menu == 'manage'}">
				<c:choose>
					<c:when test="${product.proTranCode == '1'}">
						<input type="hidden" id="prodNo" name="prodNo" value="${ product.prodNo }"/>						
						<input type="hidden" id="proTranCode" name="proTranCode" value="${product.proTranCode }"/>						

						구매 완료  <a href="/purchase/updateTranCodeByProd?prodNo=${product.prodNo}&tranCode=${product.proTranCode}">배송하기</a> 
					</c:when>
					<c:when test="${product.proTranCode == '2'}">
						배송중
					</c:when>
					<c:when test="${product.proTranCode == '3'}">
						배송 완료
					</c:when>
					<c:otherwise>
						판매중
					</c:otherwise>
				</c:choose>
				</c:if>
				<c:if test="${param.menu == 'search'}">
				<c:choose>
					<c:when test="${!empty product.proTranCode}">
						판매중
					</c:when>
					<c:otherwise>
						재고 없음
					</c:otherwise>
				</c:choose>
				</c:if>	
		</td>	
	</tr>
	<tr>
		<td colspan="11" bgcolor="D6D7D6" height="1"></td>
	</tr>	
	</c:forEach>
	
</table>





<!-- PageNavigation Start... -->
<table width="100%" border="0" cellspacing="0" cellpadding="0"	style="margin-top:10px;">
	<tr>
		<td align="center">
		   <input type="hidden" id="currentPage" name="currentPage" value=""/>
			<jsp:include page="../common/pageNavigator.jsp"/>	
			</td>
			</tr>
			</table>
<!-- page  navigator 끝 -->
</form>

</div>

</body>
</html>