<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
<head>
    <title>Hello World!</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<link type="text/css" rel="Stylesheet" href="http://db-bpm-dev03.vm.baidu.com:8687/gcrm/resources/css/bootstrap.min.css" media="all"/>
<link type="text/css" rel="Stylesheet" href="http://db-bpm-dev03.vm.baidu.com:8687/gcrm/resources/css/core.css" media="all"/>
<script type="text/javascript" src="http://db-bpm-dev03.vm.baidu.com:8687/gcrm/resources/js/common.js"></script>
<script type="text/javascript" src="http://db-bpm-dev03.vm.baidu.com:8687/gcrm/resources/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript">
var GCRM = GCRM||{};
 GCRM.constants = GCRM.constants || {};
 GCRM.constants.CONTEXT = "http://db-bpm-dev03.vm.baidu.com:8687/gcrm/";
 GCRM.constants.CURRENT_USER_NAME = "";
 GCRM.messages =  {"view.index.register":"Register","view.settings.country.remark":"Rmark","view.msg.subscribe.list":"Subscription List","view.settings.country.phoneCode":"Phone Code","view.offer.customer.name":"Customer name","view.search":"Search","view.offer.ad":"Ad offer","view.add":"Add","view.settings.position.thirdCloumn":"Third Level Column","view.msg.subscribed":"User was subscribed","view.offer.url":"Url","email.message":"The value must be a valid email!","view.settings.position.edit":"Edit position","view.fail":"Fail","view.settings":"Setting","delete.confirm":"Are you sure you want to delete this?","view.settings.country.timeZone":"Time Zone","view.close":"Close","view.settings.country":"Country","view.msg":"Message center","view.settings.position":"Position","view.startTime":"Start time","view.settings.country.edit":"Edit country info","view.endTime":"End time","view.offer":"Offer","view.remark":"Remark","view.msg.subscribe.offer":"Offer Expire","view.required":"Required","view.ok":"Ok","view.id":"ID","view.settings.country.domain":"Domain name","notBlank.message":"The value may not be empty!","view.config":"Config","view.save":"Save","view.createTime":"Create time","view.index.login":"Login","view.to":"To","view.success":"Success","view.lang":"Language","view.operate":"Operate","view.settings.country.nation":"Nation","view.open":"Open","view.settings.position.add":"Add position","operate.confirm":"Are you sure you want to do this?","view.offer.customer.info":"Customer basic info","cas.login.error.-1":"login occur error,please retry!","view.msg.subscribe":"Subscribe","view.settings.position.secondCloumn":"Second Level Column","view.offer.content":"Content","view.gcrm":"Global Crm","view.delete":"Delete","view.msg.subscribe.add":"Add Subscription","view.cancle":"Cancle","view.type":"Type","view.msg.subscribe.ad":"Daily AD Data","view.select":"Select","cas.login.error.132":"username or password is wrong!","cas.login.error.131":"captcha code error!","view.offer.type":"Offer type","view.name":"Name","view.noResult":"No result","view.settings.country.nationEnName":"English Name","signup.message.success":"Congratulations! You have successfully signed up.","view.index.logout":"Logout","view.offer.customer.fullName":"Full name","signup.message.error":"Register error,reason:{0}","view.settings.position.firstCloumn":"First Level Column","view.email":"Email","view.warning":"Warning","view.index.title":"Home page","view.offer.customer":"Customer","view.loading":"Loading","view.edit":"Edit","":"" }
;
 console.log(GCRM.util.message('signup.message.success'));
</script>
    <link type="text/css" rel="Stylesheet" href="http://db-bpm-dev03.vm.baidu.com:8687/gcrm/resources/css/signin.css" media="all"/>
</head>
<body>





<div class="navbar navbar-inverse navbar-fixed-top">
	<div class="navbar-inner">
		<div class="container">
			<a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse"> 
					<span class="icon-bar"></span> 
					<span class="icon-bar"></span> 
					<span class="icon-bar"></span>
			</a> 
			<a class="brand" href="#">Global Crm</a>
			<div class="nav-collapse collapse">
				<ul class="nav">
					<li class="active">
						<a href='/gcrm/;jsessionid=D0F7162FD90A817BC9FF989A7D1648FD.tomcat2'>
							Home page
						</a>
					</li>
					
					<li>
						<a href='/gcrm/setting/position/add;jsessionid=D0F7162FD90A817BC9FF989A7D1648FD.tomcat2'>
							Setting
						</a>
					</li>
					
					<li>
						<a href='/gcrm/offer/customer/list;jsessionid=D0F7162FD90A817BC9FF989A7D1648FD.tomcat2'>
							Offer
						</a>
					</li>
					
					<li>
						<a href='/gcrm/subscribe/list;jsessionid=D0F7162FD90A817BC9FF989A7D1648FD.tomcat2'>
							Message center
						</a>
					</li>
					
					
					
				</ul>					
				<ul class="nav pull-right">
					<li class="active">
						<a href='/gcrm/;jsessionid=D0F7162FD90A817BC9FF989A7D1648FD.tomcat2?lang=en_US'>
							English Version
						</a>
					</li>
					<li class="active">
						<a href='/gcrm/;jsessionid=D0F7162FD90A817BC9FF989A7D1648FD.tomcat2?lang=zh_CN'>
							中文版
						</a>
					</li>
					
					<li>
					<a href='/gcrm/logout;jsessionid=D0F7162FD90A817BC9FF989A7D1648FD.tomcat2'>
						Logout
					</a>
					</li>
				</ul>
			</div>
			<!--/.nav-collapse -->
		</div>
	</div>
</div>
<!-- Page content -->
<div class="container">
   <form class="form" action='${formaction}' method="post">
        <h2 class="form-heading">Please Sign In</h2>
        <input type="hidden" name="appid" value="${appid}" />
		<input type="hidden" name="selfu" value="${selfu}" />
		<input type="hidden" name="fromu" value="${fromu}" />								
		<input type="hidden" name="senderr" value="1" />
			
        <input type="text" class="input-block-level" placeholder="User name" name="entered_login"/>
        <input type="password" class="input-block-level" placeholder="Password" name="entered_password"/>
        <input type="text" maxlength="4" size="4" class="code-input" id="ValiCode" name="entered_imagecode"/>
        <img class="valicode" src="${captcha}" alt="验证码"/>
       
        <button class="btn btn-large btn-primary" type="submit">Sign In</button>
    </form>   
</div>
</body>
</html>