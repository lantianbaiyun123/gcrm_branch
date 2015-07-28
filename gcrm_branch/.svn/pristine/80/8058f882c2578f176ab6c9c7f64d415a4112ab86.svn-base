$(function(){
	$("#J_addContactBtn").closest("div").addClass("hide");
	
	//初始化修改按钮
	function initAddButton($containerId, $editBtnId){
		if($containerId.hasClass("hide"))
			$editBtnId.text("[ "+ GCRM.util.message("gcrm.title.customer.add") +" ]");
	}
	initAddButton($("#detailConcatInfo .panel-body"), $("#editContactInfo"));
	initAddButton($("#J_agentQualificationContainer"), $("#editAgentQualification"));
	initAddButton($("#J_materialsContainer"),$("#editMaterials"));
	initAddButton($("#J_businessChanceContainer"),$("#editBusinessChance"));
});