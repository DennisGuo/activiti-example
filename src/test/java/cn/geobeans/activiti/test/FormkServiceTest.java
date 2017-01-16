package cn.geobeans.activiti.test;

import com.alibaba.fastjson.JSON;
import org.activiti.engine.FormService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.FormData;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.StartFormData;
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
public class FormkServiceTest {
    @Autowired
    FormService formService;

    //根据流程定义Id获取流程启动节点的表单内容
    @Test
    public void formTest() {
        String processDefinitionId = "vacationRequest:1:cd2c3c0d817a47049377afd09a47a868";
        StartFormData startFormData = formService.getStartFormData(processDefinitionId);
        List<FormProperty> list = startFormData.getFormProperties();
        for(FormProperty fp:list){
            System.out.println(JSON.toJSONString(fp));
        }
    }

    @Test
    public void formTaskTest() {
        String taskId = "5f153afe19c94161a70c657cb9510811";
        FormData data = formService.getTaskFormData(taskId);
        List<FormProperty> list = data.getFormProperties();
        for(FormProperty fp:list){
            System.out.println(JSON.toJSONString(fp));
        }
    }

    @Test
    public void formKeyTest(){

        String processDefinitionId = "vacationRequest:1:cd2c3c0d817a47049377afd09a47a868";

        String taskDefinitionKey = "handleRequest";

        String getStartFormKey = formService.getStartFormKey(processDefinitionId);//获取流程启动的formKey
        String getTaskFormKey = formService.getTaskFormKey(processDefinitionId, taskDefinitionKey);//获取任务环节的formKey

        System.out.println("getStartFormKey:"+getStartFormKey);
        System.out.println("getTaskFormKey:"+getTaskFormKey);
    }




}
