INSERT INTO public.users
(first_name, last_name, email, "password", user_type, phone_number, activated, device_token)
VALUES ('Viktor', 'Tasevski', 'viktor-tasevski@hotmail.com',
        '$2a$10$eZVRmarKkv7MU.juKvG1ye06AQA9m5qqvx9VZifV9kiMQRPLmf.9C', 'USER', '072', false, 'nesto');

INSERT INTO public.coordinates
    (longitude, latitude)
VALUES (21.4597313, 41.9895589);

INSERT INTO public.locations (coordinates_id, municipality, address)
SELECT id, 'Aerodrom', 'Tri Biseri'
FROM coordinates
WHERE longitude = 21.4597313
  AND latitude = 41.9895589;

INSERT INTO public.lost_pets (name, pet_type, photo, additional_information, pet_owner_id, lost_at_time,
                              lost_at_location_id, status)
SELECT 'Niki', 'DOG', 'nesto', 'Mojot najubav Nikolce', users.id,
       TIMESTAMP '2023-06-28 13:01:44', loc.id,
       'LOST'
FROM users
         CROSS JOIN locations AS loc
         CROSS JOIN coordinates AS coord
WHERE users.email = 'viktor-tasevski@hotmail.com'
  AND coord.longitude = 21.4597313
  AND coord.latitude = 41.9895589
  AND loc.coordinates_id = coord.id;

INSERT INTO public.notifications
    (title, body, token, notification_type)
VALUES ('Нова локација', 'Миленикот е виден на нова локација!', 'nesto', 'NEW_LOCATION');

INSERT INTO public.coordinates
(longitude, latitude)
VALUES (18.0655534, 42.8571127);

INSERT INTO public.locations (coordinates_id, municipality, address)
SELECT id, 'Dracevo', 'Rumena Hadjipanzova'
FROM coordinates
WHERE longitude = 18.0655534
  AND latitude = 42.8571127;

INSERT INTO public.lost_pets (name, pet_type, photo, additional_information, pet_owner_id, lost_at_time,
                              lost_at_location_id, status)
SELECT 'Fifi', 'DOG', 'nesto', 'Lepoticata Fifi', users.id,
       TIMESTAMP '2023-05-27 13:01:44', loc.id,
       'LOST'
FROM users
         CROSS JOIN locations AS loc
         CROSS JOIN coordinates AS coord
WHERE users.email = 'viktor-tasevski@hotmail.com'
  AND coord.longitude = 18.0655534
  AND coord.latitude = 42.8571127
  AND loc.coordinates_id = coord.id;

INSERT INTO public.coordinates
(longitude, latitude)
VALUES (21.39171065286019, 42.00476155);

INSERT INTO public.locations (coordinates_id, municipality, address)
SELECT id, 'Karpos', 'Kaj Emilijana vo dvor'
FROM coordinates
WHERE longitude = 21.39171065286019
  AND latitude = 42.00476155;

INSERT INTO public.lost_pets (name, pet_type, photo, additional_information, pet_owner_id, lost_at_time,
                              lost_at_location_id, status)
SELECT 'Dora', 'DOG', 'nesto', 'Dora na Emilijana', users.id,
       TIMESTAMP '2023-04-20 13:01:44', loc.id,
       'LOST'
FROM users
         CROSS JOIN locations AS loc
         CROSS JOIN coordinates AS coord
WHERE users.email = 'viktor-tasevski@hotmail.com'
  AND coord.longitude = 21.39171065286019
  AND coord.latitude = 42.00476155
  AND loc.coordinates_id = coord.id;