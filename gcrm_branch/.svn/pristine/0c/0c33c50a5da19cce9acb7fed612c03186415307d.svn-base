//newBaseInfo ;
define(function(require, exports, module) {
  	// 模块代码

  
    // var baseInfoSaveUrl="#";
    // var baseInfoSaveLoadUrl="widget/updateBaseInfo.jsp";
    
  	exports.save = function(options){
        var setValidate={
            rules:{
                companyName:"required",
                contract:"required",
                budget:"required",
                periodStrat:"required",
                periodEnd:"required"
            }
        };

        var ops = this.ops, 
            $panel = ops.panel;
            $panelBody = $panel.find(".panel-body");

        var postData = $panelBody.find("form").serialize();
        var $panelBodyForm = $panelBody.find("form");

        var formState = $.gcrm.validate_form($panelBodyForm, setValidate);

        var $companyForm=$panel.find(".panel-body").find("form");
        $('.date-time').datetimepicker().on('hide', function(){
            var formState=$.gcrm.validate_form($companyForm,{
                rules:{
                    "periodStrat":"required",
                    "periodEnd":"required"
                }
            });
            if(formState==false){
                return;
            }
        });
        if(formState == false){
            return;
        } 

        //!todo暂存和保存、编辑生成id，如果id存在，回传回去
        $.ajax({
            type: "POST",
            url: "adbaseinfo/submit",
            data: postData,
            success:function(msg){
            	if(msg.success){
                	var advertiseSolution = msg.retBean.advertiseSolutionView.advertiseSolution;
                	 $panelBody.load("adbaseinfo/view/"+advertiseSolution.id,function(){
                         options.success(advertiseSolution.id);
                     });
                    $("#adSolutionId").val(advertiseSolution.id);
                    $("#adSolutionNumber").val(advertiseSolution.number);
                }else{
                	$.each(msg.errors,function(i,item){
                		var $dom = $('[name="'+i+'"]').closest(".form-group").find(".help-block");
                		$dom.text(GCRM.util.message(item));
                	});
                }          
            }
        });

  	};
  	
    exports.tempSave = function(options){

        var ops = this.ops, 
            $panel = ops.panel,
            $panelBody = $panel.find(".panel-body");

        var postData = $panelBody.find("form").serialize();
        $.ajax({
            type: "POST",
            url: "adbaseinfo/save",
            data: postData,
            success:function(msg){
                if(msg.success){
                	var advertiseSolution = msg.retBean.advertiseSolutionView.advertiseSolution;
                    options.success(advertiseSolution.id);
                    $("#adSolutionId").val(advertiseSolution.id);
                    $("#adSolutionNumber").val(advertiseSolution.number);
                }else{
                	$.each(msg.errors,function(i,item){
                		var $dom = $('[name="'+i+'"]').closest(".form-group").find(".help-block");
                		$dom.text(GCRM.util.message(item));
                	});
                }
            }
        });
    };

  	exports.edit = function(options){

        var ops = this.ops, 
            $panel = ops.panel,
            $panelBody = $panel.find(".panel-body");
        var id=$("#adSolutionId").val();
        if(!id){
        	id = "";
        }
        $.get("adbaseinfo/preSave?id="+id,function(data){
            //重新获取四个应用
            var $el = $(data);
            $contractInfo = $el.find("#contractInfo");
            $contractNumber = $el.find("#contractNumber");
            $companyInfo = $el.find("#companyInfo");
            $companyName = $el.find("#companyName");

            companyNameSuggestion( $companyInfo , $companyName );
            contractNumberSuggestion( $contractInfo , $contractNumber );

            //初始化合同编号切换
            initagentTypeBoxTab($el);
            //初始化Date Picker
            initDateTimePicker($el); 
            options.success();

            $panelBody.empty().append($el);
            
        });
        
  	};

  	exports.cancel = function(options){
  		
        var ops = this.ops, 
            $panel = ops.panel,
            $panelBody = $panel.find(".panel-body");
        var id=$("#adSolutionId").val();
        if(!id){
        	id = "";
        }
        $panelBody.load("adbaseinfo/view/"+id,{}, function(){

            options.success();
        });
  	};

    var hideInfo=function(operObj){
        var infoBox=operObj || {};
        if(!infoBox.hasClass("hide"))
            infoBox.addClass("hide");
    };
    var showInfo=function(operObj){
        var infoBox=operObj || {};
        if(infoBox.hasClass("hide"))
            infoBox.removeClass("hide");
    };

    //DateTime Picker 初始化
    var initDateTimePicker=function($el){
        
        var $dt = $el.find(".date-time"),
            locale = $dt.attr('locale') || {};
        $dt.datetimepicker({
            language : locale,
            weekStart: 1,
            todayBtn:  0,
            autoclose: 1,
            todayHighlight: 1,
            startView: 2,
            minView: 2,
            forceParse: 0,
            format: 'dd-mm-yyyy',
            pickerPosition:"bottom-right"
        });
    };
    var initagentTypeBoxTab=function($el){
        $el.delegate("input[name='hasContract']","change",function(){
            var $agentTypeBox=$("#agentTypeBox");
            if($(this).val()==1){
                $agentTypeBox.removeClass("hide");
            }else{
                $agentTypeBox.addClass("hide");
            }
        });
    };
    var companyNameSuggestion = function($companyInfo , $companyName){
        /*公司实体名称Suggestion*/
        //hideInfo($companyInfo);
        $companyName.autocomplete({
            serviceUrl: 'adbaseinfo/customersSuggest',
            dataType: "json",
            beforeRender: function (container) {
 
            },
            onSelect:function (suggestion) {
                $.ajax({
                    type: "POST",
                    url: "adbaseinfo/queryCustomer",
                    data: { companyName: suggestion.value,
                    		customerNumber:suggestion.data},
                    success:function(msg){
                    	data = msg.retBean;
                        $("#customerType").text(data.customerType);
                        $("#sale").text(data.customer.belongSales);
                        $("#agentType").text(data.agentType);
                        $("#country").text(data.country.i18nName);
                        $("#customerNumber").val(data.customer.customerNumber);
                        var countryString = "";
                        var countries = data.agentCountry;
                        for(var index in countries){
                        	countryString += countries[index].i18nName;
                        	countryString += " ";
                        }
                        $("#agentCountry").text(countryString);
                        $("#agentArea").text(data.agentRegional.i18nName);

                        showInfo($companyInfo);

                    },
                    error:function(msg){

                    }
                }).done(function( msg ) {

                });  
            },
            onSearchStart:function(){
                hideInfo($companyInfo);
            },
            onHint: function (hint) {
                hideInfo($companyInfo);
            }
        });
    };

    var contractNumberSuggestion = function($contractInfo , $contractNumber){
        //hideInfo($contractInfo);
        $contractNumber.autocomplete({
            serviceUrl: "adbaseinfo/contractSuggest", 
            width:500,
            beforeRender: function (container) {
                var $container = $(container),
                    $suggestion = $(container).find("div"),
                    $suggestionBox = $("#suggestionTable"),
                    $table = $suggestionBox.find("table"),
                    suggestionDataArr=$(this).autocomplete().suggestions;

                var $tbody = $table.find("tbody");

                /*清除上一次的数据*/
                $table.find("tbody tr").remove();

                $suggestion.each(function(index, element){
                    var $element=$(element);
                    var $trNode=$(['<tr class="autocomplete-suggestion" data-index="'+$element.attr("data-index")+'" style="cursor:pointer;">',
                                        '<td>',
                                            '<input type="radio" name="contractNumber" data-index="'+$element.attr("data-index")+'"/>',
                                        '</td>',
                                        '<td>'+suggestionDataArr[index].value+'</td>',
                                        '<td>'+suggestionDataArr[index].data.category+'</td>',
                                        '<td>'+suggestionDataArr[index].data.summary+'</td>',
                                    '</tr>'].join(""));
                    $tbody.append($trNode);
                });

                $suggestionBox.attr("style",$container.attr("style")+"padding-top:10px;").css({"display":"block","visibility":"visible","overflow" : "visible"});
                $container.css("visibility","hidden");

            },                                                
            onSelect:function (suggestion) {

                $.ajax({
                    type: "POST",
                    url: "adbaseinfo/queryContract",
                    data: { number: suggestion.data.number },
                    success:function(msg){
                    	data = msg.retBean;
                        /*这里添加填充数据操作*/
                        $("#contractSummary").text(data.summary);
                        $("#contractType").text(data.category);
                        var beginDate = new Date(data.beginDate);
                        var beginEndDate = new Date(data.endDate);
                        $("#contractexpDate").text(beginDate+"~"+beginEndDate);

                        showInfo($contractInfo);

                        //隐藏模态框
                        $("#suggestionTable button.close").click();

                    },
                    error:function(msg){

                    }
                }).done(function( msg ) {

                });  

            },
            onSearchStart:function(){
                hideInfo($contractInfo);

            },
            onHint: function (hint) {
                hideInfo($contractInfo);
            }
        });

        var initTableSuggestion=function($contractNumber){
            var $suggestionBox=$([  '<div class="modal" id="suggestionTable">',
                                            '<div class="modal-dialog" style="margin:0px;">',
                                                '<div class="modal-content">',
                                                    '<div class="modal-header" style="border-bottom:0;">',
                                                        '<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>',
                                                    '</div>',
                                                    '<div class="modal-body" style="padding-top:0;">',
                                                        '<table class="table">',
                                                            '<thead>',
                                                                '<tr>',
                                                                    '<th></th>',
                                                                    '<th>合同编号</th>',
                                                                    '<th>合同类型</th>',
                                                                    '<th>概要</th>',
                                                                '</tr>',
                                                            '</thead>',
                                                            '<tbody>',
                                                            '</tbody>',
                                                        '</table>',
                                                    '</div>',
                                                '</div>',
                                            '</div>',
                                        '</div>'].join("")
                                    );
            var $originalSuggestionBox=$($contractNumber.autocomplete().suggestionsContainer);

            $suggestionBox.css("display","none");
            $("body").append($suggestionBox);
            //在body底部追加样式
            $("body").append('<style>.modal tbody tr{cursor:pointer;}</style>');

            //为Tr绑定事件
            $suggestionBox.delegate("table tr","click",function(){
                var $contractNumber=$("#contractNumber");
                var $modal = $(this).closest("div.modal");
                var dataIndex = $modal.find("input:checked");
                

                $(this).find("td:eq(0) input[type='radio']")[0].click();
                $contractNumber.val($(this).find("td:eq(1)").text());
                $originalSuggestionBox.find("div.autocomplete-suggestion[data-index='"+dataIndex+"']").click();
                $contractNumber.change();
                $modal.css("display","none");
            });

            //绑定关闭按钮的事件
            $suggestionBox.delegate("button[data-dismiss='modal']","click",function(){
                var $modal = $(this).closest("div.modal");
                $modal.css("display","none");
            });
        };

        /*初始化表格*/
        initTableSuggestion($contractNumber);
     };

  	exports.init = function(options){
  		var ops = this.ops;
  	};

});
