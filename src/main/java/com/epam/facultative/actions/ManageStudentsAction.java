package com.epam.facultative.actions;

import com.epam.facultative.dto.UserDTO;
import com.epam.facultative.exception.ServiceException;
import com.epam.facultative.service.AdminService;
import com.epam.facultative.service.ServiceFactory;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public class ManageStudentsAction implements Action {
    @Override
    public String execute(HttpServletRequest req) {
        AdminService adminService = ServiceFactory.getInstance().getAdminService();
        try {
            List<UserDTO> students = adminService.getAllStudents();
            req.setAttribute("students", students);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }

        return "admin_students.jsp";
    }
}
