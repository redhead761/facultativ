package com.epam.facultative.controller.listener;

import com.epam.facultative.controller.app_context.AppContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.extern.slf4j.Slf4j;

/**
 * ContextListener  class.
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
@WebListener
@Slf4j
public class ContextListener implements ServletContextListener {
    private static final String CONFIGURATION_PROPERTIES_FILE = "configuration.properties";

    /**
     * creates AppContext and passes properties to initialize all required classes
     *
     * @param sce passed by application
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        AppContext.createAppContext(CONFIGURATION_PROPERTIES_FILE);
        log.info("AppContext is set. Application started successfully");
    }
}