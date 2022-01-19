package com.example.droolstest.service;

import com.example.droolstest.pojo.Order;
import org.kie.api.KieBase;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 服务实现
 */
@Service
public class RuleServiceImpl implements RuleService {

    @Resource
    private KieBase kieBase;

    @Override
    public Order executeOrderRule(Order order) {
        KieSession kieSession = kieBase.newKieSession();
        kieSession.insert(order);
        kieSession.fireAllRules();
        kieSession.dispose();
        return order;
    }
}
