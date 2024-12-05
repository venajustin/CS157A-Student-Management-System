-- Populare Students
-- An account with an easy to sign into email and password (password is qwerqwer)
INSERT INTO public.students (studentid, name, email, password, birthdate, major, minor, gpa, unitscompleted)
VALUES (DEFAULT, 'justin', 'qwer', '$2a$06$/HdpJIHlAZ9a36sKUxHN1OvSmZ77gXR6nGTdnlM0clgaiNcCTz7Qu', null, 'qwer', 'N/A',
        null, null);

-- Populate Departments
INSERT INTO departments VALUES ('MATH', 'Mathmatics');
INSERT INTO departments VALUES ('CS', 'Computer Science');
INSERT INTO departments VALUES ('CMPE', 'Computer Engineering');
INSERT INTO departments VALUES ('SE', 'Software Engineering');
INSERT INTO departments VALUES ('EE', 'Electrical Engineering');
INSERT INTO departments VALUES ('ME', 'Mechanical Engineering');
INSERT INTO departments VALUES ('COMM', 'Communications');
INSERT INTO departments VALUES ('PHYS', 'Physics and Astronomy');
INSERT INTO departments VALUES ('ENGR', 'General Engineering');
INSERT INTO departments VALUES ('AFAM', 'African American Studies');
INSERT INTO departments VALUES ('ISE', 'Industrial Engineering');


-- Populate Courses
INSERT INTO courses VALUES ('130', 'ISE', 'Engineering Statistics', 'Introduction to statistical analysys and probability distributions', 3);
INSERT INTO courses VALUES ('120', 'CMPE', 'Computer Organization and Architecture', 'CPU Architecture and design. Cache hierarchy, Multithreading and RISC-V assembly', 3);
INSERT INTO courses VALUES ('102', 'CMPE', 'Assebly Language Programming', 'Assembly language. Fundamentals of static compilation of C source code.', 4);

-- Add an ongoing session ( this current season )
INSERT INTO sessions VALUES (default, to_date('7/15/2024', 'DD/MM/YYYY'), to_date('12/30/2024', 'DD/MM/YYYY'), 'FALL');

-- Populate Sections
INSERT INTO sections VALUES (default, 'ISE', 130, )
