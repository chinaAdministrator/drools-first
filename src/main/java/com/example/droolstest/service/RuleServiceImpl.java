package com.example.droolstest.service;

import com.example.droolstest.pojo.Order;
import lombok.RequiredArgsConstructor;
import org.kie.api.KieBase;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;

/**
 * 服务实现
 */
@RequiredArgsConstructor
@Service
public class RuleServiceImpl implements RuleService {

    private final KieBase kieBase;

    @Override
    public Order executeOrderRule(Order order) {
        KieSession kieSession = kieBase.newKieSession();
        kieSession.insert(order);
        kieSession.fireAllRules();
        kieSession.dispose();
        return order;
    }
}
