define(["app"],function(n){n.registerFilter("RotationTypeFilter",function(){var n={0:"SCHEDULE_STATIC_POSITION",1:"SCHEDULE_ROTATION_POSITION"};return function(t){return n[t]?n[t]:"SCHEDULE_STATIC_POSITION"}})});