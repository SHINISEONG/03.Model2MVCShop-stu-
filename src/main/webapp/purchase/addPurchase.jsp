<%@ page contentType="text/html; charset=euc-kr"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>Insert title here</title>
</head>

<body>

	<form name="addPurchase" action="/updatePurchaseView.do?tranNo=0"
		method="post">

		������ ���� ���Ű� �Ǿ����ϴ�.

		<table border=1>
			<tr>
				<td>��ǰ��ȣ</td>
				<td>${purchase.purchaseProd.prodNo}</td>
				<td></td>
			</tr>
			<tr>
				<td>�����ھ��̵�</td>
				<td>${purchase.buyer.userId}</td>
				<td></td>
			</tr>
			<tr>
				<td>���Ź��</td>
				<td><c:choose>
						<c:when test="${purchase.paymentOption eq '1' }">
					���ݱ���
				</c:when>

						<c:when test="${purchase.paymentOption eq '2' }">
					�ſ뱸��
				</c:when>
					</c:choose></td>
				<td></td>
			</tr>
			<tr>
				<td>�������̸�</td>
				<td>${purchase.receiverName}</td>
				<td></td>
			</tr>
			<tr>
				<td>�����ڿ���ó</td>
				<td>${purchase.receiverPhone}</td>
				<td></td>
			</tr>
			<tr>
				<td>�������ּ�</td>
				<td>${purchase.divyAddr}</td>
				<td></td>
			</tr>
			<tr>
				<td>���ſ�û����</td>
				<td>${purchase.divyRequest}</td>
				<td></td>
			</tr>
			<tr>
				<td>����������</td>
				<td>${purchase.divyDate}</td>
				<td></td>
			</tr>
		</table>
	</form>

</body>
</html>