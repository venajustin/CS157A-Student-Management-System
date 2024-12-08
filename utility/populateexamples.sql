
-- Populate Departments
INSERT INTO departments VALUES ('MATH', 'Mathmatics');
INSERT INTO departments VALUES ('CS', 'Computer Science');
INSERT INTO departments VALUES ('CMPE', 'Computer Engineering');
-- INSERT INTO departments VALUES ('SE', 'Software Engineering');
-- INSERT INTO departments VALUES ('EE', 'Electrical Engineering');
-- INSERT INTO departments VALUES ('ME', 'Mechanical Engineering');
-- INSERT INTO departments VALUES ('COMM', 'Communications');
-- INSERT INTO departments VALUES ('PHYS', 'Physics and Astronomy');
-- INSERT INTO departments VALUES ('ENGR', 'General Engineering');
-- INSERT INTO departments VALUES ('AFAM', 'African American Studies');
INSERT INTO departments VALUES ('ISE', 'Industrial Engineering');


-- Populate Courses
INSERT INTO courses VALUES ('130', 'ISE', 'Engineering Statistics', 'Introduction to statistical analysys and probability distributions', 3);
INSERT INTO courses VALUES ('120', 'CMPE', 'Computer Organization and Architecture', 'CPU Architecture and design. Cache hierarchy, Multithreading and RISC-V assembly', 3);
INSERT INTO courses VALUES ('102', 'CMPE', 'Assembly Language Programming', 'Assembly language. Fundamentals of static compilation of C source code.', 4);
INSERT INTO courses VALUES ('30', 'MATH', 'Calculus 1', 'Introduces the concepts of differential and integral calculus. Topics include limits, continuity, derivatives, and their applications to real-world problems such as optimization and motion.', 3);
INSERT INTO courses VALUES ('31', 'MATH', 'Calculus 2', 'Explore advanced integration techniques, infinite series, and applications of calculus to physics, engineering, and economics. Topics include Taylor series, polar coordinates, and sequences.', 3);
INSERT INTO courses VALUES ('32', 'MATH', 'Calculus 3', 'Extend calculus into three dimensions with topics like vectors, partial derivatives, multiple integrals, and vector calculus. Applications include physics and engineering, focusing on theorems such as Green''s and Stokes''.', 3);
INSERT INTO courses VALUES ('34', 'MATH', 'Differential Equations', 'Learn to solve ordinary differential equations (ODEs) and apply them to real-world systems in physics, biology, and engineering. Topics include Laplace transforms, numerical methods, and systems of equations.', 4);
INSERT INTO courses VALUES ('33', 'MATH', 'Linear Algebra', 'Study vector spaces, matrices, and linear transformations with applications in computer science, data analysis, and engineering. Key topics include eigenvalues, eigenvectors, and solving systems of equations.', 3);
INSERT INTO courses VALUES ('46', 'MATH', 'Discrete Math', 'Discover the foundations of discrete mathematics, including logic, set theory, graph theory, and combinatorics. Develop problem-solving and proof techniques essential for computer science and algorithms.', 3);
INSERT INTO courses VALUES ('46', 'CS', 'Java 1', 'Learn the fundamentals of programming using Java, including variables, loops, functions, and object-oriented principles. This course emphasizes problem-solving and coding skills for beginners.', 3);
INSERT INTO courses VALUES ('47', 'CS', 'Java 2', 'Build on basic Java skills by exploring advanced topics like inheritance, polymorphism, exception handling, and file input/output. Gain a deeper understanding of object-oriented design and Java development practices.', 3);
INSERT INTO courses VALUES ('146', 'CS', 'Data Structures and Algorithms', 'Study essential data structures such as arrays, linked lists, trees, and graphs, alongside algorithms for searching, sorting, and optimization. This course focuses on writing efficient code and analyzing algorithm performance.', 3);
INSERT INTO courses VALUES ('189', 'CS', 'Computer Vision', 'Explore the principles and techniques for enabling computers to interpret and process visual data. Topics include image processing, object detection, feature extraction, and applications like facial recognition and scene analysis.', 3);
INSERT INTO courses VALUES ('194', 'CS', 'Introduction to Machine Learning', 'Learn the fundamentals of machine learning, including supervised and unsupervised learning techniques, model evaluation, and applications in data analysis. Topics include regression, classification, and neural networks.', 3);
INSERT INTO courses VALUES ('144', 'CMPE', 'Digital Electronics', 'Learn the fundamentals of digital logic design, including binary systems, Boolean algebra, combinational and sequential circuits, and hardware implementation. This course provides the foundation for understanding computer hardware architecture.', 3);
INSERT INTO courses VALUES ('149', 'CMPE', 'Operating Systems', 'Explore the design and functionality of operating systems, including process management, memory allocation, file systems, and concurrency. Gain hands-on experience with concepts like scheduling, multitasking, and system security.', 3);


-- Add an ongoing session ( this current season )
INSERT INTO sessions VALUES (default, to_date('7/15/2024', 'MM/DD/YYYY'), to_date('12/30/2024', 'MM/DD/YYYY'), 'FALL');

-- Add a default teacher
INSERT INTO accounts VALUES (default, 'Smith', 'smith@school.edu', crypt('12345qwert', gen_salt('bf')), 'professor');
INSERT INTO accounts VALUES (default, 'Johnson', 'johnson@school.edu', crypt('12345qwert', gen_salt('bf')), 'professor');
INSERT INTO accounts VALUES (default, 'Garcia', 'garcia@school.edu', crypt('12345qwert', gen_salt('bf')), 'professor');
INSERT INTO accounts VALUES (default, 'Wilson', 'wilson@school.edu', crypt('12345qwert', gen_salt('bf')), 'professor');
INSERT INTO accounts VALUES (default, 'Brown', 'brown@school.edu', crypt('12345qwert', gen_salt('bf')), 'professor');
INSERT INTO accounts VALUES (default, 'Jones', 'jones@school.edu', crypt('12345qwert', gen_salt('bf')), 'professor');
INSERT INTO accounts VALUES (default, 'Miller', 'miller@school.edu', crypt('12345qwert', gen_salt('bf')), 'professor');
INSERT INTO accounts VALUES (default, 'Davis', 'davis@school.edu', crypt('12345qwert', gen_salt('bf')), 'professor');
INSERT INTO accounts VALUES (default, 'Martinez', 'martinez@school.edu', crypt('12345qwert', gen_salt('bf')), 'professor');
INSERT INTO accounts VALUES (default, 'Williams', 'williams@school.edu', crypt('12345qwert', gen_salt('bf')), 'professor');

INSERT INTO professors VALUES ((SELECT id FROM accounts WHERE name = 'Smith'), 'ISE');
INSERT INTO professors VALUES ((SELECT id FROM accounts WHERE name = 'Johnson'), 'CMPE');
INSERT INTO professors VALUES ((SELECT id FROM accounts WHERE name = 'Garcia'), 'CMPE');
INSERT INTO professors VALUES ((SELECT id FROM accounts WHERE name = 'Wilson'), 'MATH');
INSERT INTO professors VALUES ((SELECT id FROM accounts WHERE name = 'Brown'), 'MATH');
INSERT INTO professors VALUES ((SELECT id FROM accounts WHERE name = 'Jones'), 'MATH');
INSERT INTO professors VALUES ((SELECT id FROM accounts WHERE name = 'Miller'), 'MATH');
INSERT INTO professors VALUES ((SELECT id FROM accounts WHERE name = 'Davis'), 'CS');
INSERT INTO professors VALUES ((SELECT id FROM accounts WHERE name = 'Martinez'), 'CS');
INSERT INTO professors VALUES ((SELECT id FROM accounts WHERE name = 'Williams'), 'CS');

-- Populate Sections
INSERT INTO sections VALUES (default, 'ISE', 130, current_session(), 'MW', '10:30:00', '13:00:00', (SELECT id from accounts where name LIKE 'Smith'));
INSERT INTO sections VALUES (default, 'ISE', 130, current_session(), 'MW', '12:15:00', '14:30:00', (SELECT id from accounts where name LIKE 'Smith'));
INSERT INTO sections VALUES (default, 'ISE', 130, current_session(), 'MW', '10:30:00', '12:00:00', (SELECT id from accounts where name LIKE 'Smith'));
INSERT INTO sections VALUES (default, 'CMPE', 120, current_session(), 'MW', '09:00:00', '10:30:00', (SELECT id from accounts where name LIKE 'Garcia'));
INSERT INTO sections VALUES (default, 'CMPE', 120, current_session(), 'F', '13:00:00', '16:00:00', (SELECT id from accounts where name LIKE 'Garcia'));
INSERT INTO sections VALUES (default, 'CMPE', 102, current_session(), 'TR', '08:00:00', '09:30:00', (SELECT id from accounts where name LIKE 'Johnson'));
INSERT INTO sections VALUES (default, 'CMPE', 102, current_session(), 'MW', '13:30:00', '15:00:00', (SELECT id from accounts where name LIKE 'Johnson'));
INSERT INTO sections VALUES (default, 'CMPE', 102, current_session(), 'F', '08:30:00', '11:30:00', (SELECT id from accounts where name LIKE 'Smith'));
INSERT INTO sections VALUES (default, 'MATH', 30, current_session(), 'MW', '11:00:00', '12:30:00', (SELECT id from accounts where name LIKE 'Wilson'));
INSERT INTO sections VALUES (default, 'MATH', 30, current_session(), 'TR', '15:30:00', '17:00:00', (SELECT id from accounts where name LIKE 'Wilson'));
INSERT INTO sections VALUES (default, 'MATH', 30, current_session(), 'TR', '12:30:00', '14:00:00', (SELECT id from accounts where name LIKE 'Wilson'));
INSERT INTO sections VALUES (default, 'MATH', 30, current_session(), 'MW', '15:00:00', '16:30:00', (SELECT id from accounts where name LIKE 'Brown'));
INSERT INTO sections VALUES (default, 'MATH', 31, current_session(), 'MW', '10:00:00', '11:30:00', (SELECT id from accounts where name LIKE 'Wilson'));
INSERT INTO sections VALUES (default, 'MATH', 31, current_session(), 'F', '13:00:00', '16:00:00', (SELECT id from accounts where name LIKE 'Wilson'));
INSERT INTO sections VALUES (default, 'MATH', 31, current_session(), 'TR', '09:30:00', '11:00:00', (SELECT id from accounts where name LIKE 'Brown'));
INSERT INTO sections VALUES (default, 'MATH', 31, current_session(), 'MW', '13:00:00', '14:30:00', (SELECT id from accounts where name LIKE 'Brown'));
INSERT INTO sections VALUES (default, 'MATH', 32, current_session(), 'MW', '09:30:00', '11:00:00', (SELECT id from accounts where name LIKE 'Jones'));
INSERT INTO sections VALUES (default, 'MATH', 32, current_session(), 'TR', '14:00:00', '15:30:00', (SELECT id from accounts where name LIKE 'Jones'));
INSERT INTO sections VALUES (default, 'MATH', 32, current_session(), 'MW', '16:30:00', '18:00:00', (SELECT id from accounts where name LIKE 'Jones'));
INSERT INTO sections VALUES (default, 'MATH', 34, current_session(), 'MW', '12:30:00', '14:00:00', (SELECT id from accounts where name LIKE 'Jones'));
INSERT INTO sections VALUES (default, 'MATH', 34, current_session(), 'F', '09:00:00', '12:00:00', (SELECT id from accounts where name LIKE 'Jones'));
INSERT INTO sections VALUES (default, 'MATH', 34, current_session(), 'TR', '08:30:00', '10:00:00', (SELECT id from accounts where name LIKE 'Miller'));
INSERT INTO sections VALUES (default, 'MATH', 34, current_session(), 'MW', '14:30:00', '16:00:00', (SELECT id from accounts where name LIKE 'Miller'));
INSERT INTO sections VALUES (default, 'MATH', 33, current_session(), 'TR', '13:00:00', '14:30:00', (SELECT id from accounts where name LIKE 'Brown'));
INSERT INTO sections VALUES (default, 'MATH', 33, current_session(), 'MW', '15:00:00', '16:30:00', (SELECT id from accounts where name LIKE 'Brown'));
INSERT INTO sections VALUES (default, 'MATH', 33, current_session(), 'MW', '11:30:00', '13:00:00', (SELECT id from accounts where name LIKE 'Brown'));
INSERT INTO sections VALUES (default, 'MATH', 46, current_session(), 'MW', '10:00:00', '11:30:00', (SELECT id from accounts where name LIKE 'Miller'));
INSERT INTO sections VALUES (default, 'MATH', 46, current_session(), 'TR', '13:00:00', '14:30:00', (SELECT id from accounts where name LIKE 'Miller'));
INSERT INTO sections VALUES (default, 'MATH', 46, current_session(), 'MW', '09:30:00', '11:00:00', (SELECT id from accounts where name LIKE 'Miller'));
INSERT INTO sections VALUES (default, 'CS', 46, current_session(), 'MW', '08:00:00', '09:30:00', (SELECT id from accounts where name LIKE 'Davis'));
INSERT INTO sections VALUES (default, 'CS', 46, current_session(), 'TR', '10:30:00', '12:00:00', (SELECT id from accounts where name LIKE 'Davis'));
INSERT INTO sections VALUES (default, 'CS', 46, current_session(), 'TR', '08:00:00', '09:30:00', (SELECT id from accounts where name LIKE 'Davis'));
INSERT INTO sections VALUES (default, 'CS', 47, current_session(), 'F', '08:00:00', '11:00:00', (SELECT id from accounts where name LIKE 'Davis'));
INSERT INTO sections VALUES (default, 'CS', 47, current_session(), 'MW', '10:30:00', '12:00:00', (SELECT id from accounts where name LIKE 'Davis'));
INSERT INTO sections VALUES (default, 'CS', 47, current_session(), 'F', '13:30:00', '16:30:00', (SELECT id from accounts where name LIKE 'Davis'));
INSERT INTO sections VALUES (default, 'CS', 146, current_session(), 'TR', '12:30:00', '14:00:00', (SELECT id from accounts where name LIKE 'Martinez'));
INSERT INTO sections VALUES (default, 'CS', 146, current_session(), 'MW', '16:00:00', '17:30:00', (SELECT id from accounts where name LIKE 'Martinez'));
INSERT INTO sections VALUES (default, 'CS', 146, current_session(), 'TR', '15:30:00', '17:00:00', (SELECT id from accounts where name LIKE 'Martinez'));
INSERT INTO sections VALUES (default, 'CS', 146, current_session(), 'MW', '08:00:00', '09:30:00', (SELECT id from accounts where name LIKE 'Williams'));
INSERT INTO sections VALUES (default, 'CS', 189, current_session(), 'TR', '11:30:00', '13:00:00', (SELECT id from accounts where name LIKE 'Williams'));
INSERT INTO sections VALUES (default, 'CS', 194, current_session(), 'TR', '14:00:00', '15:30:00', (SELECT id from accounts where name LIKE 'Martinez'));
INSERT INTO sections VALUES (default, 'CS', 194, current_session(), 'MW', '16:00:00', '17:30:00', (SELECT id from accounts where name LIKE 'Williams'));
INSERT INTO sections VALUES (default, 'CMPE', 144, current_session(), 'TR', '08:00:00', '09:30:00', (SELECT id from accounts where name LIKE 'Garcia'));
INSERT INTO sections VALUES (default, 'CMPE', 144, current_session(), 'F', '09:00:00', '12:00:00', (SELECT id from accounts where name LIKE 'Garcia'));
INSERT INTO sections VALUES (default, 'CMPE', 149, current_session(), 'TR', '09:00:00', '10:30:00', (SELECT id from accounts where name LIKE 'Johnson'));
INSERT INTO sections VALUES (default, 'CMPE', 149, current_session(), 'TR', '10:30:00', '12:00:00', (SELECT id from accounts where name LIKE 'Johnson'));
