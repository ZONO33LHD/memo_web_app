<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="constants.ForwardConst" %>
<%@ page import="constants.AttributeConst" %>

<c:set var="actMem" value="${ForwardConst.ACT_MEM.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commEdt" value="${ForwardConst.CMD_EDIT.getValue()}" />
<c:set var="commDel" value="${ForwardConst.CMD_DESTROY.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">

        <h2>メモ 詳細ページ</h2>

        <table>
            <tbody>

                <tr>
                    <th>日付</th>
                    <fmt:parseDate value="${memo.reportDate}" pattern="yyyy-MM-dd" var="reportDay" type="date" />
                    <td><fmt:formatDate value='${reportDay}' pattern='yyyy-MM-dd' /></td>
                </tr>
                <tr>
                    <th>内容</th>
                    <td><pre><c:out value="${memo.content}" /></pre></td>
                </tr>
                <tr>
                    <th>登録日時</th>
                    <fmt:parseDate value="${memo.createdAt}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="createDay" type="date" />
                    <td><fmt:formatDate value="${createDay}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                </tr>
                <tr>
                    <th>更新日時</th>
                    <fmt:parseDate value="${memo.updatedAt}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="updateDay" type="date" />
                    <td><fmt:formatDate value="${updateDay}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                </tr>
            </tbody>
        </table>

        <c:if test="${sessionScope.login_user.id == memo.user.id}">
            <p>
                <a href="<c:url value='?action=${actMem}&command=${commEdt}&id=${memo.id}' />">このメモを編集する</a>
            </p>

        </c:if>

        <c:if test="${sessionScope.login_user.id == memo.user.id}">
            <p>
                <a href="#" onclick="confirmDestroy();">このメモ情報を削除する</a>
            </p>
            <form method="POST" name="delForm"
                action="<c:url value='?action=${actMem}&command=${commDel}' />">
                <input type="hidden" name="id"
                    value="${memo.id}" />
                <input type="hidden" name="${AttributeConst.TOKEN.getValue()}"
                    value="${_token}" />
            </form>

            <script>
            function confirmDestroy() {
                if (confirm("本当に削除してよろしいですか？")) {
                    document.forms.delForm.submit();
                }
            }
            </script>
        </c:if>


        <p>
            <a href="<c:url value='?action=${actMem}&command=${commIdx}' />">一覧に戻る</a>
        </p>
    </c:param>
</c:import>