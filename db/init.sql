-- jobtype
INSERT INTO `jobtype` (`id`, `create_at`, `job_type_name`, `update_at`)
VALUES (1, '2024-01-01 08:00:00', 'Full-Time', '2024-01-01 08:00:00'),
       (2, '2024-02-01 08:00:00', 'Part-Time', '2024-02-01 08:00:00'),
       (3, '2024-03-01 08:00:00', 'Contract', '2024-03-01 08:00:00'),
       (4, '2024-04-01 08:00:00', 'Internship', '2024-04-01 08:00:00'),
       (5, '2024-05-01 08:00:00', 'Freelance', '2024-05-01 08:00:00'),
       (6, '2024-06-01 08:00:00', 'Temporary', '2024-06-01 08:00:00'),
       (7, '2024-07-01 08:00:00', 'Consultant', '2024-07-01 08:00:00'),
       (8, '2024-08-01 08:00:00', 'Seasonal', '2024-08-01 08:00:00'),
       (9, '2024-09-01 08:00:00', 'Remote', '2024-09-01 08:00:00'),
       (10, '2024-10-01 08:00:00', 'Volunteer', '2024-10-01 08:00:00');

-- position
INSERT INTO `position` (`id`, `position_name`)
VALUES (1, 'Software Engineer'),
       (2, 'Project Manager'),
       (3, 'Business Analyst'),
       (4, 'Quality Assurance'),
       (5, 'DevOps Engineer'),
       (6, 'Data Scientist'),
       (7, 'Product Owner'),
       (8, 'UX/UI Designer'),
       (9, 'Scrum Master'),
       (10, 'Technical Lead');

-- user, user_passwords is 123456
INSERT INTO `user` (`id`, `user_full_name`, `user_name`, `user_passwords`)
VALUES (1, 'Alice Johnson', 'alicej', '{bcrypt}$2a$10$szCPjIxEd/R/Mc76BZW85ujRvR8t9Zi45KIVqdjZL75mq4L4nhx2W'),
       (2, 'Bob Smith', 'bobsmith', '{bcrypt}$2a$10$szCPjIxEd/R/Mc76BZW85ujRvR8t9Zi45KIVqdjZL75mq4L4nhx2W'),
       (3, 'Charlie Brown', 'charlieb', '{bcrypt}$2a$10$szCPjIxEd/R/Mc76BZW85ujRvR8t9Zi45KIVqdjZL75mq4L4nhx2W'),
       (4, 'Daisy Lee', 'daisylee', '{bcrypt}$2a$10$szCPjIxEd/R/Mc76BZW85ujRvR8t9Zi45KIVqdjZL75mq4L4nhx2W'),
       (5, 'Evan Davis', 'evand', '{bcrypt}$2a$10$szCPjIxEd/R/Mc76BZW85ujRvR8t9Zi45KIVqdjZL75mq4L4nhx2W'),
       (6, 'Fiona Green', 'fionag', '{bcrypt}$2a$10$szCPjIxEd/R/Mc76BZW85ujRvR8t9Zi45KIVqdjZL75mq4L4nhx2W'),
       (7, 'George White', 'georgew', '{bcrypt}$2a$10$szCPjIxEd/R/Mc76BZW85ujRvR8t9Zi45KIVqdjZL75mq4L4nhx2W'),
       (8, 'Hannah Kim', 'hannahk', '{bcrypt}$2a$10$szCPjIxEd/R/Mc76BZW85ujRvR8t9Zi45KIVqdjZL75mq4L4nhx2W'),
       (9, 'Ivy Chen', 'ivyc', '{bcrypt}$2a$10$szCPjIxEd/R/Mc76BZW85ujRvR8t9Zi45KIVqdjZL75mq4L4nhx2W'),
       (10, 'Jack Brown', 'jackb', '{bcrypt}$2a$10$szCPjIxEd/R/Mc76BZW85ujRvR8t9Zi45KIVqdjZL75mq4L4nhx2W');

-- department
INSERT INTO `department` (`id`, `department_name`, `user_id`)
VALUES (1, 'Engineering', 1),
       (2, 'Marketing', 2),
       (3, 'Sales', 3),
       (4, 'HR', 4),
       (5, 'Customer Support', 5),
       (6, 'Finance', 6),
       (7, 'Product', 7),
       (8, 'Operations', 8),
       (9, 'Legal', 9),
       (10, 'IT', 10);

-- department_jobtype
INSERT INTO `department_jobtype` (`department_id`, `jobtype_id`)
VALUES (1, 1),
       (2, 2),
       (3, 3),
       (4, 4),
       (5, 5),
       (6, 6),
       (7, 7),
       (8, 8),
       (9, 9),
       (10, 10);

-- user_department
INSERT INTO `user_department` (`user_id`, `department_id`)
VALUES (1, 1),
       (2, 2),
       (3, 3),
       (4, 4),
       (5, 5),
       (6, 6),
       (7, 7),
       (8, 8),
       (9, 9),
       (10, 10);

-- user_position
INSERT INTO `user_position` (`user_id`, `position_id`)
VALUES (1, 1),
       (2, 2),
       (3, 3),
       (4, 4),
       (5, 5),
       (6, 6),
       (7, 7),
       (8, 8),
       (9, 9),
       (10, 10);

-- working_time
INSERT INTO `working_time` (`id`, `breaktime`, `checkin_time`, `checkout_time`, `date`, `overtime`, `user_id`, `worktime`)
VALUES (1, 1.0, '2024-01-01 09:00:00', '2024-01-01 18:00:00', '2024-01-01', 2.0, 1, 8.0),
       (2, 1.0, '2024-01-02 09:00:00', '2024-01-02 18:00:00', '2024-01-02', 1.5, 2, 8.5),
       (3, 1.0, '2024-01-03 09:00:00', '2024-01-03 17:30:00', '2024-01-03', 0.0, 3, 7.5),
       (4, 1.0, '2024-01-04 09:15:00', '2024-01-04 18:00:00', '2024-01-04', 1.0, 4, 8.0),
       (5, 1.0, '2024-01-05 09:00:00', '2024-01-05 18:30:00', '2024-01-05', 0.5, 5, 8.5),
       (6, 1.0, '2024-01-06 08:45:00', '2024-01-06 18:15:00', '2024-01-06', 2.0, 6, 9.0),
       (7, 1.0, '2024-01-07 09:00:00', '2024-01-07 17:45:00', '2024-01-07', 0.5, 7, 8.0),
       (8, 1.0, '2024-01-08 09:30:00', '2024-01-08 18:30:00', '2024-01-08', 1.0, 8, 8.5),
       (9, 1.0, '2024-01-09 09:00:00', '2024-01-09 18:00:00', '2024-01-09', 0.0, 9, 8.0),
       (10, 1.0, '2024-01-10 08:30:00', '2024-01-10 18:00:00', '2024-01-10', 2.5, 10, 9.0);

-- project
INSERT INTO `project` (`id`, `code`, `create_at`, `name`, `update_at`)
VALUES (1, 'PRJ001', '2024-01-10 09:00:00', 'Attendance App Development', '2024-01-10 09:00:00'),
       (2, 'PRJ002', '2024-02-15 10:00:00', 'E-Commerce Platform', '2024-02-15 10:00:00'),
       (3, 'PRJ003', '2024-03-05 08:30:00', 'HR Management System', '2024-03-05 08:30:00'),
       (4, 'PRJ004', '2024-04-20 11:15:00', 'AI Chatbot Integration', '2024-04-20 11:15:00'),
       (5, 'PRJ005', '2024-05-18 09:45:00', 'Mobile App Development', '2024-05-18 09:45:00'),
       (6, 'PRJ006', '2024-06-21 14:00:00', 'Data Analytics Dashboard', '2024-06-21 14:00:00'),
       (7, 'PRJ007', '2024-07-19 10:30:00', 'ERP Solution', '2024-07-19 10:30:00'),
       (8, 'PRJ008', '2024-08-25 13:00:00', 'Inventory Management System', '2024-08-25 13:00:00'),
       (9, 'PRJ009', '2024-09-10 15:20:00', 'CRM System', '2024-09-10 15:20:00'),
       (10, 'PRJ010', '2024-10-05 12:30:00', 'Customer Feedback Portal', '2024-10-05 12:30:00');

-- task
INSERT INTO `task` (`id`, `comment`, `date`, `total_time`, `jobtype_id`, `project_id`, `working_time_id`)
VALUES ('T001', 'Initial Setup', '2024-01-01 10:00:00', 5.0, 1, 1, 1),
       ('T002', 'Requirement Analysis', '2024-01-02 11:30:00', 4.0, 2, 2, 2),
       ('T003', 'Database Design', '2024-01-03 13:45:00', 6.0, 3, 3, 3),
       ('T004', 'API Development', '2024-01-04 09:00:00', 8.0, 4, 4, 4),
       ('T005', 'Frontend Development', '2024-01-05 14:00:00', 7.0, 5, 5, 5),
       ('T006', 'Testing', '2024-01-06 16:30:00', 3.0, 6, 6, 6),
       ('T007', 'Bug Fixing', '2024-01-07 09:15:00', 4.5, 7, 7, 7),
       ('T008', 'Deployment', '2024-01-08 10:45:00', 5.5, 8, 8, 8),
       ('T009', 'Documentation', '2024-01-09 13:30:00', 2.0, 9, 9, 9),
       ('T010', 'Review and Retrospective', '2024-01-10 15:15:00', 3.5, 10, 10, 10);




