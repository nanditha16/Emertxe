<%@ include file="../common/header.jspf"%>
<%@ include file="../common/navigation.jspf"%>


	<div class="container">
		<form action="/login.do" method="post">
			<p>
				<font color="red">${errorMessage}</font>
			</p>
			Name: <input type="text" name="name" /> Password:<input
				type="password" name="password" /> <input type="submit"
				value="Login" />
		</form>

	</div>

<%@ include file="../common/footer.jspf"%>