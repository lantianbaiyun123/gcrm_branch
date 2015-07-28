<!--增加新联系人-->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!--<ul class="nav nav-tabs">
  <li class="active"><a href="#">联系人</a></li>
  <li><a href="#">业务机会</a></li>
  <li><a href="#">广告方案</a></li>
  <li><a href="#">合同/PO</a></li>
  <li><a href="#">资质认证材料</a></li>
  <li><a href="#">账号信息</a></li>
  <li><a href="#">代理商资历</a></li>
</ul>-->
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
		
    </tbody>
</table>
<div class="addContactBtn" style="display:none;">
    <!-- Indicates a successful or positive action -->
    <button type="button" class="btn btn-success"><span class="glyphicon glyphicon-plus"></span> 新增一行</button>
</div>
<div class="text-center">
	<div class="jumbotron" style="background-color:#fff;">
		<P>很抱歉！暂时没有内容</p>
		<button type="button" class="btn btn-success" id="addContact"><span class="glyphicon glyphicon-plus"></span> 添加内容</button>
	</div>
</div>

<fis:script>
	$.gcrm.addContact = function(){
		var thisTrMount=0;
		var trNode=['<tr>',
				 '	<td><input class="form-control" type="text" data-required="true" ></td>',
				 '	<td><select class="form-control sex" ><option>男</option><option>女</option><option>其他</option></select></td>',
				 ' 	<td><input class="form-control" type="text" ></td>',
				 '	<td><input class="form-control" type="text" ></td>',
				 '	<td><input class="form-control" type="text" ></td>',
				 '	<td><input class="form-control" type="text" placeholder="手机" data-required data-pattern="^(13[0-9]{9})| (18[0-9]{9}) |(15[89][0-9]{8})$"></td>',
				 '	<td><input class="form-control" type="text" ></td>',
				 '	<td><input class="form-control" type="text"  placeholder="Email" data-required data-pattern="^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$"></td>',
				 '	<td><input class="form-control" type="checkbox" ></td>',
				 '	<td><input class="form-control" type="checkbox" ></td>',
				 '	<td><a class="form-control-static delContact" href="?id=1" >删除</a> <a class="form-control-static editContact" href="?id=2">添加</a></td>',
				 '</tr>'
			];
		var emptyTable=$("#ContactTable-empty");
		var emptyTbody=emptyTable.find("tbody:eq(0)");
		$("#addContact").on("click",function(){
			$(trNode.join("")).appendTo(emptyTbody);
			thisTrMount++;
			$("#contactAdd-empty select").selectpicker({});
			$.gcrm.editContact($("#contactAdd-empty"));
			decideFun();
			$(this).closest("div.text-center").css("display","none");
			emptyTable.css("visibility","visible");
			$(this).closest("div.panel-body").find("div.addContactBtn").css("display","block");
		});
		
		var tempVar=$(emptyTable.closest(".panel-body").find('div.addContactBtn button:eq(0)'));
		tempVar.on("click",function(){
			thisTrMount++;
			if(thisTrMount>=10)
				$("#contactAdd-empty .addContactBtn button:eq(0)").addClass("disabled");
			$(trNode.join("")).appendTo(emptyTbody);
			$("#contactAdd-empty tr:last select").selectpicker({});
			decideFun();
		});
		var decideFun=function(){
		$(".delContact").popover({
			'html':true,
			'content':"<p class='text-center popoverButtons' style='margin:0;'><a href='#' class='btn btn-primary  btn-md delContactOk'>确认</a>  <a href='#' class='btn btn-default btn-md delContactCancel' >取消</a></p>",
			'trigger':"click",
			'placement':"left",
			'title':"确定要删除联系人吗？",
			'delay':{show:100,hide:100}
		});
		 
		$('#contactAdd-empty .delContact:last').on('shown.bs.popover', function (){
			var $self=$(this);
			var buttonInPopover=$self.next(".popover").find("div.popover-content>p.popoverButtons:eq(0)");
			$self.next(".popover").css("zIndex",1011);
			buttonInPopover.find("a.delContactOk:eq(0)").on("click",function(){
				  var contactId=$self.attr("href").substring($self.attr("href").indexOf("?id=")+4,$self.attr("href").length);
				  var delUrl="http://localhost:8080/gcrm/views/demo/index.jsp";
					
				  var postDate={
                    "id":contactId
					};
				  $.post(delUrl,postDate,function(){
						$($self.closest("tr")).remove();
				  });
				  thisTrMount--;
				  if(thisTrMount<=10)
					$("#contactAdd-empty .addContactBtn button").removeClass("disabled");
				  else if(thisTrMount<=0){
					 
					$("#contactAdd-empty div.text-center:eq(0)").css("display","block");
					emptyTable.css("visibility","hidden");
					$("#contactAdd-empty div.addContactBtn:eq(0)").css("display","none");	
				  }
				 
				  return false;
				  
			});
			buttonInPopover.find("a.delContactCancel:eq(0)").on("click",function(){
				 $self.popover("hide");
				 return false;
			});
		});
		$('.delContact').on('hidden.bs.popover', function (){
			$(this).next(".popover").css("zIndex",1010);
		
		});
	};
	}
	$.gcrm.addContact();
</fis:script>