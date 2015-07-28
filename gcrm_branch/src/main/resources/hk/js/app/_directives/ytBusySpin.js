define(["app"],function(i){i.registerDirective("ytBusySpin",["$compile","$document",function(i,e){return{restrict:"A",scope:{ytBusySpinShow:"="},link:function(s,c,n){var t={doubleBounce:"yt-busy-spin-double-bounce",pulse:"yt-busy-spin-pulse",wave:"yt-busy-spin-wave",wanderingCubes:"yt-busy-spin-wandering-cubes",chasingDots:"yt-busy-spin-chasing-dots",threeBounce:"yt-busy-spin-three-bounce",circle:"yt-busy-spin-circle"},r=n.ytBusySpinType||"threeBounce",d=e.find("body").eq(0),v=s.$new(),l=i('<div class="busy-spin" '+t[r]+"></div>")(v);s.$watch("ytBusySpinShow",function(i){i?d.append(l):l.remove()})}}}]),i.registerDirective("ytBusySpinDoubleBounce",function(){return{restrict:"A",template:'<div class="double-bounce"><div class="double-bounce1"></div><div class="double-bounce2"></div></div>',link:function(){}}}),i.registerDirective("ytBusySpinPulse",function(){return{restrict:"A",template:'<div class="pulse"></div>',link:function(){}}}),i.registerDirective("ytBusySpinWave",function(){return{restrict:"A",template:'<div class="wave"><div class="rect1"></div><div class="rect2"></div><div class="rect3"></div><div class="rect4"></div><div class="rect5"></div></div>',link:function(){}}}),i.registerDirective("ytBusySpinWanderingCubes",function(){return{restrict:"A",template:'<div class="wandering-cubes"><div class="cube1"></div><div class="cube2"></div></div>',link:function(){}}}),i.registerDirective("ytBusySpinChasingDots",function(){return{restrict:"A",template:'<div class="chasing-dots"><div class="dot1"></div><div class="dot2"></div></div>'}}),i.registerDirective("ytBusySpinThreeBounce",function(){return{restrict:"A",template:'<div class="three-bounce"><div class="bounce1"></div><div class="bounce2"></div><div class="bounce3"></div></div>'}}),i.registerDirective("ytBusySpinCircle",function(){return{restrict:"A",template:'<div class="circle"><div class="spinner-container container1"><div class="circle1"></div><div class="circle2"></div><div class="circle3"></div><div class="circle4"></div></div><div class="spinner-container container2"><div class="circle1"></div><div class="circle2"></div><div class="circle3"></div><div class="circle4"></div></div><div class="spinner-container container3"><div class="circle1"></div><div class="circle2"></div><div class="circle3"></div><div class="circle4"></div></div></div>'}})});