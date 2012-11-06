<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Sign in</title>
<style type="text/css">
.submitbox .submitcancel {
	color: #21759B;
	border-bottom-color: #21759B;
}

.submitbox .submitcancel:hover {
	background: #21759B;
	color: #fff;
}

.fullscreen-overlay {
	background: #fff;
}

.wp-fullscreen-focus #wp-fullscreen-title,.wp-fullscreen-focus #wp-fullscreen-container
	{
	border-color: #ccc;
}

#fullscreen-topbar {
	border-bottom-color: #DFDFDF;
}

* {
	margin: 0;
	padding: 0;
}

html {
	background: #fbfbfb !important;
}

body {
	padding-top: 30px;
	font-family: sans-serif;
	font-size: 12px;
}

form {
	margin: 100px auto;
	padding: 26px 24px 46px;
	font-weight: normal;
	-moz-border-radius: 3px;
	-khtml-border-radius: 3px;
	-webkit-border-radius: 3px;
	border-radius: 3px;
	background: #fff;
	border: 1px solid #e5e5e5;
	-moz-box-shadow: rgba(200, 200, 200, 0.7) 0 4px 10px -1px;
	-webkit-box-shadow: rgba(200, 200, 200, 0.7) 0 4px 10px -1px;
	-khtml-box-shadow: rgba(200, 200, 200, 0.7) 0 4px 10px -1px;
	box-shadow: rgba(200, 200, 200, 0.7) 0 4px 10px -1px;
	width: 400px;
}

form .forgetmenot {
	font-weight: normal;
	float: left;
	margin-bottom: 0;
}

.button-primary {
	font-family: sans-serif;
	padding: 3px 10px;
	border: none;
	font-size: 13px;
	border-width: 1px;
	border-style: solid;
	-moz-border-radius: 11px;
	-khtml-border-radius: 11px;
	-webkit-border-radius: 11px;
	border-radius: 11px;
	cursor: pointer;
	text-decoration: none;
	margin-top: -3px;
}

#login form p {
	margin-bottom: 0;
}

label {
	color: #777;
	font-size: 14px;
}

form .forgetmenot label {
	font-size: 12px;
	line-height: 19px;
}

form .submit,.alignright {
	float: right;
}

form p {
	margin-bottom: 24px;
}

#login {
	width: 320px;
	margin: 7em auto;
}

#login_error,.message {
	margin: 0 0 16px 8px;
	border-width: 1px;
	border-style: solid;
	padding: 12px;
	-moz-border-radius: 3px;
	-khtml-border-radius: 3px;
	-webkit-border-radius: 3px;
	border-radius: 3px;
}

#nav,#backtoblog {
	text-shadow: rgba(255, 255, 255, 1) 0 1px 0;
	margin: 0 0 0 16px;
	padding: 16px 16px 0;
}

#backtoblog {
	padding: 12px 16px 0;
}

body form .input {
	font-family: "HelveticaNeue-Light", "Helvetica Neue Light",
		"Helvetica Neue", sans-serif;
	font-weight: 200;
	font-size: 24px;
	width: 97%;
	padding: 3px;
	margin-top: 2px;
	margin-right: 6px;
	margin-bottom: 16px;
	border: 1px solid #e5e5e5;
	background: #fbfbfb;
	outline: none;
	-moz-box-shadow: inset 1px 1px 2px rgba(200, 200, 200, 0.2);
	-webkit-box-shadow: inset 1px 1px 2px rgba(200, 200, 200, 0.2);
	box-shadow: inset 1px 1px 2px rgba(200, 200, 200, 0.2);
}

input {
	color: #555;
}

.clear {
	clear: both;
}

#pass-strength-result {
	font-weight: bold;
	border-style: solid;
	border-width: 1px;
	margin: 12px 0 6px;
	padding: 6px 5px;
	text-align: center;
}
/* Sabre CSS for login form */
#sabre_spectre {
	display: none;
}

a:link#sabre_link,a:hover#sabre_link,a:visited#sabre_link {
	color: #69c;
}

#user_pwd1,#user_pwd2,#captcha,#math,#letter,#invite_code {
	font-size: 24px;
	width: 97%;
	padding: 3px;
	margin-top: 2px;
	margin-right: 6px;
	margin-bottom: 16px;
	border: 1px solid #e5e5e5;
	background: #fbfbfb;
}

#sabre_banner,#sabre_pwd {
	padding: 3px;
	margin-top: 2px;
	margin-right: 6px;
	margin-bottom: 16px;
}
</style>
</head>
<body>
	<form name="loginform" id="loginform"
		action="admin" method="post">
		<p>
			<label>Username<br> <input type="text"
				id="username" name="username" class="input" value="" size="20" tabindex="10">
			</label>
		</p>
		<p>
			<label>Password<br> <input type="password"
				id="pwd" name="pwd" class="input" value="" size="20" tabindex="20">
			</label>
		</p>
		<!-- <p class="forgetmenot">
			<label><input name="rememberme" type="checkbox"
				id="rememberme" value="forever" tabindex="90"> Remember Me</label>
		</p> -->
		<p class="submit">
			<input type="submit" name="wp-submit" id="wp-submit"
				class="button-primary" value="Log In" tabindex="100"><input
				type="hidden" name="debug"
				value="true"> <!--<input
				type="hidden" name="testcookie" value="1"> -->
		</p>
	</form>

</body>
</html>