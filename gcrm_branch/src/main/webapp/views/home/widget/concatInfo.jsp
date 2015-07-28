<!--联系人信息-->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<table class="table">
    <thead>
        <tr>
            <th>联系人姓名</th>
            <th>性别</th>
            <th>职务</th>
            <th>上级</th>
            <th>所在部门</th>
            <th>手机</th>
            <th>固定电话</th>
            <th>E-mail</th>
            <th class="col-xs-1">法人</th>
            <th class="col-xs-1">决策人</th>
            <th class="col-xs-1">操作</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td><input class="form-control" type="text"></td>
            <td class="col-xs-1"><select class="form-control"><option>男</option><option>女</option><option>其他</option></select></td>
            <td><input class="form-control" type="text"></td>
            <td><input class="form-control" type="text"></td>
            <td><input class="form-control" type="text"></td>
            <td><input class="form-control" type="text"></td>
            <td><input class="form-control" type="text"></td>
            <td><input class="form-control" type="text"></td>
            <td><input class="form-control" type="checkbox"></td>
            <td><input class="form-control" type="checkbox"></td>
            <td><a href="###">删除</a></td>
        </tr>
        <tr>
            <td><input class="form-control" type="text"></td>
            <td><select class="form-control"><option>男</option><option>女</option><option>其他</option></select></td>
            <td><input class="form-control" type="text"></td>
            <td><input class="form-control" type="text"></td>
            <td><input class="form-control" type="text"></td>
            <td><input class="form-control" type="text"></td>
            <td><input class="form-control" type="text"></td>
            <td><input class="form-control" type="text"></td>
            <td><input class="form-control" type="checkbox"></td>
            <td><input class="form-control" type="checkbox"></td>
            <td><a href="###">删除</a></td>
        </tr>
      
    </tbody>
</table>
<div>
    <!-- Indicates a successful or positive action -->
    <button type="button" class="btn btn-success"><span class="glyphicon glyphicon-plus"></span> 新增一行</button>
</div>