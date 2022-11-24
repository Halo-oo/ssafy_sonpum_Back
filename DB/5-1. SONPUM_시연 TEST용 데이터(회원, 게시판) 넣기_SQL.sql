use sonpum;

-- 사용자 test data 넣기 
select * from user;
insert into user(userId, userName, userPwd, email, joindate, role, delFlag, phoneNumber, reportCount)
values ('ssafy', '김싸피', '2f90ad2809ce486328537a402d84387159598e4265d685b9fcee61b41066c9ee', 'ssafy@ssafy.com', now(), 'USER', 0, '01012345678', 0);
insert into user(userId, userName, userPwd, email, joindate, role, delFlag, phoneNumber, reportCount)
values ('corp', '코퍼레이션', '74df35043bd01af2ffb8ecdc3033824661a4f8c1f311bfbac3c412c598a28e24', 'corp@ssafy.com', now(), 'CORP', 0, '01099998745', 0);
insert into user(userId, userName, userPwd, email, joindate, role, delFlag, phoneNumber, reportCount)
values ('admin', 'admin', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', 'admin@ssafy.com', now(), 'ADMIN', 0, '01044442222', 0);
insert into user(userId, userName, userPwd, email, joindate, role, delFlag, phoneNumber, reportCount)
values ('realrent', '부동산', 'f2d971794808171c87eaa01e02140c9d57775fe33374278f0f95b8e127553245', 'realrent@ssafy.com', now(), 'CORP', 0, '01033332222', 1);


-- 공지사항 게시글 넣기
insert into board_notice(userid, subject, content, hit, regitime)
values('admin', '[공지사항] 허위매물 신고자 보상 정책 변경', '오는 6월부터 허위매물을 신고한 이용자 분들에게 제공되던 정책이 변경될 예정입니다.<br> 그 동안 허위매물 신고자 분들께 최초 1회 기프트콘을 제공했던 서비스는 5월31일부로 종료되며 6월부터 새로운 보상 정책을 준비하고 있으니 참고 부탁드립니다.<br> 앞으로도 허위매물 근절을 위한 모니터링 및 시스템 강화를 통해 다방 사용자 분들의 피해를 예방 할 수 있도록 최선을 다하겠습니다.<br> 감사합니다.', 0, now());
insert into board_notice(userid, subject, content, hit, regitime)
values('admin', '[공지사항] 개인정보 처리방침 개정 안내', '개인정보보호법, 정보통신망 이용촉진 및 정보보호 등에 관한 법률 등 정보통신서비스 관련 법규상의 개인정보 보호 규정을 준수하며, 관련 법령에 의거한 개인정보처리방침을 정하여 사용자의 권익 보호를 위해 노력하고 있습니다.
<br>
아래와 같이 개인정보처리방침 개정될 예정임을 안내드리오니, 확인 후 이용에 참고하시기 바랍니다. <변경 상세내용>
<br>
(1) 개정일정 :
<br>
공고일자 : 2021년 9월 29일
시행일자 : 2021년 10월 6일<br>
(2) 주요 개정 내용 : 개인정보의 수집•이용 목적•항목•수집방법, 개인정보 처리기간, 개인정보의 제 3자 제공, 개인정보처리의 위탁, 이용자의 권리, 개인정보 보호책임자 등
<br>
(3) 개정내용 대비표', 0, now());
insert into board_notice(userid, subject, content, hit, regitime)
values('admin', '[공지사항] LH 마이홈포털 공공임대주택 정보 제공 안내', '공공임대주택 정보를 확인하실 수 있게 됐습니다.
<br>
매물검색 시 사용하시는 [맞춤필터]를 통해 공공임대주택을 클릭하시면,
공공임대주택 정보가 포함된 매물리스트와 지도가 제공됩니다.
<br>
주택 정보를 자세히 보기를 원하시면 클릭 시
소재지, 모집호수 등 상세 정보가 담긴 안내페이지가 뜨며
입주자모집공고로 바로 연결되는 마이홈포털 URL도 제공됩니다.
<br>
앞으로도 고객님들의 주거 정보를 보다 다양하게 제공할 수 있도록 노력하겠습니다.
<br>
감사합니다.', 0, now());
insert into board_notice(userid, subject, content, hit, regitime)
values('admin', '[공지사항] 서비스 이용약관 신설 안내', '서비스 이용약관에 신설된 내용이 있어 회원님들께 사전에 안내드립니다.
<br>
<br>
※ 변경 적용일 : 2020년 8월 10일(월)
[ 주요 사항 ]
<br>
<br>
1. 서비스 약관 신설
제17조 (이용자의 의무)
1항
11호  회사 업무를 방해하는 행위
<br>
<br>
(1) 회사의 임직원, 피용자, 상담원, 업무수탁자(이하‘임직원등’)에게 폭력적, 명예훼손적, 모욕적,  또는 성적 굴욕감이나 혐오감을 유발할 수 있는 발언이나 행동을 하는 행위
(2) 통념상 비합리적인 요구 또는 요청을 임직원등에게 지속적으로 또는 반복적으로 하는 행위
<br>
(3) 임직원등에게 서비스 이용과 무관한 연락을 하는 행위
<br>
(4) 그 밖에 위 각 호의 행위에 준하는 일체의 업무 방해 행위', 0, now());
insert into board_notice(userid, subject, content, hit, regitime)
values('admin', '[공지사항] 생활형숙박시설 서비스 종료 예정 안내 (12/15)', '생활숙박시설의 주거용 사용 제한에 대한 건축법 시행령이 11월 2일부터 시행되었습니다.
<br>
국토교통부 보도내용 바로 가기
<br>
​건축법령 개정에 따라, 오피스텔 카테고리에서 제공하는 생활숙박시설 단지는 하기 일정으로 서비스를 중단 합니다.
<br>
● 서비스 중단 일정 : 12/15(수)
<br>
생활숙박시설 단지에 등록된 매물은 비공개로 전환 될 예정입니다.
<br>
관련한 문의사항은 고객센터 (1899-6840)로 부탁 드립니다. 감사합니다.', 0, now());
insert into board_notice(userid, subject, content, hit, regitime)
values('admin', '[공지사항] 안드로이드 OS 최소사양 변경 안내', '오는 12월 30일부로 안드로이드 최소지원 OS사양이 5.0에서 6.0으로 변경됩니다.
<br>
이에 따라 안드로이드 OS 6.0 미만 버전의 사용이 종료됨을 말씀드립니다.
<br>
[앱 안드로이드 OS 최소 사양]
<br>
기존) 안드로이드 OS 5.0
<br>
변경) 안드로이드 OS 6.0
<br>
[적용일]
<br>
2021년 12월 30일(목)
<br>
이용하시는 고객님들께서는 위의 내용을 참고하여 앱을 사용하여 주시기 바랍니다.
<br>
감사합니다.', 0, now());
insert into board_notice(userid, subject, content, hit, regitime)
values('admin', '[공지사항] 간편문의(매물확인 메신저) 서비스 안내', '‘간편문의(매물확인 메신저)’출시 안내 드립니다.
<br>
4월 16일(목)부터 매물상세 페이지에 ‘간편문의(매물확인 메신저)’ 서비스가 도입됩니다.
<br>
앱 매물상세 페이지 하단에 [계약 가능 여부 확인]을 터치하면,
<br>
해당 매물을 올린 공인중개사에게
<br>
매물의 거래 가능 여부를 문의해 회원님의 카카오톡으로 알려 드립니다.
<br>
간편문의(매물확인 메신저)를 통해 매물의 진위 및 거래 가능 여부를 확인하실 수 있습니다.', 0, now());
insert into board_notice(userid, subject, content, hit, regitime)
values('admin', '[공지사항] 생활숙박시설 서비스 재개 안내 (12/22)', '생활형 숙박시설의 불법 전용을 방지하기 위한 개정 건축법 시행령이 11월2일 부터 시행되었습니다. 건축법령 개정에 대한 KISO 해석에 따라, 12월15일부터 오피스텔 카테고리에서 생활형 숙박시설 노출을 제외 하였습니다.
<br>
서비스 중단 후 이용에 대한 불편함이 있다는 민원이 지속적으로 접수되어, 법령 해석에 대해 국토부와 KISO에 재 검토 요청했고, 생활형 숙박시설에 대한 이용자의 오인이 없도록 서비스 한다면, 재개 해도 이슈 없다는 답변을 받았습니다.
<br>
이에, 생활형 숙박시설 단지 서비스를 12/22(수)부터 재개 할 예정입니다.
<br>
서비스 이용에 불편을 드려 죄송합니다.
<br>
관련한 문의사항은 고객센터(1899-6840)로 부탁 드립니다. 감사합니다.', 0, now());
select * from board_notice;


-- 매물 test용 데이터 넣기 
-- "1168010600" 대치동, 11680000000099 대치 현대, 11680000000111 대치 아이파크  
select * from house_product;
insert into house_product (userId, addressId, floor, buildYear, dealAmount, area, dealType, content)
values ('corp', 11680000000099, '24', '2022', '170,000', '59.82', 'SALE', '대치동유명학원가 중심지 초근접 컨디션 전세금 조정 가능
<br><br>
전세 귀한 매물
약간의 조정 가능해요
대치동 유명학원가 가기 제일 좋은 핵심 중심지 아파트입니다
시간,공간이동,경제적 등 절감 되어 너무 좋지요. 
남향이라 따뜻하고 채광 아주 좋아요
우수학군지역이고 
살기 쾌적하고 주차하기 편하지요
컨디션 좋아요
<br><br>
다른 다양한 매물 있으니
부자부동산으로 문의주시면
최선을 다해 중개합니다. 
행복하세요~!!'); 
insert into house_product (userId, addressId, floor, buildYear, dealAmount, area, dealType, content)
values ('corp', 11680000000099, '20', '2022', '200,000', '114.82', 'YEAR', '-2,9호선 종합운동장 역세권 45평 아파트, 29동 3층
<br><br>
-방4개, 화장실2개, 안방 확장, 거실 확장, 올수리 깨끗함
<br><br>
-현시세 10억대비 7억7천보증금 아주저렴
<br><br>
-현세입자가 자기 집처럼 아주깨끗하게 사용중
<br><br>
-거실에서 바로보는 단풍풍경 굿, 매우 조용한 아파트.
<br><br>
-전세입주시기는 조정협의'); 
insert into house_product (userId, addressId, floor, buildYear, dealAmount, area, dealType, content)
values ('corp', 11680000000099, '10', '2022', '90,000', '40.82', 'MONTH', '55 주인수리됨, L 타워,산성조망권, 장기거주
<br><br>
**전화상담 방문상담 환영합니다**'); 
insert into house_product (userId, addressId, floor, buildYear, dealAmount, area, dealType, content)
values ('corp', 11680000000111, '3', '2022', '120,000', '130.82', 'SALE', '방3화2,식기세척기,붙박이장,넓은베란다와옥상,각세대지하창고
<br><br>
1.방3,화2 큰주방과 거실,파우더룸,지하창고(세대별)
<br><br>
2.넓은 주차장(세대별 지정주차)
<br><br>
3.주민 공동 편의시설 - 루프탑 테라스와 탁구장'); 
insert into house_product (userId, addressId, floor, buildYear, dealAmount, area, dealType, content)
values ('corp', 11680000000111, '14', '1992', '3400,000', '135.82', 'SALE', '특올수리 41 최고물건  상담환영'); 
insert into house_product (userId, addressId, floor, buildYear, dealAmount, area, dealType, content)
values ('corp', 11680000000111, '24', '1995', '101,000', '101.22', 'MONTH', '39. 방4 화장실2  실내깔금 입주협의'); 
select * from house_product;

