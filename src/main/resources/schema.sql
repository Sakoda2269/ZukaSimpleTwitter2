--起動するごとにデータベースをリセットしています。
DROP TABLE IF EXISTS tweets;
DROP TABLE IF EXISTS accounts;

CREATE TABLE IF NOT EXISTS accounts(
	email VARCHAR(50) PRIMARY KEY, 
	name VARCHAR(50), 
	password VARCHAR(255), 
	role VARCHAR(10)
);

CREATE TABLE IF NOT EXISTS tweets(
	tid VARCHAR(255) PRIMARY KEY,
	contents VARCHAR(255),
	author VARCHAR(50),
	FOREIGN KEY (author) REFERENCES accounts(email) ON DELETE CASCADE
);