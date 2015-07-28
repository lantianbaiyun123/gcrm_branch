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
        <fis:require name="resources/lib/bootstrap/css/bootstrap-datetimepicker.min.css"></fis:require>
        <fis:require name="resources/lib/bootstrap/css/bootstrap-extend.css"></fis:require>
        <fis:require name="resources/lib/jqueryUpload/css/jquery.fileupload.css"></fis:require>
        <fis:require name="resources/lib/jqueryUpload/css/jquery.fileupload-ui.css"></fis:require>
        <fis:require name="resources/css/common.css"></fis:require>

        <fis:require name="resources/lib/js/jquery.js"></fis:require>
        <fis:require name="resources/lib/js/jquery.ui.widget.min.js"></fis:require>
        <fis:require name="resources/lib/js/jquery.iframe-transport.js"></fis:require>
        <fis:require name="resources/lib/js/jquery-validate.js"></fis:require>
        <fis:require name="resources/lib/js/jquery.form.js"></fis:require>
        <fis:require name="resources/lib/jqueryUpload/js/jquery.fileupload.js"></fis:require>
        <fis:require name="resources/lib/bootstrap/js/bootstrap.js"></fis:require>
        <fis:require name="resources/lib/bootstrap/js/bootstrap-select.js"></fis:require>
        <fis:require name="resources/lib/bootstrap/js/bootstrap-datetimepicker.min.js"></fis:require>
        <fis:require name="resources/lib/bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></fis:require>
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
                <li class="active">客户详细信息</li>
            </ol>
        </div>
        <div class="container">
            <div class="page-header">
                <h3>客户详细信息
                    <div class="pull-right">
                        <input class="btn btn-primary" type="submit" value="提交审核">
                        <input class="btn btn-success" type="button" value="暂存">
                    </div>
                </h3>
            </div>
            <div class="panel panel-gcrm">
                <div class="panel-body">
                    <div class="companyHeader">
                        <h3>北京当当网络信息技术有限公司</h3>
                        <div class="pull-right"><a href="#">查看审批记录</a> | <a href="#">修改记录</a></div>
                        <div class="clear-float"></div>
                    </div>
                    <div class="alert alert-info alert-dismissable">
                        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                        客户信息未提交审核
                    </div>
                    
                    <div class="btn-group btn-group-justified companyHeader">
                        <a class="btn btn-default disabled" role="button">编辑:1234567890</a>
                        <a class="btn btn-default disabled" role="button">审核状态:待提交</a>
                        <a class="btn btn-default disabled" role="button">提交时间:2013.11.31</a>
                        <a class="btn btn-default disabled" role="button">提交人:王颖</a>
                    </div>
                </div><!--panel-body-->
            </div><!-- end panel -->

            <div id="J_detailBaseInfo" class="panel panel-gcrm">
                <div class="panel-heading clearfix">基本信息  
                    <a id="editBaseInfo" href="###" class="btn btn-default pull-right">修改</a>
                </div>
                <div class="panel-body">
                    <%@ include file="./widget/detailBaseInfo.jsp"%>
                </div><!--panel-body-->
                
                <div id="BaseInfoSubmitBox" class="panel-footer text-center hide">
                    <a id="saveBaseInfo" href="###" class="btn bnt-info">保存</a>
                    <a id="cancelBaseInfo" href="#" class="btn btn-default">取消</a>
                </div>
                
            </div><!-- end panel -->

			<ul class="nav nav-tabs" id="myTab">
				<li class="active">
                    <a href="#detailConcatInfo" data-id="detailConcatInfo" data-url="./widget/detailConcatInfo.jsp" data-toggle="tab">联系人</a></li>
				<li>
                    <a href="#detailConcatInfoEmpty" data-id="detailConcatInfoEmpty" data-url="./widget/detailConcatInfoEmpty.jsp" data-toggle="tab">联系人（空）</a>
                </li>
				<li><a href="#detailBusinessChance" data-id="detailBusinessChance" data-url="./widget/detailBusinessChance.jsp" data-toggle="tab">业务机会</a>
                </li>
                <li>
                    <a href="#detailAgentQualification" data-id="detailAgentQualification" data-url="./widget/detailAgentQualification.jsp" data-toggle="tab">代理商资历</a>
                </li>
				<li>
                    <a href="#detailMaterials" data-id="detailMaterials" data-url="./widget/detailMaterials.jsp" data-toggle="tab">资质认证材料</a>
                </li>
			</ul>
			<div class="tab-content">
				<div class="tab-pane active" id="detailConcatInfo">
                    <div class="panel panel-gcrm">
                        <div class="panel-body"></div>
                    </div>
				</div>
				<div class="tab-pane" id="detailConcatInfoEmpty">
                    <div class="panel panel-gcrm">
                        <div class="panel-body"></div>
                    </div>
				</div>
                <!--业务机会-->
				<div class="tab-pane" id="detailBusinessChance">
                    <div class="panel panel-gcrm">
                        <div class="panel-heading clearfix">
                            <a id="editBusinessChance" href="###" class="btn btn-default pull-right">修改</a>
                        </div>
                        <div class="panel-body"></div><!--panel-body-->
                        <div class="panel-footer text-center hide" id="BusinessChanceSubmitBox">
                            <a id="saveBusinessChance" href="###" class="btn bnt-info">保存</a>
                            <a id="cancelBusinessChance" href="#" class="btn btn-default">取消</a>
                        </div>
                    </div><!-- end panel -->
				</div>
				<!--代理商资历-->
                <div class="tab-pane" id="detailAgentQualification">
                    <div class="panel panel-gcrm">
                        <div class="panel-heading clearfix clearfix">
                            <a id="editAgentQualification" href="###" class="btn btn-default pull-right">修改</a>
                        </div>
                        <div class="panel-body"></div><!--panel-body-->
                        <div class="panel-footer text-center hide" id="AgentQualificationSubmitBox">
                            <a id="saveAgentQualification" href="###" class="btn bnt-info">保存</a>
                            <a id="cancelAgentQualification" href="#" class="btn btn-default">取消</a>
                        </div>
                        
                    </div><!-- end panel -->
                </div>
                <!--资质认证材料-->
                <div class="tab-pane" id="detailMaterials">
                    <div class="panel panel-gcrm">
                        <div class="panel-heading clearfix ">
                            <a id="editMaterials" href="###" class="btn btn-default pull-right">修改</a>
                        </div>
                        <div class="panel-body"></div><!--panel-body-->
                        <div class="panel-footer text-center hide" id="MaterialsSubmitBox">
                            <a id="saveMaterials" href="###" class="btn btn-info">保存</a>
                            <a id="cancelMaterials" href="#" class="btn btn-default">取消</a>
                        </div>
                    </div><!-- end panel -->
                </div>
			</div>
        </div>
    <fis:require name="resources/js/detail.js"></fis:require>
    <fis:require name="resources/js/detailBaseInfo.js"></fis:require>
    <fis:scripts/>
    </body>
</fis:html>