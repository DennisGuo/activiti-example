package cn.geobeans.activiti.test;

import com.alibaba.fastjson.JSON;
import org.activiti.engine.FormService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.form.FormData;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.StartFormData;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by ghx on 2017/1/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = {
        "classpath:application-context.xml",
        "classpath:application-context-activiti.xml"
})
public class IdentityServiceTest {
    @Autowired
    IdentityService identityService;

    @Test
    public void testUser() {
        createUser();

        //删除用户
        identityService.deleteUser("Jonathan");
        //验证是否删除成功
        User userInDb = identityService.createUserQuery().userId("Jonathan").singleResult();
        System.out.println("用户是否删除成功：" + (userInDb == null));
    }

    private void createUser() {
        User user = identityService.newUser("Jonathan");
        user.setFirstName("Jonathan");
        user.setLastName("chang");
        user.setEmail("whatlookingfor@gmail.com");
        user.setPassword("123");
        //保存用户到数据库
        identityService.saveUser(user);
        //用户的查询
        User userInDb = identityService.createUserQuery().userId("Jonathan").singleResult();
        System.out.println("用户查询结果：" + JSON.toJSONString(userInDb));
        //验证用户名和密码
        System.out.println("验证用户密码：" + identityService.checkPassword("Jonathan", "123"));
    }

    /**
     * 用户组管理
     */
    @Test
    public void testGroup() {
        createGroup();
        //删除用户组
        identityService.deleteGroup("hr");
        //验证是否删除成功
        Group groupInDb = identityService.createGroupQuery().groupId("hr").singleResult();
        System.out.println("删除用户组：" + (groupInDb == null));

    }

    private void createGroup() {
        //创建用户组对象
        Group group = identityService.newGroup("hr");
        group.setName("hr用户组");
        group.setType("assignment");
        //保存用户组
        identityService.saveGroup(group);
        //验证是否保存成功
        Group groupInDb = identityService.createGroupQuery().groupId("hr").singleResult();
        System.out.println("查询用户组：" + groupInDb);
    }


    /**
     * 用户 用户组管理
     */
    @Test
    public void testUserAndGroupMemership() {
        // Group groupInDb = identityService.createGroupQuery().groupId("hr").singleResult();
        // User userInDb = identityService.createUserQuery().userId("Jonathan").singleResult();
        createUser();
        createGroup();
        //将用户Jonathan加入到用户组hr中
        identityService.createMembership("Jonathan", "hr");
        //查询属于HR用户组的用户
        List<User> userInGroup = identityService.createUserQuery().memberOfGroup("hr").list();
        System.out.println("用户组hr中的用户有：");
        for (User u : userInGroup) {
            System.out.println(JSON.toJSONString(u));
        }
        //查询用户所属组
        List<Group> groupContainsUser = identityService.createGroupQuery().groupMember("Jonathan").list();
        System.out.println("用户Jonathan所在的用户组有：");
        for (Group g : groupContainsUser) {
            System.out.println(String.format("用户组：name=%s , type=%s", g.getName(), g.getType()));
        }
    }

}
