package com.baidu.gcrm.occupation1.service;

public interface IHistoryDataTransferService {
    /**
     * 迁移历史表中数据，迭代四上线后执行一次，之后可以废弃
     * <ul>
     * <li>迁移g_position_occupation表中有效位置的数据到g_position_date中，id要保持一致</li>
     * <li>根据position的轮播数和click数新建库存量g_stock</li>
     * <li>根据g_position_occupation表中的sold_amount更新g_stock已占用库存</li>
     * </ul>
     */
    void initFromHistory();
}
