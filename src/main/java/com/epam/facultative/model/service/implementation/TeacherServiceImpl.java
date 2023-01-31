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
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

import static com.epam.facultative.model.exception.ConstantsValidateMessage.*;
import static com.epam.facultative.model.utils.converter.Converter.*;
import static com.epam.facultative.model.utils.param_builder.ParamBuilderForQueryUtil.studentParamBuilderForQuery;
import static com.epam.facultative.model.utils.validator.Validator.*;

/**
 * Implementation of StudentService interface.
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {
    private final CourseDao courseDao;
    private final StudentDao studentDao;
    private final TeacherDao teacherDao;

    /**
     * Gets courseId, userId and grade and calls DAO to add student`s grade for course.
     *
     * @param courseId,userId - use to find
     * @param grade           - use to add
     * @throws ServiceException - may wrap DAOException or be thrown by another mistakes
     */

    @Override
    public void grading(int courseId, int userId, int grade) throws ServiceException {
        try {
            courseDao.updateJournal(courseId, userId, grade);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Gets parameter from action and calls DAO to get relevant entities and count rows. Convert entity to DTO.
     *
     * @param param - parameters to get
     * @return {@code Map.Entry<Integer, List < StudentDTO>>} - return relevant DTO and count rows
     * @throws ServiceException - may wrap DAOException or be thrown by another mistakes
     */
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

    /**
     * Gets TeacherDTO from action and calls DAO to update relevant entity. Validate teacher's fields. Convert DTO to entity.
     *
     * @param teacherDTO - DTO to be updated as Teacher to database
     * @throws ServiceException  - may wrap DAOException or be thrown by another mistakes
     * @throws ValidateException - occurs in case of non-validation of data
     */
    @Override
    public TeacherDTO updateTeacher(TeacherDTO teacherDTO) throws ValidateException, ServiceException {
        validateLogin(teacherDTO.getLogin());
        validateName(teacherDTO.getName());
        validateName(teacherDTO.getSurname());
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