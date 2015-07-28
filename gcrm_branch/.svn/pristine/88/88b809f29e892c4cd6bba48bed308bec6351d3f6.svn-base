<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="fis" uri="http://gcrm.baidu.com/tags"%>
<!DOCTYPE html>
<fis:html>
    <head>
    <title>GCRM</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <fis:styles/>
    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.0/html5shiv.min.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->

        <fis:require name="resources/lib/bootstrap/css/bootstrap.css"></fis:require>
        <fis:require name="resources/lib/bootstrap/css/bootstrap-select.css"></fis:require>
        <fis:require name="resources/lib/bootstrap/css/bootstrap-extend.css"></fis:require>
        <fis:require name="resources/lib/jqueryUpload/css/jquery.fileupload.css"></fis:require>
        <fis:require name="resources/lib/jqueryUpload/css/jquery.fileupload-ui.css"></fis:require>
        <fis:require name="resources/css/common.css"></fis:require>

        <fis:require name="resources/lib/js/jquery.js"></fis:require>
        <fis:require name="resources/lib/js/jquery.ui.widget.min.js"></fis:require>
        <fis:require name="resources/lib/js/jquery.iframe-transport.js"></fis:require>
        <fis:require name="resources/lib/js/jquery-validate.js"></fis:require>
        <fis:require name="resources/lib/jqueryUpload/js/jquery.fileupload.js"></fis:require>
        <fis:require name="resources/lib/bootstrap/js/bootstrap.js"></fis:require>
        <fis:require name="resources/lib/bootstrap/js/bootstrap-select.js"></fis:require>
        <fis:require name="resources/js/common.js"></fis:require>
        
    </head>
    <body>

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
                <div class="page-header">
                    <h3>
                    添加客户
                        <div class="pull-right">
                            <input class="btn btn-primary" type="submit" value="提交审核">
                            <input class="btn btn-success" type="button" value="暂存">
                        </div>
                    </h3>
                </div>
                <div class="panel panel-gcrm">
                    <div class="panel-heading">基本信息</div>
                    <div class="panel-body">
                        <%@ include file="./widget/addBaseInfo.jsp"%>
                    </div><!--panel-body-->
                </div><!-- end panel -->
                <div class="panel panel-gcrm">
                    <div class="panel-heading">联系人信息  <span>（所有项必填）</span></div>
                    <div class="panel-body">
                        <%@ include file="./widget/addConcatInfo.jsp"%>
                    </div><!--<div class="panel-body">-->
                </div>
				
                <div class="panel panel-gcrm">
                    <!-- Default panel contents -->
                    <div class="panel-heading">业务机会</div>
                    <div class="panel-body">
                        <%@ include file="./widget/addBusinessChance.jsp"%>
                    </div>
                </div><!--panel-body-->
                <div class="panel panel-gcrm">
                    <div class="panel-heading">代理商资历</div>
                    <div class="panel-body">
                        <%@ include file="./widget/addAgentQualification.jsp"%>
                    </div>
                </div><!--panel-body-->
                <div class="panel panel-gcrm">
                    <div class="panel-heading">资质认证材料</div>
                    <div class="panel-body">
                        
                        <%@ include file="./widget/addMaterials.jsp"%>
                    </div>
                </div><!--panel-body-->
                <div class="panel panel-gcrm">
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-md-12 text-center">
                                <input class="btn btn-primary" type="submit" value="提交审核">
                                <input class="btn btn-success" type="button" value="暂存">
                            </div>
                        </div>
                    </div>
                </div><!--panel-body-->
            </form>
        </div>
    </body>
     <fis:require name="resources/js/addMaterials.js"></fis:require>
    <fis:require name="resources/js/index.js"></fis:require>
    <fis:scripts/>
</fis:html>