<%-- 
    Document   : product_list
    Created on : Mar 12, 2013, 4:26:06 PM
    Author     : phitt60230
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html>
<html>
    <h1>Exiting Products</h1>
    <c:url var="getProductListUrl" value="/product/getProductList" />
	<table class="table">
		<tr>
			<th>Name</th>
			<th>Description</th>
			<th>Image</th>
		</tr>
		
		<c:forEach items="${product}" var="product">
		<tr>
			<td><c:out value="${product.name}" /></td>
			<td><c:out value="${product.description}" /></td>
			<td><c:out value="${event.image}" /></td>
                </tr>
                </c:forEach>
	</table>
        <c:if test="${!empty product}">
            No Product in list
        </c:if>
</html>
