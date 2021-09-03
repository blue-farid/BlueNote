create table notes
(
    username varchar(255)              null,
    title    varchar(255)              null,
    body     varchar(2000)             null,
    noteDate timestamp default (now()) not null,
    note_id  int auto_increment
        primary key,
    constraint notes_ibfk_1
        foreign key (username) references clients (username)
);

create index username
    on notes (username);

INSERT INTO blue_note.notes (username, title, body, noteDate, note_id) VALUES ('farid_127', 'BlueNote', 'just a note to store in the database that is going to upload on the github :)
', '2021-09-03 11:33:57', 3);