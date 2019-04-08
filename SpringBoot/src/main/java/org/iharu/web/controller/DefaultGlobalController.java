/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.web.controller;

import org.iharu.proto.web.WebResponseProto;
import org.iharu.type.ResultType;
import org.iharu.web.BaseComponent;
import org.iharu.web.util.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author iHaru
 */
@RestController
public class DefaultGlobalController extends BaseComponent {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultGlobalController.class);
    
    @RequestMapping("*")
    public WebResponseProto defaultResponse(){
        return HttpUtils.StandardResponseGen(ResultType.FAIL, "请求URL有误");
    }

}
