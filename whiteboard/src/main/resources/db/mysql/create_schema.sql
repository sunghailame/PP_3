CREATE DATABASE IF NOT EXISTS db309pp3;

USE db309pp3;

CREATE TABLE IF NOT EXISTS person (
	`ID` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `Name` VARCHAR(45) ,
    `Password` VARCHAR(45) ,
    `Email` VARCHAR(45) ,
    `Role` VARCHAR(45) ,
    `Username` VARCHAR(45) ,
    `Enrolled` BIT ,
	CONSTRAINT UC_person UNIQUE (Username)
    )
    ;

CREATE TABLE IF NOT EXISTS Admin (
  `ID` BIGINT PRIMARY KEY auto_increment,
  FOREIGN KEY (ID) REFERENCES person(ID)
  )
  ;


CREATE TABLE IF NOT EXISTS Course (
  `CourseCode` VARCHAR(45) PRIMARY KEY, 
  `CourseName` VARCHAR(45),
   CONSTRAINT UC_Course UNIQUE (CourseCode, CourseName)
  )
  ;
  
CREATE TABLE IF NOT EXISTS Professor (
  `ID` BIGINT auto_increment,
  `CourseCode` VARCHAR(45), 
  FOREIGN KEY (ID) REFERENCES person(ID),
  FOREIGN KEY (CourseCode) REFERENCES Course(CourseCode),
  CONSTRAINT UC_Professor UNIQUE (ID, CourseCode)
  )
  ;
  
CREATE TABLE IF NOT EXISTS Student (
  `ID` BIGINT PRIMARY KEY auto_increment,
  FOREIGN KEY (ID) REFERENCES person(ID)
  )
  ;
  
  
CREATE TABLE IF NOT EXISTS Enrollment (
  `ID` BIGINT,
  `CourseCode` VARCHAR(45), 
  `SectionNo` VARCHAR(45),
  FOREIGN KEY (CourseCode) REFERENCES Course(CourseCode),
  FOREIGN KEY (ID) REFERENCES Student(ID),
  CONSTRAINT UC_Enrollment UNIQUE (ID, CourseCode,SectionNo)
  )
  ;

  

  


