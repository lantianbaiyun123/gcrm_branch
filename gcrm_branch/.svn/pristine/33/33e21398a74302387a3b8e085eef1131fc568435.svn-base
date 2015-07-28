package com.baidu.gcrm.customer.web.helper;

import com.baidu.gcrm.common.auth.RequestThreadLocal;
import com.baidu.gcrm.customer.vo.CustomerApplyInfo;

public class CustomerDetailView extends CustomerView {
    private CustomerApplyInfo customerApplyInfo;

    private boolean typeChangeAllowed;

    @SuppressWarnings("unused")
	private boolean isOwner;

    public boolean getIsOwner() {
    	Long loginUserId = RequestThreadLocal.getLoginUserId();
        if (this.customer.getCreateOperator().equals(loginUserId)) {
            return true;	
        }

        if (this.belongSales != null && this.belongSales.getUcid().equals(loginUserId)) {
            return true;
        }
        return false;
    }

    public void setOwner(boolean isOwner) {
        this.isOwner = isOwner;
    }

    public CustomerApplyInfo getCustomerApplyInfo() {
        return customerApplyInfo;
    }

    public void setCustomerApplyInfo(CustomerApplyInfo customerApplyInfo) {
        this.customerApplyInfo = customerApplyInfo;
    }

    public boolean isTypeChangeAllowed() {
        return typeChangeAllowed;
    }

    public void setTypeChangeAllowed(boolean typeChangeAllowed) {
        this.typeChangeAllowed = typeChangeAllowed;
    }

}
