START TRANSACTION;
-- Create the Client
INSERT INTO clients (
    accountType,
    subdomain,
    name,
    email,
    phone,
    country,
    city,
    zip,
    address,
    createdAt,
    updatedAt
) VALUES (
          "INDIVIDUAL",
          "test",
          "Test Client",
          "test@hotels.test",
          "+256770000001",
          "Uganda",
          "Kampala",
          null,
          "Kampala Rd",
          NOW(),
          NOW()
);
-- Create the superuser
INSERT INTO users (
    type,
    email,
    clientId,
    password,
    active,
    createdAt,
    updatedAt
) VALUES (
          "BUSINESS_OWNER",
          "test@hotels.test",
          LAST_INSERT_ID(),
          "$2a$12$f9wd.mnPgU.8PRn98yScROXrC.cGWByFTer3F0vu31qTTK41BYGL6", -- BcryptUtil.bcryptHash("password")
          1,
          NOW(),
          NOW()
);

COMMIT;
