use sonpum;

-- 사용자 test data 넣기 
insert into user(userId, userName, userPwd, email, joindate, role, delFlag, phoneNumber, reportCount)
values ('ssafy', 'ssafy', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', 'ssafy@ssafy.com', now(), 'USER', 0, '01011112222', 0);
-- 1234 암호화 > 03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4
insert into user(userId, userName, userPwd, email, joindate, role, delFlag, phoneNumber, reportCount)
values ('corp', 'corp', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', 'corp@ssafy.com', now(), 'CORP', 0, '01033332222', 1);
insert into user(userId, userName, userPwd, email, joindate, role, delFlag, phoneNumber, reportCount)
values ('realrent', 'realrent', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', 'realrent@ssafy.com', now(), 'CORP', 0, '01033332222', 1);
insert into user(userId, userName, userPwd, email, joindate, role, delFlag, phoneNumber, reportCount)
values ('admin', 'admin', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', 'admin@ssafy.com', now(), 'ADMIN', 0, '01044442222', 0);
select * from user;

-- 공지사항 게시글 300개 자동넣기
delimiter $$
create procedure insertData()
BEGIN
    DECLARE i INT default 1;
    WHILE (i<=300) do
    insert into board_notice(userid, subject, content, hit, regitime)
    values('ssafy', concat('안녕하세요 ',i), '반갑습니다', 0, now());
    set i=i+1;
    end while;
    end $$
delimiter ;
call insertData();
select * from board_notice;

-- 매물 test용 데이터 넣기 
insert into house_product (userId, addressId, floor, buildYear, dealAmount, area, dealType, content)
values ('corp', 11110000000001, '16', '2022', '82,000', '30.36', 'MONTH', '월세 내놓습니다- :)'); 
insert into house_product (userId, addressId, floor, buildYear, dealAmount, area, dealType, content)
values ('corp', 11110000000001, '10', '2022', '50,000', '15.36', 'YEAR', '전세 내놓습니다- :)'); 
insert into house_product (userId, addressId, floor, buildYear, dealAmount, area, dealType, content)
values ('corp', 11110000000017, '34', '2022', '1000,000', '90.36', 'YEAR', '전세 내놓습니다- :)'); 
insert into house_product (userId, addressId, floor, buildYear, dealAmount, area, dealType, content)
values ('realrent', 11110000000017, '25', '2022', '7,000', '100.36', 'SALE', '매매 내놓습니다- :)'); 
select * from house_product;

-- 매물 북마크 test용 데이터
insert house_product_bookmark(userId, houseProductId)
values('realrent', 4);
insert house_product_bookmark(userId, houseProductId)
values('corp', 2);
insert house_product_bookmark(userId, houseProductId)
values('corp', 3);
select * from house_product_bookmark;

-- 매물 리뷰 test용 데이터
insert into house_product_review(houseProductId, userId, writerUserId, rating, content, regtime, image)
values (4, 'realrent', 'admin', 4, '너무 쪼아요', now(), '');
insert into house_product_review(houseProductId, userId, writerUserId, rating, content, regtime, image)
values (2, 'corp', 'ssafy', 2, '추워엉', now(), '');
insert into house_product_review(houseProductId, userId, writerUserId, rating, content, regtime, image)
values (1, 'corp', 'ssafy', 5, '만죡', now(), '');
select * from house_product_review;