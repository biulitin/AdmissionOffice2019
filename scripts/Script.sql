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
	groupExam varchar(MAX),			--группа
	numberIn_group varchar(MAX),		--порядковый номер в группе
	dateOf_exam date,				--дата испытания
	score int,					--балл
	id_basisMark int,		 --код основания оценки 
	pass_mark int,			 --отметка сдачи
	has_100 int,			--флаг о наличии права на 100 баллов по предмету

	--Внешние ключи
	foreign key (id_abiturient) references Abiturient(aid) on update cascade on delete cascade,
	foreign key (id_entranceExam) references EntranceExam(id) on update cascade on delete set null,
	foreign key (id_formOfExam) references FormOfExam(id) on update cascade on delete set null,
	foreign key (id_languageOfExam) references LanguageOfExam(id) on update cascade on delete set null,
	foreign key (id_basisMark) references BasisMark(id) on update cascade on delete set null
);

--Олимпиады
create table Olympiad(
	id int primary key,	-- идентификатор  
	name varchar(MAX),		-- наименование

	codeFIS varchar(MAX)		--код_ФИС
);

--Абитуриент_документы, подтверждающие право на 100 баллов по предметам
create table AbiturientDocumentsFor100balls(
	id_abiturient int, --id абитуриента
	id_olympiad int, --id олимпиады
	nameOfDocument varchar(MAX), --Наименование документа
	diplomaDegree varchar(MAX),       --Степень диплома
	diplomaSubject varchar(MAX),      --Предмет олимпиады
	olympLevel varchar(MAX),          --Уровень олимпиады
	series_document varchar(MAX),    --серия документа
	number_document varchar(MAX),    --номер документа
	date_of_issue date,      --дата выдачи 
	issued_by varchar(MAX),          --кем выдан
	
--Внешние ключи
	foreign key(id_abiturient) references Abiturient(aid) on update cascade, --идентификатор абитуриента
	foreign key(id_olympiad) references Olympiad(id) on update cascade --идентификатор абитуриента
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

--Индивидуальные достижения
create table IndividualAchievement(
    id int primary key,	--код
	name varchar(MAX),			 --наименование
	codeFIS varchar(MAX),		--код выгрузки в ФИС
    max_score int   --максимальный балл за индивидуальное достижение
);

--Абитуриент_Индивидуальные достижения
create table AbiturientIndividAchievement(
	id_abiturient int,    --код абитуриента
    id_individualAchievements int,   --код индвидуального достижения
	score int,   --балл за индивидуальное достижение
    name varchar(MAX),    --название документа
	series varchar(MAX),           --серия
	number varchar(MAX),           --номер
	issued_by varchar(MAX),        --кем выдан
	dateOf_issue date,     --дата выдачи
    
    --Внешние ключи
	foreign key (id_abiturient) references Abiturient(aid) on update cascade on delete cascade,
    foreign key(id_individualAchievements) references IndividualAchievement(id) on update cascade on delete set null
);

--Специальность
create table Speciality(
    id int primary key,	--код
	name varchar(MAX),			--название пола (для отображения в интерфейсе)
	codeFIS varchar(MAX)		--код выгрузки в ФИС
);

--Форма обучения
create table FormOfEducation(	
    id int primary key,	--код
	name varchar(MAX),			--название пола (для отображения в интерфейсе)
	codeFIS varchar(MAX)		--код выгрузки в ФИС
);

--Конкурсная группа
create table CompetitiveGroup(
    id int primary key,	--код
	name varchar(MAX),			--название пола (для отображения в интерфейсе)
	codeFIS varchar(MAX)		--код выгрузки в ФИС
);

--Целевая организация
create table TargetOrganization(
    id int primary key,	--код
	name varchar(MAX),			--название пола (для отображения в интерфейсе)
	codeFIS varchar(MAX)		--код выгрузки в ФИС
);

--План приёма
create table AdmissionPlan(
    id_speciality int,   --код специальности
    id_formOfEducation int,    --код формы обучения
    id_competitiveGroup int,    --код конкурсной группы
    id_targetOrganization int,    --код целевой организации
    amount_of_places int,        --количество мест
    amount_of_places_quota int     --количество мест по квоте
    
    --Внешние ключи
    foreign key(id_speciality) references Speciality(id) on update cascade on delete set null,
    foreign key(id_formOfEducation) references FormOfeducation(id) on update cascade on delete set null,
    foreign key(id_competitiveGroup) references CompetitiveGroup(id) on update cascade on delete set null,
    foreign key(id_targetOrganization) references TargetOrganization(id) on update cascade on delete set null
);

--Абитуриент_Конкурсные группы
create table AbiturientCompetitiveGroup(
  id_abiturient int,  --код абитуриента
  id_speciality int,   --код специальности 
  id_formOfEducation int,   --код формы обучения
  id_competitiveGroup int,   --код конкурсной группы 
  id_targetOrganization int,   --код целевой организации
  haveBasisForBVI int,   --метка "Право на поступление без вступительных испытаний"
  haveBasisForQuota int,   --метка "Право на поступление в рамках квоты"
  havePreferredRight int,   --метка "Право на поступление с преимущественным правом"
  competitiveScore int,   --конкурсный балл
  scoresIndAchievements int,  --сумма баллов за индивидуальные достижения
  is_enrolled int, --метка о зачислении
  
  --Внешние ключи
  foreign key (id_abiturient) references Abiturient(aid) on update cascade on delete cascade,
  foreign key(id_speciality) references Speciality(id) on update cascade on delete set null,
  foreign key(id_formOfEducation) references FormOfEducation(id) on update cascade on delete set null,
  foreign key(id_competitiveGroup) references CompetitiveGroup(id) on update cascade on delete set null,
  foreign key(id_targetOrganization) references TargetOrganization(id) on update cascade on delete set null
);

--Тип_БВИ
create table TypeOfBVI(
	id int primary key,	--код
	name varchar(MAX),		--наименование
	codeFIS varchar(MAX)		--код выгрузки в ФИС
);
--Абитуриент_БВИ
create table AbiturientBVI(
	id_abiturient int,  --код абитуриента
	id_typeOfBVI int,   --код типа БВИ

--Внешние ключи
	foreign key (id_abiturient) references Abiturient(aid) on update cascade on delete cascade,
	foreign key(id_typeOfBVI) references TypeOfBVI (id) on update cascade on delete set null
);

--Категория_допсведений
create table CategoryOfExtraInfo(
	id int primary key,	--код
	name varchar(MAX),			-- наименование
	codeFIS varchar(MAX)		--код выгрузки в ФИС
);

--Абитуриент_допсведения
create table AbiturientExtraInfo (
	id_abiturient int,  --код абитуриента
	id_categoryOfExtraInfo int,   --код допсведений 
	name_of_document varchar(MAX), --Наименование документа
	series_of_document  varchar(MAX), --Документ серия
	number_of_document varchar(MAX), --Документ номер 
	issued_by  varchar(MAX), --Кем выдан
	dateOf_issue date --Дата выдачи	

--Внешние ключи
	foreign key (id_abiturient) references Abiturient(aid) on update cascade on delete cascade,
	foreign key(id_categoryOfExtraInfo) references CategoryOfExtraInfo (id) on update cascade on delete set null
);

--Абитуриент_документ_БВИ
create table AbiturientDocumentsBVI (
	id_abiturient int,  --код абитуриента
	name_of_document varchar(MAX), --Наименование документа
	series  varchar(MAX), --Документ серия
	number varchar(MAX), --Документ номер 
	issued_by  varchar(MAX), --Кем выдан
	dateOf_issue date --Дата выдачи	

--Внешний ключ
	foreign key (id_abiturient) references Abiturient(aid) on update cascade on delete cascade
);	


--Пользователи
create table Users(
      id int primary key, --идентификатор
      login varchar(MAX), --логин
      password varchar(MAX), --пароль
      fio varchar(MAX) --ФИО
);

--Тип Квоты
create table TypeOfQuote (
	id int primary key, -- id
	name varchar(255), -- наименование
	codeFIS varchar(255) -- Код_ФИС
);

--Абитуриент_Квота
create table AbiturientQuota (
	id_abiturient int, -- id абитуриента
	id_quotaType int, -- id типа квоты

--Внешний ключ	
	foreign key(id_abiturient) references Abiturient(aid) on update cascade on delete cascade,
	foreign key(id_quotaType) references TypeOfQuote(id) on update cascade on delete set null	
);

--Абитуриент_Документ Квота
create table AbiturientDocumentQuota (
	id_abiturient int, -- id абитуриента
	name varchar(255), -- Наименование документа
	series varchar(255), -- Серия
	num varchar(255), -- Номер
	issuedBy varchar(255), -- Кем_выдан
	issueDate date, -- Дата_выдачи

--Внешний ключ	
	foreign key(id_abiturient) references Abiturient(aid) on update cascade on delete cascade
);

create table TypeOfPreferredRight (
	id int primary key, -- id
	name varchar(255), -- Наименование
	codeFIS varchar(255) -- Код_ФИС	
);

create table AbiturientPreemptitiveRight (
	id_abiturient int, -- id абитуриента
	id_preemptitiveRight int, -- id типа преимущественного права

--Внешний ключ	
	foreign key(id_abiturient) references Abiturient(aid) on update cascade on delete cascade,
	foreign key(id_preemptitiveRight) references TypeOfPreferredRight(id) on update cascade on delete set null
);

create table AbiturientDocumentsPreferredRight (
	id_abiturient int,  -- id абитуриента
	name varchar(255), -- Наименование документа
	series varchar(255), -- Серия
	num varchar(255), -- Номер
	issuedBy varchar(255), -- Кем_выдан
	issueDate date, -- Дата_выдачи

--Внешний ключ	
	foreign key(id_abiturient) references Abiturient(aid) on update cascade on delete cascade	
);
