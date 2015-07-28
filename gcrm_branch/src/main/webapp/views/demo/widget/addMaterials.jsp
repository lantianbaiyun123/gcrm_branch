<!--资质认证材料-->
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<table id="materialsTable" class="table table-striped">
    <thead>
        <tr>
            <th class="col-md-2">类型</th>
            <th class="col-md-3">文件内容</th>
            <th class="col-md-3">进度</th>
            <th class="col-md-1">操作</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td class="col-md-2">
                <select class="form-control">
                    <option value="0">广告平台1</option>
                    <option value="1">广告平台2</option>
                    <option value="2">广告平台3</option>
                    <option value="3">广告平台4</option>
                </select>
            </td>
            <td class="col-md-3">
                <span class="btn btn-success fileinput-button">
                    <i class="glyphicon glyphicon-plus"></i>
                    <span>添 加</span>
                    <!-- The file input field used as target for the file upload widget -->
                    <input id="fileupload" type="file" name="files[]" data-url="server/php/" multiple>
                </span>
            </td>
            <td class="col-md-3"></td>
            <td class="col-md-1"></td>
        </tr>
    </tbody>
 </table>