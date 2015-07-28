<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

        <form class="form-horizontal" role="form">
            <div class="col-md-2">
                <div class="col-md-2"><p class="form-control-static">按:</p></div>
                <div class="col-md-10">
                    <select class="form-control" id="market" name="market">
                        <option selected="" value="0">全部</option>
                        <option value="1">所属销售</option>
                        <option value="2">所属代理商</option>
                        <option value="3">公司实体名称</option>
                        <option value="4">客户代理商编号</option>
                    </select>
                </div>
            </div>

            <div class="col-md-1">
                <input type="text" class="form-control" name="markValue" id="markValue" >
            </div>

            <div class="col-md-2">
                <div class="col-md-5"><p class="form-control-static">代理地区:</p></div>
                <div class="col-md-7">
                    <select class="form-control" name="region" id="region">
                        <option selected=""  value="0">全部</option>
                        <option value="1">阿语区</option>
                        <option value="2">西葡语区</option>
                        <option value="3">泰语区</option>
                        <option value="4">印尼语区</option>
                    </select>
                </div>
            </div>
            <div class="col-md-2">
                <div class="col-md-5"><p class="form-control-static">审核状态:</p></div>
                <div class="col-md-7">
                    <select class="form-control" id="auditState" name="auditState">
                        <option selected="" value="0">全部</option>
                        <option value="1">待提交</option>
                        <option value="2">审核中</option>
                        <option value="3">审核通过</option>
                        <option value="4">审核驳回</option>
                    </select>
                </div>
            </div>
            <div class="col-md-2">
                <div class="col-md-5"><p class="form-control-static">所属国家:</p></div>
                <div class="col-md-7">
                    <select class="form-control" id="country" name="country">
                        <option selected="" value="0">全部</option>
                        <option value="1">中国</option>
                        <option value="2">日本</option>
                        <option value="3">美国</option>
                    </select>
                </div>
            </div>
            <div class="col-md-2">
                <div class="col-md-5"><p class="form-control-static">客户类型:</p></div>
                <div class="col-md-7">
                    <select class="form-control" id="clientType" name="clientType">
                        <option selected="" value="0">全部</option>
                        <option value="1">广告主-直客</option>
                        <option value="2">广告主-直客</option>
                        <option value="3">广告主-直客</option>
                    </select>
                </div>
            </div>
            <button type="button" class="btn btn-info" id="selectSub">查询</button> 
        </form>
        
         