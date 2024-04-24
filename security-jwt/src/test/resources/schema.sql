DROP TABLE IF EXISTS user_role;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS users;

CREATE TABLE IF NOT EXISTS users
(
    id          UUID DEFAULT random_uuid(),
    name        VARCHAR(64) NOT NULL,
    email       VARCHAR(64) UNIQUE NOT NULL,
    CONSTRAINT  pk_user  PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS roles
(
    id          UUID DEFAULT random_uuid(),
    role_name   VARCHAR(64) NOT NULL,
    CONSTRAINT  pk_role  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS user_role
(
    user_id     UUID NOT NULL,
    role_id     UUID NOT NULL,
    CONSTRAINT  fk_ur_users FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT  fk_ur_roles FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE,
    CONSTRAINT  pk_user_role  PRIMARY KEY (user_id, role_id)
);

CREATE TABLE IF NOT EXISTS books
(
    id           UUID DEFAULT random_uuid(),
    title        VARCHAR(64)  NOT NULL,
    description  VARCHAR(255) NOT NULL,
    author_id    UUID         NOT NULL,
    publish_date timestamp without time zone,
    CONSTRAINT   pk_books PRIMARY KEY (id),
    CONSTRAINT   fk_author FOREIGN KEY (author_id) REFERENCES users (id) ON DELETE CASCADE
);
