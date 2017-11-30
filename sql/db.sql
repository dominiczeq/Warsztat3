CREATE TABLE user(
	id INT AUTO_INCREMENT,
	name VARCHAR(255),
	email VARCHAR(255),
	user_group_id INT,
	PRIMARY KEY(id),
	FOREIGN KEY(user_group_id) references user_group(id)
	);

CREATE TABLE solution(
	id INT AUTO_INCREMENT,
	title VARCHAR(255),
	description TEXT,
	user_id INT,
	excercise_id INT,
	submition_date DATETIME,
	PRIMARY KEY(id),
	FOREIGN KEY(user_id) REFERENCES user(id),
	FOREIGN KEY(excercise_id) REFERENCES excercise(id)
	);
	
CREATE TABLE excercise(
	id INT AUTO_INCREMENT,
	title VARCHAR(255),
	description TEXT,
	PRIMARY KEY(id)
	);
	
CREATE TABLE user_group(
	id INT AUTO_INCREMENT,
	name VARCHAR(255),
	PRIMARY KEY(id)
	);