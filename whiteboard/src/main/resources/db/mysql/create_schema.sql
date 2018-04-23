CREATE DATABASE IF NOT EXISTS db309pp3;

USE db309pp3;

CREATE TABLE IF NOT EXISTS person (
	`ID` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `Name` VARCHAR(45) ,
    `Password` VARCHAR(45) ,
    `Email` VARCHAR(45) ,
    `Role` VARCHAR(45) ,
    `Username` VARCHAR(45) ,
	CONSTRAINT UC_person UNIQUE (Username)
    )
    ;

CREATE TABLE IF NOT EXISTS Course (
  `CourseCode` VARCHAR(45) PRIMARY KEY, 
  `CourseName` VARCHAR(45),
   CONSTRAINT UC_Course UNIQUE (CourseCode, CourseName)
  )
  ;
  
CREATE TABLE IF NOT EXISTS Enrollment (
  `ID` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `PersonID` BIGINT NOT NULL,
  `CourseCode` VARCHAR(45) NOT NULL, 
  `SectionNo` VARCHAR(45) NOT NULL,
  `Role` VARCHAR(45) NOT NULL,
  FOREIGN KEY (CourseCode) REFERENCES Course(CourseCode),
  FOREIGN KEY (PersonID) REFERENCES person(ID),
  CONSTRAINT UC_Enrollment UNIQUE (PersonID, CourseCode,SectionNo, Role) 
  )
  ;

CREATE TABLE IF NOT EXISTS Lecture (
  `LectureID` BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
  `ProfID` BIGINT,
   `lecDate` date,
   `Title` VARCHAR(45),
  `CourseCode` VARCHAR(45), 
  `Link` VARCHAR(45),
  `Details` VARCHAR(45),
  `OpenAttendance` TINYINT(1),
  FOREIGN KEY (ProfID) REFERENCES person(ID),
  FOREIGN KEY (CourseCode) REFERENCES Course(CourseCode),
  CONSTRAINT UC_Lecture UNIQUE (ProfID, lecDate, Title, CourseCode)
)
;

CREATE TABLE IF NOT EXISTS Attendance (
  `ID` BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
  `LectureID` BIGINT, 
  `StuId` BIGINT,
  FOREIGN KEY (LectureID) REFERENCES Lecture(LectureID),
  FOREIGN KEY (StuId) REFERENCES Enrollment(PersonID)
  )
  ;
  
CREATE TABLE IF NOT EXISTS SeatingChart (
  `ChartId` BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
  `LectureId` BIGINT NOT NULL, 
  `StudId` BIGINT,
  `XRow` int(11),
  `YColumn` int(11) ,
  `StudName` varchar(45),
  FOREIGN KEY (StudId) REFERENCES person(ID),
  FOREIGN KEY (LectureId) REFERENCES Lecture(LectureID)
  )
  ;
  
CREATE TABLE IF NOT EXISTS Assignment (
  `AssID` BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
  `CourseCode` VARCHAR(45) NOT NULL, 
  `AssignmentName` VARCHAR(45),
  `Percentage` bigint(20),
  FOREIGN KEY (CourseCode) REFERENCES Course(CourseCode)
  )
  ;
  
CREATE TABLE IF NOT EXISTS Grades (
  `GradeId` BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
  `StudentId` BIGINT, 
  `AssId` BIGINT,
  `Grade` int(11),
  FOREIGN KEY (AssId) REFERENCES Assignment(AssID),
  FOREIGN KEY (StudentId) REFERENCES person(ID)
  )
  ;

CREATE TABLE IF NOT EXISTS Notification (
  `NoteId` BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
  `PersonId` BIGINT NOT NULL, 
  `CourseCode` VARCHAR(45) NOT NULL,
  `Note` longblob NOT NULL,
  `StartDate` date NOT NULL,
  `EndDate` date NOT NULL,
  FOREIGN KEY (CourseCode) REFERENCES Course(CourseCode),
  FOREIGN KEY (PersonId) REFERENCES person(ID)
  )
  ;
  
CREATE TABLE IF NOT EXISTS Location (
  `LocationId` BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
  `Building` VARCHAR(45) NOT NULL,
  `Latitude` VARCHAR(45) NOT NULL,
  `Longitude` VARCHAR(45) NOT NULL
  )
  ;
  

  


  

  


