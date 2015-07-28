package com.baidu.gcrm.valuelist.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.valuelist.dao.IValuelistDao;
import com.baidu.gcrm.valuelist.service.IValuelistDBService;

@Service
public class ValuelistDBService implements IValuelistDBService{
    
    @Autowired
    IValuelistDao valueListDao;

    @Override
    public List<Long> getAllIdList(String tableName) {
        return valueListDao.loadIdList(tableName);
    }

}
