define(["app"],function(I){I.registerFilter("OperateTypeFilter",function(){var I={all:"ALL",online:"PUBLICATION_OPERATE_ONLINE",offline:"PUBLICATION_OPERATE_OFFLINE",materialChange:"PUBLICATION_OPERATE_MATERIAL_CHANGE"};return function(e){return I[e]?I[e]:"--"}}),I.registerFilter("OperationTypeFilter",function(){var I={publish:"PUBLICATION_OPERATION_PUBLISH",unpublish:"PUBLICATION_OPERATION_UNPUBLISH",material_update:"PUBLICATION_OPERATION_MATERIAL_UPDATE",terminate:"PUBLICATION_OPERATION_TERMINATE",force_publish:"PUBLICATION_OPERATION_FORCE_PUBLISH"};return function(e){return I[e]?I[e]:"--"}})});