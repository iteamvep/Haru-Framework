/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 *
 * @author iTeamVEP
 */
public interface AspectInterface {
    public void pointCut();
    public void doBefore(JoinPoint joinPoint);
    public void doAfter(JoinPoint joinPoint);
    public void afterThrowing(JoinPoint joinPoint,Throwable error);
    public Object around(ProceedingJoinPoint pjp);
}
