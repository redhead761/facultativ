package com.epam.facultative.controller.listener;

import com.epam.facultative.controller.app_context.AppContext;
import jakarta.servlet.ServletContextEvent;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

class ContextListenerTest {

    private static final ServletContextEvent servletContextEvent = mock(ServletContextEvent.class);

    @Test
    void testContextInitialized() {
        try (MockedStatic<AppContext> mocked = mockStatic(AppContext.class)) {
            mocked.when(() -> AppContext.createAppContext(isA(String.class)))
                    .thenAnswer(Answers.RETURNS_DEFAULTS);
            assertDoesNotThrow(() -> new ContextListener().contextInitialized(servletContextEvent));
        }
    }
}