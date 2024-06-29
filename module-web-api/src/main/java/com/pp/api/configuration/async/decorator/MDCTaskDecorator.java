package com.pp.api.configuration.async.decorator;

import org.springframework.core.task.TaskDecorator;

import java.util.Map;

import static org.slf4j.MDC.*;

public class MDCTaskDecorator implements TaskDecorator {

    @Override
    public Runnable decorate(Runnable runnable) {
        Map<String, String> contexts = getCopyOfContextMap();

        return () -> {
            try {
                setContextMap(contexts);
                runnable.run();
            } finally {
                clear();
            }
        };
    }

}
