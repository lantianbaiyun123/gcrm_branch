/**
 * GCRM顶级名空间.
 * @namespace GCRM
 * @type Object
 */
var GCRM = GCRM || {};

/**
 * GCRM常量类名空间.
 * @namespace GCRM.constants
 * @type Object
 */
GCRM.constants = GCRM.constants || {};

/**
 * GCRM消息资源名空间.
 * @namespace Spark.messages
 * @type Object
 */
GCRM.messages = GCRM.messages || {};
/**
 * GCRM工具类名空间.
 * @namespace GCRM.util
 * @type Object
 */
GCRM.util = GCRM.util || {};

(function () {
	 /**
     * 国际化方法.
     * 
     * @method GCRM.util.message
     * @param code {String} 国际化标识
     * @param arguments {Array} 国际化参数(隐含可变参数)
     * @return {String} 国际化完成后的字符串
     * @static
     */
	GCRM.util.message = function(code) {
        if (!GCRM.messages) {
            return "";
        }
        var tokens = Array.prototype.slice.call(arguments).slice(1);
        var message = GCRM.messages[code];
        var result = GCRM.util.substitute(message, tokens);
        return result || "";
    };
    /**
     * 将字符串中的{xx}按照数据模型中的key/value值进行替换.
     * 
     * @method Spark.util.substitute
     * @param text {String} 待替换的字符串
     * @param obj {Object} 待替换的数据模型
     * @return {String} 替换后的字符串
     * @static
     */
    GCRM.util.substitute = function(text, obj) {
        if (!text) {
            return text;
        }
        for (key in obj) {
            text = text.replace(new RegExp("\\{" + key + "\\}", "g"), obj[key]);
        }
        return text;
    };
})();
/*
    首页的validate
*/
$.gcrm = $.gcrm || {};

$.validator.setDefaults({
    errorClass: "txt-impt", 
    errorElement: "div",
    errorPlacement: function(error,element) {
        element.parents('.form-group').children(".help-block").html(error); //错误显示的位置 element验证的元素error错误提示
    },
    ignore: ":hidden:not('.validate')"
})

$.gcrm.validate_form = function(element,param){
    var validator=$(element).validate(param);
    return formStatus=validator.form();
}

$.gcrm.loadScript = function(src){
    var script = document.createElement("script");
    script.type = "text/javascript";
    if(script.readyState){

    } else {
        script.onload = function(){
            //callback();
        }
    }
    script.src = src + "?t=" + new Date().getTime();

    document.getElementsByTagName("body")[0].appendChild(script);
};
//$.gcrm.loadModuleConfig = {};

$.gcrm.loadModule = function(options){
    var ops = $.extend({
    	moduleId:null,
        container:null,
        url:"",
        success: null,
        css:[],
        js:[]
    }, options);
    /*if( $.gcrm.loadModuleConfig[ops.moduleId] ){
    	return false;
    }*/
    //console.log($.gcrm.loadModuleConfig[ops.moduleId], " ----- ", ops.moduleId)
    if($.trim(ops.url) == ''){
    	return;
    }
    
    var tempUrl;
    if(ops.url.indexOf("?") != -1)
    	tempUrl = ops.url + "&t=" + new Date().getTime();
    else
    	tempUrl = ops.url + "?t=" + new Date().getTime();
    
    ops.container.load(tempUrl, function(){
    	//console.log("load module success");
    	options.success();
    	
    	//$.gcrm.loadModuleConfig[ops.moduleId] = true;
        /*$.each(ops.js, function(i, item){
            $.gcrm.loadScript(item);  
        });*/
    });
    
};

$.gcrm.dealCancellation = function(dealObj){
	var thisDealObj=$(dealObj);
	var tempRestoreCustomer = $("#restoreCustomer");
	if(tempRestoreCustomer && tempRestoreCustomer.attr("data-companyStatus")){
		thisDealObj.addClass("hide");
	}
}

// 防止ajax get请求当参数一样时，从缓存中读取数据，而不是现实后台返回的数据
$.ajaxSetup({cache:false});

/**
 *@fileOverview 消息提示
 *@param {text} cont 提示内容
 *@param {String} type 提示类型，必须是success、warning、info、danger，默认success
 *@param {Number} timeout 如果传递该参数，在timeout毫秒后，提示自动消失
 *@example 
    $("#msgBox").alert()
 */
$.fn.alert = function(cont, type, timeout){
    var $this = $(this);
    var type = "alert-" + type || "alert-success";
    
    
    return this.each(function (){
        var $close = $('<button type="button" class="close" data-dismiss="alert">×</button>');
        
        $this.html(cont);
        $close.appendTo($this);
        $this.addClass(type);
        $this.fadeIn();
        
        $close.click(function (){
            $this.html("");
            $this.fadeOut();
        });
        
        if (timeout != undefined) {
        	setTimeout(function(){
        		$this.html("");
                $this.fadeOut();
        	}, timeout);
        }
        
    });
};

