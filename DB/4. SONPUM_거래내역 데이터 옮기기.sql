use sonpum;
-- table data 전체 삭제 
-- truncate address;
-- truncate house_deal;
-- truncate house_product;

-- 확인
-- select * from dongcode;
-- select * from address;
-- select * from house_deal;
-- select * from house_product;
-- select * from temp.housedeal;
-- select * from houseinfo;

-- ! [사전작업] 
-- temp 데이터베이스에 아파트 크롤링 sql문 돌리기 
-- ssafyvue 데이터베이스에 vue_table.sql문 돌리기

-- 1) dongcode 복제
insert into sonpum.dongcode 
select *
from temp.dongcode;

-- 2) temp.houseinfo 를 address로 옮기기
insert into sonpum.address (addressId, dongCode, roadName, roadNameBonbun, roadNameBubun, apartName, buildYear)
select houseinfo.aptCode, houseinfo.dongCode, houseinfo.roadName, houseinfo.roadNameBonbun, houseinfo.roadNameBubun, houseinfo.apartmentName, houseinfo.buildYear
from temp.houseinfo;

-- 3) housedeal 테이블의 컬럼 값을 > house_deal 테이블로 옮기기
insert into sonpum.house_deal (dealAmount, dealYear, dealMonth, dealDay, area, floor, cancelDealType, addressId)
select housedeal.dealAmount, housedeal.dealYear, housedeal.dealMonth, housedeal.dealDay, housedeal.area, housedeal.floor, housedeal.cancelDealType, housedeal.aptCode
from temp.housedeal housedeal;

-- 4) sidocode, guguncode 컬럼 값을 > sonpum DB sidocode, guguncode 테이블로 옮기기
insert into sonpum.sidocode (sidoCode, sidoName)
select vue.sidoCode, vue.sidoName
from ssafyvue.sidocode vue; 

insert into sonpum.guguncode (gugunCode, gugunName)
select vue.gugunCode, vue.gugunName
from ssafyvue.guguncode vue; 
select * from sidocode; 
select * from guguncode;