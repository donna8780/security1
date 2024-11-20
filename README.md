
# Security

### 1. 회원가입 (POST /api/v1/member)

#### 클라이언트 요청:
클라이언트는 회원가입을 위해 /api/v1/member 경로로 HTTP POST 요청을 보냅니다.
요청 본문에 회원 정보 (이메일, 패스워드, 이름, 성별, 전화번호 등)를 포함하여 전송합니다.
요청 본문은 MemberCreateRequestDto 객체로 매핑됩니다.

#### 서버 처리:
DTO 검증 (@Valid):
클라이언트가 보낸 요청 본문을 MemberCreateRequestDto로 변환하고, 유효성 검사를 진행합니다. (예: @NotBlank, @Pattern 등)

#### MemberFacade.createMember(dto) 호출:
요청이 유효하면 memberFacade.createMember(dto)가 호출됩니다.
MemberFacade는 서비스 레이어로, 실제 비즈니스 로직을 처리하는 클래스입니다. 이 메서드는 회원을 생성하는 로직을 담당합니다.

#### DTO → Member 객체 변환:
MemberCreateRequestDto는 Member 도메인 객체로 변환됩니다.
이때 Member 객체의 password는 이미 요청에서 받은 비밀번호를 그대로 사용합니다.
password는 보통 암호화(예: bcrypt) 과정을 거쳐 데이터베이스에 저장됩니다.

#### Member 저장:
Member 객체는 데이터베이스에 저장됩니다.
이 객체에는 이메일, 이름, 패스워드, 성별, 전화번호 등의 정보가 포함됩니다.
서버 응답:
회원가입이 완료되면, 서버는 HTTP 200 OK 상태 코드로 응답을 반환합니다.
응답 본문은 비어있습니다. (ResponseEntity.ok().build())


### 2. 로그인 (POST /api/v1/login)

#### 클라이언트 요청:
클라이언트는 로그인을 위해 /api/v1/login 경로로 HTTP POST 요청을 보냅니다.
요청 본문에 이메일과 패스워드를 포함하여 전송합니다.
요청 본문은 LoginMemberRequestDto 객체로 매핑됩니다.
#### 서버 처리:
#### DTO 검증 (@Valid):

클라이언트가 보낸 요청 본문을 LoginMemberRequestDto로 변환하고, 유효성 검사를 진행합니다.
MemberFacade.login(dto) 호출:

요청이 유효하면, memberFacade.login(dto)가 호출됩니다.
login 메서드는 이메일과 패스워드를 사용하여 로그인 검증을 수행합니다.
#### 회원 인증:

이메일을 기반으로 데이터베이스에서 해당 회원을 조회합니다.
패스워드는 데이터베이스에 저장된 암호화된 비밀번호와 비교됩니다. 만약 비밀번호가 일치하지 않으면 UnauthorizedException이 발생합니다.

#### JWT 토큰 발급:

인증이 성공하면, 서버는 JWT(Json Web Token) 토큰을 생성하여 클라이언트에 전달합니다.
이 토큰은 클라이언트가 이후의 요청에서 인증을 받을 수 있도록 사용됩니다. 보통 토큰은 클라이언트의 로컬 스토리지나 쿠키에 저장됩니다.

#### 서버 응답:
로그인 성공 시, 서버는 HTTP 200 OK 상태 코드와 함께 JWT 토큰을 응답 본문으로 반환합니다.
응답 예시: { "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." }

### 3. 각 단계에서의 데이터 흐름
#### 회원가입:

클라이언트 → MemberCreateRequestDto (이메일, 패스워드 등)
서버 → MemberCreateRequestDto 검증 → Member 객체로 변환 → 데이터베이스에 저장
서버 → 200 OK (응답 없음)

#### 로그인:

클라이언트 → LoginMemberRequestDto (이메일, 패스워드)
서버 → 이메일로 Member 조회 → 패스워드 검증 → JWT 토큰 발급
서버 → 200 OK (JWT 토큰)

### 4. 각 클래스와 객체의 역할
MemberCreateRequestDto: 회원가입 요청을 받을 때 사용하는 DTO로, 클라이언트에서 보내는 데이터를 캡슐화합니다.
LoginMemberRequestDto: 로그인 요청을 받을 때 사용하는 DTO로, 이메일과 패스워드를 포함합니다.
Member: 실제 회원 정보를 저장하는 도메인 객체로, 데이터베이스에 저장됩니다.
MemberFacade: 서비스 레이어로, 실제 비즈니스 로직을 처리합니다. 회원가입과 로그인 등의 로직을 담당합니다.
JWT: 로그인 성공 시 클라이언트에게 반환되는 JSON Web Token으로, 이후 요청에 대한 인증을 처리합니다.

### 5. 전체 흐름 요약

#### 회원가입:
클라이언트가 회원가입 정보를 제출 → 서버는 이를 MemberCreateRequestDto로 받아서 검증 후 Member 객체로 변환 → Member 객체를 데이터베이스에 저장 → 성공 응답(200 OK)

#### 로그인:
클라이언트가 이메일과 패스워드를 제출 → 서버는 이를 LoginMemberRequestDto로 받아서 유효성 검증 후 Member 객체를 조회 → 패스워드 검증 후 JWT 토큰을 발급 → 클라이언트에게 JWT 토큰 반환
