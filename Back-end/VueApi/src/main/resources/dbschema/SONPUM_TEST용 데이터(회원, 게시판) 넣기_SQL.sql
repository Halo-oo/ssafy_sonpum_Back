use sonpum;

-- 사용자 test data 넣기 
insert into user(userId, userName, userPwd, email, joindate, role, delFlag, phoneNumber, reportCount)
values ('ssafy', 'ssafy', '1234', 'ssafy@ssafy.com', now(), 'USER', 0, '01011112222', 0);
insert into user(userId, userName, userPwd, email, joindate, role, delFlag, phoneNumber, reportCount)
values ('corp', 'corp', '1234', 'corp@ssafy.com', now(), 'CORP', 0, '01033332222', 1);
insert into user(userId, userName, userPwd, email, joindate, role, delFlag, phoneNumber, reportCount)
values ('admin', 'admin', '1234', 'admin@ssafy.com', now(), 'ADMIN', 0, '01044442222', 0);
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