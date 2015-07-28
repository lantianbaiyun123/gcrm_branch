<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
<link rel="icon" href="/gcrm/resources/images/favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="/gcrm/resources/images/favicon.ico" type="image/x-icon" />

  <meta charset="UTF-8">
  <title>Magellan</title>
  <style>
   /* reset */
body,div,dl,dt,dd,ul,ol,li,h1,h2,h3,h4,h5,h6,pre,code,form,fieldset,legend,input,button,textarea,select,p,blockquote,table,th,td{margin:0;padding:0;}
article,aside,details,figcaption,figure,footer,header,hgroup,menu,nav,section,summary{display:block;margin:0;padding:0;}
table{border-collapse:collapse;border-spacing:0;}
fieldset,img{border:0;}
address,button,caption,cite,code,dfn,em,input,optgroup,option,select,strong,textarea,th,var{font:inherit;}
li{list-style:none;}
caption,th{text-align:left;font-weight:normal;}
h1,h2,h3,h4,h5,h6{font-size:100%;font-weight:normal;}
    /*img{height: 100%;}*/
    body{min-width: 1100px;}
    #logo-mid{position: absolute;z-index: 5;}
    .login-logo{position: absolute;z-index: 3;left: 25px;top: 19px;}
    .mock .fill-mock{width: 100%;height: 100%;background: url(resources/images/login-fill.png) repeat;opacity: .9;filter: alpha(opacity=90);position: absolute;top: 0;left: 0;z-index: 4;}
    .mock{overflow: hidden;position: absolute;z-index: 2;width: 100%;height: 100%;}
    .footer{position: absolute;height: 30px;bottom: 0;text-align: center;z-index: 3;color: #fff;font-family: tahoma,arial,"宋体";font-size: 12px;width: 100%;}
    .login{width: 380px;height: 312px;z-index: 3;position: absolute;left: 550px;display: none;}
    .login .border-wrapper{width: 420px;height: 372px;background: #143465;opacity: .2;filter: alpha(opacity=20);position: absolute;top: -20px;left: -20px;}
    .login-form{
      position: absolute;
      background: #fff;
      width: 302px;height: 308px;
      z-index: 4;
padding: 12px 38px;
    }
    .login-wrapper{margin: 0 auto;
clear: both;
width: 1002px;
height: 465px;
position: relative;}
.login-form h1{font-family: "微软雅黑";color: #645d54;font-size: 20px;line-height: 36px;height: 36px;border-bottom: 1px solid #cccccc;}
.login-form input{/*line-height: 26px;*/border: 1px solid #b1b1b1;height: 26px;width: 258px;border-radius: 4px;
  -webkit-box-shadow: inset 0 2px 2px rgba(0, 0, 0, 0.075);
box-shadow: inset 0 2px 2px rgba(0, 0, 0, 0.075);
-webkit-transition: border-color ease-in-out .15s, box-shadow ease-in-out .15s;padding-left: 43px;padding-top:7px;padding-bottom: 7px;
}

.username{background: url(resources/images/login-icons.png) no-repeat 14px 4px;margin-top: 20px;}
.password{margin-top: 16px;}
.div-password .icon{width: 20px;height: 20px;background: url(resources/images/login-icons.png) no-repeat 0px -55px;position: absolute;left: 15px;top: 26px;z-index: 999;}
.div-username .icon{width: 20px;height: 20px;background: url(resources/images/login-icons.png) no-repeat 0px -4px;position: absolute;left: 15px;top: 29px;z-index: 999;}
.misc{margin-top: 16px;position: relative;}
.div-username{position: relative;}
.div-password{position: relative;}
.div-username label{position: absolute;left: 44px;top: 32px;color: #A9A9A9;cursor: text;}
.div-password label{position: absolute;left: 44px;top: 28px;color: #A9A9A9;cursor: text;}
.misc label{position: absolute;left: 44px;top: 13px;color: #A9A9A9;cursor: text;}
.v-code{}
.refresher{background: url(resources/images/login-icons.png) no-repeat 14px -88px;position: absolute;left: 0;
top: 4px;
width: 40px;
height: 36px;cursor: pointer;}
.v-code-img{display: inline-block;vertical-align: middle;position: absolute;right: 12px;top: 4px;cursor: pointer;}

.btnSubmit{width: 100%;background: #fe8840;border: none;border-radius: 4px;font-family: "微软雅黑";font-size: 16px;color: #fff;line-height: 24px;padding-top: 10px;padding-bottom: 10px;margin-top: 16px;cursor: pointer;}
.forget-password{margin: 10px 0;}
.forget-password a{color: #808080;font-size: 12px;text-decoration: none;float: right;}
.forget-password a:hover{text-decoration: underline;}
.forget-password span{color: #a94442;font-size: 12px;}
.login-form input:focus{border-color: #66afe9;
outline: 0;
-webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,.075), 0 0 8px rgba(102, 175, 233, 0.6);
box-shadow: inset 0 1px 1px rgba(0,0,0,.075), 0 0 8px rgba(102, 175, 233, 0.6);}
input::-ms-clear {
  display: none;
}
input:-webkit-autofill, input:focus:-webkit-autofill{
-webkit-box-shadow: 0 0 0px 1000px #fff inset;
}
.blank-hint{display: none;}
  </style>
</head>
<body>
  <div class="login-logo">
    <img src="resources/images/login-logo.png" alt="logo" height="44" width="240">
  </div>
  <div class="mock">
    <img src="resources/images/login-bg.jpg" id="bg">
    <div class="fill-mock"></div>
    <img src="resources/images/login-logo-mid.png" id="logo-mid">
  </div>
  <div class="login-wrapper">
    <div class="login">
      <div class="border-wrapper">

      </div>
      <form class="login-form" action="${formaction}" method="post" autocomplete="off" id="loginForm">
        <input type="hidden" name="appid" value="${appid}" />
        <input type="hidden" name="selfu" value="${selfu}" />
        <input type="hidden" name="fromu" value="${fromu}" />
        <input type="hidden" name="language" value="${lang_login}" />
        <input type="hidden" name="senderr" value="1" />
        <h1><s:message code="view.index.login"/></h1>
        <div class="div-username">
          <div class="icon"></div>
          <label><s:message code="view.index.username"/></label>
          <input type="text" class="username" id="companyName" name="entered_login"  autocomplete="off" />
        </div>
        <div class="div-password">
          <div class="icon"></div>
          <label><s:message code="view.index.password"/></label>
          <input type="password" class="password" id="password" name="entered_password" autocomplete="off" />
        </div>
        <div class="misc">
          <div class="refresher" title="<s:message code="view.index.refreshcaptcha"/>"></div>
          <label><s:message code="view.index.captcha"/></label>
          <input type="text" class="v-code" id="captcha" name="entered_imagecode" autocomplete="off" />
          <img class="v-code-img" src="${captcha}" alt="<s:message code="view.index.captcha"/>" width="80" height="37" title="<s:message code="view.index.refreshcaptcha"/>" />
        </div>
        <button type="submit" class="btnSubmit"><s:message code="view.index.login"/></button>
        <div class="forget-password">
          <c:if test='${errMsg != null && errMsg != "" }'>
            <span>${errMsg}</span>
          </c:if>
          <span class="blank-hint"><s:message code="view.index.unamepwdisnull"/></span>
          <a href="${findpwd}"><s:message code="view.index.forget_password"/></a>
        </div>
      </form>
    </div>
  </div>
  <div class="footer">
    ©2014 Baidu
  </div>
  <script src="resources/vendor/jquery/jquery.js"></script>
  <script>
  $(document).ready(function () {
    $(window).resize(function() {
      refresh();
    });
    var naturalWidth = 1680;
    var naturalHeight = 953;
    function refresh () {
      var $login = $('.login');
      var $bg = $('#bg');
      var $mid = $('#logo-mid');
      var viewWidth = $('body').width();
      if (viewWidth < 1100) {
        viewWidth = 1100;
      }
      var viewHeight = $(window).height();
      $login.css({
        top: (viewHeight / 2 - 190) + 'px',
        display: 'block'
      });
      $('.mock').css({
        width: viewWidth+ 'px'
      });
      if ( naturalWidth / naturalHeight >= viewWidth / viewHeight ) {
        $bg.css({
          height: "auto",
          width: (viewHeight * naturalWidth / naturalHeight) + "px"
        });
        $mid.css({
          top: ( 252 * viewWidth / naturalWidth ) + "px",
          left: ( 354 * viewHeight / naturalHeight ) + "px",
          height: "auto",
          width: ( 290 * viewWidth / naturalWidth) + "px"
        });
      } else {
        $bg.css({
          width: "auto",
          height: (viewWidth * naturalHeight / naturalWidth) + "px"
        });
        $mid.css({
          left: ( 354 * viewHeight / naturalHeight ) + "px",
          top: ( 252 * viewWidth / naturalWidth ) + "px",
          width: "auto",
          height: ( 210 * viewHeight / naturalHeight) + "px"
        });
      }
    }
    refresh();

    $('.v-code-img,.refresher').on('click', function () {
      var temp = $('.v-code-img').attr('src');
      $('.v-code-img').attr('src', '');
      $('.v-code-img').attr('src', temp);
    });

    var isInputSupported = 'placeholder' in document.createElement('input');
    var $inputs = $('.div-username,.div-password,.misc');
    if ( !isInputSupported ) {
      $inputs.each(function () {
        var $this = $(this);
        var $label = $this.find('label');
        var $input = $this.find('input');
        var placeholderText = $label.text();
        if ( $input.val() ) {
          $label.hide();
        }
        $label.on('click', function () {
          $label.hide();
          $input.focus();
        });
        $input.focus(function (){
          $label.hide();
        });
        $input.blur(function (){
            var iptVal = $.trim( $(this).val() );
            if( !iptVal ){
              $label.show();
            }
        });
      });
    } else {
      $inputs.each(function () {
        var $this = $(this);
        var $label = $this.find('label');
        var $input = $this.find('input');
        $input.attr('placeholder', $label.text());
        $label.hide();
      });
    }
    $('#loginForm').submit(function (e) {
      if ( $('#companyName').val() && $('#password').val() ) {
        $('.blank-hint').hide();
      } else {
        e.preventDefault();
        $('.blank-hint').show();
      }
    });
  });
  </script>
</body>
</html>