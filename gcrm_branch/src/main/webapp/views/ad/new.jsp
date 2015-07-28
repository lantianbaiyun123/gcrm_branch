<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fis" uri="http://gcrm.baidu.com/tags"%>
<!DOCTYPE html>
<fis:html>
    <head>
    <title>GCRM</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <%@ include file="../include/common.jsp"%>
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
        <fis:require name="resources/lib/js/jquery.autocomplete.js"></fis:require>
        <fis:require name="resources/lib/js/jquery.ui.widget.min.js"></fis:require>
        <fis:require name="resources/lib/js/jquery.iframe-transport.js"></fis:require>
        <fis:require name="resources/lib/jqueryValidation/jquery.validate.js"></fis:require>
        <fis:require name="resources/lib/jqueryValidation/additional-methods.js"></fis:require>
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
            <%--@ include file="../widget/nav.jsp" --%>
        </div>
        <!-- <div class="container">
            <ol class="breadcrumb">
                <li><a href="#">首页</a></li>
                <li><a href="#">客户管理</a></li>
                <li class="active">客户详细信息</li>
            </ol>
        </div> -->
        <div class="container">
            <div class="page-header" >
                <div class="row">
                    <div class="col-md-3">
                        <h3>添加广告方案</h3>
                    </div>
                    <div class="col-md-2">
                        <div class="input-group">
                            <span class="input-group-addon">提交人</span>
                            <input type="text" class="form-control">
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="btn-group">
                            <a role="button" class="btn btn-default disabled">方案状态:待提交</a>
                            <a role="button" class="btn btn-default disabled">方案类型:新增</a>
                        </div>
                    </div>
                    <!-- <div class="col-md-6">
                        <table>
                            <tr>
                                <td><span>提交人</span></td>
                                <td><a role="button" class="btn btn-default disabled">方案状态:待提交</a></td>
                                <td><a role="button" class="btn btn-default disabled">方案类型:新增</a></td>
                            </tr>
                        </table>
                    </div> -->
                    <div class="col-md-3 text-right">
                        <a href="###" class="J_submit btn btn-primary disabled">提交广告方案</a>
                    </div>
                </div>
            </div>
            
            <div class="J_newBaseInfoPanel panel panel-gcrm" id="J_newBaseInfoPanel">
                <a id="editBaseInfo" href="###" class="btn btn-link pull-right hide">添加</a>
                <!-- <a id="editBaseInfo" href="###" data-id="10" class="btn btn-link pull-right hide">添加</a> -->
                <div class="panel-heading"><span class="black-block">&nbsp;</span>广告方案基本信息</div>
                <div class="panel-body hide"></div><!--panel-body-->
                
                <div class="panel-footer text-left hide">
                    <a id="saveBaseInfo" href="###" class="btn btn-primary">保存</a>
                    <a id="tempSaveBaseInfo" href="###" class="btn btn-default">暂存</a>
                    <a id="cancelBaseInfo" href="#" class="btn btn-default">取消</a>
                </div>
            </div><!-- end panel -->
            <!-- 广告内容 -->
            <div id="newAdContentPanelBox">
                <div class="J_newAdContent panel panel-gcrm">
                    <a href="###" class="J_editAdContent btn btn-link pull-right hide">添加</a>
                    <div class="panel-heading"><span class="black-block">&nbsp;</span>广告内容1</div>
                    <div class="panel-body hide">
                        <%--@ include file="./widget/newAdContent.jsp"--%>
                    </div><!--panel-body-->
                    
                    <div class="panel-footer text-left hide">
                        <a href="###" class="J_saveAdContent btn btn-primary">保存</a>
                        <a href="###" class="J_cancelAdContent btn btn-default">取消</a>
                        <a href="###" class="J_delAdContent btn btn-danger hide" data-toggle="popover">删除</a>
                    </div>
                </div><!-- end panel -->
                
            </div>
            <div class="text-center">
                <a href="###" class="J_addAdContentBtn btn btn-default-dashed btn-block">
                    <span class="glyphicon glyphicon-plus"></span> 继续添加广告内容
                </a>
            </div>
            
            <div class="text-center mt10">
                <a href="###" class="J_submit btn btn-primary">提交广告方案</a>
            </div>
        </div>
        <div id="shortcuts" class="">   
                <div class="shortcut"> <a class="btn btn-default" href="#J_newBaseInfoPanel">基础信息<span style="visibility:hidden;">0</span>  </a></div>
                <div class="shortcut"> <a class="btn btn-default" href="#newAdContentPanelBox">广告方案1</a></div>
        </div>
    <fis:require name="resources/lib/js/sea.js"></fis:require>
    <fis:require name="resources/js/ad/new.js"></fis:require>
    <fis:scripts/>
    </body>
</fis:html>