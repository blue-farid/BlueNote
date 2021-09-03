create table clients
(
    username varchar(255) not null
        primary key,
    password varchar(255) null
);

INSERT INTO blue_note.clients (username, password) VALUES ('farid_127', 'admin');