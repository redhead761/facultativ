package com.epam.facultative.daos;

import com.epam.facultative.entity.Status;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StatusDaoTest {
    private static StatusDao statusDao;
    private static Status status;

    @BeforeAll
    static void setUp() {
        statusDao = new StatusDao();
        status = new Status(11, "Complete and delete");
    }

    @Order(1)
    @Test
    void findAll() {
        assertEquals(3, statusDao.getAll().size());
    }

    @Order(2)
    @Test
    void insert() {
        statusDao.add(status);
        assertEquals(status.toString(), statusDao.getByName(status.getTitle()).toString());
    }

    @Order(3)
    @Test
    void update() {
        status.setTitle("Over");
        statusDao.update(status);
        assertEquals(status.toString(), statusDao.getById(status.getId()).toString());
    }

    @Order(4)
    @Test
    void findById() {
        assertEquals(status.toString(), statusDao.getById(status.getId()).toString());
    }

    @Order(5)
    @Test
    void findByTitle() {
        assertEquals(status.toString(), statusDao.getByName(status.getTitle()).toString());
    }

    @Order(6)
    @Test
    void delete() {
        statusDao.delete(status);
        assertEquals(3, statusDao.getAll().size());
    }
}