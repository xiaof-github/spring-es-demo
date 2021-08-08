package com.example.demoes.proxy;

public class Client {
    public static void main(String[] args){
        Host host = new Host();
        Object proxy = new ProxyDynamic(host).getProxy();
        //将object类对象强制转换为目标类对象
        Action proxyD = (Action) proxy;
        proxyD.Rent();

    }
}
