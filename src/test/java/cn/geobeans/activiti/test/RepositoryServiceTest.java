package cn.geobeans.activiti.test;

import com.alibaba.fastjson.JSON;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by ghx on 2017/1/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = {
        "classpath:application-context.xml",
        "classpath:application-context-activiti.xml"
})
public class RepositoryServiceTest {
    @Autowired
    RepositoryService repositoryService;

    @Test
    public void deployTest() {
        Deployment dp = repositoryService.createDeployment().addClasspathResource("ask-for-vacation.bpmn20.xml").deploy();
        System.out.println(JSON.toJSONString(dp));
        System.out.println(String.format("name=%s,id=%s,category=%s,deploytime=%s,tenantid=%s",dp.getName(),dp.getId(),dp.getCategory(),dp.getDeploymentTime(),dp.getTenantId()));
    }

    @Test
    public void repositoryQueryTest(){
        ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
        System.out.println("已有规则总数："+query.count());
        //查询所有已发布的流程规则
        List<ProcessDefinition> list = query.list();
        for(ProcessDefinition pd:list){
            System.out.println(String.format("\n规则名称：%s\nID\t= %s\nKEY\t= %s\nDEPLOYMENT_ID\t= %s",pd.getName(),pd.getId(),pd.getKey(),pd.getDeploymentId()));
        }
    }

    @Test
    public void deleteProcess(){
        System.out.println("....删除前....");
        repositoryQueryTest();
        String id = "b5b9d3afe2ac490da448150f711980bf";
        repositoryService.deleteDeployment(id);
        System.out.println("....删除后....");
        repositoryQueryTest();
    }
}
