
create database db_blog;
use db_blog;
CREATE TABLE blog (
	id int unsigned not null auto_increment,
	title varchar(128),
	created timestamp not null,
        isDeleted boolean default 0,
	PRIMARY KEY(id)
);


CREATE TABLE author (
	id int unsigned not null auto_increment,
	name varchar(128),
	PRIMARY KEY(id)
);


CREATE TABLE blog_author (
	author_id int unsigned not null, 
	blog_id int unsigned not null, 
	PRIMARY KEY(author_id, blog_id),
	FOREIGN KEY (author_id) REFERENCES author(id),
	FOREIGN KEY (blog_id) REFERENCES blog(id)
);


CREATE TABLE blog_data (
	id int unsigned not null auto_increment,
	blog_id int unsigned not null,
	paragraph text,
        isDeleted boolean default 0,
	PRIMARY KEY(id),
	FOREIGN KEY (blog_id) REFERENCES blog(id)
);


CREATE TABLE tag (
	id int unsigned not null auto_increment,
	name varchar(50),
	PRIMARY KEY(id),
	UNIQUE KEY (name)
);


CREATE TABLE blogs_tags (
	blog_id int unsigned not null,
	tag_id int unsigned not null,
	PRIMARY KEY(blog_id, tag_id),
	FOREIGN KEY (blog_id) REFERENCES blog(id),
 	FOREIGN KEY (tag_id) REFERENCES tag(id)
);


CREATE TABLE blog_comment (
	id int unsigned not null auto_increment,
	commentText text,
	created timestamp not null,
	paragraph_id int unsigned not null,
        isDeleted boolean default 0,
	PRIMARY KEY(id),
 	FOREIGN KEY (paragraph_id) REFERENCES blog_data(id)
);

insert into tag (name) values ('Technology'),('Politics'),('Geography'),('Economics'),('Education');

CREATE USER IF NOT EXISTS 'blog_user'@'localhost';
SET PASSWORD FOR 'blog_user'@'localhost' = 'blog_user';
GRANT ALL PRIVILEGES ON  db_blog.* TO 'blog_user'@'localhost';
FLUSH PRIVILEGES;



