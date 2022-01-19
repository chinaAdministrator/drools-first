package com.example.droolstest.config;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.*;
import org.kie.api.runtime.KieContainer;
import org.kie.internal.io.ResourceFactory;
import org.kie.spring.KModuleBeanFactoryPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;

/**
 * drools配置类
 */
@Configuration
public class DroolsConfig {

    //指定规则文件存放的目录
    private static final String RULES_PATH = "rules/";
    //单例
    private final KieServices kieServices = KieServices.Factory.get();

    /**
     * 3
     */
    @Bean
    //此注解保证只有一个实例 当注册多个相同的bean时，会出现异常
    @ConditionalOnMissingBean
    public KieFileSystem kieFileSystem() {
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        /**
         * spring的资源文件加载器
         */
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        /**
         * 加载资源: PathMatchingResourcePatternResolver是一个资源文件加载器 可以加载类路径下或者文件系统中的资源
         * 加载文件系统中的资源 :resourcePatternResolver.getResource("file:c:/dd.properties")
         * 加载当前项目 resources下的资源 : resourcePatternResolver.getResources("classpath*:" + RULES_PATH + "*.*")
         */
        Resource[] files = new Resource[0];
        try {
            //29 21:22 --> 22:43
            //31 06:53 --> 08:00
            files = resourcePatternResolver.getResources("classpath*:" + RULES_PATH + "*.*");
        } catch (IOException e) {
            e.printStackTrace();
        }
        String path;
        for (Resource file : files) {
            path = RULES_PATH + file.getFilename();
            /**
             * 将spring的org.springframework.core.io.Resource获取路径转为org.kie.api.io.Resource
             * 然后将资源添加到 kieFileSystem
             */
            kieFileSystem.write(ResourceFactory.newClassPathResource(path, "UTF-8"));
        }
        /**
         * 返回 kieFileSystem 实例
         */
        return kieFileSystem;
    }

    /**
     * 2
     */
    @Bean
    //单例
    @ConditionalOnMissingBean
    public KieContainer kieContainer() {
        KieRepository kieRepository = kieServices.getRepository();
        kieRepository.addKieModule(new KieModule() {
            @Override
            public ReleaseId getReleaseId() {
                return kieRepository.getDefaultReleaseId();
            }
        });
        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem());
        kieBuilder.buildAll();
        return kieServices.newKieContainer(kieRepository.getDefaultReleaseId());
    }

    /**
     * 1
     */
    @Bean
    @ConditionalOnMissingBean
    public KieBase kieBase() {
        return kieContainer().getKieBase();
    }

    @Bean
    @ConditionalOnMissingBean
    public KModuleBeanFactoryPostProcessor kiePostProcessor() {
        return new KModuleBeanFactoryPostProcessor();
    }
}
