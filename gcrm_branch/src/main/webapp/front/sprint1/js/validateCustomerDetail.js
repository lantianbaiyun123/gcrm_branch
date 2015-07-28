var setValidate = {
			rules: {
			    "customer.companyName":"required",
			    "customer.businessType": {
			    	"required": true
			    },
			    "customer.registerTime": {
			    	"required":true, 
			    	"regex": /^(\d{2})([-])(\d{2})([-])(\d{4})/ 
			    },   
			    "customer.businessLicense":"required",
			    "customer.belongSales":{
			    	"required":true,
			    	"number":true
			    },
			    "customer.registerCapital":"number",  
			    "customer.country":"required",
			    "customer.address":"required",
			    "customer.url":"required",
			    "customer.type":"required",
			    "customer.agentType":"required",
			    "customer.agentRegional":"required",
			    "customer.industry":"required",
			    "customer.agentCountry":"required",
			    "customer.agentCompany":"required"
		   	}, 
		   	messages:{
		   		"customer.companyName": GCRM.util.message("customer.companyName.required"),
		   		"customer.businessType": GCRM.util.message("customer.businessType.required"),
		   		"customer.registerTime":{
		   			"required":GCRM.util.message("customer.registerTime.required"),
		   			"regex":GCRM.util.message("typeMismatch.afterSaveBase.customer.registerTime")
		   		},
			    "customer.businessLicense":GCRM.util.message("customer.businessLicense.required"),
			    "customer.belongSales":{
			    	"required":GCRM.util.message("customer.belongSales.required"),
			    	"number":GCRM.util.message("typeMismatch.afterSaveBase.customer.belongSales")
			    },
			    "customer.registerCapital":GCRM.util.message("typeMismatch.afterSaveBase.customer.registerCapital"),
			    "customer.country":GCRM.util.message("customer.country.required"),
			    "customer.address":GCRM.util.message("customer.address.required"),
			    "customer.url":GCRM.util.message("customer.url.required"),
			    "customer.type":GCRM.util.message("customer.type.required"),
			    "customer.agentType":GCRM.util.message("customer.agentType.required"),
			    "customer.agentRegional":GCRM.util.message("customer.agentRegional.required"),
			    "customer.industry":GCRM.util.message("customer.industry.required"),
			    "customer.agentCountry":GCRM.util.message("customer.agentCountry.required"),
			    "customer.agentCompany":GCRM.util.message("customer.agentCompany.required"),
			}
		}