USE sample_intx;

IF (EXISTS (SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'dbo' AND  TABLE_NAME = 'Address_INTX'))
BEGIN
drop table Profile_Section_INTX;
drop table Section_INTX;
drop table User_INTX;
drop table Profile_INTX;
drop table Log_INTX;
drop table Log_Type_INTX;
drop table Setting_INTX;
drop table Resource_INTX;
drop table Resource_Type_INTX;
drop table Address_INTX;
drop table Sepomex;

END


IF (NOT EXISTS (SELECT * 
                 FROM INFORMATION_SCHEMA.TABLES 
                 WHERE TABLE_SCHEMA = 'dbo' 
                 AND  TABLE_NAME = 'Sepomex'))
BEGIN
     CREATE TABLE Sepomex (  
	  d_codigo	        VARCHAR(5)   NOT NULL,
	  d_asenta	        VARCHAR(100) NULL,
	  d_tipo_asenta	    VARCHAR(50)  NULL,	
	  d_mnpio	        VARCHAR(50)  NULL,	
	  d_estado	        VARCHAR(50)  NULL,
	  d_ciudad	        VARCHAR(50)  NULL,
      d_cp	            VARCHAR(5)   NULL,
	  c_estado	        VARCHAR(2)   NOT NULL,
	  c_oficina	        VARCHAR(5)   NULL,
	  c_cp	            VARCHAR(5)   NULL,
	  c_tipo_asenta		VARCHAR(2)   NULL,
	  c_mnpio	        VARCHAR(3)   NOT NULL,
	  id_asenta_cpcons  VARCHAR(4)   NOT NULL,
	  d_zona	        VARCHAR(50)  NULL,
	  c_cve_ciudad      VARCHAR(2)   NULL,
  	CONSTRAINT PK_Sepomex PRIMARY KEY (d_codigo, c_estado, c_mnpio, id_asenta_cpcons)  
  )
END

IF (NOT EXISTS (SELECT * 
                 FROM INFORMATION_SCHEMA.TABLES 
                 WHERE TABLE_SCHEMA = 'dbo' 
                 AND  TABLE_NAME = 'Address_INTX'))
BEGIN
     CREATE TABLE Address_INTX (
	  id_address       BIGINT IDENTITY(1,1) PRIMARY KEY NOT NULL,
	  street           VARCHAR(100) NULL,
	  exterior_number  VARCHAR(100) NULL,
	  interior_number  VARCHAR(100) NULL,
	  home_phone       VARCHAR(12)  NULL,
	  office_phone     VARCHAR(12)  NULL,
	  d_codigo         VARCHAR(5)   NOT NULL,
	  c_estado         VARCHAR(2)   NOT NULL,
	  c_mnpio          VARCHAR(3)   NOT NULL,
	  id_asenta_cpcons VARCHAR(4)   NOT NULL,
      CONSTRAINT fk_Address_Sepomex FOREIGN KEY (d_codigo,c_estado,c_mnpio,id_asenta_cpcons) REFERENCES Sepomex (d_codigo,c_estado,c_mnpio,id_asenta_cpcons)
  )
END

IF (NOT EXISTS (SELECT * 
                 FROM INFORMATION_SCHEMA.TABLES 
                 WHERE TABLE_SCHEMA = 'dbo' 
                 AND  TABLE_NAME = 'Resource_Type_INTX'))
BEGIN
	CREATE TABLE Resource_Type_INTX(
		id_resource_type INT IDENTITY(1,1) PRIMARY KEY NOT NULL,
		resource_type_name VARCHAR(20) NOT NULL
	)
END

IF (NOT EXISTS (SELECT * 
                 FROM INFORMATION_SCHEMA.TABLES 
                 WHERE TABLE_SCHEMA = 'dbo' 
                 AND  TABLE_NAME = 'Resource_INTX'))
BEGIN
	CREATE TABLE Resource_INTX(
		id_resource       BIGINT IDENTITY(1,1) PRIMARY KEY NOT NULL, 
		file_name         VARCHAR(100) NOT NULL,  
		description       VARCHAR(100) NULL,
		file_mime         VARCHAR(200) NULL,
		file_data         VARCHAR(MAX) NULL,
		file_path         VARCHAR(500) NULL,
		id_resource_type  INT NOT NULL,
		registration_date DATETIME     NOT NULL,
		username          VARCHAR(100) NOT NULL,
	    file_size         INT NULL,
		CONSTRAINT fk_Resource_Resource_Type FOREIGN KEY (id_resource_type) REFERENCES Resource_Type_INTX(id_resource_type)
	)
END

IF (NOT EXISTS (SELECT * 
                 FROM INFORMATION_SCHEMA.TABLES 
                 WHERE TABLE_SCHEMA = 'dbo' 
                 AND  TABLE_NAME = 'Setting_INTX'))
BEGIN
	CREATE TABLE Setting_INTX(
		id_setting	         INT IDENTITY(1,1) PRIMARY KEY NOT NULL,
		session_time           BIGINT NULL,
		logo                   BIGINT NULL,
		logo_with              INT NULL,
		logo_heigth            INT NULL,
		background_type        INT NULL,
		background_image       BIGINT NULL,
		background_color       VARCHAR(20) NULL,
		ico_image              BIGINT NULL,
		main_color             VARCHAR(200) NULL,
		mail_smtp_auth         VARCHAR(5) NULL,
		mail_smtp_starttls     VARCHAR(5) NULL,
		mail_smtp_host         VARCHAR(80) NULL,
		mail_smtp_port         VARCHAR(5) NULL,
		mail_smtp_user         VARCHAR(80) NULL,
		mail_smtp_pass         VARCHAR(500) NULL,	
		CONSTRAINT fk_Setting_Resource_1 FOREIGN KEY (logo) REFERENCES Resource_INTX (id_resource),	
		CONSTRAINT fk_Setting_Resource_2 FOREIGN KEY (background_image) REFERENCES Resource_INTX (id_resource),	
		CONSTRAINT fk_Setting_Resource_3 FOREIGN KEY (ico_image) REFERENCES Resource_INTX (id_resource)
	)
END


IF (NOT EXISTS (SELECT * 
                 FROM INFORMATION_SCHEMA.TABLES 
                 WHERE TABLE_SCHEMA = 'dbo' 
                 AND  TABLE_NAME = 'Log_Type_INTX'))
BEGIN
	CREATE TABLE Log_Type_INTX(
		id_log_type          INT     IDENTITY(1,1) PRIMARY KEY NOT NULL,
		log_type_name        VARCHAR(80) NOT NULL
	)
END

IF (NOT EXISTS (SELECT * 
                 FROM INFORMATION_SCHEMA.TABLES 
                 WHERE TABLE_SCHEMA = 'dbo' 
                 AND  TABLE_NAME = 'Log_INTX'))
BEGIN
	CREATE TABLE Log_INTX(
		id_log				BIGINT IDENTITY(1,1) PRIMARY KEY NOT NULL,
		id_log_type       INT NOT NULL,			
		description       VARCHAR(2000) NOT NULL,
		trace             VARCHAR(MAX) NULL,
		service_name      VARCHAR(255) NOT NULL,
		registration_date DATETIME NOT NULL,
		username          VARCHAR(255) NOT NULL,		
		error             BIT NOT NULL DEFAULT 0,
		error_code        INT NULL,
		CONSTRAINT fk_Log_INTX_Log_Type FOREIGN KEY (id_log_type) REFERENCES Log_Type_INTX (id_log_type)
	)
END

IF (NOT EXISTS (SELECT * 
                 FROM INFORMATION_SCHEMA.TABLES 
                 WHERE TABLE_SCHEMA = 'dbo' 
                 AND  TABLE_NAME = 'Profile_INTX'))
BEGIN
	CREATE TABLE Profile_INTX(
		id_profile BIGINT IDENTITY(1,1) PRIMARY KEY NOT NULL,
		name VARCHAR(100) NOT NULL,
		description VARCHAR(100) NOT NULL
	)
END

IF (NOT EXISTS (SELECT * 
                 FROM INFORMATION_SCHEMA.TABLES 
                 WHERE TABLE_SCHEMA = 'dbo' 
                 AND  TABLE_NAME = 'User_INTX'))
BEGIN
	CREATE TABLE User_INTX(
		id_user            BIGINT IDENTITY(1,1) PRIMARY KEY NOT NULL,
		id_profile         BIGINT NOT NULL,
		username           VARCHAR(100) NOT NULL UNIQUE,
		password           varchar(max) NOT NULL,
		name               VARCHAR(100) NOT NULL,
		last_name_p        VARCHAR(100) NULL,
		last_name_m        VARCHAR(100) NULL,
		email              VARCHAR(100) NULL,
		registration_date  DATETIME     NOT NULL,
		status_user        INT          NOT NULL,
		logging_attempts   INT          NULL,
		attempting_logging DATETIME     NULL,
		birth_date         DATE         NULL,
		id_address         BIGINT          NULL,
		user_photo         BIGINT          NULL,
		CONSTRAINT fk_User_INTX_Resource FOREIGN KEY (user_photo) REFERENCES Resource_INTX (id_resource),
		CONSTRAINT fk_User_Profile_INTX FOREIGN KEY (id_profile) REFERENCES Profile_INTX (id_profile),		
		CONSTRAINT fk_User_Address_INTX FOREIGN KEY (id_address) REFERENCES Address_INTX (id_address)
		)
END
	
IF (NOT EXISTS (SELECT * 
                 FROM INFORMATION_SCHEMA.TABLES 
                 WHERE TABLE_SCHEMA = 'dbo' 
                 AND  TABLE_NAME = 'Section_INTX'))
BEGIN
	CREATE TABLE Section_INTX(
		id_section  BIGINT IDENTITY(1,1) PRIMARY KEY NOT NULL,
		name        VARCHAR(100) NOT NULL,
		description VARCHAR(100) NOT NULL,
		id_parent   BIGINT NULL,
		parent      BIT NOT NULL, 
		enabled     BIT NOT NULL
	)
END

IF (NOT EXISTS (SELECT * 
                 FROM INFORMATION_SCHEMA.TABLES 
                 WHERE TABLE_SCHEMA = 'dbo' 
                 AND  TABLE_NAME = 'Profile_Section_INTX'))
BEGIN
	CREATE TABLE Profile_Section_INTX(
		id_profile BIGINT NOT NULL,
		id_section BIGINT NOT NULL,
		CONSTRAINT PK_Profile_Section_INTX PRIMARY KEY (id_profile, id_section),  
		CONSTRAINT fk_Profile_Section_INTX_Profile FOREIGN KEY (id_profile) REFERENCES Profile_INTX(id_profile),	
		CONSTRAINT fk_Profile_Section_INTX_Section FOREIGN KEY (id_section) REFERENCES Section_INTX(id_section)
	)
END
