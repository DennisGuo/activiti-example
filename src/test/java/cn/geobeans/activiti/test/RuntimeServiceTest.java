package cn.geobeans.activiti.test;

import com.alibaba.fastjson.JSON;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ghx on 2017/1/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = {
        "classpath:application-context.xml",
        "classpath:application-context-activiti.xml"
})
public class RuntimeServiceTest {

    @Autowired
    RuntimeService runtimeService;

    /**
     * 执行流的查询
     */
    @Test
    public void executionQueryTest(){
        String processKey = "vacationRequest";
        List<Execution> executionList = runtimeService.createExecutionQuery().processDefinitionKey(processKey).list();
        for(Execution exe:executionList){
            System.out.println(String.format("执行流：Name = %s, Id = %s,Description = %s",exe.getName(),exe.getId(),exe.getDescription()));
        }
    }

    /**
     * 流程的挂起和激活
     */
    @Test
    public void suspendAndActivateTest(){
        String processInstanceId = "10076e30b5914f0eb19a0a41d04fa588";
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        System.out.println("流程是否挂起："+processInstance.isSuspended());
        //挂起流程实例
        runtimeService.suspendProcessInstanceById(processInstanceId);
        //验证是否挂起
        System.out.println("流程是否挂起："+runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult().isSuspended());
        //激活流程实例
        runtimeService.activateProcessInstanceById(processInstanceId);
        //验证是否激活
        System.out.println("流程是否激活："+!runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult().isSuspended());
    }

    @Test
    public void runtimeServiceQueryTest(){
        String processKey = "vacationRequest";
        List<ProcessInstance> list = runtimeService.createProcessInstanceQuery().processDefinitionKey(processKey).list();
        for(ProcessInstance instance: list){
            System.out.println(String.format("ProcessDefinitionKey : %s\nID : %s\nDeploymentId : %s\nProcessVariables : %s",
                    instance.getProcessDefinitionKey(),
                    instance.getId(),
                    instance.getDeploymentId(),
                    JSON.toJSONString(instance.getProcessVariables())));
        }
    }

    @Test
    public void runtimeServiceTest(){
        String processId = "vacationRequest:1:cd2c3c0d817a47049377afd09a47a868";
        String processKey = "vacationRequest";
        //ProcessInstance instance = runtimeService.startProcessInstanceById(processId);
        //ProcessInstance instance = runtimeService.startProcessInstanceByKey(processKey);
        //1. 定义初始化流程变量
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("employeeName", "Sclla");
        variables.put("numberOfDays", 7);
        variables.put("vacationMotivation", "工作累了，想休息~！");
        //2. 使用process key 创建实例
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processKey, variables);
        //3. 查看所有运行中的工单
        System.out.println("运行中的所有工单（实例）: " + runtimeService.createProcessInstanceQuery().count());
    }

}
