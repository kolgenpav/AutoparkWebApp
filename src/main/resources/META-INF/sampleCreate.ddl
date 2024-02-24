    create table buses (
       id bigint not null auto_increment,
        number varchar(10) not null,
        route_id bigint,
        primary key (id)
    ) engine=InnoDB;

    create table drivers (
       id bigint not null auto_increment,
        age integer not null,
        name varchar(25) not null,
        surname varchar(45) not null,
        primary key (id)
    ) engine=InnoDB;

    create table drivers_buses (
       driver_id bigint not null,
        bus_id bigint not null,
        primary key (driver_id, bus_id)
    ) engine=InnoDB;

    create table routes (
       id bigint not null auto_increment,
        name varchar(150) not null,
        number integer not null,
        primary key (id)
    ) engine=InnoDB;

    create table users (
       id bigint not null auto_increment,
        password varchar(10) not null,
        username varchar(10) not null,
        primary key (id)
    ) engine=InnoDB;

    alter table buses 
       add constraint UK_63h4nddufrk6njpdy85sxi5ml unique (number);

    alter table routes 
       add constraint UK_gclhuhdkauatimjoqj7kc210i unique (name);

    alter table routes 
       add constraint UK_f0sg33e8blenymvw4c86xrv7b unique (number);

    alter table users 
       add constraint UK_r43af9ap4edm43mmtq01oddj6 unique (username);

    alter table buses 
       add constraint FKryb7cf7s26rht8lyp8ry0ek0g 
       foreign key (route_id) 
       references routes (id);

    alter table drivers_buses 
       add constraint FKalvoey1gtxm3whgjfstwf9b 
       foreign key (bus_id) 
       references buses (id);

    alter table drivers_buses 
       add constraint FKn39eiqqe0b3svbtl2b9r4vc0h 
       foreign key (driver_id) 
       references drivers (id);
