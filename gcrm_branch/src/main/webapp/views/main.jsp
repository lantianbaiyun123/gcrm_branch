<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
    <title>Magellan</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" href="/gcrm/resources/images/favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="/gcrm/resources/images/favicon.ico" type="image/x-icon" />
        <link rel="stylesheet" href="/gcrm/resources/vendor/bootstrap/css/bootstrap.css">
    <link rel="stylesheet" href="/gcrm/resources/vendor/select2-3.4.5/select2.css">
    <link rel="stylesheet" type="text/css" href="/gcrm/resources/vendor/font-awesome/css/font-awesome.css"/>
    <link rel="stylesheet" type="text/css" href="/gcrm/resources/vendor/summernote/summernote.css"/>
    <link rel="stylesheet" href="/gcrm/resources/app/app.css">
    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="/gcrm/resources/vendor/html5shiv/html5shiv.min.js"></script>
      <script src="/gcrm/resources/vendor/es5-shim/es5-shim.js"></script>
      <script src="/gcrm/resources/vendor/respond/respond.min.js"></script>
    <![endif]-->
    </head>
    <body ng-controller="CtrlApp" class="{{lang}}">
        <div class="wrap">
            <div ui-view="header" class="app-header"></div>
            <div ui-view="body" class="app-body"></div>
        </div>
        <div ui-view="footer"></div>
        <%@ include file="./include/common.jsp"%>
        <script src="/gcrm/resources/app/main.js?v=2014112111"></script>
    </body>
</html>