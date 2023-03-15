package org.getyou123;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LogAspect {

    /**
     * 前置通知，在执行目标方法之前执行
     *
     * @param joinPoint 连接点对象
     */
    @Before("execution(public int org.getyou123.CalculatorImpl.* (..))")
    public void beforeMethod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String args = Arrays.toString(joinPoint.getArgs());
        System.out.println("Logger-->前置通知，方法名:" + methodName + "，参数:" + args);
    }

    /**
     * 函数结束之后就调用，不管是否是正常结束或者异常结束
     *
     * @param joinPoint 连接点
     */
    @After("execution(* org.getyou123.CalculatorImpl.*(..))")
    public void afterMethod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("Logger-->后置通知，方法名:" + methodName);
    }

    /**
     * 这里获取了返回值，只有正常结束并获取到返回值才调用
     *
     * @param joinPoint 连接点
     * @param result    返回值
     */
    @AfterReturning(value = "execution(* org.getyou123.CalculatorImpl.*(..))", returning = "result")
    public void afterReturningMethod(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("Logger-->返回通知，方法名:" + methodName + "，结果:" + result);
    }

    /**
     * 异常通知，出现异常的时候通知
     * @param joinPoint
     * @param ex 获取异常的名字
     */
    @AfterThrowing(value = "execution(* org.getyou123.CalculatorImpl.*(..))", throwing = "ex")
    public void afterThrowingMethod(JoinPoint joinPoint, Throwable ex) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("Logger-->异常通知，方法名:" + methodName + "，异常:" + ex);
    }


    /**
     * result = joinPoint.proceed();
     * 这句话箱相当于直接去调用目标方法
     * @param joinPoint 连接点
     * @return 目标方法的返回参数
     */
    @Around("execution(* org.getyou123.CalculatorImpl.*(..))")
    public Object aroundMethod(ProceedingJoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String args = Arrays.toString(joinPoint.getArgs());
        Object result = null;
        try {
            System.out.println("环绕通知-->目标对象方法执行之前"); //目标对象(连接点)方法的执行
            result = joinPoint.proceed();
            System.out.println("环绕通知-->目标对象方法返回值之后");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            System.out.println("环绕通知-->目标对象方法出现异常时");
        } finally {
            System.out.println("环绕通知-->目标对象方法执行完毕");
        }
        return result;
    }

}

