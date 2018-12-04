/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.iharu.exception.BaseException;
import org.iharu.proto.web.WebResponseProto;
import org.iharu.type.ResultType;
import static org.iharu.util.BaseConstantValue.LINESEPARATOR;
import org.iharu.web.BaseComponent;
import org.iharu.web.util.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author x5171
 */
@RestController
public class DefaultGlobalController extends BaseComponent {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultGlobalController.class);
    
    @RequestMapping("*")
    public WebResponseProto defaultResponse(){
        return HttpUtils.StandardResponseGen(ResultType.FAIL, "请求URL有误");
    }

}
