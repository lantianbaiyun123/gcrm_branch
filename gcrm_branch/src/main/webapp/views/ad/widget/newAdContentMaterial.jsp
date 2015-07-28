<!--物料信息-->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <div class="form-group">
        <label class="control-label col-md-2">是否嵌入代码：</label>
        <div class="col-md-2">
            <label class="radio radio-inline">
                <input type="radio" name="codeStatues" value="0" id="codeStatues1">是
            </label>
            <label class="radio radio-inline">
                <input type="radio" name="codeStatues" value="1" checked="checked" id="codeStatues2">否
            </label>
        </div>
    </div>
    <div class="form-group materialTitle">
        <label class="control-label col-md-2">物料title：</label>
        <div class="col-md-3">
            <input placeholder="" type="text" class="form-control" id="materialTitle" name="materialTitle" required />
        </div>
        <div class="col-md-3 help-block"></div>
    </div>
    <div class="form-group">
        <label class="control-label col-md-2">url：</label>
        <div class="col-md-3">
            <input placeholder="" type="text" class="form-control" id="material" name="material" required />
        </div>
        <div class="col-md-3 help-block"></div>
    </div>
    <div class="materialMsg">
        <div class="form-group">
            <label class="control-label col-md-2">物料详细：</label>
            <div class="col-md-10 form-control-static">
                <div class="col-md-5 form-group"><span class="alert alert-info uploadSize"></span> </div>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-md-2">&nbsp;</label>
            <div class="col-md-9 form-control-static">
                <table id="materialsTable" class="table table-gcrm">
                    <thead>
                        <tr>
                            <th class="col-md-8 text-left">文件内容</th>
                            <th class="col-md-4">操作</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td class="col-md-8">
                                <span class="btn btn-link fileinput-button pull-left">
                                    <i class="glyphicon glyphicon-plus"></i>
                                    <span>新增附件</span>
                                    <!-- The file input field used as target for the file upload widget -->
                                    <input id="fileupload" type="file" name="files[]" data-url="server/php/" multiple>
                                </span>
                            </td >
                            <td class="col-md-4"></td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <div class="form-group codeContent hide">
        <div class="form-group">
            <label class="control-label col-md-2">代码内容：</label>
            <div class="col-md-5">
                <textarea class="form-control" name="adCode" required rows="3" cols="30"></textarea>
            </div>
            <div class="col-md-3 help-block"></div>
        </div>
    </div>