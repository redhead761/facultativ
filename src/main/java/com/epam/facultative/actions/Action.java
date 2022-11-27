package com.epam.facultative.actions;

import jakarta.servlet.http.HttpServletRequest;

public interface Action {
    String execute(HttpServletRequest req);
}
