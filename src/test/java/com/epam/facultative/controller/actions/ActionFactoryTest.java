package com.epam.facultative.controller.actions;

import com.epam.facultative.controller.app_context.AppContext;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActionFactoryTest {
    private static ActionFactory actionFactory;

    @BeforeAll
    static void beforeAll() {
        AppContext.createAppContext("configuration.properties");
        actionFactory = ActionFactory.getActionFactory();
    }

    @Test
    void getActionFactory() {
        assertInstanceOf(ActionFactory.class, actionFactory);

    }

    @Test
    void getAction() {
        Action action = actionFactory.getAction("auth");
        assertInstanceOf(Action.class, action);
    }
}