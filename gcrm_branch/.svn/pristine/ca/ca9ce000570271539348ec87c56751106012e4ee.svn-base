package com.baidu.gcrm.common.log;

import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.ThrowsAdvice;

import com.baidu.gcrm.common.CommonHelper;
import com.baidu.gcrm.common.GcrmConfig;
import com.baidu.gwfp.ws.exception.IBusinessException;



/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description: 日志拦截类
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * 
 * <p>
 * Company: www.baidu.com
 * </p>
 *
 * @author fangjp
 * @version 1.0
 */
public class LoggerInterceptor implements ThrowsAdvice, MethodBeforeAdvice, AfterReturningAdvice {
    /** 字符串最大长度 */
    public static final int MAX_STR_LENGTH = 5000;

    /**
     * 被拦截方法正常执行以后,此方法被调用
     *
     * @param returnValue Object 目标方法返回的结果
     * @param method Method 目标方法对象
     * @param args Object[] 目标方法的参数
     * @param target Object 目标类对象
     *
     * @throws Throwable
     */
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target)
        throws Throwable {
        String methodName = method.getName();
        
        if(contain(methodName)){
        	// 方法不输出log
        	return;
        }
        LoggerHelper.info(target.getClass(),
            methodName + "结果:" +
            ((returnValue instanceof Object[]) ? CommonHelper.deepToString((Object[]) returnValue, MAX_STR_LENGTH)
                                               : CommonHelper.deepToString(new Object[] { returnValue }, MAX_STR_LENGTH)));
    }

    /**
     * 被拦截方法执行前,此方法被调用
     *
     * @param method Method 目标方法对象
     * @param args Object[] 目标方法的参数
     * @param target Object 目标类对象
     *
     * @throws Throwable
     */
    public void before(Method method, Object[] args, Object target)
        throws Throwable {
        String methodName = method.getName();
        if(contain(methodName)){
        	// 方法不输出log
        	return;
        }
        LoggerHelper.info(target.getClass(), methodName + "被调用,参数:" + CommonHelper.deepToString(args, MAX_STR_LENGTH));
    }

    /**
     * 方法抛出异常以后所执行的方法
     *
     * @param method Method 目标方法对象
     * @param args Object[] 目标方法的参数
     * @param target Object 目标类对象
     * @param ex Throwable
     */
    public void afterThrowing(Method method, Object[] args, Object target, Throwable ex) {
        if(ex instanceof IBusinessException){
    		LoggerHelper.warn(target.getClass(),
		            " " + method.getName() + "(" + CommonHelper.deepToString(args,MAX_STR_LENGTH) + ")抛出业务异常:" + ex.getMessage());
		}else{
			LoggerHelper.err(target.getClass(),
		            " " + method.getName() + "(" + CommonHelper.deepToString(args,MAX_STR_LENGTH) + ")抛出异常:" + ex.getMessage(), ex);
		}
    }
    /**
     * 是否包含不输出的名称
     * @param name
     * @return
     */
    private boolean contain(String name){
    	name =StringUtils.trimToEmpty(name);
    	return GcrmConfig.isPermitOutputLog(name);
    }
}
