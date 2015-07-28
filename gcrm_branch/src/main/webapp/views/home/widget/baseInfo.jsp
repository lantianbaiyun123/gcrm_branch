<!--基本信息-->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="form-group">
    <label class="control-label col-md-2">公司实体名称：</label>
    <div class="col-md-3">
        <input placeholder="请输入一个公司实体名称" type="text" class="form-control" id="companyName" name="companyName" data-required="">
    </div>
    <div class="col-md-3 help-block">请输入一个公司实体名称</div>
</div>

<div class="form-group">
    <label class="control-label col-md-2" for="companySize">公司规模：</label>
    <div class="col-md-3">
        <select class="form-control" name="companySize" class="span2">
            <option selected="" value="0">50人以下</option>
            <option value="1">50~100人</option>
            <option value="2">100~200人</option>
            <option value="3">200~500人</option>
            <option value="4">500~1000人</option>
            <option value="5">1000人以上</option>
        </select>
        <!-- <span class="help-inline">请选择公司规模.</span> -->
    </div>
</div>

<div class="form-group">
    <label class="control-label col-md-2" for="type">客户类型：</label>
    <div class="col-md-3">
        <select class="form-control" name="type" class="span2" data-validate="type">
            <option value="0">广告代理（线下）</option>
            <option value="1">广告主—直客</option>
            <option value="2">广告主—非直客</option>
            <option value="3">网盟</option>
            <option selected="" value="-1">请选择</option>
        </select>
    </div>
    <div class="col-md-3 help-block">-------<s:message code="${errors['customer.companyName']}"/></div>
</div>

<div class="form-group">
    <label class="control-label col-md-2" for="registerTime">注册时间：</label>
    <div class="col-md-3">
        <input type="text" class="form-control" id="registerTime" name="registerTime" placeholder="请输入注册时间(dd-mm-yyyy)" data-validate="time">
        <!-- <span class="help-inline">请输入注册时间(dd-mm-yyyy).</span>  -->   
    </div>
    <div class="col-md-3 help-block">-------<s:message code="${errors['customer.companyName']}"/></div>
</div>

<div class="form-group">
    <label class="control-label col-md-2" for="belongSales">所属销售：</label>
    <div class="col-md-3">
        <input type="text" class="form-control" id="belongSales" name="belongSales" placeholder="请输入所属销售">
        <!-- <span class="help-inline">请输入所属销售.</span>     -->
    </div>
</div>


<div class="form-group">
    <label class="control-label col-md-2" for="currencyType">注册资金：</label>
    <div class="col-md-3">
        <div class="input-group">
            <div class="input-group-btn">
                <button name="currencyType" type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">￥<span class="caret"></span></button>
                <ul class="dropdown-menu">
                    <li><a href="#">￥</a></li>
                    <li><a href="#">$</a></li>
                </ul>
            </div><!-- /btn-group -->
            <input id="registerCapital" name="registerCapital" type="text" class="form-control" class="form-control">
        </div><!-- /input-group -->
    </div>    
</div>

<div class="form-group">
    <label class="control-label col-md-2" for="belongManager">直接上级：</label>
    <div class="col-md-3">
        <input type="text" class="form-control" id="belongManager" name="belongManager" placeholder="请输入所属销售">
        <!-- <span class="help-inline">请输入所属销售.</span>  -->   
    </div>
</div>

<div class="form-group">
    <label class="control-label col-md-2" for="country">所属国家：</label>
    <div class="col-md-3">
        <select  class="form-control" name="country" class="span2">
            <option value="巴西 Brazil">巴西 Brazil</option>
            <option value="墨西哥 Mexico">墨西哥 Mexico</option>
            <option value="阿根廷 Argentina">阿根廷 Argentina</option>
            <option value="埃及 Egypt">埃及 Egypt</option>
            <option value="沙特阿拉伯 Saudi Arabia">沙特阿拉伯 Saudi Arabia</option>
            <option value="阿联酋 United Arab Emirates">阿联酋 United Arab Emirates</option>
            <option value="科威特 Kuwait">科威特 Kuwait</option>
            <option value="泰国 Thailand">泰国 Thailand</option>
            <option value="越南 Vietnam">越南 Vietnam</option>
            <option value="菲律宾 Philippines">菲律宾 Philippines</option>
            <option value="台湾 TaiWan">台湾 TaiWan</option>
            <option value="印度尼西亚 Indonesia">印度尼西亚 Indonesia</option>
            <option value="马来西亚 Malaysia">马来西亚 Malaysia</option>
            <option value="澳大利亚 Australia">澳大利亚 Australia</option>
            <option value="新西兰 New Zealand">新西兰 New Zealand</option>
            <option value="全球 Global">全球 Global</option>
            <option selected="" value="请选择">请选择</option>
        </select>
        <!-- <span class="help-inline">请选择客户类型.</span> -->
    </div>
</div>

<div class="form-group">
    <label class="control-label col-md-2" for="businessScope">经营范围：</label>
    <div class="col-md-3">
        <input type="text" class="form-control" id="businessScope" name="businessScope" placeholder="请输入经营范围">
        <!-- <span class="help-inline">请输入经营范围.</span>  -->   
    </div>
</div>

<div class="form-group">
    <label class="control-label col-md-2" for="address">公司地址：</label>
    <div class="col-md-3">
        <input type="text" class="form-control" id="address" name="address" placeholder="请输入公司地址">
        <!-- <span class="help-inline">请输入公司地址.</span>  -->   
    </div>
</div>

<div class="form-group">
    <label class="control-label col-md-2" for="agentType">所属行业：</label>
    <div class="col-md-3">
        <select class="form-control" name="agentType" class="span2">
            <option value="互联网">互联网</option>
            <option value="互联网-视频">互联网-视频</option>
            <option selected="" value="请选择客户行业">请选择客户行业</option>    
        </select>
        <!-- <span class="help-inline">请选择所属行业.</span>    -->
    </div>    
</div>

<div class="form-group">
    <label class="control-label col-md-2" for="url">公司网址：</label>
    <div class="col-md-3">
        <input type="text" class="form-control" id="url" name="url" placeholder="请输入公司网址">
        <!-- <span class="help-inline">请输入公司网址.</span> -->    
    </div>
</div>

<div class="form-group">
    <label class="control-label col-md-2">业务类型：</label>
    <div class="col-md-3">
        <label class="checkbox checkbox-inline">
            <input type="checkbox" value="0" name="agentType" />销售
        </label>
        <label class="checkbox checkbox-inline">
            <input type="checkbox" value="1" name="agentType" />变现
        </label>
    </div>
</div>

<div class="form-group">
    <label class="control-label col-md-2">公司描述：</label>
    <div class="col-md-5">
        <textarea class="form-control" rows="3" cols="30" placeholder="简单介绍下自己吧！"></textarea>
    </div>
</div>

<div class="form-group">
    <label class="control-label col-md-2">代理类型：</label>
    <div class="col-md-3">
        <label class="radio radio-inline">
            <input type="radio" value="0" name="agentType" />独家代理
        </label>
        <label class="radio radio-inline">
            <input type="radio" value="1" name="agentType" />普通代理
        </label>
        <label class="radio radio-inline">
            <input type="radio" value="2" name="agentType" />二级代理
        </label>
    </div>
</div>

<div class="form-group">
    <label class="control-label col-md-2">代理区域：</label>
    <div class="col-md-5">
        <label class="radio radio-inline">
            <input type="radio" value="0" name="agentRegional" />西葡语区
        </label>
        <label class="radio radio-inline">
            <input type="radio" value="1" name="agentRegional" />阿语区
        </label>
        <label class="radio radio-inline">
            <input type="radio" value="2" name="agentRegional" />印尼语区
        </label>
        <label class="radio radio-inline">
            <input type="radio" value="3" name="agentRegional" />泰语区
        </label>
    </div>
</div>

<div class="form-group">
    <label class="control-label col-md-2" for="agentCompany">所属代理商：</label>
    <div class="col-md-3">
        <select class="form-control" name="agentCompany" class="span2">
            <option value="互联网">互联网</option>
            <option value="互联网-视频">互联网-视频</option>
            <option selected="" value="请选择代理商">请选择代理商</option>
        </select>
        <label class="checkbox checkbox-inline">
            <input type="checkbox" value="3" name="agentRegional" />新增代理商
        </label>
    </div>    
</div>

<fis:script>
    
/*
    内容筛选器 参数：select 控件的原始id和change后的id
*/
$.gcrm_filter = function(orign, dest){
    var tem_obj = {
        "-1":[
            $("[name='registerTime']")
        ],
        "2":[
            $("[name='companySize']")
        ]
    };

    var reset = function(t_arr){
        for (var i = t_arr.length - 1; i >= 0; i--) {
            var $el = t_arr[i];
            if($el.is("select")){
                $el.val(-1);
            }else{
                $el.val("");
            }
            $el.closest(".form-group").hide();
        };
    };

    var init = function(t_arr){
        for (var i = t_arr.length - 1; i >= 0; i--) {
            var $el = t_arr[i];
            
            $el.closest(".form-group").show();
        };
    }
    reset(tem_obj[orign]);
        
    init(tem_obj[dest]);

}
$(function(){
    $("[name='type']").change(function(){
        $.gcrm_filter(-1, 2);   
    })
})

</fis:script>