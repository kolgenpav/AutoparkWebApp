    alter table buses
       drop 
       foreign key FKryb7cf7s26rht8lyp8ry0ek0g;

    alter table drivers_buses 
       drop 
       foreign key FKalvoey1gtxm3whgjfstwf9b;

    alter table drivers_buses 
       drop 
       foreign key FKn39eiqqe0b3svbtl2b9r4vc0h;

    drop table if exists buses;

    drop table if exists drivers;

    drop table if exists drivers_buses;

    drop table if exists routes;

    drop table if exists users;
