package org.apache.skywalking.apm.toolkit.activation.log.logback.v1.x.logstash;

import ch.qos.logback.classic.spi.ILoggingEvent;
import org.apache.skywalking.apm.agent.core.context.ContextManager;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.enhance.EnhancedInstance;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.enhance.InstanceMethodsAroundInterceptor;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.enhance.MethodInterceptResult;

import java.lang.reflect.Method;

public class FileAppenderInterceptor implements InstanceMethodsAroundInterceptor {

    @Override
    public void beforeMethod(EnhancedInstance objInst, Method method, Object[] allArguments, Class<?>[] argumentsTypes,
                             MethodInterceptResult result) throws Throwable {
        ILoggingEvent event = (ILoggingEvent) allArguments[0];
        if (event != null && event.getLoggerContextVO() != null && event.getLoggerContextVO()
                .getPropertyMap() != null) {
            event.getLoggerContextVO().getPropertyMap().put("TID", ContextManager.getGlobalTraceId());
        }
    }

    @Override
    public Object afterMethod(EnhancedInstance objInst, Method method, Object[] allArguments, Class<?>[] argumentsTypes,
                              Object ret) throws Throwable {
        return ret;
    }

    @Override
    public void handleMethodException(EnhancedInstance objInst, Method method, Object[] allArguments,
                                      Class<?>[] argumentsTypes, Throwable t) {

    }
}
