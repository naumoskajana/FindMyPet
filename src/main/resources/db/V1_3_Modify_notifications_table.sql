ALTER TABLE notifications
    RENAME COLUMN token TO user_email;

ALTER TABLE notifications
    ADD COLUMN seen_pet_id BIGINT DEFAULT NULL;
