<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="constants.AttributeConst" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="action" value="${ForwardConst.ACT_USR.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />

<c:if test="${errors != null}">
    <div id="flush_error">
        入力内容にエラーがあります。<br />
        <c:forEach var="error" items="${errors}">
            ・<c:out value="${error}" /><br />
        </c:forEach>

    </div>
</c:if>
<label for="${AttributeConst.USR_CODE.getValue()}">ユーザー番号</label><br />
<input type="text" name="${AttributeConst.USR_CODE.getValue()}" id="${AttributeConst.USR_CODE.getValue()}" value="${user.code}" />
<br /><br />

<label for="${AttributeConst.USR_NAME.getValue()}">氏名</label><br />
<input type="text" name="${AttributeConst.USR_NAME.getValue()}" id="${AttributeConst.USR_NAME.getValue()}" value="${user.name}" />
<br /><br />

<label for="${AttributeConst.USR_PASS.getValue()}">パスワード</label><br />
<input type="password" name="${AttributeConst.USR_PASS.getValue()}" id="${AttributeConst.USR_PASS.getValue()}" />
<br /><br />

<input type="hidden" name="${AttributeConst.USR_ID.getValue()}" value="${user.id}" />
<input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
<button type="submit">新規登録</button>