<!--联系人信息-->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<table class="table table-gcrm">
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
            <td><input class="form-control" type="text" data-required="true" disabled></td>
            <td><select class="form-control sex" disabled><option>男</option><option>女</option><option>其他</option></select></td>
            <td><input class="form-control" type="text" disabled></td>
            <td><input class="form-control" type="text" disabled></td>
            <td><input class="form-control" type="text" disabled></td>
            <td><input class="form-control" type="text" placeholder="手机" disabled  data-required data-pattern="^(13[0-9]{9})| (18[0-9]{9}) |(15[89][0-9]{8})$"></td>
            <td><input class="form-control" type="text" disabled></td>
            <td><input class="form-control" type="text" disabled placeholder="Email" data-required data-pattern="^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$"></td>
            <td><input class="form-control" type="checkbox" disabled></td>
            <td><input class="form-control" type="checkbox" disabled></td>
            <td><a class="form-control-static delContact" href="?id=1" >删除</a> <a class="form-control-static editContact"  href="?id=1">编辑</a></td>
        </tr>
        <tr>
            <td><input class="form-control" type="text" data-required="true" disabled></td>
            <td><select class="form-control sex" disabled><option>男</option><option>女</option><option>其他</option></select></td>
            <td><input class="form-control" type="text" disabled></td>
            <td><input class="form-control" type="text" disabled></td>
            <td><input class="form-control" type="text" disabled></td>
            <td><input class="form-control" type="text" placeholder="手机" disabled  data-required data-pattern="^(13[0-9]{9})| (18[0-9]{9}) |(15[89][0-9]{8})$"></td>
            <td><input class="form-control" type="text" disabled></td>
            <td><input class="form-control" type="text" disabled placeholder="Email" data-required data-pattern="^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$"></td>
            <td><input class="form-control" type="checkbox" disabled></td>
            <td><input class="form-control" type="checkbox" disabled></td>
            <td><a class="form-control-static delContact" href="?id=1" >删除</a> <a class="form-control-static editContact" href="?id=2">编辑</a></td>
        </tr>
		<tr>
            <td><input class="form-control" type="text" data-required="true" disabled></td>
            <td><select class="form-control sex" disabled><option>男</option><option>女</option><option>其他</option></select></td>
            <td><input class="form-control" type="text" disabled></td>
            <td><input class="form-control" type="text" disabled></td>
            <td><input class="form-control" type="text" disabled></td>
            <td><input class="form-control" type="text" placeholder="手机" disabled  data-required data-pattern="^(13[0-9]{9})| (18[0-9]{9}) |(15[89][0-9]{8})$"></td>
            <td><input class="form-control" type="text" disabled></td>
            <td><input class="form-control" type="text" disabled placeholder="Email" data-required data-pattern="^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$"></td>
            <td><input class="form-control" type="checkbox" disabled></td>
            <td><input class="form-control" type="checkbox" disabled></td>
            <td><a class="form-control-static delContact" href="?id=3">删除</a> <a class="form-control-static editContact" href="?id=4">编辑</a></td>
        </tr>
		<tr>
            <td><input class="form-control" type="text" data-required="true" disabled></td>
            <td><select class="form-control sex" disabled><option>男</option><option>女</option><option>其他</option></select></td>
            <td><input class="form-control" type="text" disabled></td>
            <td><input class="form-control" type="text" disabled></td>
            <td><input class="form-control" type="text" disabled></td>
            <td><input class="form-control" type="text" placeholder="手机" disabled  data-required data-pattern="^(13[0-9]{9})| (18[0-9]{9}) |(15[89][0-9]{8})$"></td>
            <td><input class="form-control" type="text" disabled></td>
            <td><input class="form-control" type="text" disabled placeholder="Email" data-required data-pattern="^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$"></td>
            <td><input class="form-control" type="checkbox" disabled></td>
            <td><input class="form-control" type="checkbox" disabled></td>
            <td><a class="form-control-static delContact" href="?id=4">删除</a> <a class="form-control-static editContact"  href="?id=4">编辑</a></td>
        </tr>
      
    </tbody>
</table>
<div>
    <!-- Indicates a successful or positive action -->
    <button type="button" class="btn btn-success"><span class="glyphicon glyphicon-plus"></span> 新增一行</button>
</div>

<fis:script>
/*!
    编辑联系人
*/
$.gcrm.editContact = function(parentNode){
	var operateArea=parentNode || {};
	var editUrl="http://localhost:8080/gcrm/views/demo/index.jsp";
    var delUrl="http://localhost:8080/gcrm/views/demo/index.jsp";
	var contactId="";
   //$("#contactsEdit").delegate("a","click",function(){
    operateArea.delegate("a","click",function(){
        var $self = $(this);
        var parTem=$(this).closest("tr");
        var inputList=parTem.find("input");
        var phoneRegExp=/^(13[0-9]{9})| (18[0-9]{9}) |(15[89][0-9]{8})$/g;
        var emailRegExp=/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/g;
        contactId=$self.attr("href").substring($self.attr("href").indexOf("?id=")+4,$self.attr("href").length);
        
        if($self.hasClass("editContact")){
            $.each(inputList,function(index,value){
                $(value).removeAttr("disabled");
            });
            
            $(parTem.find('.sex')[0]).removeAttr("disabled");
            $(parTem.find('button.selectpicker')[0]).removeClass().addClass("btn dropdown-toggle selectpicker btn-default");
            $.each(parTem.find('ul.selectpicker>li'),function(index,value){
                $(value).removeClass("disabled");
            });
            $self.html("确认");
            if($self.hasClass("editContact"))
                $self.removeClass("editContact").addClass("editOK");
            
        }else if($self.hasClass("editOK")){
            if(phoneRegExp.test(inputList[4].value)){
				if(emailRegExp.test(inputList[6].value)){
					var postDate={
						"id":contactId,
						"name":encodeURI(inputList[0].value),
						"sex":parTem.find('select')[0].selectedIndex,
						"job":encodeURI(inputList[1].value),
						"leader":encodeURI(inputList[2].value),
						"class":encodeURI(inputList[3].value),
						"mobilePhone":inputList[4].value,
						"phone":inputList[5].value,
						"email":inputList[6].value,
						"artificialPerson":inputList[7].checked,
						"decisionMaker":inputList[8].checked
					};
					$.post(editUrl,postDate,function(){
						$.each(inputList,function(index,value){
							$(value).attr("disabled","true");
						});
						$(parTem.find('.sex')[0]).attr("disabled","true");
						$(parTem.find('button.selectpicker')[0]).removeClass().addClass("btn dropdown-toggle selectpicker disabled btn-default");
						$.each(parTem.find('ul.selectpicker>li'),function(index,value){
							$(value).addClass("disabled");
						});
						
						$self.html("编辑");
						if($self.hasClass("editOK"))
							$self.removeClass("editOK").addClass("editContact");
					}); 
					$(parTem.find('input[placeholder="Email"]:eq(0)')).closest("td").removeClass("has-error").addClass("has-success");
					$(parTem.find('input[placeholder="手机"]:eq(0)')).closest("td").removeClass("has-error").addClass("has-success");
				}
				else
				{
					$(parTem.find('input[placeholder="Email"]:eq(0)')).closest("td").removeClass("has-success").addClass("has-error");
				}
            }
            else{
                if(!emailRegExp.test(inputList[6].value)){
					$(parTem.find('input[placeholder="Email"]:eq(0)')).closest("td").removeClass("has-success").addClass("has-error");
					$(parTem.find('input[placeholder="手机"]:eq(0)')).closest("td").removeClass("has-success").addClass("has-error");
				}
				else{
					$(parTem.find('input[placeholder="手机"]:eq(0)')).closest("td").removeClass("has-success").addClass("has-error");
				}
            }
        }else if($self.hasClass("delContact")){
			$(".delContact").popover("hide");
            /*var postDate={
                    "id":contactId
                };
            $.post(delUrl,postDate,function(){
                $($self.closest("tr")).remove();
            });*/
			
        }
        return false;
    });
	(function(){
		$(".delContact").popover({
			'html':true,
			'content':"<p class='text-center popoverButtons' style='margin:0;'><a href='#' class='btn btn-primary  btn-md delContactOk'>确认</a>  <a href='#' class='btn btn-default btn-md delContactCancel' >取消</a></p>",
			'trigger':"click",
			'placement':"left",
			'title':"确定要删除联系人吗？",
			'delay':{show:100,hide:100}
		});
		$('.delContact').on('shown.bs.popover', function (){
			var $self=$(this);
			var buttonInPopover=$self.next(".popover").find("div.popover-content>p.popoverButtons:eq(0)");
			$self.next(".popover").css("zIndex",1011);
			buttonInPopover.find("a.delContactOk:eq(0)").on("click",function(){
				  //var contactId=$self.attr("href").substring($self.attr("href").indexOf("?id=")+4,$self.attr("href").length);
				  //var delUrl="http://localhost:8080/gcrm/views/demo/index.jsp";
				  var postDate={
                    "id":contactId
					};
				  $.post(delUrl,postDate,function(){
						$($self.closest("tr")).remove();
				  });
				  
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
	})();
	$(document).delegate("*","click",function(){
		$(".delContact").popover("hide");
	});
}

$.gcrm.editContact($("#contactsEdit"));
</fis:script>