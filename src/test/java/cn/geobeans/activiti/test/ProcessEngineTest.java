package cn.geobeans.activiti.test;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by ghx on 2017/1/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = {
        "classpath:application-context.xml",
        "classpath:application-context-activiti.xml"
})
public class ProcessEngineTest {

    @Autowired
    ProcessEngine processEngine;


    @Test
    public void engineTests() {
//        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        // 加载配置文件
        //ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
//        ProcessEngine processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("application-context.xml").buildProcessEngine();
        String pName = processEngine.getName();
        String ver = ProcessEngine.VERSION;
        System.out.println("ProcessEngine [" + pName + "] Version: [" + ver + "]");
    }

}
