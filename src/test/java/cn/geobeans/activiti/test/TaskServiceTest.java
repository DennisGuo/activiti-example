package cn.geobeans.activiti.test;

import com.alibaba.fastjson.JSON;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.task.Task;
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
public class TaskServiceTest {
    @Autowired
    TaskService taskService;

    @Test
    public void taskQueryTest() {
        List<Task> list = taskService.createTaskQuery().list();
        for(Task t:list){
            printTask(t);
        }
    }

    private void printTask(Task t) {
        System.out.println(String.format("ID = %s\nNAME = %s\nDelegationState = %s\nTaskDefinitionKey = %s\nDESCRIPTION = %s\nLocalVariables = %s\n",
                t.getId(),
                t.getName(),
                t.getDelegationState(),
                t.getTaskDefinitionKey(),
                t.getDescription(),
                JSON.toJSONString(t.getTaskLocalVariables())));
    }

    @Test
    public void taskQueryByVariableTest() {
        String name = "Sclla";
        Task t = taskService.createTaskQuery().processVariableValueEquals("employeeName",name).singleResult();
        printTask(t);
        Map<String,Object> variables = taskService.getVariables(t.getId());
        System.out.println("任务变量："+JSON.toJSONString(variables));
    }

    @Test
    public void taskQueryByDefinitionKeyTest() {
        String taskDefinitionKey = "handleRequest";
        List<Task> list = taskService.createTaskQuery().taskDefinitionKey(taskDefinitionKey).list();
        for(Task t:list){
            printTask(t);
        }
    }

    @Test
    public void taskCompleteTest() {
        String taskId = "fadfb5b627814d82b44c48e74c34560c";
        Map<String,Object> variable = new HashMap<String, Object>();
        variable.put("vacationApproved",true);
        variable.put("managerMotivation","去吧，看看世界也好，才知道还是这里好。");
        taskService.complete(taskId,variable);

        taskQueryTest();
    }




}
