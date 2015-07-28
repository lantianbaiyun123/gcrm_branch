<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
<head>
    <title>Hello World!</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
     <%@include file="../include/common.jsp" %>
    <link type="text/css" rel="Stylesheet" href="<%=basePath%>resources/css/signin.css" media="all"/>
    <style type="text/css">
    	.control-group {
    		
    	}
    </style>
</head>
<body>
<%@include file="../include/header.jsp" %>
<!-- Page content -->
<div class="container">
<div class="container">
	<h2>基本信息</h2>
	<form class="form-horizontal" method="POST"  action="<%=basePath%>customer/addNewCustomer">
		<div class="control-group">
			<label class="control-label" for="companyName">公司实体名称:</label>
			<div class="controls">
				<input type="text" id="companyName" name="companyName" value="${customer.companyName}">
				<span class="help-inline">dd
				<s:message code=""/>
				<s:hasBindErrors name="companyName">
					<b>please fix all errors!</b>
				</s:hasBindErrors>

				${companyName}</span> 
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label" for="companySize" >公司规模</label>
			<div class="controls">
				<select name="companySize" class="span2">
					<option selected="" value="less_50">50人以下</option>
					<option value="1">50~100人</option>
					<option value="2">100~200人</option>
					<option value="3">200~500人</option>
					<option value="4">500~1000人</option>
					<option value="5">1000人以上</option>
				</select>
				<span class="help-inline">请选择公司规模.</span>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label" for="type">客户类型</label>
			<div class="controls">
				<select name="type" class="span2">
					<option value="0">广告代理（线下）</option>
					<option value="1">广告主—直客</option>
					<option value="2">广告主—非直客</option>
					<option value="3">网盟</option>
					<option selected="" value="-1">请选择</option>
				</select>
				<span class="help-inline">请选择客户类型.</span>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label" for="registerTime">注册时间:</label>
			<div class="controls">
				<input type="text" id="registerTime" name="registerTime">
				<span class="help-inline">请输入注册时间(dd-mm-yyyy).</span>    
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label" for="belongSales">所属销售:</label>
			<div class="controls">
				<input type="text" id="belongSales" name="belongSales" value="${customer.belongSales}">
				<span class="help-inline">请输入所属销售.</span>    
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label" for="currencyType">注册资金</label>
			<div class="controls">
				<select name="currencyType" class="span2" style="width:50px;">
					<option value="0">￥</option>
					<option value="1">$</option>
				</select>
				<input type="text" id="registerCapital" name="registerCapital">
				<span class="help-inline">请输入注册资金.</span>   
			</div>    
		</div>
		
		<div class="control-group">
			<label class="control-label" for="belongManager">直接上级:</label>
			<div class="controls">
				<input type="text" id="belongManager" name="belongManager">
				<span class="help-inline">请输入直接上级.</span>    
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label" for="country">所属国家</label>
			<div class="controls">
				<select name="country" class="span2">
					<option value="1">巴西 Brazil</option>
					<option value="2">墨西哥 Mexico</option>
				</select>
				<span class="help-inline">请选择客户类型.</span>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label" for="businessScope">经营范围:</label>
			<div class="controls">
				<input type="text" id="businessScope" name="businessScope">
				<span class="help-inline">请输入经营范围.</span>    
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label" for="address">公司地址:</label>
			<div class="controls">
				<input type="text" id="address" name="address">
				<span class="help-inline">请输入公司地址.</span>    
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">代理类型</label>
			<div class="controls">
				<label class="radio inline">
					<input type="radio" value="0" name="agentType" />独家代理
				</label>
				<label class="radio inline">
					<input type="radio" value="1" name="agentType" />普通代理
				</label>
				<label class="radio inline">
					<input type="radio" value="2" name="agentType" />二级代理
				</label>
			</div>
		</div>
			
	
		<div class="control-group">
			<label class="control-label" for="url">公司网址:</label>
			<div class="controls">
				<input type="text" id="url" name="url">
				<span class="help-inline">请输入公司网址.</span>    
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">代理区域</label>
			<div class="controls">
				<label class="radio inline">
					<input type="radio" value="0" name="agentRegional" />西葡语区
				</label>
				<label class="radio inline">
					<input type="radio" value="1" name="agentRegional" />阿语区
				</label>
				<label class="radio inline">
					<input type="radio" value="2" name="agentRegional" />印尼语区
				</label>
				<label class="radio inline">
					<input type="radio" value="3" name="agentRegional" />泰语区
				</label>
			</div>
		</div>
	
		<div class="control-group">
			<label class="control-label">业务类型</label>
			<div class="controls">
				<label class="checkbox inline">
					<input type="checkbox" value="0" name="agentType" />销售
				</label>
				<label class="checkbox inline">
					<input type="checkbox" value="1" name="agentType" />变现
				</label>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">公司描述:</label>
			<div class="controls">
				<textarea rows="3" placeholder="简单介绍下自己吧！"></textarea>
			</div>
		</div>

		<input type="submit" id="createCustomer" class="btn btn-large btn-primary" value="保存客户" /> 
	</form>
	</div>
</div>
</body>
</html>
