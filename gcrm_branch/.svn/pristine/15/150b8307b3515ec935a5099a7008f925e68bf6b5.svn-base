<!--增加新联系人-->
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
	<table class="table table-gcrm" id="ContactTable-empty" style="visibility:hidden;">
	<thead>
		<tr>
			<th>联系人姓名</th>
			<th class="w-80">性别</th>
			<th>职务</th>
			<th>上级</th>
			<th>所在部门</th>
			<th>手机</th>
			<th>固定电话</th>
			<th>E-mail</th>
			<th class="w-50">法人</th>
			<th class="w-50">决策人</th>
			<th class="w-80">操作</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td><input class="form-control" type="text" data-required="true" ></td>
			<td><select class="form-control J_sex" ><option>男</option><option>女</option><option>其他</option></select></td>
			<td><input class="form-control" type="text" ></td>
			<td><input class="form-control" type="text" ></td>
			<td><input class="form-control" type="text" ></td>
			<td><input class="form-control" type="text" placeholder="手机"   data-required data-pattern="^(13[0-9]{9})| (18[0-9]{9}) |(15[89][0-9]{8})$"></td>
			<td><input class="form-control" type="text" ></td>
			<td><input class="form-control" type="text"  placeholder="Email" data-required data-pattern="^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$"></td>
			<td><input class="form-control" type="checkbox" ></td>
			<td><input class="form-control" type="checkbox" ></td>
			<td>
				<a class="form-control-static J_delContact" href="?id=1" >删除</a>
				<a class="form-control-static J_submitContact"  href="?id=1">添加</a>
			</td>
		</tr>
	</tbody>
</table>
<div class="addContactBtn" style="display:none;">
	<!-- Indicates a successful or positive action -->
	<button type="button" class="btn btn-success" id="J_addContactBtn"><span class="glyphicon glyphicon-plus"></span> 新增一行</button>
</div>
<div class="text-center">
	<div class="jumbotron" style="background-color:#fff;">
		<P>很抱歉！暂时没有内容</p>
		<button type="button" class="btn btn-success" id="addContact"><span class="glyphicon glyphicon-plus"></span> 添加内容</button>
	</div>
</div>
		
		
