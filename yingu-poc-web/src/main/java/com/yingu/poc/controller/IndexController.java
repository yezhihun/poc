package com.yingu.poc.controller;

import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;


/**
 *
 */
@Controller
@RequestMapping(value = "/")
public class IndexController {

    @ResponseBody
    @RequestMapping(value = "userInfo")
    public String userInfo(HttpSession session){
        Object object = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
        SimplePrincipalCollection simplePrincipalCollection = (SimplePrincipalCollection)object;
        Object o = simplePrincipalCollection.getPrimaryPrincipal();
        return o.toString();
    }

    @RequestMapping("login")
    public String login(HttpSession session){
        System.out.println(session.getId());
        Object object = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
        System.out.println(object);
        return "/index";
    }

}
