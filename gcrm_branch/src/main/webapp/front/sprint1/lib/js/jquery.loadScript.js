/*load script*/
var scripts = {
	"bbbbbb":"http://localhost:8080/gcrm/resources/lib/js/jquey.js",
	"aaaaaa":"http://localhost:8080/gcrm/resources/lib/js/jquer.js"
};

var loadScript = function(name,src){
	var script = document.createElement("script");
	script.type = "text/javascript";
	/*if(script.readyState){

	} else {
		script.onload = function(){
			//callback();
		}
	}*/
	script.src = src;
	script.name = name;
//	$("script:last-child").after($(script));
	document.getElementsByTagName("head")[0].appendChild(script);
};

var loadScripts = function(scripts){
	$.each( scripts, function(i, item){
		console.log(i, item)
		loadScript(i, item);
	})
};
loadScripts(scripts);