INSERT INTO tb_family(id, last_name, state, city, street, address_number, collection_date) VALUES (UUID(), 'Pereira', 'SP', 'São Paulo', 'Soares de Faria', 60, NOW());
INSERT INTO tb_family(id, last_name, state, city, street, address_number, collection_date) VALUES (UUID(), 'Alric', 'MG', 'Belo Horizonte', 'Mendes Ribeiro', 70, NOW());
INSERT INTO tb_family(id, last_name, state, city, street, address_number, collection_date) VALUES (UUID(), 'Souza', 'MS', 'Campo Grande', 'Alameda Café', 80, NOW());
INSERT INTO tb_family(id, last_name, state, city, street, address_number, collection_date) VALUES (UUID(), 'Silva', 'RR', 'Iracema', 'Marechal Rondon', 90, NOW());
INSERT INTO tb_family(id, last_name, state, city, street, address_number, collection_date) VALUES (UUID(), 'Lima', 'RO', 'Cabixi', 'Baroros', 100, NOW());

SET @family1_id = (SELECT id FROM tb_family WHERE last_name = 'Pereira');
SET @family2_id = (SELECT id FROM tb_family WHERE last_name = 'Alric');
SET @family3_id = (SELECT id FROM tb_family WHERE last_name = 'Souza');
SET @family4_id = (SELECT id FROM tb_family WHERE last_name = 'Silva');
SET @family5_id = (SELECT id FROM tb_family WHERE last_name = 'Lima');

INSERT INTO tb_person(id, first_name, last_name, date_birth, age, cpf, income, collection_date, family_id) VALUES (UUID(), 'Ana', 'Albuquerque Pereira', '1960-01-01', 64, '323.244.300-10', 3000.00, NOW(), @family1_id);
INSERT INTO tb_person(id, first_name, last_name, date_birth, age, cpf, income, collection_date, family_id) VALUES (UUID(), 'Lúcio', 'Schendroski Alric ', '1970-02-02', 54, '641.179.010-87', 4000.00, NOW(), @family2_id);
INSERT INTO tb_person(id, first_name, last_name, date_birth, age, cpf, income, collection_date, family_id) VALUES (UUID(), 'Amanda', 'Ferraz Souza ', '1980-03-03', 44, '456.374.800-52', 5000.00, NOW(), @family3_id);
INSERT INTO tb_person(id, first_name, last_name, date_birth, age, cpf, income, collection_date, family_id) VALUES (UUID(), 'Aline', 'Alves Silva', '1990-04-04', 34, '437.589.710-47', 6000.00, NOW(), @family4_id);
INSERT INTO tb_person(id, first_name, last_name, date_birth, age, cpf, income, collection_date, family_id) VALUES (UUID(), 'Andrea', 'Rodrigues de Lima', '1995-07-07', 29, '182.845.830-97', 7000.00, NOW(), @family5_id);

INSERT INTO tb_children(id, first_name, last_name, date_birth, age, cpf, collection_date, family_id) VALUES (UUID(), 'Pedro', 'Pereira Almeida', '2006-01-01', 18, '832.092.230-50', NOW(), @family1_id);
INSERT INTO tb_children(id, first_name, last_name, date_birth, age, cpf, collection_date, family_id) VALUES (UUID(), 'Luana', 'Alric Barbosa', '2005-02-02', 19, '742.218.630-56', NOW(), @family2_id);
INSERT INTO tb_children(id, first_name, last_name, date_birth, age, cpf, collection_date, family_id) VALUES (UUID(), 'Lucas', 'Alric Barbosa', '2004-03-03', 20, '842.847.660-82', NOW(), @family2_id);
INSERT INTO tb_children(id, first_name, last_name, date_birth, age, cpf, collection_date, family_id) VALUES (UUID(), 'Bianca', 'Souza Campos', '2003-04-04', 21, '268.327.650-88', NOW(), @family3_id);
INSERT INTO tb_children(id, first_name, last_name, date_birth, age, cpf, collection_date, family_id) VALUES (UUID(), 'Ricardo', 'Souza Campos', '2002-05-05', 22, '698.332.250-98', NOW(), @family3_id);
INSERT INTO tb_children(id, first_name, last_name, date_birth, age, cpf, collection_date, family_id) VALUES (UUID(), 'Paulo', 'Souza Campos', '2001-06-06', 23, '305.691.370-55', NOW(), @family3_id);
INSERT INTO tb_children(id, first_name, last_name, date_birth, age, cpf, collection_date, family_id) VALUES (UUID(), 'Rogério', 'Silva Dias', '2000-07-07', 24, '063.749.010-02', NOW(), @family4_id);
INSERT INTO tb_children(id, first_name, last_name, date_birth, age, cpf, collection_date, family_id) VALUES (UUID(), 'Renata', 'Lima Andrade', '1999-08-08', 25, '487.698.870-63', NOW(), @family5_id);
INSERT INTO tb_children(id, first_name, last_name, date_birth, age, cpf, collection_date, family_id) VALUES (UUID(), 'Carol', 'Lima Andrade', '1998-09-09', 26, '579.605.630-12', NOW(), @family5_id);

INSERT INTO tb_role(id, authority) VALUES (UUID(), 'Boss');
INSERT INTO tb_role(id, authority) VALUES (UUID(), 'CEO');
INSERT INTO tb_role(id, authority) VALUES (UUID(), 'President');
INSERT INTO tb_role(id, authority) VALUES (UUID(), 'Manager');
INSERT INTO tb_role(id, authority) VALUES (UUID(), 'Coordinator');

SET @role1_id = (SELECT id FROM tb_role WHERE authority = 'Boss');
SET @role2_id = (SELECT id FROM tb_role WHERE authority = 'CEO');
SET @role3_id = (SELECT id FROM tb_role WHERE authority = 'President');
SET @role4_id = (SELECT id FROM tb_role WHERE authority = 'Manager');
SET @role5_id = (SELECT id FROM tb_role WHERE authority = 'Coordinator');

INSERT INTO tb_user(id, username, email, password, full_name, phone_number, cpf, image_url, collection_date, last_update_date) VALUES (UUID(), 'IgorTechnology', 'igor@gmail.com', '1234567', 'Igor Gonçalves', '+55 19 98765-4321', '123.456.789-0', 'www.image.com', NOW(), NOW());
INSERT INTO tb_user(id, username, email, password, full_name, phone_number, cpf, image_url, collection_date, last_update_date) VALUES (UUID(), 'HenriqueTechnology', 'henrique@gmail.com', '1234567', 'Henrique Souza', '+55 11 99784-6352', '231.123.312-0', 'www.image.com', NOW(), NOW());
INSERT INTO tb_user(id, username, email, password, full_name, phone_number, cpf, image_url, collection_date, last_update_date) VALUES (UUID(), 'DiegoTechnology', 'diego@gmail.com', '1234567', 'Diego Cruz', '+55 11 93124-5768', '321.421-521-0', 'www.image.com', NOW(), NOW());
INSERT INTO tb_user(id, username, email, password, full_name, phone_number, cpf, image_url, collection_date, last_update_date) VALUES (UUID(), 'AndrezaTechnology', 'andreza@gmail.com', '1234567', 'Andreza Pipolo', '+55 19 91324-5779', '312.123.213-0', 'www.image.com', NOW(), NOW());
INSERT INTO tb_user(id, username, email, password, full_name, phone_number, cpf, image_url, collection_date, last_update_date) VALUES (UUID(), 'MatheusTechnology', 'Matheus@gmail.com', '1234567', 'Matheus', '+55 19 92134-6587', '432.543.654-0.', 'www.image.com', NOW(), NOW());

SET @user1_id = (SELECT id FROM tb_user WHERE username = 'IgorTechnology');
SET @user2_id = (SELECT id FROM tb_user WHERE username = 'HenriqueTechnology');
SET @user3_id = (SELECT id FROM tb_user WHERE username = 'DiegoTechnology');
SET @user4_id = (SELECT id FROM tb_user WHERE username = 'AndrezaTechnology');
SET @user5_id = (SELECT id FROM tb_user WHERE username = 'MatheusTechnology');

INSERT INTO tb_user_role(user_id, role_id) VALUES (@user1_id, @role1_id);
INSERT INTO tb_user_role(user_id, role_id) VALUES (@user2_id, @role2_id);
INSERT INTO tb_user_role(user_id, role_id) VALUES (@user3_id, @role3_id);
INSERT INTO tb_user_role(user_id, role_id) VALUES (@user4_id, @role4_id);
INSERT INTO tb_user_role(user_id, role_id) VALUES (@user5_id, @role5_id);



