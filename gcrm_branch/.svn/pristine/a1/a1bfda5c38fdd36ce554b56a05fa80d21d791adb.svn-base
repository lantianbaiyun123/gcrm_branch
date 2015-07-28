<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fis" uri="http://gcrm.baidu.com/tags"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<fis:html>
<head>
<title>GCRM</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<fis:styles />
<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.0/html5shiv.min.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.3.0/respond.min.js"></script>

    <![endif]-->
<%@ include file="../include/common.jsp"%>
</head>
<body>
	<fis:require name="resources/lib/bootstrap/css/bootstrap.css"></fis:require>
	<fis:require name="resources/lib/bootstrap/css/bootstrap-select.css"></fis:require>
	<fis:require
		name="resources/lib/bootstrap/css/bootstrap-datetimepicker.min.css"></fis:require>
	<fis:require name="resources/lib/bootstrap/css/bootstrap-extend.css"></fis:require>
	<fis:require
		name="resources/lib/jqueryUpload/css/jquery.fileupload.css"></fis:require>
	<fis:require
		name="resources/lib/jqueryUpload/css/jquery.fileupload-ui.css"></fis:require>
	<fis:require name="resources/css/common.css"></fis:require>

	<fis:require name="resources/lib/js/jquery.js"></fis:require>
	<fis:require name="resources/lib/js/jquery.ui.widget.min.js"></fis:require>
	<fis:require name="resources/lib/js/jquery.iframe-transport.js"></fis:require>

	<fis:require name="resources/lib/jqueryValidation/jquery.validate.js"></fis:require>
	<fis:require
		name="resources/lib/jqueryValidation/additional-methods.js"></fis:require>

	<fis:require name="resources/lib/js/jquery.form.js"></fis:require>
	<fis:require name="resources/lib/js/jquery.suggestion.js"></fis:require>
	<fis:require name="resources/lib/jqueryUpload/js/jquery.fileupload.js"></fis:require>
	<fis:require name="resources/lib/bootstrap/js/bootstrap.js"></fis:require>
	<fis:require name="resources/lib/bootstrap/js/bootstrap-select.js"></fis:require>
	<fis:require
		name="resources/lib/bootstrap/js/bootstrap-datetimepicker.min.js"></fis:require>
	<fis:require
		name="resources/lib/bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></fis:require>
	<fis:require name="resources/lib/jqueryValidation/jquery.validate.js"></fis:require>
	<fis:require name="resources/js/common.js"></fis:require>


	<div class="container">
		<%@ include file="../widget/nav.jsp"%>
	</div>
	<div class="container">
		<ol class="breadcrumb marginBottom10">
			<li><a href="#"><s:message code="gcrm.title.home" /></a></li>
			<li><a href="<c:url value='/customer'/>"><s:message
						code="gcrm.title.customer" /></a></li>
			<li class="active"><s:message code="gcrm.title.addcustomer" /></li>
		</ol>
	</div>

	<div class="container">
		<div class="page-header addcustomer marginBottom10">
			<h3>
				<s:message code="gcrm.title.addcustomer" />
				<div class="pull-right">
					<a class="btn btn-default submit" disabled><s:message
							code="gcrm.title.customer.submit" /></a>
				</div>
			</h3>
		</div>

		<div id="J_detailBaseInfo" class="panel panel-gcrm marginBottom10">
			<div class="panel-heading clearfix">
				<s:message code="customer.title.base" />
				<a id="editBaseInfo" href="###" class="hide pull-right">[ <s:message
						code="gcrm.title.customer.modify" /> ]
				</a>
			</div>

			<div class="panel-body">
				<form id='baseInfoForm' class='form-horizontal' method='POST'
					action='afterEdit' >
					<%@ include file="../widget/baseInfo.jsp"%>
				</form>
			</div>
			<div id="BaseInfoSubmitBox" class="panel-footer text-left">
				<a id="saveBaseInfo" href="###" class="btn btn-primary"><s:message
						code="gcrm.title.customer.save" /></a> <a id="tempSaveBaseInfo"
					href="#" class="btn btn-default"><s:message
						code="gcrm.title.customer.tempSave" /></a> <a id="cancelBaseInfo"
					href="#" class="btn btn-default" disabled><s:message
						code="gcrm.title.customer.cancel" /></a>
			</div>
		</div>
		<!-- end panel -->
		<div class="panel panel-gcrm marginBottom10" id="detailConcatInfo">
			<div class="panel-heading clearfix">
				<s:message code="contact.info" />
				<a id="editContactInfo" data-id="detailConcatInfo"
					customerId="${customerView.customer.id}"
					data-url="<%=basePath%>contact/view/${customerView.customer.id}"
					class="pull-right hide J_init" href="###">[ <s:message
						code="gcrm.title.customer.add" /> ]
				</a>
			</div>
			<div class="panel-body hide"></div>
		</div>
		<!--业务机会-->
		<div class="panel panel-gcrm marginBottom10" id="detailBusinessChance">
			<div class="panel-heading clearfix">
				<s:message code="opportunity.title" />
				<a id="editBusinessChance" data-id="detailBusinessChance"
					data-url="<%=basePath%>opportunity/preUpdateOpportunity/${customerView.customer.id}"
					class="pull-right hide J_init" href="###">[ <s:message
						code="gcrm.title.customer.add" /> ]
				</a>
			</div>
			<div id="J_businessChanceContainer" class="panel-body hide">
				<form id='businessChance' class='form-horizontal' method='POST'
					action='opportunity/preUpdateOpportunity?'></form>
			</div>
			<!--panel-body-->
			<div class="panel-footer hide" id="BusinessChanceSubmitBox">
				<a id="saveBusinessChance" href="###" class="btn btn-info"><s:message
						code="gcrm.title.customer.save" /></a> <a id="cancelBusinessChance"
					data-url="<%=basePath%>opportunity/reloadOpportunity/"
					data-id="detailBusinessChance" href="###" class="btn btn-default"><s:message
						code="gcrm.title.customer.cancel" /></a>
			</div>
		</div>
		<!-- end panel -->

		<!--代理商资历-->
		<div class="panel panel-gcrm hide marginBottom10"
			id="detailAgentQualification">
			<div class="panel-heading clearfix">
				<s:message code="qualification.title" />
				<a id="editAgentQualification" data-id="detailAgentQualification"
					data-url="<%=basePath%>qualification/gotoEditQualification/${customerView.customer.customerNumber}"
					class="pull-right hide J_init" href="###">[ <s:message
						code="gcrm.title.customer.add" /> ]
				</a>
			</div>
			<div class="panel-body hide" id="J_agentQualificationContainer">
				<form id='agentQualificationForm' class='form-horizontal'
					method='POST'></form>
			</div>
			<!--panel-body-->
			<div class="panel-footer hide" id="AgentQualificationSubmitBox">
				<a id="saveAgentQualification" customerNumber="${customerNumber}"
					href="###" class="btn btn-info"><s:message
						code="gcrm.title.customer.save" /></a> <a
					id="cancelAgentQualification" customerNumber="${customerNumber}"
					href="#" class="btn btn-default"><s:message
						code="gcrm.title.customer.cancel" /></a>
			</div>
		</div>
		<!-- end panel -->
		<!--资质认证材料-->
		<div class="panel panel-gcrm gcrm-relative marginBottom10"
			id="detailMaterials">
			<div class="panel-heading clearfix">
				<s:message code="materials.title" />
				<a id="editMaterials" class="pull-right hide J_init"
					data-id="detailMaterials"
					data-url="<%=basePath%>materials/gotoEditMaterials/${customerView.customer.customerNumber}"
					href="###">[ <s:message code="gcrm.title.customer.add" /> ]
				</a>
			</div>
			<div class="panel-body hide" id="J_materialsContainer"></div>
			<!--panel-body-->
			<div class="panel-footer hide" id="MaterialsSubmitBox">
				<a id="saveMaterials" href="###" class="btn btn-info"><s:message
						code="gcrm.title.customer.save" /></a> <a id="cancelMaterials"
					href="#" class="btn btn-default"><s:message
						code="gcrm.title.customer.cancel" /></a>
			</div>
		</div>
		<!-- end panel -->
		<div class="text-center">
			<div style="display: inline-block;">
				<a class="btn btn-default submit" disabled><s:message
						code="gcrm.title.customer.submit" /></a>
			</div>
		</div>
	</div>
</body>

<fis:scripts />
<fis:require name="resources/js/validateCustomerDetail.js"></fis:require>
<fis:require name="resources/lib/js/additional-methods.js"></fis:require>
<fis:require name="resources/js/detail.js"></fis:require>
<fis:require name="resources/js/detailBaseInfo.js"></fis:require>
<fis:require name="resources/js/newConcatInfo.js"></fis:require>

<fis:require name="resources/js/newBusinessChance.js"></fis:require>
<fis:require name="resources/js/newAgentQualification.js"></fis:require>

<fis:require name="resources/js/newMaterials.js"></fis:require>
<fis:require name="resources/js/detailMaterialsEmpty.js"></fis:require>
<fis:require name="resources/js/newCustomer.js"></fis:require>
</fis:html>