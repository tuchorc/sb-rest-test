create table sb_test_item (
    id bigint auto_increment not null,
    description varchar(25) not null,
    primary key(id),
    unique key sb_test_item_uq (description)
);
