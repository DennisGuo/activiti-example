package cn.geobeans.activiti;

import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by ghx on 2016/12/30.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = {
        "classpath:application-context.xml",
        "classpath:application-context-activiti.xml"
})
public class FlowTest {

    @Autowired
    ProcessEngine processEngine;

    @Test
    public void processTests() {
//        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        // 加载配置文件
        //ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
//        ProcessEngine processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("application-context.xml").buildProcessEngine();
        String pName = processEngine.getName();
        String ver = ProcessEngine.VERSION;
        System.out.println("ProcessEngine [" + pName + "] Version: [" + ver + "]");
        parseFlow(processEngine);
    }

    private void parseFlow(ProcessEngine processEngine) {

        RepositoryService repositoryService = processEngine.getRepositoryService();
        RuntimeService runtimeService = processEngine.getRuntimeService();

//        repositoryService.createDeployment().addClasspathResource("interview.bpmn").deploy();
//        String processId = runtimeService.startProcessInstanceByKey("Interview").getId();

        TaskService taskService = processEngine.getTaskService();
        /*
        //得到笔试的流程
        System.out.println("\n***************笔试流程开始***************");

        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("人力资源部").list();
        for (Task task : tasks) {
            System.out.println("人力资源部的任务：name:"+task.getName()+",id:"+task.getId());
            taskService.claim(task.getId(), "张三");
        }

        System.out.println("张三的任务数量："+taskService.createTaskQuery().taskAssignee("张三").count());
        tasks = taskService.createTaskQuery().taskAssignee("张三").list();
        for (Task task : tasks) {
            System.out.println("张三的任务：name:"+task.getName()+",id:"+task.getId());
            taskService.complete(task.getId());
        }

        System.out.println("张三的任务数量："+taskService.createTaskQuery().taskAssignee("张三").count());
        System.out.println("***************笔试流程结束***************");

        System.out.println("\n***************一面流程开始***************");
        tasks = taskService.createTaskQuery().taskCandidateGroup("技术部").list();
        for (Task task : tasks) {
            System.out.println("技术部的任务：name:"+task.getName()+",id:"+task.getId());
            taskService.claim(task.getId(), "李四");
        }

        System.out.println("李四的任务数量："+taskService.createTaskQuery().taskAssignee("李四").count());
        for (Task task : tasks) {
            System.out.println("李四的任务：name:"+task.getName()+",id:"+task.getId());
            taskService.complete(task.getId());
        }

        System.out.println("李四的任务数量："+taskService.createTaskQuery().taskAssignee("李四").count());
        System.out.println("***************一面流程结束***************");

        System.out.println("\n***************二面流程开始***************");
        tasks = taskService.createTaskQuery().taskCandidateGroup("技术部").list();
        for (Task task : tasks) {
            System.out.println("技术部的任务：name:"+task.getName()+",id:"+task.getId());
            taskService.claim(task.getId(), "李四");
        }

        System.out.println("李四的任务数量："+taskService.createTaskQuery().taskAssignee("李四").count());
        for (Task task : tasks) {
            System.out.println("李四的任务：name:"+task.getName()+",id:"+task.getId());
            taskService.complete(task.getId());
        }

        System.out.println("李四的任务数量："+taskService.createTaskQuery().taskAssignee("李四").count());
        System.out.println("***************二面流程结束***************");

        System.out.println("***************HR面流程开始***************");
        tasks = taskService.createTaskQuery().taskCandidateGroup("人力资源部").list();
        for (Task task : tasks) {
            System.out.println("技术部的任务：name:"+task.getName()+",id:"+task.getId());
            taskService.claim(task.getId(), "李四");
        }

        System.out.println("李四的任务数量："+taskService.createTaskQuery().taskAssignee("李四").count());
        for (Task task : tasks) {
            System.out.println("李四的任务：name:"+task.getName()+",id:"+task.getId());
            taskService.complete(task.getId());
        }

        System.out.println("李四的任务数量："+taskService.createTaskQuery().taskAssignee("李四").count());
        System.out.println("***************HR面流程结束***************");

        System.out.println("\n***************录用流程开始***************");
        tasks = taskService.createTaskQuery().taskCandidateGroup("人力资源部").list();
        for (Task task : tasks) {
            System.out.println("技术部的任务：name:"+task.getName()+",id:"+task.getId());
            taskService.claim(task.getId(), "李四");
        }

        System.out.println("李四的任务数量："+taskService.createTaskQuery().taskAssignee("李四").count());
        for (Task task : tasks) {
            System.out.println("李四的任务：name:"+task.getName()+",id:"+task.getId());
            taskService.complete(task.getId());
        }

        System.out.println("李四的任务数量："+taskService.createTaskQuery().taskAssignee("李四").count());
        System.out.println("***************录用流程结束***************");

        HistoryService historyService = processEngine.getHistoryService();
        HistoricProcessInstance historicProcessInstance = historyService
                .createHistoricProcessInstanceQuery()
                .processInstanceId(processId).singleResult();
        System.out.println("\n流程结束时间："+historicProcessInstance.getEndTime());
    */

    }
}
