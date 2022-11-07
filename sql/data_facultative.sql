-- -----------------------------------------------------
-- INSERTS
-- -----------------------------------------------------

-- user
INSERT INTO user (id, login, password, first_name, last_name, email, role_id)
	VALUES		
		-- admin
		(DEFAULT, "admin", "admin", "admin", "admin", "admin@epam.com", (SELECT id FROM role WHERE name="admin")),
        -- teachers
        (DEFAULT, "teacher1", "teacher1", "teacher1", "teacher1", "teacher1@epam.com", (SELECT id FROM role WHERE name="teacher")),
        (DEFAULT, "teacher2", "teacher2", "teacher2", "teacher2", "teacher2@epam.com", (SELECT id FROM role WHERE name="teacher")),
        (DEFAULT, "teacher3", "teacher3", "teacher3", "teacher3", "teacher3@epam.com", (SELECT id FROM role WHERE name="teacher")),
        -- students
        (DEFAULT, "student1", "student1", "student1", "student1", "student1@epam.com", (SELECT id FROM role WHERE name="student")),
        (DEFAULT, "student2", "student2", "student2", "student2", "student2@epam.com", (SELECT id FROM role WHERE name="student")),
        (DEFAULT, "student3", "student3", "student3", "student3", "student3@epam.com", (SELECT id FROM role WHERE name="student")),
        (DEFAULT, "student4", "student4", "student4", "student4", "student4@epam.com", (SELECT id FROM role WHERE name="student")),
        (DEFAULT, "student5", "student5", "student5", "student5", "student5@epam.com", (SELECT id FROM role WHERE name="student")),
        (DEFAULT, "student6", "student6", "student6", "student6", "student6@epam.com", (SELECT id FROM role WHERE name="student")),
        (DEFAULT, "student7", "student7", "student7", "student7", "student7@epam.com", (SELECT id FROM role WHERE name="student")),
        (DEFAULT, "student8", "student8", "student8", "student8", "student8@epam.com", (SELECT id FROM role WHERE name="student")),
        (DEFAULT, "student9", "student9", "student9", "student9", "student9@epam.com", (SELECT id FROM role WHERE name="student"));
        
        
-- category
INSERT INTO category (id, name)
	VALUES
		(DEFAULT, "programming"),
        (DEFAULT, "english");
        
-- course
INSERT INTO course (id, title, duration, course_start_date, category_id, course_status_id)
	VALUES
		(DEFAULT, "java", 30, DEFAULT, (SELECT id FROM category WHERE name="programming"), (SELECT id FROM course_status WHERE name="not start")),
        (DEFAULT, "c#", 30, DEFAULT, (SELECT id FROM category WHERE name="programming"), (SELECT id FROM course_status WHERE name="not start")),
        (DEFAULT, "start speaking", 30, DEFAULT, (SELECT id FROM category WHERE name="english"), (SELECT id FROM course_status WHERE name="not start"));
        
        