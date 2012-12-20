<%@page import="com.spshop.utils.Constants"%>
<%@ include file="../include.jsp"%>
<div class="user_menu" id="user_menu">
	<ul>
		<li>Help
			<div
				style="width: 150px; display: none; cursor: default; padding-top: 10px;"
				class="user_menu_hidd currency ">
				<!-- <a
					href="#"
					rel="nofollow" title="Submit a question">Submit a question </a> --> <a
					href="http://joybuy.co.uk/uc/helpCenter"
					rel="nofollow" title="Help Center">Help Center</a>
			</div>
		</li>
		<li class="icon_${currency}" id="currency">
			<a href="javascript:window.location=window.location.toString().split('?')[0]+'?currency=${currency }'" rel="nofollow" title="${currency }"
				>
				Currency(${currency })
			</a>
			<dl style="width: 150px;cursor: default;display: none;"
				class="user_menu_hidd">
					<c:forEach items="${currencies }" var="c">
							<dd currency="${c.key }"  class="current_language ">
								<a href="javascript:window.location=window.location.toString().split('?')[0]+'?currency=${c.key }'" rel="nofollow" title="${c.key }" class="${c.key }">${cd[c.key] }</a>
							</dd>
				</c:forEach>
			</dl>
		</li>
		<li>User Center
				<dl class="user_menu_hidd" style="width: 200px;display: none;padding: 5px;">
					<dd>
						<a class="menuNav" href="/uc/changePwd">Change Password</a>
					</dd>
					<dd>
						<a class="menuNav" href="/uc/userProfile">My Profile</a>
					</dd>
					<dd>
						<a class="menuNav" href="/uc/myOrder">My Order</a>
					</dd>
					<dd>
						<a class="menuNav" href="/uc/feedback">My Message</a>
					</dd>
				</dl>
		</li>
		<script type="text/javascript">
		if(""!="${pageForm.pageProperties.loginError}"){
			alert("Wrong User ID or Password!");
		}
		</script>
		<c:if test="${sessionScope.userInfo eq null}">
			<li id="base_unlogin_li" class="">Login or Register
				<div style="width: 200px; cursor: default; display: none;"
					class="user_menu_hidd">
					<form method="post" action="/uc/login">
						<div class="border_bot indexlogin">
							<dl>
								<dd>
									Username<br> <input type="text" autocomplete="off"
										name="LoginEmail" id="loginEmail" class="loginuserpass">
								</dd>
								<dd>
									Password<br> <input type="password"
										name="LoginPwd" class="loginuserpass">
								</dd>
								<dd class="Question_mark">
									Forgot your password?<br> <a
										href="/uc/recoverPwd"
										class="underline" rel="nofollow">Click here</a>
								</dd>
								<dd>
									<input type="hidden" name="<%=Constants.ACTION %>" value="<%=Constants.LOGIN_ACTION %>">
									<input type="submit" value="Login"
										class="button_01"><span id="warn_login_span"></span>
								</dd>
							</dl>
						</div>
					</form>
					<div class="indexlogin">
						<p class="lineheight15 font_size11">Register Now for $5 in joybuy.co.uk!</p>
						<div class="hei5"></div>
						<input type="button" value="CREATE AN ACCOUNT" class="button_01"
							onclick="window.location.href='/uc/createAccount';">
					</div>
				</div>
			</li>
		</c:if>
		<c:if test="${sessionScope.userInfo != null}">
			<li id="hide_login_li">
				<span id="hide_login_span">
					${sessionScope.userInfo.email}
				</span>.
				(<a href="/uc/logout" title="Sign out">Sign out</a>)
			</li>
		</c:if>
	</ul>
</div>
