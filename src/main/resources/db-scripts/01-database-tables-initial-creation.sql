-- -------------------------------------------------------------------------------------------------
-- This script is necessary to create the database and the initial database tables.
--
-- This must be the first script to be executed
--

-- Create the database on the MySQL Server
CREATE DATABASE netflix_subscriptions CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Create the user's database on the MySQL Server
CREATE USER 'netflix-user'@'localhost' IDENTIFIED WITH caching_sha2_password BY 'netflix-secret';

-- Grant privileges to the new user in the created database
GRANT ALL PRIVILEGES ON netflix_subscriptions.* TO 'netflix-user'@'localhost';

CREATE TABLE IF NOT EXISTS netflix_subscriptions.subscription (
  id BIGINT NOT NULL AUTO_INCREMENT,
  type VARCHAR(255) NOT NULL,
  price DECIMAL NOT NULL,
  start_date DATE NOT NULL,
  end_date DATE NOT NULL,
  CONSTRAINT pk_subscription PRIMARY KEY (id),
  UNIQUE INDEX UNIQUE_ID (id ASC) VISIBLE
);

CREATE TABLE IF NOT EXISTS netflix_subscriptions.profile (
  id BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  alias VARCHAR(255) NOT NULL,
  avatar LONGTEXT NULL,
  subscription_id BIGINT NOT NULL,
  CONSTRAINT pk_profile PRIMARY KEY (id),
  UNIQUE INDEX UNIQUE_ID (id ASC) VISIBLE,
  CONSTRAINT FK_PROFILE_SUBSCRIPTION_ID FOREIGN KEY (subscription_id) REFERENCES subscription (id)
);
