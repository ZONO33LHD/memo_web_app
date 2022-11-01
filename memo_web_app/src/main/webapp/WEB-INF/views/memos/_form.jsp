<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="constants.AttributeConst" %>

<c:if test="${errors != null}">
    <div id="flush_error">
        入力内容にエラーがあります。<br />
        <c:forEach var="error" items="${errors}">
            ・<c:out value="${error}" /><br />
        </c:forEach>

    </div>
</c:if>
<fmt:parseDate value="${memo.reportDate}" pattern="yyyy-MM-dd" var="reportDay" type="date" />
<label for="${AttributeConst.MEM_DATE.getValue()}">日付</label><br />
<input type="date" name="${AttributeConst.MEM_DATE.getValue()}" id="${AttributeConst.MEM_DATE.getValue()}" value="<fmt:formatDate value='${reportDay}' pattern='yyyy-MM-dd' />" />
<br /><br />

<label>氏名</label><br />
<c:out value="${sessionScope.login_user.name}" />
<br /><br />

<label for="${AttributeConst.MEM_TITLE.getValue()}">タイトル</label><br />
<input type="text" name="${AttributeConst.MEM_TITLE.getValue()}" id="${AttributeConst.MEM_TITLE.getValue()}" value="${memo.title}" />
<br /><br />

<label for="${AttributeConst.MEM_CONTENT.getValue()}">内容</label><br />
<textarea  name="${AttributeConst.MEM_CONTENT.getValue()}" id="${AttributeConst.MEM_CONTENT.getValue()}" rows="10" cols="50">${memo.content}</textarea>
<br /><br />
<input type="hidden" name="${AttributeConst.MEM_ID.getValue()}" value="${memo.id}" />
<input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
<button type="submit">投稿</button>