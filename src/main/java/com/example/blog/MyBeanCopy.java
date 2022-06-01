package com.example.blog;

import org.apache.commons.beanutils.BeanUtilsBean;

import java.lang.reflect.InvocationTargetException;

public class MyBeanCopy extends BeanUtilsBean {

    @Override
    public void copyProperty(Object bean, String name, Object value) throws IllegalAccessException, InvocationTargetException, InvocationTargetException {
        if (value == null || value.toString().isEmpty())
            return;
        super.copyProperty(bean, name, value);
    }
}