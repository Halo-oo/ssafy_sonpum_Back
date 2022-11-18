use sonpum;

-- 사용자 test data 넣기 
insert into user(userId, userName, userPwd, email, joindate, role, delFlag, phoneNumber, reportCount)
values ('ssafy', 'ssafy', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', 'ssafy@ssafy.com', now(), 'USER', 0, '01011112222', 0);
-- 1234 암호화 > 03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4
insert into user(userId, userName, userPwd, email, joindate, role, delFlag, phoneNumber, reportCount)
values ('corp', 'corp', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', 'corp@ssafy.com', now(), 'CORP', 0, '01033332222', 1);

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