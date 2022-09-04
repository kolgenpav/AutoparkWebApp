SET SQL_SAFE_UPDATES = 0;

LOCK TABLES `drivers_buses` WRITE;
DELETE FROM `drivers_buses`;
UNLOCK TABLES;

LOCK TABLES `drivers` WRITE;
DELETE FROM `drivers`;
UNLOCK TABLES;

LOCK TABLES `buses` WRITE;
DELETE FROM `buses`;
UNLOCK TABLES;

LOCK TABLES `routes` WRITE;
DELETE FROM `routes`;
UNLOCK TABLES;

LOCK TABLES `users` WRITE;
DELETE FROM `users`;
UNLOCK TABLES;

LOCK TABLES `drivers` WRITE;
INSERT INTO `drivers` (id, name, surname, age) VALUES (1,'Микола','Іванченко',45),(2,'Ігор','Дорошенко',35),
(3,'Артур','Пиріжков',29),(4,'Петро','Гасанов',23),(5,'Андрій','Петренко',57),
(6,'Олег','Головін',41);
UNLOCK TABLES;

LOCK TABLES `routes` WRITE;
INSERT INTO `routes` (id, name, `number`) VALUES (1,'вул. Милославська-ст. м. "Героїв Дніпра"',39),
(2,'Кінотеатр "Братислава"-вул.Светлицького',157),
(3,'Контрактова площа-Залізничний вокзал "Дарниця"',74),
(4,'ст. м. "Чернігівська"-ст. м. "Політехнічний інститут"',388),
(5,'ст. м. "Васильківська"-вул. Смелянська',114),(6,'Рибальский півострів-вул. Північна',11),
(7,'ст. м. "Виставковий центр"-с. Жуляни',7);
UNLOCK TABLES;

LOCK TABLES `buses` WRITE;
INSERT INTO `buses` (id, `number`, route_id) VALUES (1,'к254тр',1),(2,'т436ку',7),(3,'п398тм',2),(4,'п387тс',4),
(5,'о567рп',2),(6,'а576рн',1),(7,'ч265ек',2),(8,'т764дл',5),(9,'п765ра',5),(10,'г459пи',3),
(11,'ц255ка',7);
UNLOCK TABLES;

LOCK TABLES `drivers_buses` WRITE;
INSERT INTO `drivers_buses` (bus_id, driver_id) VALUES (1,1),(1,4),(2,1),(2,2),(3,2),(3,3),
(4,3),(4,4),(5,3),(6,5),(7,5),(8,5),(9,1),(9,4),(10,2),(11,3);
UNLOCK TABLES;

LOCK TABLES `users` WRITE;
INSERT INTO `users` (id, `username`, password) VALUES (1, 'foo','bar');
UNLOCK TABLES;

SET SQL_SAFE_UPDATES = 1;
