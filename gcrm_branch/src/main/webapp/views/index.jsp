<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fis" uri="http://gcrm.baidu.com/tags"%>
<!DOCTYPE html>
<fis:html>
    <head>
    <title>GCRM</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <fis:styles/>
    <!-- HTMLshiv for IE8 -->
    <!--[if lt IE 9]>
        <link rel="stylesheet" type="text/css" href="http://localhost:8080/gcrm/resources/lib/bootstrap/css/bootstrap-ie78.css"/>
        <script type="text/javascript" src="http://localhost:8080/gcrm/resources/lib/js/html5shiv.min.js"></script>
        <script type="text/javascript" src="http://localhost:8080/gcrm/resources/lib/js/respond.min.js"></script>
    <![endif]-->
    </head>
    <body>
        <fis:require name="lib/bootstrap/css/bootstrap.css"></fis:require>
        <fis:require name="lib/bootstrap/css/bootstrap-extend.css"></fis:require>
        <fis:require name="css/common.css"></fis:require>

        <div class="container">
            <%@ include file="./widget/nav.jsp"%>
        </div>
        <div class="container">
            <ol class="breadcrumb">
                <li><a href="#">首页</a></li>
                <li><a href="#">客户管理</a></li>
                <li class="active">添加客户</li>
            </ol>
        </div>
        
        <div class="container">
            <form class="form-horizontal" method="post">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <h3 class="row">
                            <div class="col-md-10">
                                添加客户
                            </div>
                            <div class="col-md-2 text-right">
                                <input class="btn btn-success" type="submit" value="提交审核">
                                <input class="btn btn-success" type="button" value="暂存">
                            </div>
                        </h3>
                    </div>
                </div><!--panel-default-->
                
                <div class="panel panel-default">
                    <div class="panel-heading">基本信息</div>
                    <div class="panel-body">
                        <%@ include file="./widget/baseInfo.jsp"%>
                    </div><!--panel-body-->
                </div><!-- end panel -->
                <div class="panel panel-default">
                    <div class="panel-heading">联系人信息  <span>（所有项必填）</span></div>
                    <div class="panel-body">
                        <%@ include file="./widget/concatInfo.jsp"%>
                    </div><!--<div class="panel-body">-->

                </div>
                <div class="panel panel-default">
                    <!-- Default panel contents -->
                    <div class="panel-heading">业务机会</div>
                    <div class="panel-body">
                        <%@ include file="./widget/businessChance.jsp"%>
                    </div>
                </div><!--panel-body-->
                <div class="panel panel-default">
                    <div class="panel-heading">代理商资历</div>
                    <div class="panel-body">
                        <%@ include file="./widget/agentQualification.jsp"%>
                    </div>
                </div><!--panel-body-->
                <div class="panel panel-default">
                    <div class="panel-heading">资质认证材料</div>
                    <div class="panel-body">
                        <%@ include file="./widget/materials.jsp"%>
                    </div>
                </div><!--panel-body-->
                <div class="panel panel-default">
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-md-12 text-center">
                                <input class="btn btn-success" type="submit" value="提交审核">
                                <input class="btn btn-success" type="button" value="暂存">
                            </div>
                        </div>
                    </div>
                </div><!--panel-body-->
            </form>
        </div>
    </body>
    <fis:require name="lib/js/jquery.js"></fis:require>
    <fis:require name="lib/js/jquery-validate.min.js"></fis:require>
    <fis:require name="lib/bootstrap/js/bootstrap.js"></fis:require>
    <fis:require name="js/index.js"></fis:require>
    <fis:scripts/>
</fis:html>