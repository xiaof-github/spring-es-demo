package com.example.demoes.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(value="/demo", tags="Swagger样例")
@RestController
@RequestMapping("/api")
public class UserController {

    @ApiOperation(value="根据Id查询用户信息", notes = "根据Id查询用户信息,这里是详细说明")
    @ApiImplicitParam(name="id", value="用户唯一id", required = true, dataType = "Long")
    @RequestMapping(value = "/hello", params = "id", method = RequestMethod.GET)
    public String hello(@RequestParam Long id){
        return "hello" + id.toString();
    }
}
