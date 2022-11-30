package com.epam.facultative.actions;

import com.epam.facultative.dto.UserDTO;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.AdminService;
import com.epam.facultative.service.ServiceFactory;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public class ManageTeachersAction implements Action {
    @Override
    public String execute(HttpServletRequest req) {
        AdminService adminService = ServiceFactory.getInstance().getAdminService();
        try {
            List<UserDTO> teachers = adminService.getAllTeachers();
            req.setAttribute("teachers", teachers);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        return "admin_teachers.jsp";
    }
}
