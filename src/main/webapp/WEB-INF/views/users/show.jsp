<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="constants.ForwardConst" %>
<%@ page import="constants.AttributeConst" %>

<c:set var="actUsr" value="${ForwardConst.ACT_USR.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commEdit" value="${ForwardConst.CMD_EDIT.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">

        <h2>id : ${user.id} のユーザー情報 詳細ページ</h2>

        <table>
            <tbody>
                <tr>
                    <th>ユーザー番号</th>
                    <td><c:out value="${user.code}" /></td>
                </tr>


                <tr>
                    <th>登録日時</th>
                    <fmt:parseDate value="${user.createdAt}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="createDay" type="date" />
                    <td><fmt:formatDate value="${createDay}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                </tr>
                <tr>
                    <th>更新日時</th>
                    <fmt:parseDate value="${user.updatedAt}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="updateDay" type="date" />
                    <td><fmt:formatDate value="${updateDay}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                </tr>
            </tbody>
        </table>

        <p>
            <a href="<c:url value='?action=${actUsr}&command=${commEdit}&id=${user.id}' />">このユーザー情報を編集する</a>
        </p>

        <p>
            <a href="<c:url value='?action=${actUsr}&command=${commIdx}' />">一覧に戻る</a>
        </p>
    </c:param>
</c:import>