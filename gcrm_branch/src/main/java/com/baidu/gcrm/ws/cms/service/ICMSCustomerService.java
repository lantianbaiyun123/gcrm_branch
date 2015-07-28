package com.baidu.gcrm.ws.cms.service;

import com.baidu.gcrm.ws.cms.customer.dto.CustomerDTO;

public interface ICMSCustomerService {
    CustomerDTO findByCustomerId(Long customerId);
}
