INSERT INTO mobile (name, brand, price, description, rating) VALUES
('iPhone 14 Pro', 'Apple', 1199.99, 'Flagship Apple phone with triple-camera system and A16 Bionic chip.', 4.8),
('Galaxy S23 Ultra', 'Samsung', 1099.99, 'Samsungs top-tier phone with S Pen support and 200MP camera.', 4.7),
('Pixel 8 Pro', 'Google', 999.00, 'Googles AI-powered smartphone with excellent computational photography.', 4.6),
('OnePlus 11', 'OnePlus', 699.99, 'High-performance device with Snapdragon 8 Gen 2 and fast charging.', 4.5),
('Xiaomi 13 Pro', 'Xiaomi', 899.50, 'Premium phone with Leica camera system and ceramic build.', 4.4);

insert into USERS(username, password) values('user', '$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW'); --password
insert into USERS(username, password) values('admin', '$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW'); --password
insert into USERS(username, password) values('read_only', '$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW'); --password
insert into USERS(username, password) values('dummy', '$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW'); --password

insert into roles(name) values('USER');
insert into roles(name) values('ADMIN');
insert into roles(name) values('READ_ONLY');
insert into roles(name) values('DUMMY');

insert into role_user(application_user_id, role_id) values(1, 1);
insert into role_user(application_user_id, role_id) values(2, 1);
insert into role_user(application_user_id, role_id) values(2, 2);
insert into role_user(application_user_id, role_id) values(3, 3);