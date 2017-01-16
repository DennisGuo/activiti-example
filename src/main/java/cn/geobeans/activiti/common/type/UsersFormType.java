package cn.geobeans.activiti.common.type;

import org.activiti.engine.form.AbstractFormType;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * 自义定表单类型
 * Created by ghx on 2017/1/16.
 */
public class UsersFormType extends AbstractFormType {

    /**
     * 把字符串的值转换为集合对象
     * @param propertyValue
     * @return
     */
    @Override
    public Object convertFormValueToModelValue(String propertyValue) {
        String[] split = StringUtils.split(propertyValue, ",");
        return Arrays.asList(split);
    }

    /**
     * 把集合对象的值转换为字符串
     * @param modelValue
     * @return
     */
    @Override
    public String convertModelValueToFormValue(Object modelValue) {
        if(modelValue==null){
            return null;
        }
        return modelValue.toString();
    }

    /**
     * 定义表单类型的标识符
     * @return
     */
    public String getName() {
        return "users";
    }
}
