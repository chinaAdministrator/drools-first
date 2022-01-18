package com.example.droolstest.controller;

import com.example.droolstest.pojo.Order;
import com.example.droolstest.service.RuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 控制
 */
@RequiredArgsConstructor
@RestController
public class RuleController {

    private final RuleService ruleService;

    @GetMapping("order")
    public Order executeOrderRule(@RequestParam(value = "price", defaultValue = "200") Double price) {
        Order order = new Order();
        order.setAmout(price);
        return ruleService.executeOrderRule(order);
    }

}
