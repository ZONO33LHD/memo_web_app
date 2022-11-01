<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="constants.ForwardConst" %>
<%@ page import="constants.AttributeConst" %>

<c:set var="actMem" value="${ForwardConst.ACT_MEM.getValue()}" />
<c:set var="commUpd" value="${ForwardConst.CMD_UPDATE.getValue()}" />




<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">

        <h2>メモ 編集ページ</h2>
        <form method="POST" action="<c:url value='?action=${actMem}&command=${commUpd}' />">
            <c:import url="_form.jsp" />
        </form>



        <p>
            <a href="<c:url value='?action=Memo&command=index' />">一覧に戻る</a>
        </p>



    </c:param>
</c:import>