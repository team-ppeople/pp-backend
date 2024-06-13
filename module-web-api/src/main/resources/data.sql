INSERT INTO users (created_date, email, nickname, updated_date)
VALUES (now(), 'nmrhtn7898@naver.com', '유저2686982302', now());

INSERT INTO oauth_user (client, client_subject, created_date, updated_date, user_id)
VALUES ('KAKAO', 'KAKAO:2686982302', now(), now(), 1);

INSERT INTO upload_file(content_length, uploader_id, url, content_type, file_type, created_date, updated_date)
values (1048576, 1, 'https://pp-public-bucket.s3.ap-northeast-2.amazonaws.com/default/default_profile.png', 'image/png', 'PROFILE_IMAGE', now(), now());

INSERT INTO upload_file(content_length, uploader_id, url, content_type, file_type, created_date, updated_date)
values (1048576, 1, 'https://pp-public-bucket.s3.ap-northeast-2.amazonaws.com/default/default_profile.png', 'image/png', 'POST_IMAGE', now(), now());

INSERT INTO upload_file(content_length, uploader_id, url, content_type, file_type, created_date, updated_date)
values (1048576, 1, 'https://pp-public-bucket.s3.ap-northeast-2.amazonaws.com/default/default_profile.png', 'image/png', 'POST_IMAGE', now(), now());

INSERT INTO upload_file(content_length, uploader_id, url, content_type, file_type, created_date, updated_date)
values (1048576, 1, 'https://pp-public-bucket.s3.ap-northeast-2.amazonaws.com/default/default_profile.png', 'image/png', 'POST_IMAGE', now(), now());

INSERT INTO post (content, created_date, creator_id, title, updated_date)
values ('반갑습니다 🙋', now(), 1, '안녕하세요~ 여러분 👋', now());

INSERT INTO post_image (created_date, post_id, updated_date, upload_file_id)
VALUES (now(), 1, now(), 2);

INSERT INTO post_image (created_date, post_id, updated_date, upload_file_id)
VALUES (now(), 1, now(), 3);

INSERT INTO post_image (created_date, post_id, updated_date, upload_file_id)
VALUES (now(), 1, now(), 4);

INSERT INTO comment (content, created_date, creator_id, post_id, updated_date)
VALUES ('자유롭게 댓글 달아주세요 👍', now(), 1, 1, now());