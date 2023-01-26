package com.epam.facultative.model.service.implementation;

import com.epam.facultative.model.dao.CourseDao;
import com.epam.facultative.model.dao.StudentDao;
import com.epam.facultative.model.dao.TeacherDao;
import com.epam.facultative.model.entities.Role;
import com.epam.facultative.model.entities.Student;
import com.epam.facultative.model.entities.Teacher;
import com.epam.facultative.model.dto.StudentDTO;
import com.epam.facultative.model.dto.TeacherDTO;
import com.epam.facultative.model.exception.DAOException;
import com.epam.facultative.model.exception.ServiceException;
import com.epam.facultative.model.exception.ValidateException;
import com.epam.facultative.model.service.TeacherService;

import java.util.List;
import java.util.Map;

import static com.epam.facultative.model.utils.converter.Converter.*;
import static com.epam.facultative.model.utils.param_builder.ParamBuilderForQueryUtil.studentParamBuilderForQuery;
import static com.epam.facultative.model.utils.validator.ValidateExceptionMessageConstants.LOGIN_NOT_EXIST_MESSAGE;
import static com.epam.facultative.model.utils.validator.Validator.*;

public class TeacherServiceImpl implements TeacherService {
    private final CourseDao courseDao;
    private final StudentDao studentDao;
    private final TeacherDao teacherDao;

    public TeacherServiceImpl(CourseDao courseDao, StudentDao studentDao, TeacherDao teacherDao) {
        this.courseDao = courseDao;
        this.studentDao = studentDao;
        this.teacherDao = teacherDao;
    }

    @Override
    public void grading(int courseId, int userId, int grade) throws ServiceException {
        try {
            courseDao.updateJournal(courseId, userId, grade);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Map.Entry<Integer, List<StudentDTO>> getStudentsByCourse(String param) throws ServiceException {
        try {
            Map.Entry<Integer, List<Student>> studentsWithRows = studentDao.getStudentsByCourse(param);
            List<StudentDTO> students = prepareStudents(studentsWithRows.getValue());
            return Map.entry(studentsWithRows.getKey(), students);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public TeacherDTO updateTeacher(TeacherDTO teacherDTO) throws ValidateException, ServiceException {
        validateLogin(teacherDTO.getLogin());
        validateNameAndSurname(teacherDTO.getName(), teacherDTO.getSurname());
        validateEmail(teacherDTO.getEmail());
        teacherDTO.setRole(Role.TEACHER);
        try {
            Teacher teacher = teacherDao
                    .get(studentParamBuilderForQuery().setUserIdFilter(String.valueOf(teacherDTO.getId())).getParam())
                    .orElseThrow(() -> new ValidateException(LOGIN_NOT_EXIST_MESSAGE));
            teacher.setLogin(teacherDTO.getLogin());
            teacher.setName(teacherDTO.getName());
            teacher.setSurname(teacherDTO.getSurname());
            teacher.setEmail(teacherDTO.getEmail());
            teacher.setDegree(teacherDTO.getDegree());
            teacherDao.update(teacher);
            return convertTeacherToDTO(teacher);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

}