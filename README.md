# springminiproject
java기반 spring 프레임워크를 적용해서 작은 서비스를 만듭니다. 이것을 이용해서 여러 웹 서비스를 제공할 수 있습니다.
기본적인 제작 순서는 다음과 같습니다.


1.계획(전반적인 주제와 기능을 정합니다)

2.설계(전반적인 구조를 설계(데이터베이스 테이블의 수, 클래스들의 구성도, 화면 흐름도 등) 합니다.)

3.구현(직접 모든 코드를 작성합니다.)

4.테스트(테스트는 기능 한개가 만들어질 때마다 시행합니다)



설계, 구현, 테스트의 과정은 여러번 반복될 수 있습니다. 중간에 여러 요인으로 인해 기능이 추가되거나, 사라질 수도 있습니다.



현재까지 구현상황(2024/2/27):
-게시판 생성/삭제/읽기 기능 

-게시판 설명 수정 기능, 게시판 권한 보유자 열람 기능

-게시물 생성/삭제/수정/읽기, 페이징, 검색 기능

-파일 업로드/다운로드 기능

-댓글 생성/삭제/수정/읽기, 페이징 기능

-회원 가입/로그인/로그아웃 기능

-마이페이지 일부 기능(작성한 댓글/글 기록 읽기/삭제, 업로드 파일 기록 읽기/삭제, 기타정보 수정/추가/삭제 기능)





앞으로 구현 예정 기능:


-회원관련 기능 추가

아이디 찾기 기능(이메일, 전화번호 활용, 가능하다면 이메일을 이용한 인증기능을 추가), 비밀번호 찾기 기능(아이디, 이메일, 전화번호 활용, 가능하다면 이메일을 이용한 인증 기능 추가)-> 초기에 전화번호, 이메일 등록시 중복여부와 유효성 확인해야
회원 탈퇴기능


-관리자 페이지 관련 기능 생성

가입된 유저 목록 읽어오기 
가입된 유저에게 권한 부여/박탈
가입된 유저 차단
