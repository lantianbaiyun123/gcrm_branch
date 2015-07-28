<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fis" uri="http://gcrm.baidu.com/tags"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
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
    </head>
    <body> 
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
        
        
        <fis:require name="resources/lib/js/jquery.form.js"></fis:require>
        <fis:require name="resources/lib/jqueryUpload/js/jquery.fileupload.js"></fis:require>
        <fis:require name="resources/lib/bootstrap/js/bootstrap.js"></fis:require>
        <fis:require name="resources/lib/bootstrap/js/bootstrap-select.js"></fis:require>
        <fis:require name="resources/js/common.js"></fis:require>

        <fis:require name="resources/js/detail.js"></fis:require>
        
        <div class="container">
            <%@ include file="../widget/nav.jsp"%>
        </div>
        <div class="container">
            <ol class="breadcrumb">
                <li><a href="#">首页</a></li>
                <li><a href="#">客户管理</a></li>
                <li class="active">添加客户</li>
            </ol>
        </div>
        
        <div class="container">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <h3 class="row">
                        	<div class="col-md-10">
	                              		  客户详细信息
	                            </div>
	                            <div class="col-md-2 text-right">
	                                <input class="btn btn-success" type="submit" value="创建广告方案">
	                                <input class="btn btn-success" type="button" value="废除">
	                            </div>
                        </h3>
                    </div>
                </div><!--panel-default-->
				
                <%@ include file="../widget/companyHeader.jsp"%>
                <div class="panel panel-default"> 
                    <div class="panel-heading">
                        <h4 style="display:inline-block;">基本信息   
                            <small><a id="editBaseInfo" customerId="${customerView.customer.id}" href="###">[ 修改 ]</a></small>
                        </h4>
                       <div class="pull-right">
                            <a id="saveBaseInfo" customerId="${customerView.customer.id}" href="###" class="btn btn-success">保存</a>
                            <a id="cancelBaseInfo" customerId="${customerView.customer.id}" href="#" class="btn btn-default">取消</a>
                        </div>
                    </div>
                    <div id="J_detailBaseInfo" class="J_detailBaseInfo panel-body">
                        <%@ include file="../widget/detailBaseInfo.jsp"%>
                    </div><!--panel-body-->
                </div><!-- end panel -->
        
				<ul class="nav nav-tabs" id="myTab">
					<li class="active"><a href="#detailConcatInfo" data-toggle="tab">联系人</a></li>
					<c:choose>
  						<c:when test="${customerView.customerType == 'offline'}">
  							<li id="qualificationLi"><a href="#detailAgentQualification" data-toggle="tab">代理商资历</a></li>
  							<li id="businessChanceLi" style="display:none;"><a href="#detailBusinessChance" data-toggle="tab">业务机会</a></li>
  						</c:when>
  						<c:otherwise>
  							<li id="qualificationLi" style="display:none;"><a href="#detailAgentQualification" data-toggle="tab">代理商资历</a></li>
  							<li id="businessChanceLi"><a href="#detailBusinessChance" data-toggle="tab">业务机会</a></li>
  						</c:otherwise> 
  					</c:choose>
					<li><a href="#detailMaterials" data-toggle="tab">资质认证材料</a></li>
				</ul>
				<div class="tab-content">
					<div class="tab-pane active" id="detailConcatInfo">
                        <div class="panel panel-default">
                            <div class="panel-body">
						        <%@ include file="../widget/detailConcatInfo.jsp"%>
                            </div>
                        </div>
					</div>
                    <!--业务机会-->
					<div class="tab-pane" id="detailBusinessChance">
                        <div class="panel panel-default">
                            <div class="panel-heading">  
                                <div class="row">业务机会<small><a  id="editBusinessChance" customerId="${customerView.customer.id}" href="###">[ 修改 ]</a></small>
                                    <div class="pull-right hide" id="BusinessChanceSubmitBox">
                                        <a id="saveBusinessChance" customerId="${customerView.customer.id}" href="###" class="btn btn-success">保存</a>
                                        <a id="cancelBusinessChance" customerId="${customerView.customer.id}" href="#" class="btn btn-default">取消</a>
                                    </div>
                                </div>
                            </div>
                            <div id="J_businessChanceContainer" class="panel-body">
                               <%@ include file="../widget/detailBusinessChance.jsp"%>
                            </div><!--panel-body-->
                        </div><!-- end panel -->
					</div>
					<!--代理商资历-->
	               <div class="tab-pane" id="detailAgentQualification">
	                   <div class="panel panel-default">
	                       <div class="panel-heading">  
	                           <div class="row">代理商资历<small><a id="editAgentQualification" customerId="${customerView.customer.id}" href="###">[ 修改 ]</a></small>
	                               <div class="pull-right hide" id="AgentQualificationSubmitBox">
	                                   <a id="saveAgentQualification" customerId="${customerView.customer.id}" href="###" class="btn btn-success">保存</a>
	                                   <a id="cancelAgentQualification" customerId="${customerView.customer.id}" href="#" class="btn btn-default">取消</a>
	                               </div>
	                           </div>
	                       </div>
	                       <div class="panel-body" id="J_agentQualificationContainer">
	                          <%@ include file="../widget/detailAgentQualification.jsp"%>
	                       </div><!--panel-body-->
	                   </div><!-- end panel -->
	               </div>
                    <!--资质认证材料-->
	                <div class="tab-pane" id="detailMaterials">
	                    <div class="panel panel-default">
	                        <div class="panel-heading ">
	                            <div class="row">资质认证资料<small><a id="editMaterials" customerId="${customerView.customer.id}" href="###">[ 修改 ]</a></small>
	                                <div class="pull-right hide" id="MaterialsSubmitBox">
	                                    <a id="saveMaterials" customerId="${customerView.customer.id}" href="###" class="btn btn-success">保存</a>
	                                    <a id="cancelMaterials" customerId="${customerView.customer.id}" href="#" class="btn btn-default">取消</a>
	                                </div>
	                            </div>
	                        </div>
	                        <div class="panel-body" id="J_materialsContainer">
	                           <%@ include file="../widget/detailMaterials.jsp"%>
	                        </div><!--panel-body-->
	                    </div><!-- end panel -->
	                </div>
				</div>
       	 </div>
    </body>
    <fis:require name="resources/lib/js/jquery.js"></fis:require>
    <fis:require name="resources/lib/js/jquery.ui.widget.min.js"></fis:require>
    <fis:require name="resources/lib/js/jquery.iframe-transport.js"></fis:require>
    <fis:require name="resources/lib/js/jquery-validate.js"></fis:require>
    <fis:require name="resources/lib/jqueryUpload/js/jquery.fileupload.js"></fis:require>
    <fis:require name="resources/lib/bootstrap/js/bootstrap.js"></fis:require>
    <fis:require name="resources/lib/bootstrap/js/bootstrap-select.js"></fis:require>
    <fis:require name="resources/js/index.js"></fis:require>
    <fis:require name="resources/js/addMaterials.js"></fis:require>
    <fis:require name="resources/js/detail.js"></fis:require>
    <fis:require name="resources/js/detailBaseInfo.js"></fis:require>
    <fis:scripts/>
</fis:html>