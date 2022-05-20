
CREATE DATABASE OJTManagement

CREATE TABLE tblAccount(
	username varchar(25) NOT NULL,
	password varchar(20) NOT NULL,
	name nvarchar(50) NOT NULL,
	avatar varchar(100),
	isAdmin int NOT NULL,

	PRIMARY KEY (username)
);

CREATE TABLE tblStudent(
	studentCode varchar(8) NOT NULL,
	birthDay date,
	address nvarchar(100),
	gender bit,
	phone varchar(10),
	is_Intern int,
	numberOfCredit int,
	major nvarchar(30),
	username varchar(25) NOT NULL,

	PRIMARY KEY (studentCode)
);

ALTER TABLE tblStudent 
ADD CONSTRAINT FK_Student_Account FOREIGN KEY (username)
REFERENCES tblAccount(username);

CREATE TABLE tblCompany(
	companyID varchar(10) NOT NULL,
	address nvarchar(100),
	city nvarchar(30),
	phone varchar(10),
	company_Description nvarchar(2000),
	is_Signed bit,
	username varchar(25) NOT NULL,

	PRIMARY KEY (companyID)
);

ALTER TABLE tblCompany 
ADD CONSTRAINT FK_Company_Account FOREIGN KEY (username)
REFERENCES tblAccount(username);

CREATE TABLE tblCategory(
	categoryID int identity(1,1) NOT NULL,
	categoryName nvarchar(50) NOT NULL,

	PRIMARY KEY (categoryID)
);

CREATE TABLE tblMajor(
	majorID int identity(1,1) NOT NULL,
	majorName nvarchar(50) NOT NULL,
	categoryID int NOT NULL,

	PRIMARY KEY (majorID)
);

ALTER TABLE tblMajor
ADD CONSTRAINT FK_Major_Category FOREIGN KEY (categoryID)
REFERENCES tblCategory(categoryID);

CREATE TABLE tblCompany_Post(
	postID int identity(1, 1) NOT NULL,
	title_Post nvarchar(50) NOT NULL,
	job_Description nvarchar(2000) NOT NULL,
	job_Requirement nvarchar(2000) NOT NULL,
	remuneration nvarchar(200) NOT NULL,
	workLocation nvarchar(100) NOT NULL,
	quantityIterns int NOT NULL,
	postingDate date NOT NULL,
	expirationDate date NOT NULL,
	school_confirm bit NOT NULL,
	statusPost int NOT NULL,
	companyID varchar(10) NOT NULL,
	majorID int NOT NULL,

	PRIMARY KEY (postID)
);

ALTER TABLE tblCompany_Post
ADD CONSTRAINT FK_Post_Major FOREIGN KEY (majorID)
REFERENCES tblMajor(majorID);

ALTER TABLE tblCompany_Post
ADD CONSTRAINT FK_Post_Company FOREIGN KEY (companyID)
REFERENCES tblCompany(companyID);

CREATE TABLE tblFollowing_Post(
	studentCode varchar(8) NOT NULL,
	postID int NOT NULL,

	PRIMARY KEY (studentCode, postID)
);

ALTER TABLE tblFollowing_Post
ADD CONSTRAINT FK_Student_FollowingPost FOREIGN KEY (studentCode)
REFERENCES tblStudent(studentCode);

ALTER TABLE tblFollowing_Post
ADD CONSTRAINT FK_Post_FollowingPost FOREIGN KEY (postID)
REFERENCES tblCompany_Post(postID);

CREATE TABLE tblApplication(
	applicationID int identity(1,1) NOT NULL,
	attachmentPath varchar(100) NOT NULL,
	expected_Job nvarchar(50) NOT NULL,
	technology nvarchar(50) NOT NULL,
	experience nvarchar(30) NOT NULL,
	foreign_Language nvarchar(50) NOT NULL,
	otherSkills nvarchar(50) NOT NULL,
	evaluation nvarchar(500),
	grade decimal(2,1),
	is_Pass bit,
	student_Confirm bit,
	school_Confirm bit,
	company_Confirm bit,
	studentCode varchar(8) NOT NULL,
	postID int NOT NULL,

	PRIMARY KEY (applicationID)
);

ALTER TABLE tblApplication
ADD CONSTRAINT FK_Student_Application FOREIGN KEY (studentCode)
REFERENCES tblStudent(studentCode);

ALTER TABLE tblApplication
ADD CONSTRAINT FK_Post_Application FOREIGN KEY (postID)
REFERENCES tblCompany_Post(postID);


