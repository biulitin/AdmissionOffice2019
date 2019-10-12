--create database Abiturient;
--go

use Abiturient;
go

--Пол
create table Gender (
	id int primary key,	--код
	name varchar(MAX),			--название пола (для отображения в интерфейсе)
	codeFIS varchar(MAX)		--код выгрузки в ФИС
);


--Гражданство
create table Nationality (
	id int primary key,	--код
	name varchar(MAX),			--название гражданства/страна (для отображения в интерфейсе)
	codeFIS varchar(MAX)		--код выгрузки в ФИС
);


--Причины возврата документов
create table ReturnReasons (
	id int primary key,	--код
	name varchar(MAX),			--наименование причины (для отображения в интерфейсе)
	codeFIS varchar(MAX)		--код выгрузки в ФИС
);


--Абитуриент
create table Abiturient (
	aid int primary key,	--код/номер личного дела абитуриента
	SName varchar(200),		--Фамилия
	Fname varchar(200),		--Имя
	MName varchar(200),		--Отчество
	Birthday Date,			--Дата рождения
	Birthplace varchar(MAX),		--Место рождения
	id_gender int,			--код пола
	id_nationality int,		--код страны гражданства
	email varchar(MAX),				--e-mail
	phoneNumbers varchar(MAX),		--телефоны
	inn varchar(MAX),				--ИНН
	needHostel int,			--метка "Нуждается в общежитии"
	registrationDate Date,	--дата подачи заявления
	returnDate Date,		--Дата возврата документов
	id_returnReason int,	--причина возврата документов
	needSpecConditions int,	--метка "Нуждается в специальных условиях вступительных испытаний"
	is_enrolled int,		--метка о зачислении
	
	--Внешние ключи
	foreign key (id_gender) references Gender(id) on update cascade on delete set null,
	foreign key (id_nationality) references Nationality(id) on update cascade on delete set null,
	foreign key (id_returnReason) references ReturnReasons(id) on update cascade on delete set null
);

-- Тип паспорта 
create table TypePassport (
	id int primary key,    --код
	name varchar(MAX),             --наименование (тип паспорта)
	codeFIS varchar(MAX)           --код выгрузки в ФИС
);

--Абитуриент_паспорт
create table AbiturientPassport (
	id_abiturient int,     --код абитуриента
	id_typePassport int,   --код паспорта
	series varchar(MAX),           --серия
	number varchar(MAX),           --номер
	issued_by varchar(MAX),        --кем выдан
	dateOf_issue date,     --дата выдачи

	
	--Внешние ключи
	foreign key (id_abiturient) references Abiturient(aid) on update cascade on delete cascade,
	foreign key (id_typePassport) references TypePassport(id) on update cascade on delete set null
);

--Регион
create table Region (
    id int primary key,  --код
	name varchar(MAX),			 --наименование
	codeFIS varchar(MAX)		 --код выгрузки в ФИС
);

--Тип населенного пункта
create table TypeSettlement (
    id int primary key,  --код
	name varchar(MAX),			 --наименование
	codeFIS varchar(MAX)		 --код выгрузки в ФИС
);

--Абитуриент_адрес 
create table AbiturientAdress (
	id_abiturient int,       --код абитуриента
	id_region int,           --код региона
	id_typeSettlement int,   --код населенного пункта
    postcode varchar(MAX),           --индекс
	adress varchar(MAX),             --адрес
	
	--Внешние ключи
	foreign key (id_abiturient) references Abiturient(aid) on update cascade on delete cascade,
	foreign key (id_region) references Region(id) on update cascade on delete set null,
	foreign key (id_typeSettlement) references TypeSettlement(id) on update cascade on delete set null
);

--Вступительные_испытания
create table EntranceExam (
	id int primary key,	--код
	name varchar(MAX),			--наименование
	codeFIS varchar(MAX),		--код выгрузки в ФИС
	min_score int		--минимальный балл
);

--Формат_испытания
create table FormOfExam (
	id int primary key,	--код
	name varchar(MAX),			 --наименование
	codeFIS varchar(MAX)		 --код выгрузки в ФИС
);

--Язык_испытания
create table LanguageOfExam (
	id int primary key,	--код
	name varchar(MAX),			 --наименование
	codeFIS varchar(MAX)		 --код выгрузки в ФИС
);

--Основание оценки
create table BasisMark (
	id int primary key,	--код
	name varchar(MAX),			 --наименование
	codeFIS varchar(MAX)		--код выгрузки в ФИС
);

--Абитуриент_Вступительные испытания
create table AbiturientEntranceExam (
	id_abiturient int,       	 --код абитуриента
	id_entranceExam int,    	 --код испытания
	id_formOfExam int,     		 --код формата испытания
	id_languageOfExam int,		 --код языка испытания 
	group varchar(MAX),			--группа
	numberIn_group varchar(MAX),		--порядковый номер в группе
	dateOf_exam date,				--дата испытания
	score int,					--балл
	id_basisMark int,		 --код основания оценки 
	pass_mark int,			 --отметка сдачи

	--Внешние ключи
	foreign key (id_abiturient) references Abiturient(aid) on update cascade on delete cascade,
	foreign key (id_entranceExam) references EntranceExam(id) on update cascade on delete set null,
	foreign key (id_formOfExam) references FormOfExam(id) on update cascade on delete set null,
	foreign key (id_languageOfExam) references LanguageOfExam(id) on update cascade on delete set null,
	foreign key (id_basisMark) references BasisMark(id) on update cascade on delete set null
);

--Уровень образования
create table LevelEducation (
	id int primary key,	--код
	name varchar(MAX),			 --наименование
	codeFIS varchar(MAX)		--код выгрузки в ФИС
);

--Тип образования
create table TypeEducation (
	id int primary key,	--код
	name varchar(MAX),			 --наименование
	codeFIS varchar(MAX)		--код выгрузки в ФИС
);

--Абитуриент_образование 
create table AbiturientEducation (
	id_abiturient int,       	 --код абитуриента
	id_levelEducation int,    	 --код уровня образования
	id_typeEducation int,     	 --код типа образования
	series_document varchar(MAX),			--серия документа
	number_document varchar(MAX),           	--номер документа
	name_eduInstitution varchar(MAX),	--название учебного заведения 
	dateOf_issue date,       	 	--дата выдачи
	yearOf_graduation int,			--год окончания 
	
	--Внешние ключи
	foreign key (id_abiturient) references Abiturient(aid) on update cascade on delete cascade,
	foreign key (id_levelEducation) references LevelEducation(id) on update cascade on delete set null,
	foreign key (id_typeEducation) references TypeEducation(id) on update cascade on delete set null
);

--Пользователи
create table Users(
      id int primary key, --идентификатор
      login varchar(MAX), --логин
      password varchar(MAX), --пароль
      fio varchar(MAX) --ФИО
);
