package com.epam.facultative.daos;

import com.epam.facultative.daos.impl.RoleDao;
import com.epam.facultative.entity.Role;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RoleDaoTest {
    private static RoleDao roleDao;
    private static Role role;

    @BeforeAll
    static void setUp() {
        roleDao = new RoleDao();
        role = new Role(11, "Other");
    }

    @Order(1)
    @Test
    void findAll() {
        assertEquals(3, roleDao.findAll().size());
    }

    @Order(2)
    @Test
    void insert() {
        roleDao.insert(role);
        assertEquals(role.toString(), roleDao.findById(role.getId()).toString());
    }

    @Order(3)
    @Test
    void update() {
        role.setTitle("Everybody");
        roleDao.update(role);
        assertEquals(role.toString(), roleDao.findById(role.getId()).toString());
    }

    @Order(4)
    @Test
    void findById() {
        assertEquals(role.toString(), roleDao.findById(role.getId()).toString());
    }

    @Order(5)
    @Test
    void delete() {
        roleDao.delete(role);
        assertEquals(3, roleDao.findAll().size());
    }
}