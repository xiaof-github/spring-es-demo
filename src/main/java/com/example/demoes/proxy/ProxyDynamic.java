package com.example.demoes.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyDynamic implements InvocationHandler{
    private Object target;

    public ProxyDynamic(Object object){
        this.target = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before invoke");
        Object result = method.invoke(target,args);
        System.out.println("after invoke");
        return result;
    }

    // 获取代理对象
    public <T> T getProxy(){
        return (T) Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),this);
    }
}
