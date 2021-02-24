
기본 포트는 80 사용
포트 중복 시 application.properties 에서 server.port 에서 변경하시면 됩니다.
Spring Boot Framework, Gradle, JAVA 사용
데이터 베이스는 H2 in-memory db 를 사용하였으므로 사전에 H2 Database Engine 이 설치되어있어야 합니다.

Persistence 프레임워크는 ORM : Spring Data JPA 를 이용하였습니다.


기본접속 페이지(로그인페이지) : http:localhost

회원가입이 없는 관계로 아무런 아이디, 패스워드 입력하신 후에 SIGN IN 하시면 공지사항 페이지로이동됩니다.

공지사항 기능 설명

작성
    작성버튼 클릭 후
    제목, 내용 입력 후 SAVE 시 저장
수정
    table 에 radio button click 하면 수정 버튼이 활성화 됩니다.
    제목, 내용 입력 후 SAVE 시 변경사항 저장
삭제
    table 에 radio button click 하면 삭제 버튼이 활성화 됩니다.
    해당 글 삭제.

조회는 검색인지 조회인지 명확하게 표기해주시지 않아서
페이지 로드시 페이징처리하여 글을 조회합니다.


    