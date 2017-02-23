package cn.com.warlock.wisp.test.junit.listener;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.mockito.InjectMocks;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.util.CollectionUtils;

/**
 * mockito 增强，替换隔离
 *
 */
public class UnitTestDependencyInjectionTestExecutionListener extends DependencyInjectionTestExecutionListener {
    /**
     * 记录被测试的对象
     */
    private Map<String, Object> testedObjects = new HashMap<String, Object>();

    @Override
    protected void injectDependencies(TestContext testContext) throws Exception {
        super.injectDependencies(testContext);
        /**
         * 获取测试类 & fields
         */
        Object bean = testContext.getTestInstance();
        List<Field> fields = getDeclaredFields(bean);
        if (CollectionUtils.isEmpty(fields)) {
            return;
        }
        /**
         * 如果测试类中， 被测试对象含有mockito的@InjectMocks注解，且 被测试对象被事务拦截器拦截 则 用原始对象代替
         */
        for (Field field : fields) {
            InjectMocks injectMocks = field.getAnnotation(InjectMocks.class);
            if (injectMocks == null) {
                continue;
            }
            field.setAccessible(true);
            Object proxy = field.get(bean);
            if (AopUtils.isAopProxy(proxy)) {
                // 替换对象
                Object target = ((Advised) proxy).getTargetSource().getTarget();
                field.set(bean, target);
            }
        }
    }

    @Override
    public void afterTestMethod(TestContext testContext) throws Exception {
        super.afterTestMethod(testContext);

        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) testContext.getApplicationContext()
                .getAutowireCapableBeanFactory();

        /**
         * 方法结束后记录被测试对象的bean名称
         */
        Object bean = testContext.getTestInstance();
        List<Field> fields = getDeclaredFields(bean);
        for (Field field : fields) {
            InjectMocks injectMocks = field.getAnnotation(InjectMocks.class);
            if (injectMocks == null) {
                continue;
            }
            Object testedBean = null;
            String testedBeanName = null;
            /**
             * 被测试的对象如果通过spring自动注入，则记录
             * 两种注入方式 Autowired
             * Resource
             */
            if (field.getAnnotation(Autowired.class) != null) {
                Qualifier qualifier = field.getAnnotation(Qualifier.class);
                testedBean = qualifier == null ? beanFactory.getBean(field.getType())
                        : beanFactory.getBean(qualifier.value());
                testedBeanName = qualifier == null ? beanFactory.getBeanNamesForType(field.getType())[0]
                        : qualifier.value();
            } else if (field.getAnnotation(Resource.class) != null) {
                Resource resource = field.getAnnotation(Resource.class);
                Class<?> type = resource.type();
                String name = resource.name();
                if (StringUtils.isNotEmpty(name)) {
                    testedBean = beanFactory.getBean(name);
                    testedBeanName = name;
                } else {
                    testedBean = (type != Object.class) ? beanFactory.getBean(type)
                            : beanFactory.getBean(field.getType());
                    testedBeanName = (type != Object.class) ? beanFactory.getBeanNamesForType(type)[0]
                            : beanFactory.getBeanNamesForType(field.getType())[0];
                }
            }

            if (testedBean != null) {
                testedObjects.put(testedBeanName, testedBean);
            }
        }
    }

    @Override
    public void afterTestClass(TestContext testContext) throws Exception {
        super.afterTestClass(testContext);
        /**
         * 重新注册被污染的bean
         */
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) testContext.getApplicationContext()
                .getAutowireCapableBeanFactory();
        for (Entry<String, Object> entry : testedObjects.entrySet()) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(entry.getKey());
            beanFactory.removeBeanDefinition(entry.getKey());
            beanFactory.registerBeanDefinition(entry.getKey(), beanDefinition);
        }
    }

    private List<Field> getDeclaredFields(Object object) {
        List<Field> fieldList = new ArrayList<Field>();
        Class<?> clazz = object.getClass();
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                Field[] declaredFields = clazz.getDeclaredFields();
                fieldList.addAll(Arrays.asList(declaredFields));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return fieldList;
    }
}
