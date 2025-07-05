# spring-security

jjwt-api<br/>
JWT 생성/파싱/검증에 필요한 인터페이스와 핵심 로직이 포함된 API 모듈<br/>
JwtBuilder, JwtParser, Claims 등의 핵심 클래스가 이 안에 있음.<br/>
하지만 단독으로는 작동하지 않음 => 구현체가 필요함.

jjwt-impl<br/>
jjwt-api의 구현체 라이브러리<br/>
실제로 토큰을 만들고 파싱하는 구현 로직이 여기있음.

jjwt-jackson<br/>
JWT의 payload(Claims 부분)을 JSON으로 직렬화/역직렬화할 때 Jackson 사용을 지원하는 모듈 <br/>
기본적으로 jjwtsms JSON 처리를 위한 구현체가 필요함.

JWT<br/>
\<Header>.\<Payload>.\<Signature><br/>
header => 서명 알고리즘, 토큰 타입<br/>
payload => Claim이라고 부르며, 실제 사용자 정보가 담김(Base64Url로 인코딩되어 있지만 암호화되지 않음 => 누구나 읽을 수 있다)<br/>
signature => 위변조 방지 역할 서버에서 발급 시 사용한 secretKey로 서명