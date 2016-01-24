
<%
	session.setAttribute("userLogin", null);
	session.invalidate();
	response.sendRedirect("login.jsp");
%>
