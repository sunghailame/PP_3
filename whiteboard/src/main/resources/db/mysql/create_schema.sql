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
  `ProfID` BIGINT,
   `lecDate` date,
   `Title` VARCHAR(45),
  `CourseCode` VARCHAR(45), 
  `Link` VARCHAR(45),
  `Details` VARCHAR(45),
  `ID` BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
  `Attendance` TINYINT(1),
  FOREIGN KEY (ProfID) REFERENCES person(ID),
  FOREIGN KEY (CourseCode) REFERENCES Course(CourseCode)
)
;

CREATE TABLE IF NOT EXISTS Attendance (
  `attendID` BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
  `CourseCode` VARCHAR(45), 
  `SectionNo` VARCHAR(45),
  `Date` date,
  `SectioNo` VARCHAR(45),
  `LectureTitle` VARCHAR(45),
  `ProfId` INT,
  `StudId` INT,
  FOREIGN KEY (CourseCode) REFERENCES Lecture(CourseCode),
  FOREIGN KEY (ProfId) REFERENCES Lecture(ProfID),
  FOREIGN KEY (StuId) REFERENCES Enrollment(PersonID)
  )
  ;


  

  


