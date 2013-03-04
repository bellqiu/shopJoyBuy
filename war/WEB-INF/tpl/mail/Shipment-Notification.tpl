<html>
<body>
<table width="780" cellspacing="0" cellpadding="0" border="0" bgcolor="#f2f3f4" align="center">
  <tbody>
  <tr>
    <td colspan="3">
      <table width="700" cellspacing="0" cellpadding="0" border="0" align="center">
        <tbody>
        <tr>
         <td> <img board="0" title="Joybuy.co.uk" alt="Joybuy.co.uk" 
src="http://joybuy.co.uk/image/2496802064581883.jpg"> </td>
          <td align="right" style="FONT-SIZE: 14pt">Shipment 
        Notification</td> </tr> </tbody> </table>
      <table width="700" cellspacing="0" cellpadding="0" border="0" bgcolor="#ffffff" align="center" style="BORDER-
BOTTOM: #c9c9ca 1px solid; BORDER-LEFT: #c9c9ca 1px solid; BORDER-TOP: #d4232b 1px solid; BORDER-RIGHT: #c9c9ca 1px 
solid">
        <tbody>
        <tr>
          <td colspan="2" style="border-bottom:1px solid #c9c9ca">
            <table width="700" height="55" cellspacing="0" cellpadding="0" border="0" align="center" style="BORDER-
BOTTOM: #c9c9ca 1px solid; FONT-SIZE: 12px">
              <tbody>
              <tr>
                <td width="22"> </td>
                <td align="left"> <strong>Order No.:</strong> ${order.name}</td>
                <td align="right">Ordered at <#if order.createDate??> ${order.createDate?string("yyyy-MM-dd HH:mm:ss 
zzzz")} </#if></td><td width="22"> </td> </tr> </tbody> </table>
            <table width="700" cellspacing="0" cellpadding="0" border="0" align="center" style="BORDER-BOTTOM: #c9c9ca 
1px solid; FONT-SIZE: 12px">
              <tbody>
              <tr valign="top">
                <td width="12"> </td>
                <td> <img src="http://cloud.lbox.me/images/wholesale/201009/n_car1283947100.jpg" style="PADDING-TOP: 
25px"> </td><td width="30"> </td>
                <td width="660">
                  <p style="LINE-HEIGHT: 17px; MARGIN: 26px 0px 5px; paading: 0"> <strong style="COLOR: #000">Dear <#if 
order.customerName??> ${order.customerName}</#if>,</strong></p>
                  <p style="LINE-HEIGHT: 17px; MARGIN: 5px 0px; paading: 0">We 
                  are pleased to inform you that one or more of your items has 
                  shipped. Please see the details of the shipment below. </p>
                  <p style="LINE-HEIGHT: 17px; MARGIN: 5px 0px 26px; paading: 0">If 
                  you ordered more than one item, they may ship separately. We 
                  will notify you via email each time a package is shipped until 
                  your order is complete.</p> </td>
                <td width="22"> </td> </tr> </tbody> </table> 
            <table width="700" cellspacing="0" cellpadding="0" border="0" align="center"> <tbody>
              <tr>
                <td height="15"> </td> </tr>
              <tr valign="top">
                <td width="340" style="LINE-HEIGHT: 18px; FONT-SIZE: 12px; BORDER-RIGHT: #dfdfe0 1px solid">
                  <table style="FONT-SIZE: 12px">
                    <tbody>
                    <tr valign="top">
                      <td width="15"> </td>
                      <td width="80" align="left"> <strong style="COLOR: #000">Ship 
                        to</strong> </td>
                      <td width="170" align="left">
					  				  
					          <#assign "address"= primary!order.primaryAddress >
        
		${address.fullName}
		<br>
        (${address.address1!''} ${address.city!''},
							${address.stateProvince!''},
							${siteView.countryMap[address.country?string].name}, Postal 

Code:
							${address.postalCode!''}) Phone:${address.phone!''}
					  </td> </tr>
                    <tr>
                      <td height="15"> </td> </tr> </tbody> </table> </td> 
                <td width="340" style="LINE-HEIGHT: 18px; FONT-SIZE: 12px">
                  <table style="FONT-SIZE: 12px">
                    <tbody>
                    <tr valign="top">
                      <td width="15"> </td>
                      <td width="80" align="left"> <strong style="COLOR: #000">Ship 
                        via</strong> </td>
                      <td width="170" align="left"><#if order.shippingMethod??>
			${order.shippingMethod}
		</#if></td> </tr>
                    <tr valign="top">
                    <td width="15"> </td>
                      <td width="80" align="left"> <strong style="COLOR: #000">Tracking</strong> </td>
						<td width="170" align="left">
                      <strong><a href="http://www.Joybuy.co.uk/uc/orderDetails?id=${order.name}">My Order</a></strong>
                    </td></tr> </tbody> </table> </td></tr>
              <tr>
                <td height="15"> </td> </tr> </tbody> </table>  
            <table width="100%" cellspacing="0" cellpadding="0" border="0" align="center" style="FONT-SIZE: 12px">
              <tbody>
              <tr>
                <td width="100%" height="26" bgcolor="#e7e7e7" colspan="6">
                  <table width="100%" cellspacing="0" cellpadding="0" border="0" style="COLOR: #ffffff; FONT-SIZE: 

12px">
                    <tbody>
                    <tr align="left">
                      <td width="22"> </td>
                      <td width="574" style="COLOR: #222222">Item(s) in Order</td>
                      <td width="10"> </td>
                      <td width="102" align="center" style="COLOR: #222222">Qty</td> </tr> </tbody> </table> </td>
                <td width="4"> </td> </tr>
				<#if order.items??>
				<#list order.items as item>
              <tr>
                <td width="100%" height="26" colspan="6" style="BORDER-BOTTOM: rgb(221,221,221) 1px dashed; LINE-

HEIGHT: 18px">
                  <table width="100%" cellspacing="0" cellpadding="0" border="0" style="FONT-SIZE: 12px">
                    <tbody>
                    <tr>
                      <td height="6"> </td> </tr>
                    <tr align="left">
                      <td width="22"> </td>
                      <td width="594"> <a href="http://www.Joybuy.co.uk/${item.product.name}" 

style="color:#1E4E8E;text-decoration:none;text-underline:none">${item.product.title}</a></td>
                      <td width="82" align="center">${item.quantity}</td> </tr>
                  </tbody> </table>
			   </td>
			   </tr>
			   </#list>
			   </#if>			   
            <table width="100%" cellspacing="0" cellpadding="0" border="0" align="center" style="BORDER-BOTTOM: #c9c9ca 

1px solid; FONT-SIZE: 12px; BORDER-TOP: #c9c9ca 1px solid">
              <tbody>
              <tr>
                <td height="10"> </td> </tr>
              <tr valign="top">
                <td style="PADDING-BOTTOM: 0px; PADDING-LEFT: 22px; PADDING-RIGHT: 22px; PADDING-TOP: 0px"> 

<strong>Please 
                  Note:</strong> <br>If you have chosen Standard Shipping, 
                  your package(s) will arrive in 5-8 business days from the 
                  time they are shipped, and you can enjoy the same reliable 
                  service at the lowest rate possible. </td> </tr>
              <tr>
                <td height="10"> </td> </tr> </tbody> </table>
            <table width="700" cellspacing="0" cellpadding="0" border="0" align="center" style="font-size: 12px">
              <tbody>
              <tr>
                <td height="15"> </td> </tr>
              <tr>
                <td> </td>
                <td colspan="2" style="COLOR: #444; FONT-SIZE: 12px"> <strong>Frequently Asked Questions</strong> </td> 

</tr>
              <tr>
                <td height="10"> </td> </tr>
              <tr valign="top">
                <td width="22"> </td>
                <td width="44%" style="text-align: left">
                  <p style="LINE-HEIGHT: 14px; MARGIN: 10px 0px"> <strong style="COLOR: #444">How do I track my 

package?</strong> </p>
                  <p style="line-height:14px; margin:10px 0px">All available tracking information has been provided 

above and in your <a 

href="http://www.Joybuy.co.uk/uc/orderDetails?id=${order.name}" style="COLOR: #1e4e8e; TEXT-DECORATION: none" 

target="_blank">My Order</a>. Some carriers may not provide tracking 
                  information. If you ordered multiple items, you may receive 
                  separate shipments with no additional shipping charges. </p>
                  <p style="LINE-HEIGHT: 14px; MARGIN: 10px 0px">For details 
                  regarding processing and delivery times, please see <a 

href="http://www.Joybuy.co.uk/uc/helpCenter/more-2" style="COLOR: #1e4e8e; TEXT-DECORATION: none" 

target="_blank">Shipping Information</a>.</p>
                  <p style="LINE-HEIGHT: 14px; MARGIN: 10px 0px">If you are not 
                  satisfied with your purchase, you may be eligible to return 
                  your item(s) by contacting Customer Service within 7 days of 
                  receiving your package. For more information, visit our <a 

href="http://www.Joybuy.co.uk/uc/helpCenter/more-4" style="COLOR: #1e4e8e; TEXT-DECORATION: none" 

target="_blank">Return Exchange</a> policy.</p>
                  <p style="LINE-HEIGHT: 14px; MARGIN: 10px 0px">For all other 
                  questions, visit our <a href="http://www.Joybuy.co.uk/uc/helpCenter/hcIndex" style="COLOR: #1e4e8e; 

TEXT-DECORATION: 

none" target="_blank">FAQ</a>.</p>
                  <p style="LINE-HEIGHT: 14px; MARGIN: 10px 0px"> <strong style="COLOR: #444">Do I have to pay import 
or customs duties?</strong> </p>
                  <p style="LINE-HEIGHT: 14px; MARGIN: 10px 0px">Joybuy.co.uk 
                  doesn't charge any taxes for its purchases, so now you are 
                  paid in full. However, since your order is shipping from 
                  China, it may be subject to customs or import duties charged 
                  once your order reaches your country.</p> </td>
                <td width="37"> </td>
                <td width="44%" style="TEXT-ALIGN: left">
                  <p style="LINE-HEIGHT: 14px; MARGIN: 10px 0px"> <strong style="COLOR: #444">What if my package was 
damaged during  delivery?</strong> </p>
                  <p style="LINE-HEIGHT: 14px; MARGIN: 10px 0px">Once you 
                  receive your package, please be sure to check for any damage. 
                  If the package was damaged during delivery, you can request 
                  "Proof of Damage" documentation from the carrier. You can use 
                  this when contacting Customer Service to arrange for damage 
                  reimbursement.</p>
                  <p style="LINE-HEIGHT: 14px; MARGIN: 10px 0px"> <strong style="COLOR: #444">How can I contact 
customer  service?</strong> </p>
                  <p style="LINE-HEIGHT: 14px; MARGIN: 10px 0px">For help with 
                  any order, just log in to <a href="http://www.Joybuy.co.uk/uc/orderDetails?id=${order.name}" 
style="COLOR: #1e4e8e; TEXT-DECORATION: none" target="_blank">My Order</a> and click on the order you need help 
with.Through the 
                  order details page, you can easily check order status and 
                  request the kind of Customer Service you need.  </p>
                  <p style="LINE-HEIGHT: 14px; MARGIN: 10px 0px">Please note: 
                  Our Online Sales Chat representatives do not have access to 
                  any order information. <br>For all other questions, please send Email to: 
<em>service@Joybuy.co.uk</em>.</p> </td>
                <td width="22"> </td> </tr>
              <tr>
                <td height="22"></td> 
				</tr> </tbody> </table>




 
</td> </tr> </tbody> </table> 
      <table width="700" cellspacing="0" cellpadding="0" border="0" align="center">
        <tbody>
        <tr >
          <td height="10" style="border-top:1px solid #3cb8ff"> </td> </tr>
        <tr valign="top">
          <td width="22"> </td>
          <td height="50" style="COLOR: #646464; FONT-SIZE: 12px"> <strong>PLEASE 
            DO NOT REPLY to this email.</strong> This email message was sent 
            from a notification-only email address that cannot accept incoming 
            email. If you have any questions, please send Email to: <em>service@Joybuy.co.uk</em>. Thank you for your 

cooperation!</td>
           </tr>
        <tr>
          <td width="22"> </td>
          <td height="20" align="center" style="COLOR: #646464; FONT-SIZE: 12px">Copyright&copy; 2012 Joybuy.co.uk; 

Inc. All Rights Reserved. | <a href="http://www.Joybuy.co.uk/c/Terms-Of-Use">Terms of Use </a>| <a 

href="http://www.Joybuy.co.uk/c/Privacy-Policy">Privacy Policy </td>
          <td width="22"> </td> </tr>
        <tr>
          </tr></tbody></table></td></tr></tbody></table>
</body>
</html>