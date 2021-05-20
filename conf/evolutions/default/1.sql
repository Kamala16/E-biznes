-- !Ups

CREATE TABLE "cart"
(
    "id"         INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    "userId"     INTEGER NOT NULL,
    "productId"  INTEGER NOT NULL,
    "discountId" INTEGER NOT NULL,
    "price"      INTEGER NOT NULL
);

CREATE TABLE "category"
(
    "id"        INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    "productId" INTEGER NOT NULL,
    "name"      VARCHAR NOT NULL
);

CREATE TABLE "delivery"
(
    "id"   INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    "name" VARCHAR NOT NULL
);

CREATE TABLE "discount"
(
    "id"     INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    "userId" INTEGER NOT NULL,
    "value"  INTEGER NOT NULL
);

CREATE TABLE "favorite"
(
    "id"        INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    "userId"    INTEGER NOT NULL,
    "productId" INTEGER NOT NULL
);

CREATE TABLE "payment"
(
    "id"   INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    "name" VARCHAR NOT NULL
);

CREATE TABLE "product"
(
    "id"          INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    "categoryId"  INTEGER NOT NULL,
    "rateId"      INTEGER NOT NULL,
    "promotionId" INTEGER NOT NULL,
    "price"       DOUBLE  NOT NULL
);

CREATE TABLE "promotion"
(
    "id"        INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    "productId" INTEGER NOT NULL,
    "value"     INTEGER NOT NULL
);

CREATE TABLE "rate"
(
    "id"        INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    "userId"    INTEGER NOT NULL,
    "productId" INTEGER NOT NULL,
    "value"     INTEGER NOT NULL
);

CREATE TABLE "user"
(
    "id"         INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    "favoriteId" INTEGER NOT NULL,
    "name"       VARCHAR NOT NULL,
    "email"      VARCHAR NOT NULL,
    "password"   VARCHAR NOT NULL
);

-- !Downs

DROP TABLE "cart";
DROP TABLE "category";
DROP TABLE "delivery";
DROP TABLE "discount";
DROP TABLE "favorite";
DROP TABLE "payment";
DROP TABLE "product";
DROP TABLE "promotion";
DROP TABLE "rate";
DROP TABLE "user";