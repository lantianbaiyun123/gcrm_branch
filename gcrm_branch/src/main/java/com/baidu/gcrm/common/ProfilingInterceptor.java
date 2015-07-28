package com.baidu.gcrm.common;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.util.StopWatch;

import com.baidu.gcrm.common.log.LoggerHelper;


/**
 * 拦截器，实现方法包围通知
 */
public class ProfilingInterceptor implements MethodInterceptor {
    /**
     * DOCUMENT ME!
     *
     * @param invocation DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws Throwable DOCUMENT ME!
     */
    public Object invoke(MethodInvocation invocation) throws Throwable {
        // 启动一个 stop watch
        StopWatch sw = new StopWatch();

        // 运行计时器
        sw.start(invocation.getMethod().getName());

        // 执行业务方法
        Object returnValue = invocation.proceed();

        // 停止计时器
        sw.stop();

        // 垃圾信息输出
        dumpInfo(invocation, sw.getTotalTimeMillis(), returnValue);

        // 返回业务方法返回值
        return returnValue;
    }

    /**
     * 垃圾信息输入方法，实际上输出的是方法运行的计时信息
     *
     * @param invocation DOCUMENT ME!
     * @param ms DOCUMENT ME!
     * @param returnValue DOCUMENT ME!
     */
    private void dumpInfo(MethodInvocation invocation, long ms, Object returnValue) {
        // 获取被调用方法
        Method m = invocation.getMethod();

        // 获取被调用方法所属的对象
        Object target = invocation.getThis();

        // 获取被调用方法的参数
        Object[] args = invocation.getArguments();
        String classSimpleName = target.getClass().getSimpleName();

        if (classSimpleName.startsWith("$Proxy")) {
            return;
        }

        Object[] result;

        if (returnValue instanceof Object[]) {
            result = (Object[]) returnValue;
        } else {
            result = new Object[] { returnValue };
        }

        LoggerHelper.logProfiling(ms, classSimpleName, m.getName(), CommonHelper.deepToString(args),
            CommonHelper.deepToString(result));
    }
}
