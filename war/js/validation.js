		var lastUserName='';
		var lastpassword='';
		var lastemail='';
		var lastcode='';
		var UserNamePrint='Use 4-20 characters and start with an English letter. User ID can not be changed after your registration.';
		var passwordPrint='6-20 characters(Use English letters or numbers or signs), capitalization matters';
		var password2Print='Re-enter your password';
		var emailPrint='Caution!This is the only way to redeem or recover your password';
		var codePrint='Please enter the verify code, click the picture if the code is vague';
		var UserNamecg='Congratulation,the User ID is usable';
		var UserNamecw='Wrong User ID';
		var UserNameysy='The User ID has been occupied';
		var passwordcw1='No password or contain illegal character, please re-enter';
		var passwordcw2='Password in twice entered is different, please check and re-enter';
		var passwordcg='Entering correct';
		var emailcw='Wrong Email format, please re-enter';
		var emailysy='The Email has been occupied';
		var emailcg='Entering correct';
		var codecw='Wrong verify code, submitting failed, please recheck';
		var codecg='Entering correct';
		var blwk='This blank must be filled';
		var print_coon=new Array();
		var print_css=new Array();
		function  formsubmit(){	
			checkpassword();
			checkpassword2();
			checkemail();
			if(!$('Conditions').checked){
				$('Conditionsinfo').style.display='';
				$('Conditionsinfo').innerHTML='<img src=\"../css/unchecked.gif\"> Please select "agree to abide by rules of membership"';
				return false;
			}
			if(print_css['password']=='cg' && print_css['password2']=='cg' && print_css['email']=='cg' )
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		function checkConditions(){
		 	if(!$('Conditions').checked){
				$('Conditionsinfo').style.display='';
				$('Conditionsinfo').innerHTML='<img src=\"../css/unchecked.gif\"> Please select "agree to abide by rules of membership"';
				return false;
			}
			else {
				$('Conditionsinfo').style.display='none';
			}
		 }
		 function checkUserName() {
		 		$('UserName').className='dkinput';
				var UserName_coon = trim($('UserName').value);		
				if(UserName_coon == lastUserName & UserName_coon!='') {
					$('validateUserName').innerHTML= print_coon['UserName'];
					$('validateUserName').className=print_css['UserName'];
					return;
				} else {
					lastUserName = UserName_coon;
				}
				var unlen = UserName_coon.replace(/[^\x00-\xff]/g, "**").length;		
				if(unlen > 3 ) {
				ajaxyz('UserName', 'menu=username&name=' + (is_ie && document.charset == 'utf-8' ? encodeURIComponent(UserName_coon) : UserName_coon) + '&' + Math.random());
				}
				else if (unlen > 0 ){
				print_coon['UserName']='<img src=\"../css/unchecked.gif\"> '+UserNamecw;
				print_css['UserName']='cw';
				$('validateUserName').style.display = '';
				$('validateUserName').className='cw';
				$('validateUserName').innerHTML='<img src=\"../css/unchecked.gif\"> '+UserNamecw;
				}
				else {
				print_coon['UserName']='<img src=\"../css/unchecked.gif\"> '+blwk;
				print_css['UserName']='cw';
				$('validateUserName').style.display = '';
				$('validateUserName').className='cw';
				$('validateUserName').innerHTML='<img src=\"../css/unchecked.gif\"> '+blwk;
				}	
			}
		function checkpassword(confirm) {
		 		$('password').className='dkinput';
				var password_coon = $('password').value;		
				if(password_coon == lastpassword & password_coon!='') {
					$('validatePassword').innerHTML= print_coon['password'];
					$('validatePassword').className=print_css['password'];
					return;
				} else {
					lastpassword = password_coon;
				}
				if(password_coon == '' || /[\'\" \\]/.test(password_coon)) {
					print_css['password'] = 'cw';
					$('validatePassword').style.display = '';
					print_coon['password'] ='<img src=\"css/unchecked.gif\"> '+passwordcw1;
				} else {
					print_css['password'] = 'cg';
					$('validatePassword').style.display = 'block';
// print_coon['password'] ='<img src=\"css/cg.gif\"> '+passwordcg;
					print_coon['password'] = '<img src=\"../css/checked.gif\"> ';
					if(!confirm) {
						checkpassword2(true);
					}
				}
				$('validatePassword').className=print_css['password'];
				$('validatePassword').innerHTML = print_coon['password'];
		}
		function checkpassword2(confirm) {
				var password = $('password').value;
				var password2 = $('password2').value;
				$('password2').className='dkinput';
				if(password != '') {
					checkpassword(true);
				}
				if(password == '' || (confirm && password2 == '')) {
					print_css['password2'] = 'cw';
					$('password').style.display = '';
					print_coon['password2'] ='<img src=\"../css/unchecked.gif\"> '+passwordcw1;			
				}
				else
				{
					if(password != password2) {
						print_css['password2'] = 'cw';
						$('validatePassword2').style.display = '';
						print_coon['password2'] ='<img src=\"../css/unchecked.gif\"> '+passwordcw2;			
					} else {
						print_css['password2'] = 'cg';
						$('validatePassword2').style.display = 'block';
// print_coon['password2'] ='<img
// src=\"https://www.mlo.me/image/endefault/cg.gif\"> '+passwordcg;
						print_coon['password2'] = '<img src=\"../css/checked.gif\"> '
					}
				}
				$('validatePassword2').className=print_css['password2'];
				$('validatePassword2').innerHTML = print_coon['password2'];
			}
			
		
		function checkemail() {
		 		$('email').className='dkinput';
				var email_coon = trim($('email').value);		
				if(email_coon == lastemail & email_coon!='') {
					$('validateEmail').innerHTML= print_coon['email'];
					$('validateEmail').className=print_css['email'];
					return;
				} else {
					lastemail = email_coon;
				}
				var pattern = /^([a-zA-Z0-9_.-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/;
				chkFlag = pattern.test(email_coon);
				if(!chkFlag){
					$('validateEmail').style.display = '';
					$('validateEmail').innerHTML ='<img src=\"../css/unchecked.gif\"> '+emailcw;
					return;
				}else{
					print_css['email'] = 'cg';
					$('validateEmail').style.display = '';
				}
				var unlen = email_coon.replace(/[^\x00-\xff]/g, "**").length;		
				if(unlen > 0 ) {
					AjaxService.validateUserByEmail(email_coon, queryEmailCallback);
				}
			}
		function queryEmailCallback(emailExist){
			if(emailExist){
				print_css['email'] = 'cw';
			$('validateEmail').className='cw';
				$('validateEmail').style.display = '';
				print_coon['email'] ='<img src=\"../css/unchecked.gif\"> '+UserNameysy;
			}else{
				print_css['email'] = 'cg';
				$('validateEmail').style.display = '';
				print_coon['email'] = '<img src=\"../css/checked.gif\"> ' + UserNamecg;	
				$('validateEmail').className='cg';
			}
			$('validateEmail').className=print_css['email'];
			$('validateEmail').innerHTML = print_coon['email'];
		}