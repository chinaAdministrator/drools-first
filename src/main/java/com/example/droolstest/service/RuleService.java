package com.example.droolstest.service;

import com.example.droolstest.pojo.Order;

/**
 * 服务接口
 */
public interface RuleService {
    /**
     * 订单通过规则引擎处理
     */
    Order executeOrderRule(Order order);
}
