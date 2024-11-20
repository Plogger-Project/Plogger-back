<h1 style='background-color: rgba(55, 55, 55, 0.4); text-align: center'>Plogger API 설계(명세)서</h1>

해당 API 명세서는 '플로거'의 REST API를 명세하고 있습니다.  

- Domain : <http://localhost:4000> 

***
 
<h2 style='background-color: rgba(55, 55, 55, 0.2); text-align: center'>Auth 모듈</h2>

Plogger 서비스의 인증 및 인가와 관련된 REST API 모듈입니다.
로그인, 회원가입, 소셜 로그인 등의 API가 포함되어 있습니다.  
Auth 모듈은 인증 없이 요청할 수 있습니다. 
  
- url : /api/v1/auth  

***

#### - 로그인  
  
##### 설명

클라이언트는 사용자 아이디와 평문의 비밀번호를 입력하여 요청하고 아이디와 비밀번호가 일치한다면 인증에 사용될 token과 해당 token의 만료 기간을 응답 데이터로 전달 받습니다. 만약 아이디 혹은 비밀번호가 하나라도 틀린다면 로그인 정보 불일치에 해당하는 응답을 받게됩니다. 네트워크 에러, 서버 에러, 데이터베이스 에러, 토큰 생성 에러가 발생할 수 있습니다.  

- method : **GET**  
- end point : **/sign-in**  

##### Request

###### Request Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| userId | String | 사용자의 아이디 | O |
| password | String | 사용자의 비밀번호 | O |

###### Example

```bash
curl -v -X POST "http://localhost:4000/api/v1/auth/sign-in" \
 -d "userId=qwer1234" \
 -d "password=P!ssw0rd"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |
| accessToken | String | Bearer token 인증 방식에 사용될 JWT | O |
| expiration | Integer | JWT 만료 기간 (초단위) | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success.",
  "accessToken": "${ACCESS_TOKEN}",
  "expiration": 32400
}
```

**응답 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 실패 (로그인 정보 불일치)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "SF",
  "message": "Sign in failed."
}
```

**응답 실패 (토큰 생성 실패)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "TCF",
  "message": "Token creation failed."
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```

***

#### - 아이디 중복 확인  
  
##### 설명

클라이언트는 사용할 아이디를 입력하여 요청하고 중복되지 않는 아이디라면 성공 응답을 받습니다. 만약 아이디가 중복된다면 아이디 중복에 해당하는 응답을 받게됩니다. 네트워크 에러, 서버 에러, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **POST**  
- end point : **/id-check**  

##### Request

###### Request Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| userId | String | 중복확인 할 사용자의 아이디 | O |

###### Example

```bash
curl -v -X POST "http://localhost:4000/api/v1/auth/id-check" \
 -d "userId=qwer1234"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success."
}
```

**응답 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 : 실패 (중복된 아이디)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "DI",
  "message": "Duplicated user id."
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```

***

#### - 전화번호 인증  
  
##### 설명

클라이언트는 숫자로만 이루어진 11자리 전화번호를 입력하여 요청하고 이미 사용중인 전화번호인지 확인 후 4자리의 인증번호를 해당 전화번호에 문자를 전송합니다. 인증번호가 정상적으로 전송이 된다면 성공 응답을 받습니다. 만약 중복된 전화번호를 입력한다면 중복된 전화번호에 해당하는 응답을 받게됩니다. 네트워크 에러, 서버 에러, 데이터베이스 에러, 문자 전송 실패가 발생할 수 있습니다.  

- method : **POST**  
- URL : **/tel-auth**  

##### Request

###### Request Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| telNumber | String | 인증 번호를 전송할 사용자의 전화번호 (11자리 숫자) | O |

###### Example

```bash
curl -v -X POST "http://localhost:4000/api/v1/auth/tel-auth" \
 -d "telNumber=01011112222"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success."
}
```

**응답 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 : 실패 (중복된 전화번호)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "DT",
  "message": "Duplicated user tel number."
}
```

**응답 실패 (인증번호 전송 실패)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "TF",
  "message": "Auth number send failed."
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```

***

#### - 인증번호 확인  
  
##### 설명

클라이언트는 사용자 전화번호와 인증번호를 입력하여 요청하고 해당하는 전화번호와 인증번호가 서로 일치하는지 확인합니다. 일치한다면 성공에 대한 응답을 받습니다. 만약 일치하지 않는 다면 전화번호 인증 실패에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **POST**  
- end point : **/tel-auth-check**  

##### Request

###### Request Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| telNumber | String | 인증 번호를 확인할 사용자 전화번호 | O |
| authNumber | String | 인증 확인에 사용할 인증 번호 | O |

###### Example

```bash
curl -v -X POST "http://localhost:4000/api/v1/auth/tel-auth-check" \
 -d "telNumber=01011112222" \
 -d "authNumber=1234"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success."
}
```

**응답 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 : 실패 (전화번호 인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "TAF",
  "message": "Tel number authentication failed."
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```

***

#### - 아이디 찾기 
  
##### 설명

클라이언트는 가입 시 등록한 정보를 입력하여 본인의 계정을 확인할 수 있도록 도와줍니다. 전화번호를 기반으로 아이디를 검색하며, 요청에 대한 응답으로 사용자의 아이디 또는 관련 메시지를 반환합니다.

- method : **POST**  
- URL : **/find-id**  

##### Request

###### Request Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| telNumber | String | 인증 번호를 전송할 사용자의 전화번호 (11자리 숫자) | O |
| authNumber | String | 전화번호로 전송받은 인증번호 (4자리 숫자) | O |

###### Example

```bash
curl -v -X POST "http://localhost:4000/api/v1/auth/find-id" \
 -d "telNumber=01011112222" \
 -d "authNumber=1111"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success."
}
```

**응답 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 실패 (존재하지 않는 전화번호)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "NT",
  "message": "No Exist TelNumber."
}
```

**응답 실패 (인증번호 전송 실패)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "TF",
  "message": "Message send failed."
}
```

**응답 실패 (인증번호 인증 실패)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "TAF",
  "message": "TelNumber Authentication failed."
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```

***

#### - 회원가입  
  
##### 설명

클라이언트는 사용자 이름, 사용자 아이디, 비밀번호, 전화번호, 인증번호, 가입경로, 프로필 이미지, 상태 메세지 입력하여 요청하고 회원가입이 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 만약 존재하는 아이디일 경우 중복된 아이디에 대한 응답을 받고, 만약 존재하는 전화번호일 경우 중복된 전화번호에 대한 응답을 받고, 전화번호와 인증번호가 일치하지 않으면 전화번호 인증 실패에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **POST**  
- end point : **/sign-up**  

##### Request

###### Request Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| profileImage | String | 사용자의 프로필 이미지(기본 이미지) | O |
| name | String | 사용자의 이름 | O |
| userId | String | 사용자의 아이디 | O |
| password | String | 사용자의 비밀번호 (8~13자의 영문 + 숫자) | O |
| address | String | 사용자의 주소 | O |
| telNumber | String | 사용자의 전화번호 (11자의 숫자) | O |
| authNumber | String | 전화번호 인증번호 | O |
| joinPath | String | 회원가입 경로 (기본: 'HOME', 카카오: 'KAKAO', 네이버: 'NAVER', 구글: 'GOOGLE') | O |
| snsId | String | SNS 가입시 sns oauth2 ID | X |

###### Example

```bash
curl -v -X POST "http://localhost:4000/api/v1/auth/sign-up"\
 -d "profileImage=/defaultImage"\
 -d "name=홍길동"\
 -d "userId=qwer1234"\
 -d "password=qwer1234"\
 -d "telNumber=01011112222"\
 -d "authNumber=1234"\
 -d "address=부산광역시 부산진구"\
 -d "joinPath=HOME"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success."
}
```

**응답 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 : 실패 (중복된 아이디)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "DI",
  "message": "Duplicated user id."
}
```

**응답 : 실패 (중복된 전화번호)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "DT",
  "message": "Duplicated user tel number."
}
```

**응답 : 실패 (전화번호 인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "TAF",
  "message": "Tel number authentication failed."
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```

***

#### - SNS 회원가입 및 로그인  
  
##### 설명

클라이언트는 OAuth 인증서버를 입력하여 요청하고 해당하는 Redirect 응답을 받습니다. 회원가입이 되어있는 사용자의 경우 쿼리 매개변수로 접근 토큰과 토큰 만료 기간을 반환하며 회원가입이 되어있지 않은 사용자의 경우 쿼리 매개변수로 sns 아이디와 해당하는 sns 서비스의 이름을 반환합니다. 

- method : **GET**  
- end point : **/sns-sign-in/{registerId}**  

##### Request

###### Path Variable

| name | type | description | required |
|---|:---:|:---:|:---:|
| registerId | String | 사용 SNS (카카오: 'kakao', 네이버: 'naver', 구글:'google') | O |

###### Example

```bash
curl -X POST "http://localhost:4000/api/v1/auth/sns-sign-in/{kakao}" 
```

##### Response

###### Example

**응답 성공 (회원 O)**
```bash
HTTP/1.1 302 Found 
Location: http://localhost:3000/sns-success?accessToken=${accessToken}&expiration=36000
```

**응답 성공 (회원 X)**
```bash
HTTP/1.1 302 Found 
Location: http://localhost:3000/auth?snsId=${snsId}&joinPath=${joinPath}
```

***


#### - 비밀번호 찾기 

##### 설명

클라이언트는 가입 시 등록한 정보를 입력하여 본인의 계정을 확인할 수 있도록 도와줍니다. 아이디, 전화번호, 인증번호를 기반으로 존재하는 아이디와 전화번호인지 파악합니다. 만약 존재하는 아이디와 전화번호라면 요청에 대한 응답으로 사용자에게 임시 비밀번호를 반환합니다.

- method : **POST**  
- URL : **/find-password**  

##### Request

###### Request Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| userId | String | 사용자의 아이디 | O |
| telNumber | String | 인증 번호를 전송할 사용자의 전화번호 (11자리 숫자) | O |
| authNumber | String | 전화번호로 전송받은 인증번호 (4자리 숫자) | O |

###### Example

```bash
curl -v -X POST "http://localhost:4000/api/v1/auth/find-password" \
 -d "userId=qwer1234" \
 -d "telNumber=01011112222" \
 -d "authNumber=1111"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |
| tempPassword | String | 임시 비밀번호 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success.",
  "tempPassword": "P!ssw0rd"
}
```

**응답 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 : 실패 (존재하지 않는 아이디와 전화번호)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "NIT",
  "message": "No Exist user Id and Tel Number."
}
```

**응답 실패 (존재하지 않는 전화번호)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "NT",
  "message": "No Exist TelNumber."
}
```

**응답 실패 (메시지 전송 실패)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "TF",
  "message": "Message send failed."
}
```

**응답 실패 (인증번호 인증 실패)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "TAF",
  "message": "TelNumber Authentication failed."
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```

***

<h2 style='background-color: rgba(55, 55, 55, 0.2); text-align: center'>Recruit 모듈</h2>

Plogger 서비스의 구인 게시판과 관련된 REST API 모듈입니다.  
구인 게시글 목록 보기, 게시글 열람 및 수정, 작성 및 좋아요와 스크랩, 구인 신고 내역 등록 및 열람 등의 API가 포함되어 있습니다.  
Recruit 모듈은 모두 인증이 필요합니다.  
  
- url : /api/v1/recruit

***

#### - recruit 등록하기
  
##### 설명

클라이언트는 요청 헤더에 Bearer 인증 토큰을 포함하여 요청하고 조회가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 유효성 실패, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **Post**
- URL : **/**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | Bearer 토큰 인증 헤더 | O |

###### Request Body

**RecruitReport**  
| name | type | description | required |
|---|:---:|:---:|:---:|
| recruit_post_title | String | 제목 | O |
| recruit_post_content | String | 내용 | O |
| recruit_post_image | String | 이미지 | X |
| recruit_location | String | 위치 | O |
| recruit_address | String | 주소 | O |
| recruit_end_date | String | 마감일자 | O |
| min_people | Integer | 최소 인원 | O |

###### Example

```bash
curl -v -X POST "http://localhost:4000/api/v1/recruit" \
 -h "Authorization=Bearer XXXX" \
 -d "recruitPostTitle": "플로깅 할 사람 구합니다." \
 -d "recruitPostContent": "포항 영일대 해수욕장에서 플로깅 할 사람 구합니다." \
 -d "recruitLocation": "35.1521653164553, 129.05956569987063" \
 -d "recruitAddress": "부산광역시 부산진구 중앙대로 717" \
 -d "recruitEndDate": "2024-11-01" \
 -d "minPeople": 5 \
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success.",
}
```

**응답 : 실패 (존재하지 않는 사용자)**
```bash
HTTP/1.1 400 BAD_REQUEST
Content-Type: application/json;charset=UTF-8

{
  "code": "NI",
  "message": "No exist user id."
}

```

**응답 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "AF",
  "message": "Authentication fail."
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```

***

#### - recruit 리스트 열람하기

##### 설명

클라이언트는 요청 후 조회가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 유효성 실패, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **GET**  
- URL : **/**  

##### Request

###### Header

###### Example

```bash
curl -X GET "http://localhost:4000/api/v1/recruit" \
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |
| recruits | recruit[] | 구인 게시글 리스트 | O |
  
**recruit**  
| name | type | description | required |
|---|:---:|:---:|:---:|
| recruit_post_id | Integer | 아이디 | O |
| recruit_post_title | String | 제목 | O |
| recruit_post_content | String | 내용 | O |
| recruit_post_image | String | 이미지 | X |
| recruit_post_writer | String | 작성자 | O |
| recruit_post_createdAt | String | 작성일 | O |
| recruit_location | String | 위치 | O |
| recruit_address | String | 주소 | O |
| recruit_end_date | String | 마감일 | O |
| min_people | Integer | 최소 인원 | O |
| current_people | Integer | 현재 인원 | O |
| recruit_view | Integer | 조회수 | O |
| recruit_post_like | Integer | 좋아요수 | O |
| recruit_report | Integer | 신고수 | O |
| is_completed | Boolean | 마감 유무 | O |
| is_mileage | Boolean | 마일리지 지급 유무 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success.",
  "": [
    {
      "recruitPostId": 1,
      "recruitPostTitle": "플로깅 할 사람 구합니다.",
      "recruitPostContent": "포항 영일대 해수욕장에서 플로깅 할 사람 구합니다.",
      "recruitPostWriter": "qwer1234",
      "recruitPostCreatedAt": "2024-10-28",
      "recruitLocation": "35.1521653164553, 129.05956569987063",
      "recruitAddress": "부산광역시 부산진구 중앙대로 717",
      "recruitEndDate": "2024-11-01",
      "minPeople": 5,
      "currentPeople": 3,
      "recruitView" : 300,
      "recruitPostLike" : 10,
      "recruitReport" : 1,
      "isCompleted": false,
      "isMileage" : true
    }
    ...
  ]
}
```

**응답 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "AF",
  "message": "Authentication fail."
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```
***

#### - recruit 열람하기
##### 설명

클라이언트는 요청 후 조회가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 유효성 실패, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **GET**  
- URL : **/{recruitPostId}**  

##### Request

###### Header

###### Example

```bash
curl -X GET "http://localhost:4000/api/v1/recruit/1" \
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |
| recruit | recruit | 구인 게시글 리스트 | O |
  
**recruit**  
| name | type | description | required |
|---|:---:|:---:|:---:|
| recruit_post_id | Integer | 아이디 | O |
| recruit_post_title | String | 제목 | O |
| recruit_post_content | String | 내용 | O |
| recruit_post_image | String | 이미지 | X |
| recruit_post_writer | String | 작성자 | O |
| recruit_post_created_at | String | 작성일 | O |
| recruit_location | String | 위치 | O |
| recruit_address | String | 주소 | O |
| recruit_end_date | String | 마감일 | O |
| min_people | Integer | 최소 인원 | O |
| current_people | Integer | 현재 인원 | O |
| recruit_view | Integer | 조회수 | O |
| recruit_post_like | Integer | 좋아요수 | O |
| recruit_report | Integer | 신고수 | O |
| is_completed | Boolean | 마감 유무 | O |
| is_mileage | Boolean | 마일리지 지급 유무 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success.",
  "recruit": 
    {
      "recruitPostId": 1,
      "recruitPostTitle": "플로깅 할 사람 구합니다.",
      "recruitPostContent": "포항 영일대 해수욕장에서 플로깅 할 사람 구합니다.",
      "recruitPostWriter": "qwer1234",
      "recruitPostCreatedAt": "2024-10-28",
      "recruitLocation": "35.1521653164553, 129.05956569987063",
      "recruitAddress": "부산광역시 부산진구 중앙대로 717",
      "recruitEndDate": "2024-11-01",
      "minPeople": 5,
      "currentPeople": 3,
      "recruitView" : 300,
      "recruitPostLike" : 10,
      "recruitReport" : 1,
      "isCompleted": false,
      "isMileage" : true
    }
}
```

**응답 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 : 실패 (존재하지 않는 구인 게시글)**
```bash
HTTP/1.1 400 BAD_REQUEST
Content-Type: application/json;charset=UTF-8

{
  "code": "NRP",
  "message": "No exist Recruit Post."
}

```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "AF",
  "message": "Authentication fail."
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```
***
#### - 구인 게시글 수정하기
  
##### 설명

클라이언트는 요청 헤더에 Bearer 인증 토큰을 포함하여 요청하고 조회가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 유효성 실패, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **Patch**  
- URL : **/{recruitPostId}**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | Bearer 토큰 인증 헤더 | O |

###### Request Body
  
| name | type | description | required |
|---|:---:|:---:|:---:|
| recruit_post_title | String | 제목 | O |
| recruit_post_content | String | 내용 | O | 
| recruit_post_image | String | 이미지 | X |
| recruit_end_date | String | 마감일자 | O |
| min_people | Integer | 최소 인원 | O |

###### Example

```bash
curl -v -X PATCH "http://localhost:4000/api/v1/recruit/1" \
 -h "Authorization=Bearer XXXX" \
 -d "recruitPostTitle": "플로깅 할 사람 구합니다." \
 -d "recruitPostContent": "포항 영일대 해수욕장에서 플로깅 할 사람 구합니다." \
 -d "recruitEndDate": "2024-11-01" \
 -d "minPeople": 3 \
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |
  
###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success.",
}
```

**응답 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 : 실패 (존재하지 않는 사용자)**
```bash
HTTP/1.1 400 BAD_REQUEST
Content-Type: application/json;charset=UTF-8

{
  "code": "NI",
  "message": "No exist user id."
}

```

**응답 : 실패 (존재하지 않는 구인 게시글)**
```bash
HTTP/1.1 400 BAD_REQUEST
Content-Type: application/json;charset=UTF-8

{
  "code": "NRP",
  "message": "No exist Recruit Post."
}

```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "AF",
  "message": "Authentication fail."
}
```

**응답 : 실패 (권한 없음)**
```bash
HTTP/1.1 403 Forbidden
Content-Type: application/json;charset=UTF-8

{
  "code": "NP",
  "message": "No Permission"
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```
***
#### - 구인 게시글 마감 상태 바꾸기
  
##### 설명

클라이언트는 요청 헤더에 Bearer 인증 토큰을 포함하여 요청하고 조회가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 유효성 실패, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **Patch**  
- URL : **/iscompleted/{recruitPostId}**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | Bearer 토큰 인증 헤더 | O |

###### Request Body
  
| name | type | description | required |
|---|:---:|:---:|:---:|
| is_completed | Boolean | 마감 유무 | O |

###### Example

```bash
curl -v -X PATCH "http://localhost:4000/api/v1/recruit/1" \
 -h "Authorization=Bearer XXXX" \
 -d "isCompleted": true \
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |
  
###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success.",
}
```

**응답 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 : 실패 (존재하지 않는 사용자)**
```bash
HTTP/1.1 400 BAD_REQUEST
Content-Type: application/json;charset=UTF-8

{
  "code": "NI",
  "message": "No exist user id."
}

```

**응답 : 실패 (존재하지 않는 구인 게시글)**
```bash
HTTP/1.1 400 BAD_REQUEST
Content-Type: application/json;charset=UTF-8

{
  "code": "NRP",
  "message": "No exist Recruit Post."
}

```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "AF",
  "message": "Authentication fail."
}
```

**응답 : 실패 (권한 없음)**
```bash
HTTP/1.1 403 Forbidden
Content-Type: application/json;charset=UTF-8

{
  "code": "NP",
  "message": "No Permission"
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```

***

#### - 구인 게시글 삭제하기
  
##### 설명

클라이언트는 요청 헤더에 Bearer 인증 토큰을 포함하여 요청하고 조회가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 유효성 실패, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **Delete**  
- URL : **/{recruitPostId}**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | Bearer 토큰 인증 헤더 | O |

##### Example

```bash
curl -v -X DELETE "http://localhost:4000/api/v1/recruit/1" \
 -h "Authorization=Bearer XXXX" \
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |
  
###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success.",
}
```

**응답 : 실패 (존재하지 않는 사용자)**
```bash
HTTP/1.1 400 BAD_REQUEST
Content-Type: application/json;charset=UTF-8

{
  "code": "NI",
  "message": "No exist user id."
}

```

**응답 : 실패 (존재하지 않는 구인 게시글)**
```bash
HTTP/1.1 400 BAD_REQUEST
Content-Type: application/json;charset=UTF-8

{
  "code": "NRP",
  "message": "No exist Recruit Post."
}

```


**응답 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "AF",
  "message": "Authentication fail."
}
```

**응답 : 실패 (권한 없음)**
```bash
HTTP/1.1 403 Forbidden
Content-Type: application/json;charset=UTF-8

{
  "code": "NP",
  "message": "No Permission"
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```
***
#### - recruit 댓글 등록하기
  
##### 설명

클라이언트는 요청 헤더에 Bearer 인증 토큰을 포함하여 요청하고 조회가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 유효성 실패, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **Post**
- URL : **/{recruitPostId}/comments**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | Bearer 토큰 인증 헤더 | O |

###### Request Body

**RecruitReport**  
| name | type | description | required |
|---|:---:|:---:|:---:|
| recruit_comment_content | String | 댓글 내용 | O |

###### Example

```bash
curl -v -X POST "http://localhost:4000/api/v1/recruit/1/comments" \
 -h "Authorization=Bearer XXXX" \
 -d "recruitCommentContent": "지금 참여 가능할까요?" \
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success.",
}
```

**응답 : 실패 (존재하지 않는 사용자)**
```bash
HTTP/1.1 400 BAD_REQUEST
Content-Type: application/json;charset=UTF-8

{
  "code": "NI",
  "message": "No exist user id."
}

```

**응답 : 실패 (존재하지 않는 구인 게시글)**
```bash
HTTP/1.1 400 BAD_REQUEST
Content-Type: application/json;charset=UTF-8

{
  "code": "NRP",
  "message": "No exist Recruit Post."
}

```

**응답 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "AF",
  "message": "Authentication fail."
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```

***

#### - recruit 댓글 리스트 열람하기

##### 설명

클라이언트는 요청 후 조회가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 유효성 실패, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **GET**  
- URL : **/{recruitPostId}/comments**  

##### Request

###### Header

###### Example

```bash
curl -X GET "http://localhost:4000/api/v1/recruit/1/comments" \
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |
| recruitComments | recruitComment[] | 구인 게시글 댓글 리스트 | O |
  
**recruitComment**  
| name | type | description | required |
|---|:---:|:---:|:---:|
| recruit_comment_id | Integer | 댓글 아이디 | O |
| recruit_comment_writer | String | 작성자 | O |
| recruit_comment_created_at | String | 작성일 | O |
| recruit_comment_content | String | 내용 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success.",
  "": [
    {
      "recruitCommentId": 3,
      "recruitCommentWriter": "qwer1234",
      "recruitCommentCreatedAt": "2024-10-31 15:18:18",
      "recruitCommentContent": "지금 참여 가능할까요?"
    }
    ...
  ]
}
```

**응답 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "AF",
  "message": "Authentication fail."
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```
***
#### - 구인 게시글 댓글 수정하기
  
##### 설명

클라이언트는 요청 헤더에 Bearer 인증 토큰을 포함하여 요청하고 조회가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 유효성 실패, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **Patch**  
- URL : **/{recruitPostId}/comments/{recruitCommentId}**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | Bearer 토큰 인증 헤더 | O |

###### Request Body
  
| name | type | description | required |
|---|:---:|:---:|:---:|
| recruitCommentContent | String | 댓글 내용 | O |

###### Example

```bash
curl -v -X PATCH "http://localhost:4000/api/v1/recruit/1/comments/3" \
 -h "Authorization=Bearer XXXX" \
 -d "recruitCommentContent": "플로깅 해보고 싶어요." \
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |
  
###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success.",
}
```

**응답 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 : 실패 (존재하지 않는 구인 게시글)**
```bash
HTTP/1.1 400 BAD_REQUEST
Content-Type: application/json;charset=UTF-8

{
  "code": "NRP",
  "message": "No exist Recruit Post."
}

```

**응답 : 실패 (존재하지 않는 구인 게시글 댓글)**
```bash
HTTP/1.1 400 BAD_REQUEST
Content-Type: application/json;charset=UTF-8

{
  "code": "NRC",
  "message": "No exist recruit comment."
}

```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "AF",
  "message": "Authentication fail."
}
```

**응답 : 실패 (권한 없음)**
```bash
HTTP/1.1 403 Forbidden
Content-Type: application/json;charset=UTF-8

{
  "code": "NP",
  "message": "No Permission"
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```

***

#### - 구인 게시글 댓글 삭제하기
  
##### 설명

클라이언트는 요청 헤더에 Bearer 인증 토큰을 포함하여 요청하고 조회가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 유효성 실패, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **Delete**  
- URL : **/{recruitPostId}/comments/{recruitCommentId}**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | Bearer 토큰 인증 헤더 | O |

##### Example

```bash
curl -v -X DELETE "http://localhost:4000/api/v1/recruit/1/comments/3" \
 -h "Authorization=Bearer XXXX" \
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |
  
###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success.",
}
```

**응답 : 실패 (존재하지 않는 구인 게시글)**
```bash
HTTP/1.1 400 BAD_REQUEST
Content-Type: application/json;charset=UTF-8

{
  "code": "NRP",
  "message": "No exist Recruit Post."
}

```

**응답 : 실패 (존재하지 않는 구인 게시글 댓글)**
```bash
HTTP/1.1 400 BAD_REQUEST
Content-Type: application/json;charset=UTF-8

{
  "code": "NRC",
  "message": "No exist recruit comment."
}

```

**응답 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "AF",
  "message": "Authentication fail."
}
```

**응답 : 실패 (권한 없음)**
```bash
HTTP/1.1 403 Forbidden
Content-Type: application/json;charset=UTF-8

{
  "code": "NP",
  "message": "No Permission"
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```
***
#### - 지역별 recruit 게시글 개수 반환하기 

##### 설명

클라이언트는 요청 후 조회가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 유효성 실패, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **GET**  
- URL : **/cityPostCounts**  

##### Request

###### Header

###### Example

```bash
curl -X GET "http://localhost:4000/api/v1/recruit/cityPostCounts" \
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |
| city_counts | cityCount[] | 지역별 구인 게시글 개수 | O |
  
**recruit**  
| name | type | description | required |
|---|:---:|:---:|:---:|
| city | String | 도시 이름 | O |
| post_count | Integer | 구인 게시글 개수 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success.",
  "recruit": 
    {
      "city": "부산광역시",
      "postCount": 50,
    }
}
```

**응답 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "AF",
  "message": "Authentication fail."
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```
***
#### - recruit 조인 등록하기
  
##### 설명

클라이언트는 요청 헤더에 Bearer 인증 토큰을 포함하여 요청하고 조회가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 유효성 실패, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **Post**
- URL : **/join/{recruitId}** 

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | Bearer 토큰 인증 헤더 | O |

###### Example

```bash
curl -v -X POST "http://localhost:4000/api/v1/recruit/join/1" \
 -h "Authorization=Bearer XXXX" \
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success.",
}
```

**응답 : 실패 (존재하지 않는 사용자)**
```bash
HTTP/1.1 400 BAD_REQUEST
Content-Type: application/json;charset=UTF-8

{
  "code": "NI",
  "message": "No exist user id."
}

```

**응답 : 실패 (존재하지 않는 구인 게시글)**
```bash
HTTP/1.1 400 BAD_REQUEST
Content-Type: application/json;charset=UTF-8

{
  "code": "NRP",
  "message": "No exist Recruit Post."
}

```

**응답 : 실패 (본인일 경우 제외)**
```bash
HTTP/1.1 400 BAD_REQUEST
Content-Type: application/json;charset=UTF-8

{
  "code": "NRP",
  "message": "No self participation."
}

```

**응답 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "AF",
  "message": "Authentication fail."
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```

***

#### - recruit 조인 가져오기

##### 설명

클라이언트는 요청 후 조회가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 유효성 실패, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **GET**  
- URL : **/join/{recruitId}**  

##### Request

###### Header

###### Example

```bash
curl -X GET "http://localhost:4000/api/v1/recruit/join/1" \
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |
| simpleUsers | simpleUser[] | 조인된 유저 리스트 | O |
  
**simpleUser**  
| name | type | description | required |
|---|:---:|:---:|:---:|
| user_id | String | 유저 아이디 | O |
| profile_image | String | 프로필 이미지 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success.",
  "": [
    {
      "userId": "qwer1234",
      "profileImage": "http://..."
    }
    ...
  ]
}
```

**응답 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "AF",
  "message": "Authentication fail."
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```
***

<h2 style='background-color: rgba(55, 55, 55, 0.2); text-align: center'>RecruitLike 모듈</h2>

Plogger 서비스의 RecruitLike와 관련된 REST API 모듈입니다.  
구인 게시글 좋아요 생성(취소), 조회 API가 포함되어 있습니다.  
RecruitLike 모듈은 모두 인증이 필요합니다.  
  
- url : /api/v1/recruit/like

#### - 구인게시글 좋아요 생성
  
##### 설명

클라이언트는 요청 헤더에 Bearer 인증 토큰을 포함하여 요청하고 조회가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **POST**  
- URL : **/{recruitId}**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | Bearer 토큰 인증 헤더 | O |

###### Example

```bash
curl -X POST "http://localhost:4000/api/v1/recruit/like/1" \
 -h "Authorization=Bearer XXXX"

```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success."
}

```

**응답 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 : 실패 (존재하지 않는 게시글)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "NRP",
  "message": "No exist recruit Post"
}
```

**응답 : 실패 (존재하지 않는 유저)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "NU",
  "message": "No exist User Id"
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "AF",
  "message": "Authentication fail."
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```
***
#### - 구인 게시글 좋아요 조회하기
  
##### 설명

클라이언트는 요청 헤더에 Bearer 인증 토큰을 포함하여 요청하고 조회가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 유효성 실패, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **GET**  
- URL : **/{recruitId}**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | Bearer 토큰 인증 헤더 | O |

##### Example

```bash
curl -v -X GET "http://localhost:4000/api/v1/recruit/like/1" \
 -h "Authorization=Bearer XXXX" \
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |
  
###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success."
}

```

**응답 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 : 실패 (존재하지 않는 게시글)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "NRP",
  "message": "No exist recruit Post"
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "AF",
  "message": "Authentication fail."
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```
***

<h2 style='background-color: rgba(55, 55, 55, 0.2); text-align: center'>Scrap 모듈</h2>

Plogger 서비스의 RecruitScrap와 관련된 REST API 모듈입니다.  
구인 게시글 스크랩 생성,조회,삭제 API가 포함되어 있습니다.  
RecruitScrap 모듈은 모두 인증이 필요합니다.  
  
- url : /api/v1/recruit/scrap

#### - 구인 게시글 스크랩 생성
  
##### 설명

클라이언트는 요청 헤더에 Bearer 인증 토큰을 포함하여 요청하고 조회가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **POST**  
- URL : **/{recruitId}**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | Bearer 토큰 인증 헤더 | O |

###### Example

```bash
curl -X POST "http://localhost:4000/api/v1/recruit/scrap/1" \
 -h "Authorization=Bearer XXXX"

```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success."
}

```

**응답 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 : 실패 (존재하지 않는 게시글)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "NRP",
  "message": "No exist recruit Post"
}
```

**응답 : 실패 (존재하지 않는 유저)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "NU",
  "message": "No exist User Id"
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "AF",
  "message": "Authentication fail."
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```

***
#### - 구인 게시글 스크랩 조회하기
  
##### 설명

클라이언트는 요청 헤더에 Bearer 인증 토큰을 포함하여 요청하고 조회가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 유효성 실패, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **GET**  
- URL : **/{recruitId}**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | Bearer 토큰 인증 헤더 | O |

###### Example

```bash
curl -X GET "http://localhost:4000/api/v1/recruit/scrap/1" \
 -h "Authorization=Bearer XXXX"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O | 

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success."
}
```

**응답 : 실패 (존재하지 않는 게시글)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "NRP",
  "message": "No exist recruit Post"
}
```

**응답 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "AF",
  "message": "Authentication fail."
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```

***

#### report 내역

<h2 style='background-color: rgba(55, 55, 55, 0.2); text-align: center'>report 모듈</h2>

Plogger 서비스의 구인게시판 신고와 관련된 REST API 모듈입니다.  
구인 게시글 신고 등록 및 열람 등의 API가 포함되어 있습니다.  
report 모듈은 모두 인증이 필요합니다.  
  
- url : /api/v1/report

***

#### - recruit report 내역 등록하기
  
##### 설명

클라이언트는 요청 헤더에 Bearer 인증 토큰을 포함하여 요청하고 조회가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 유효성 실패, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **Post**  
- URL : **/recruit**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | Bearer 토큰 인증 헤더 | O |

###### Request Body

**RecruitReport**  
| name | type | description | required |
|---|:---:|:---:|:---:|
| content | text | 내용 | O |

###### Example

```bash
curl -v -X POST "http://localhost:4000/api/v1/report/recruit" \
 -h "Authorization=Bearer XXXX" \
 -d "content": "게시판 성격과 맞지 않는 글입니다."

```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success."
}
```

**응답 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 : 실패 (중복된 신고)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "DR",
  "message": "Duplicated Report."
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "AF",
  "message": "Authentication fail."
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```

***

#### - recruit report 내역 열람하기

##### 설명

클라이언트는 요청 헤더에 Bearer 인증 토큰을 포함하여 요청하고 조회가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 유효성 실패, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **GET**  
- URL : **/recruit**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | Bearer 토큰 인증 헤더 | O |

###### Example

```bash
curl -X GET "http://localhost:4000/api/v1/report/recruit" \
 -h "Authorization=Bearer XXXX"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |
| reports | report[] | 신고 내역 리스트 | O |
  
**recruitreport**  
| name | type | description | required |
|---|:---:|:---:|:---:|
| report_id | Integer | 신고 아이디 | O |
| user_id | String | 유저 아이디 | O |
| recruit_id | Integer | 구인 게시글 아이디 | O |
| content | text | 내용 | O |
| created_at | String | 신고한 날짜 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success.",
  "": [
    {
      "reportId": 1,
      "userId": "qwer1234",
      "recruitId": 1,
      "content": "게시판 성격과 맞지 않는 글입니다.",
      "createdAt": "2024-11-18"
    }
    ...
  ]
}
```

**응답 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "AF",
  "message": "Authentication fail."
}
```

**응답 : 실패 (존재하지 않는 게시글)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "NRP",
  "message": "No exist recruit Post"
}
```

**응답 : 실패 (존재하지 않는 사용자)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "NU",
  "message": "No exist user."
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```

***

##### 활동 게시판

<h2 style='background-color: rgba(55, 55, 55, 0.2); text-align: center'>Active 모듈</h2>

Plogger 서비스의 활동 게시판과 관련된 REST API 모듈입니다.  
활동 게시글 목록 보기, 게시글 열람 및 수정, 작성 등의 API가 포함되어 있습니다.  
Active 모듈은 모두 인증이 필요합니다.  
  
- url : /api/v1/active  

***

#### - 활동 게시글 목록 보기
  
##### 설명

클라이언트는 요청 헤더에 Bearer 인증 토큰을 포함하여 요청하고 조회가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **GET**  
- URL : **/**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | Bearer 토큰 인증 헤더 | O |

###### Example

```bash
curl -X GET "http://localhost:4000/api/v1/active" \
 -h "Authorization=Bearer XXXX"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |
| activePosts | ActivePost[] | 게시글 리스트 | O |
  
**ActivePost**  
| name | type | description | required |
|---|:---:|:---:|:---:|
| active_post_id | Integer | 활동 게시글 번호 | O |
| active_post_title | String | 활동 게시글 제목 | O |
| active_post_writer_id | String | 작성자 | O |
| active_post_image | String | 이미지 | X | 
| active_post_content | String | 내용 | O |
| active_location | String | 활동할 위치(위도, 경도) | O |
| active_address | String | 활동할 주소 | O |
| active_post_created_at | String | 작성날짜 | O |
| active_start_date | String | 활동 시작날짜 | O |
| active_end_date | String | 활동 종료날짜 | O |
| active_view | Integer | 조회수 | O |
| active_post_like | Integer | 좋아요 수 | O |
| acitve_report | Integer | 신고 수 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success.",
  "activePosts": [
    {
      "activePostId": 1,
      "activePostTitle": "플로깅 같이 하실분 모집합니다.",
      "activePostWriterId": "qwer1234",
      "activePostImage": "http://~~~~",
      "activePostContent": "서면 쪽에서 플로깅 같이 하실분 참여버튼 눌러주세요.",
      "activeLocation": "35.15517336356195, 129.05808341790654",
      "activeAddress": "부산시 부산진구 부전동",
      "activePostCreatedAt": "2024-10-20",
      "activeStartDate": "2024-10-22",
      "activeEndDate": "2024-10-22",
      "activeView": 342,
      "activePostLike": 16,
      "activeReport": 2
    },
    ...
  ]
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "AF",
  "message": "Authentication fail."
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```
***

#### - 활동 게시글 등록하기
  
##### 설명

클라이언트는 요청 헤더에 Bearer 인증 토큰을 포함하여 요청하고 조회가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 유효성 실패, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **POST**  
- URL : **/**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | Bearer 토큰 인증 헤더 | O |

###### Request Body

**PostActivePost**  
| name | type | description | required |
|---|:---:|:---:|:---:|
| active_post_title | String | 활동 게시글 제목 | O |
| active_post_image | text | 이미지 | X |
| active_post_content | String | 내용 | O |
| active_start_date | String | 활동 시작일 | O |
| active_end_date | String | 활동 종료일 | O |
| active_location | String | 위치(위도, 경도) | O |
| active_address | String | 주소 | O |
| active_people | String[] | 태그할 인원 | O |

###### Example

```bash
curl -v -X POST "http://localhost:4000/api/v1/active" \
 -h "Authorization=Bearer XXXX" \
 -d "activePostTitle": "플로깅 후기" \
 -d "activePostImage": "http://~~~~~~~~~~~" \
 -d "activePostContent": "이번에 참여한 플로깅 후기 올립니다!" \
 -d "activeStartDate" : "2024-10-18" \
 -d "activeEndDate": "2024-10-20" \
 -d "activeLocation": "35.15517336356195, 129.05808341790654" \
 -d "activeAddress": "부산시 부산진구 부전동" \
 -d "activePeople" : [ "qwer1111", "qwer3333" ]
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success.",
}
```

**응답 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "AF",
  "message": "Authentication fail."
}
```

**응답 : 실패 (존재하지 않는 사용자)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "NU",
  "message": "No exist user."
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```

***

#### - 내 구인 게시글 불러오기
  
##### 설명

클라이언트는 요청 헤더에 Bearer 인증 토큰을 포함하여 요청하고 조회가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 유효성 실패, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **GET**  
- URL : ***/my-recruits**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | Bearer 토큰 인증 헤더 | O |

###### Example

```bash
curl -X GET "http://localhost:4000/api/v1/active/call" \
 -h "Authorization=Bearer XXXX"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |
| recruits | recruit[] | 게시글 리스트 | O |
  
**MyRecruit**  
| name | type | description | required |
|---|:---:|:---:|:---:|
| recruit_post_id | Integer | 구인 게시판 아이디 | O |
| recruit_post_title | String | 구인 게시글 제목 | O |
| recruit_location | String | 구인 게시글 위치(위도, 경도) | O |
| recruit_address | String | 구인 게시글 주소 | O |
| recruit_join_people | String[] | 구인게시글 참여 인원 | X |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success.",
  "myRecruits": [
    {
      "recruitPostId": 1,
      "recruitPostTitle": "플로깅 같이 하실분 모집합니다.",
      "recruitLocation": "35.15517336356195, 129.05808341790654",
      "recruitAddress": "부산광역시 부산진구 부전동",
      "recruitJoinPeople": [ "qwer1111", "qwer3333" ]
    },
    ...
  ]
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "AF",
  "message": "Authentication fail."
}
```

**응답 : 실패 (존재하지 않는 사용자)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "NU",
  "message": "No exist user."
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```
***

#### - 활동 게시글 불러오기
  
##### 설명

클라이언트는 요청 헤더에 Bearer 인증 토큰을 포함하여 요청하고 조회가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 유효성 실패, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **GET**  
- URL : **/{activePostId}**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | Bearer 토큰 인증 헤더 | O |

###### Example

```bash
curl -X GET "http://localhost:4000/api/v1/active/1" \
 -h "Authorization=Bearer XXXX"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |
| active_post_id | Integer | 활동 게시글 번호 | O |
| active_post_title | String | 활동 게시글 제목 | O |
| active_post_writer_id | String | 작성자 | O |
| active_post_image | text | 이미지 | X |
| active_post_content | String| 내용 | O |
| active_location | String | 위치 정보 | O |
| active_post_created_at | String | 작성날짜 | O |
| active_start_date | String | 활동 시작일 | O |
| active_end_date | String | 활동 종료일 | O |
| active_view | Integer | 조회수 | O |
| active_post_like | Integer | 좋아요 수 | O |
| active_report | Integer | 신고수 | O |
| recruit_id | Integer | 활동 게시글 번호 | O |
| active_people | String[] | 태그 인원 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success.",
  "activePostId": 1,
  "activePostTitle": "플로깅 후기",
  "activePostWriterId": "qwer1234",
  "activePostImage": "http://~~~~~~~~~",
  "activePostContent": "내용내용내용내용내용내용내용내용내용",
  "activeLocation": "부산광역시 부산진구 부전동",
  "activePostCreatedAt": "2024-10-10",
  "activeStartDate" : "2024-10-18",
  "activeEndDate": "2024-10-20",
  "activeView": 342,
  "activePostLike": 16,
  "activeReport": 3,
  "recruitId" 1,
  "activePeople": [ "qwer1111", "qwer3333" ]
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "AF",
  "message": "Authentication fail."
}
```

**응답 : 실패 (존재하지 않는 게시글)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "NAP",
  "message": "No exist active Post"
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```

***

#### - 활동 게시글 수정하기
  
##### 설명

클라이언트는 요청 헤더에 Bearer 인증 토큰을 포함하여 요청하고 조회가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 유효성 실패, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **PATCH**  
- URL : **/{activePostId}**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | Bearer 토큰 인증 헤더 | O |

###### Request Body

**PatchActivePost**  
| name | type | description | required |
|---|:---:|:---:|:---:|
| active_post_title | String | 활동 게시글 제목 | O |
| active_post_image | String | 이미지 | X |
| active_post_content | String | 내용 | O |
| active_start_date | String | 활동 시작일 | O |
| active_end_date | String | 활동 종료일 | O |
| actvive_people | String[] | 태그할 인원 | O |

###### Example

```bash
curl -v -X PATCH "http://localhost:4000/api/v1/active/1" \
 -h "Authorization=Bearer XXXX" \
 -d "activePostTitle": "플로깅 후기 올립니다." \
 -d "activePostImage": "http://~~~~~~~~~~" \
 -d "activePostContent": "이번에 참여한 플로깅 후기 올려봅니다ㅎㅎ" \
 -d "activeStartDate" : "2024-10-18" \
 -d "activeEndDate": "2024-10-20" \
 -d "activePeople": [ "qwer1111", "qwer2222" ]
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |
  
###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success.",
}
```

**응답 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "AF",
  "message": "Authentication fail."
}
```

**응답 : 실패 (존재하지 않는 사용자)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "NU",
  "message": "No exist user."
}
```

**응답 : 실패 (존재하지 않는 게시글)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "NAP",
  "message": "No exist active Post"
}
```

**응답 : 실패 (권한 없음)**
```bash
HTTP/1.1 403 Forbidden
Content-Type: application/json;charset=UTF-8

{
  "code": "NP",
  "message": "No permission."
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```

***

#### - 활동 게시글 삭제하기
  
##### 설명

클라이언트는 요청 헤더에 Bearer 인증 토큰을 포함하여 요청하고 조회가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 유효성 실패, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **DELETE**  
- URL : **/{activePostId}**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | Bearer 토큰 인증 헤더 | O |

##### Example

```bash
curl -v -X DELETE "http://localhost:4000/api/v1/active/1" \
 -h "Authorization=Bearer XXXX" \
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |
  
###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success.",
}
```

**응답 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 : 실패 (존재하지 않는 사용자)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "NU",
  "message": "No exist user."
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "AF",
  "message": "Authentication fail."
}
```

**응답 : 실패 (존재하지 않는 게시글)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "NAP",
  "message": "No exist active Post"
}
```

**응답 : 실패 (권한 없음)**
```bash
HTTP/1.1 403 Forbidden
Content-Type: application/json;charset=UTF-8

{
  "code": "NP",
  "message": "No permission."
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```
***
#### - 활동 게시글 댓글 목록 보기
  
##### 설명

클라이언트는 요청 헤더에 Bearer 인증 토큰을 포함하여 요청하고 조회가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **GET**  
- URL : **/{activePostId}/comments**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | Bearer 토큰 인증 헤더 | O |

###### Example

```bash
curl -X GET "http://localhost:4000/api/v1/active" \
 -h "Authorization=Bearer XXXX"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |
| activeComments | ActiveComment[] | 게시글 리스트 | O |
  
**ActiveComment**  
| name | type | description | required |
|---|:---:|:---:|:---:|
| active_comment_id | Integer | 게시글 댓글 번호 | O |
| active_comment_writer | String | 게시글 댓글 작성자 | O |
| active_comment_created_at | String | 게시글 댓글 작성날짜  | O |
| active_comment_content | String | 게시글 댓글 내용 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success.",
  "activeComments": [
    {
      "activeCommentId": 1,
      "activeCommentWriter": "qwer1234",
      "activeCommentCreatedAt": "2024-10-20",
      "activeCommentContent": "저도 참여해보고싶네요ㅎㅎ"
    },
    ...
  ]
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "AF",
  "message": "Authentication fail."
}
```

**응답 : 실패 (존재하지 않는 게시글)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "NAP",
  "message": "No exist active Post"
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```
***

#### - 활동 게시글 댓글 작성하기
  
##### 설명

클라이언트는 요청 헤더에 Bearer 인증 토큰을 포함하여 요청하고 조회가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 유효성 실패, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **POST**  
- URL : **/{activePostId}/comments**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | Bearer 토큰 인증 헤더 | O |

###### Request Body

**PostActiveComment**  
| name | type | description | required |
|---|:---:|:---:|:---:|
| active_comment_content | String | 게시글 댓글 내용 | O |

###### Example

```bash
curl -v -X POST "http://localhost:4000/api/v1/active" \
 -h "Authorization=Bearer XXXX" \
 -d "activeCommentContent": "저도 다음번엔 참여하고싶어요!" \
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success.",
}
```

**응답 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "AF",
  "message": "Authentication fail."
}
```

**응답 : 실패 (존재하지 않는 사용자)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "NU",
  "message": "No exist user."
}
```

**응답 : 실패 (존재하지 않는 게시글)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "NAP",
  "message": "No exist active Post"
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```
***
#### - 활동 게시글 댓글 수정하기
  
##### 설명

클라이언트는 요청 헤더에 Bearer 인증 토큰을 포함하여 요청하고 조회가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 유효성 실패, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **POST**  
- URL : **/{activePostId}/comments/{activeCommentId}**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | Bearer 토큰 인증 헤더 | O |

###### Request Body

**PatchActiveComment**  
| name | type | description | required |
|---|:---:|:---:|:---:|
| active_comment_content | String | 게시글 댓글 내용 | O |

###### Example

```bash
curl -v -X PATCH "http://localhost:4000/api/v1/active" \
 -h "Authorization=Bearer XXXX" \
 -d "activeCommentContent": "저도 다음번엔 꼭꼭 참여하고싶어요!" \
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success.",
}
```

**응답 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "AF",
  "message": "Authentication fail."
}
```

**응답 : 실패 (존재하지 않는 게시글)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "NAP",
  "message": "No exist active post."
}
```

**응답 : 실패 (존재하지 않는 댓글)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "NAC",
  "message": "No exist active comment."
}
```

**응답 : 실패 (권한 없음)**
```bash
HTTP/1.1 403 Forbidden
Content-Type: application/json;charset=UTF-8

{
  "code": "NP",
  "message": "No permission."
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```
***
#### - 활동 게시글 댓글 삭제하기
  
##### 설명

클라이언트는 요청 헤더에 Bearer 인증 토큰을 포함하여 요청하고 조회가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 유효성 실패, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **DELETE**  
- URL : **/{activePostId}/comments/{activeCommentId}**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | Bearer 토큰 인증 헤더 | O |

##### Example

```bash
curl -v -X DELETE "http://localhost:4000/api/v1/active/1" \
 -h "Authorization=Bearer XXXX" \
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |
  
###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success.",
}
```

**응답 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 : 실패 (존재하지 않는 사용자)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "NU",
  "message": "No exist user."
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "AF",
  "message": "Authentication fail."
}
```

**응답 : 실패 (존재하지 않는 게시글)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "NAP",
  "message": "No exist active Post"
}
```

**응답 : 실패 (권한 없음)**
```bash
HTTP/1.1 403 Forbidden
Content-Type: application/json;charset=UTF-8

{
  "code": "NP",
  "message": "No permission."
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```
***
#### - 활동게시글 좋아요 누르기 (생성, 삭제)
  
##### 설명

클라이언트는 요청 헤더에 Bearer 인증 토큰을 포함하여 요청하고 조회가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **POST**  
- URL : **/like/{activeId}**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | Bearer 토큰 인증 헤더 | O |

###### Example

```bash
curl -X POST "http://localhost:4000/api/v1/active/like/1" \
 -h "Authorization=Bearer XXXX"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |

###### Example

**응답 성공 (좋아요 누르기)**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "LC",
  "message": "Like click."
}
```

**응답 성공 (좋아요 취소하기)**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "LUC",
  "message": "Like unclick."
}
```

**응답 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "AF",
  "message": "Authentication fail."
}
```

**응답 : 실패 (존재하지 않는 사용자)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "NU",
  "message": "No exist user."
}
```

**응답 : 실패 (존재하지 않는 게시글)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "NAP",
  "message": "No exist active Post"
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```
***
#### - 활동 게시글 좋아요 가져오기
  
##### 설명

클라이언트는 요청 헤더에 Bearer 인증 토큰을 포함하여 요청하고 조회가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 유효성 실패, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **GET**  
- URL : **/like/{activeId}**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | Bearer 토큰 인증 헤더 | O |

##### Example

```bash
curl -v -X GET "http://localhost:4000/api/v1/active/like/1" \
 -h "Authorization=Bearer XXXX" \
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |
| activeLikes | activeLike[] | 해당 게시글의 좋아요 유저 리스트 | O |

**ActiveLike**  
| name | type | description | required |
|---|:---:|:---:|:---:|
| user_id | String | 유저 아이디 | O |
| active_id | Integer | 활동 게시판 번호 | O |
| craete_at | String | 좋아요 누른 날짜 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success.",
  "activeLikes": [
    {
      "userId: "qwer1234",
      "activeId: 1,
      "createdAt: "2024-10-10"
    },
    ...
  ]
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "AF",
  "message": "Authentication fail."
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```
***
#### - 태그 등록하기
  
##### 설명

클라이언트는 요청 헤더에 Bearer 인증 토큰을 포함하여 요청하고 조회가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 유효성 실패, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **POST**  
- URL : **/tag/{activeId}/{recruitId}**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | Bearer 토큰 인증 헤더 | O |

###### Request Body

**PostActiveTag**  
| name | type | description | required |
|---|:---:|:---:|:---:|
| tag_id | String | 태그할 유저의 아이디 | O |

###### Example

```bash
curl -v -X POST "http://localhost:4000/api/v1/active/tag/1/1" \
 -h "Authorization=Bearer XXXX" \
 -d "tagId": "qwer1234"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |1

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success.",
}
```

**응답 실패 (자기 자신을 태그함)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "NST",
  "message": "No self tag."
}
```

**응답 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "AF",
  "message": "Authentication fail."
}
```

**응답 : 실패 (존재하지 않는 게시글)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "NAP",
  "message": "No exist Active Post."
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```

***

#### - 태그 삭제하기
  
##### 설명

클라이언트는 요청 헤더에 Bearer 인증 토큰을 포함하여 요청하고 조회가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 유효성 실패, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **DELETE**  
- URL : **/tag/{activeId}/{recruitId}/{tagId}**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | Bearer 토큰 인증 헤더 | O |

###### Example

```bash
curl -v -X DELETE "http://localhost:4000/api/v1/active/tag/1/1/qwer1234" \
 -h "Authorization=Bearer XXXX" \
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |
  
###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success.",
}
```

**응답 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "AF",
  "message": "Authentication fail."
}
```

**응답 : 실패 (존재하지 않는 활동 게시글)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "NAP",
  "message": "No exist Active Post."
}
```

**응답 : 실패 (존재하지 않는 구인 게시글)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "NRP",
  "message": "No exist Recruit Post."
}
```

**응답 : 실패 (존재하지 않는 사용자)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "NU",
  "message": "No exist user."
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```
***
#### report 내역

<h2 style='background-color: rgba(55, 55, 55, 0.2); text-align: center'>report 모듈</h2>

Plogger 서비스의 활동게시판 신고와 관련된 REST API 모듈입니다.  
활동 게시글 신고 등록 및 열람 등의 API가 포함되어 있습니다.  
report 모듈은 모두 인증이 필요합니다.  
  
- url : /api/v1/report

***
#### - active report 내역 등록하기
  
##### 설명

클라이언트는 요청 헤더에 Bearer 인증 토큰을 포함하여 요청하고 조회가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 유효성 실패, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **Post**  
- URL : **/active**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | Bearer 토큰 인증 헤더 | O |

###### Request Body

**activeReport**  
| name | type | description | required |
|---|:---:|:---:|:---:|
| content | text | 내용 | O |

###### Example

```bash
curl -v -X POST "http://localhost:4000/api/v1/report/active" \
 -h "Authorization=Bearer XXXX" \
 -d "content": "게시판 성격과 맞지 않는 글입니다."

```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success.",
}
```

**응답 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 : 실패 (중복된 신고)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "DR",
  "message": "Duplicated Report."
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "AF",
  "message": "Authentication fail."
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```

***

#### - active report 내역 열람하기

##### 설명

클라이언트는 요청 헤더에 Bearer 인증 토큰을 포함하여 요청하고 조회가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 유효성 실패, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **GET**  
- URL : **/active**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | Bearer 토큰 인증 헤더 | O |

###### Example

```bash
curl -X GET "http://localhost:4000/api/v1/mileage/qwer1234" \
 -h "Authorization=Bearer XXXX"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |
| reports | report[] | 신고 내역 리스트 | O |
  
**activereport**  
| name | type | description | required |
|---|:---:|:---:|:---:|
| report_id | Integer | 신고 아이디 | O |
| user_id | String | 유저 아이디 | O |
| active_id | Integer | 구인 게시글 아이디 | O |
| content | text | 내용 | O |
| created_at | String | 신고한 날짜 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success.",
  "": [
    {
      "reportId": 1,
      "userId": "qwer1234",
      "activeId": 1,
      "content": "게시판 성격과 맞지 않는 글입니다.",
      "createdAt": "2024-11-18"
    }
    ...
  ]
}
```

**응답 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "AF",
  "message": "Authentication fail."
}
```

**응답 : 실패 (존재하지 않는 게시글)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "NAP",
  "message": "No exist active Post"
}
```

**응답 : 실패 (존재하지 않는 사용자)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "NU",
  "message": "No exist user."
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```
***
#### Q&A 게시판

<h2 style='background-color: rgba(55, 55, 55, 0.2); text-align: center'>Q&A 모듈</h2>

Plogger 서비스의 Q&A 게시판과 관련된 REST API 모듈입니다.  
Q&A 게시글 목록 보기, 게시글 열람 및 수정, 작성, 삭제, Q&A 게시글 댓글 등록,수정, 삭제, 조회 등의 API가 포함되어 있습니다.  
Q&A 모듈은 모두 인증이 필요합니다.  
  
- url : /api/v1/qna  

***

#### - Q&A 게시글 목록 보기
  
##### 설명

클라이언트는 요청 헤더에 Bearer 인증 토큰을 포함하여 요청하고 조회가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **GET**  
- URL : **/**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | Bearer 토큰 인증 헤더 | O |

###### Example

```bash
curl -X GET "http://localhost:4000/api/v1/qna" \
 -h "Authorization=Bearer XXXX"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |
| qnas | qna[] | 게시글 리스트 | O |
  
**Q&A**  
| name | type | description | required |
|---|:---:|:---:|:---:|
| qna_post_id | Integer | Q&A 게시글 번호 | O |
| qna_post_title | String | Q&A 게시글 제목 | O |
| qna_post_writer | String | 작성자 | O |
| qna_post_created_at | String | 작성날짜 | O |
| is_pinned | Boolean | 상단 고정 여부 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success.",
  "qnas": [
    {
      "qnaPostId": 1,
      "qnaPostTitle": "플로깅 뜻",
      "qnaPostWriter": "qwer1234",
      "qnaPostCreatedAt": "2024-10-11",
      "isPinned": false
    },
    ...
  ]
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "AF",
  "message": "Authentication fail."
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```
***

#### - Q&A 게시글 등록하기
  
##### 설명

클라이언트는 요청 헤더에 Bearer 인증 토큰을 포함하여 요청하고 조회가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 유효성 실패, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **Post**  
- URL : **/**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | Bearer 토큰 인증 헤더 | O |

###### Request Body

**Q&A**  
| name | type | description | required |
|---|:---:|:---:|:---:|
| qna_post_title | String | qna 게시글 제목 | O |
| qna_post_writer | String | 작성자 | O |
| qna_post_content | String | 작성 내용 | O |
| qna_post_image | text | 이미지 | X |
| is_pinned | Boolean | 상단 고정 여부 | O |

###### Example

```bash
curl -v -X POST "http://localhost:4000/api/v1/qna" \
 -h "Authorization=Bearer XXXX" \
 -d "qnaPostTitle": "플로깅 뜻" \
 -d "qnaPostWriter": "홍길동" \
 -d "qnaPostContent": "플로깅이 정확히 무엇인가요?" \
 -d "qnaPostImage": "http:~~~" \
 -d "isPinned": false
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success.",
}
```

**응답 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 : 실패 (존재하지 않는 사용자)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "NU",
  "message": "No exist user."
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "AF",
  "message": "Authentication fail."
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```

***

#### - Q&A 게시글 열람하기
  
##### 설명

클라이언트는 요청 헤더에 Bearer 인증 토큰을 포함하여 요청하고 조회가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 유효성 실패, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **GET**  
- URL : **/{qnaPostId}**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | Bearer 토큰 인증 헤더 | O |

###### Example

```bash
curl -X GET "http://localhost:4000/api/v1/qna/1" \
 -h "Authorization=Bearer XXXX"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |
  
**Q&A**  
| name | type | description | required |
|---|:---:|:---:|:---:|
| qna_post_id | Integer | qna 게시글 번호 | O |
| qna_post_title | String | qna 게시글 제목 | O |
| qna_post_writer | String | 작성자 | O |
| qna_post_image | text | 이미지 | X |
| qna_post_content | String| 내용 | O |
| qna_post_created_at | String | 작성날짜 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success.",
  "": [
    {
      "qnaPostId": 1,
      "qnaPostTitle": "플로깅 뜻",
      "qnaPostWriter": "홍길동",
      "qnaPostContent": "플로깅이 정확히 무엇인가요?",
      "qnaPostCreatedAt": "2024-10-10"    
      }
    ...
  ]
}
```

**응답 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "AF",
  "message": "Authentication fail."
}
```

**응답 : 실패 (존재하지 않는 게시글)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "NQP",
  "message": "No exist qna Post"
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```

***

#### - Q&A 게시글 수정하기
  
##### 설명

클라이언트는 요청 헤더에 Bearer 인증 토큰을 포함하여 요청하고 조회가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 유효성 실패, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **Patch**  
- URL : **/{qnaPostId}/update**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | Bearer 토큰 인증 헤더 | O |

###### Request Body

**Q&A**  
| name | type | description | required |
|---|:---:|:---:|:---:|
| qna_post_id | Integer | qna 게시글 번호 | O |
| qna_post_title | String | qna 게시글 제목 | O |
| qna_post_image | text | 이미지 | X |
| qna_post_content | String | 내용 | O |
| qna_post_created_at | String | 작성날짜 | O |
| is_pinned | Boolean | 상단 고정 여부 | O |


###### Example

```bash
curl -v -X PATCH "http://localhost:4000/api/v1/qna/1/update" \
 -h "Authorization=Bearer XXXX" \
 -d "qnaPostId": 1 \
 -d "qnaPostTitle": "플로깅 뜻" \
 -d "qnaPostContent": "플로깅 어떻게 참여하나요?" \
 -d "qnaPostImage" : "http:~~"\
 -d "qnaPostCreatedAt": "2024-10-20" \
 -d "isPinned": true
```


##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |
  
###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success.",
}
```

**응답 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "AF",
  "message": "Authentication fail."
}
```

**응답 : 실패 (존재하지 않는 사용자)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "NU",
  "message": "No exist user."
}
```

**응답 : 실패 (존재하지 않는 게시글)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "NQP",
  "message": "No exist qna Post"
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```

***

#### - Q&A 게시글 삭제하기
  
##### 설명

클라이언트는 요청 헤더에 Bearer 인증 토큰을 포함하여 요청하고 조회가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 유효성 실패, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **Delete**  
- URL : **/{qnaPostId}**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | Bearer 토큰 인증 헤더 | O |

##### Example

```bash
curl -v -X DELETE "http://localhost:4000/api/v1/qna/1" \
 -h "Authorization=Bearer XXXX" \
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |
  
###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success.",
}
```

**응답 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 : 실패 (존재하지 않는 게시글)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "NQP",
  "message": "No exist qna Post"
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "AF",
  "message": "Authentication fail."
}
```

**응답 : 실패 (권한 없음)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "NP",
  "message": "No Permission."
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```
***
#### - Q&A 게시판 댓글 생성
  
##### 설명

클라이언트는 요청 헤더에 Bearer 인증 토큰을 포함하여 요청하고 조회가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **POST**  
- URL : **/{qnaPostId}/comments**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | Bearer 토큰 인증 헤더 | O |

###### Request Body

| name | description | required |
|---|:---:|:---:|
| qna_comment_content | qna 게시글 댓글 내용 | O |
###### Example

```bash
curl -X POST "http://localhost:4000/api/v1/qna/1/comments" \
 -h "Authorization=Bearer XXXX"
 -d "qnaCommentContent=멋져요" \
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success."
}

```
**응답 실패 (존재하지 않는 아이디)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "NU",
  "message": "No exist user."
}
```

**응답 실패 (존재하지 않는 게시글)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "NQP",
  "message": "No exist qna post."
}
```

**응답 : 실패 (권한 없음)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "NP",
  "message": "No Permission."
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "AF",
  "message": "Authentication fail."
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```
***
#### - Q&A게시글 댓글 리스트 조회하기
  
##### 설명

클라이언트는 요청 헤더에 Bearer 인증 토큰을 포함하여 요청하고 조회가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 유효성 실패, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **GET**  
- URL : **/{qnaPostId}/comments**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | Bearer 토큰 인증 헤더 | O |

###### Example

```bash
curl -X GET "http://localhost:4000/api/v1/qna/1/comments" \
 -h "Authorization=Bearer XXXX"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |
| qnaComments | qnaComment[] | 댓글 리스트 | O |  
  
**qnaComments**  
| name | type | description | required |
|---|:---:|:---:|:---:|
| qna_comment_writer | String | qna게시글 댓글 작성자 | O |
| qna_comment_content | String | 내용 | O |
| qna_comment_created_at | String | 댓글 작성날짜 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success.",
  "qnaComments": [
    {
      "qnaCommentWriter=qwer1234",
      "qnaCommentContent=내용내용내용내용내용내용내용내용내용",
      "qnaCommentCreatedAt=2024-10-10"
    }
  ]
}
```

**응답 : 실패 (존재하지 않는 게시글)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "NQP",
  "message": "No exist qna Post"
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "AF",
  "message": "Authentication fail."
}
```

**응답 : 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```

***

#### - Q&A 게시글 댓글 수정하기
  
##### 설명

클라이언트는 요청 헤더에 Bearer 인증 토큰을 포함하여 요청하고 조회가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 유효성 실패, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **Patch**  
- URL : **/{qnaPostId}/comments/{qnaCommentId}**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | Bearer 토큰 인증 헤더 | O |

###### Request Body
  
| name | type | description | required |
|---|:---:|:---:|:---:|
| qna_comment_writer | String | qna게시글 댓글 작성자 | O |
| qna_comment_content | String | qna게시글 댓글 내용 | O | 
| qna_comment_created_at | String | 댓글 작성 날짜 | O |

###### Example

```bash
curl -v -X PATCH "http://localhost:4000/api/v1/qna/1/comments/1" \
 -h "Authorization=Bearer XXXX" \
 -d "qnaCommentContent=저한테 물어보지마세요." \
 -d "qnaCommentCreatedAt=2024-10-10" \
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |
  
###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success.",
}
```

**응답 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 : 실패 (존재하지 않는 댓글)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "NQC",
  "message": "No exist qna Comment"
}
```

**응답 : 실패 (존재하지 않는 게시글)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "NQP",
  "message": "No exist qna Post"
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "AF",
  "message": "Authentication fail."
}
```

**응답 : 실패 (권한 없음)**
```bash
HTTP/1.1 403 Forbidden
Content-Type: application/json;charset=UTF-8

{
  "code": "NP",
  "message": "No Permission"
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```

***

#### - Q&A 게시글 댓글 삭제하기
  
##### 설명

클라이언트는 요청 헤더에 Bearer 인증 토큰을 포함하여 요청하고 조회가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 유효성 실패, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **Delete**  
- URL : **/{qnaPostId}/comments/{qnaCommentId}**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | Bearer 토큰 인증 헤더 | O |

##### Example

```bash
curl -v -X DELETE "http://localhost:4000/api/v1/qna/1/comments/1" \
 -h "Authorization=Bearer XXXX" \
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |
  
###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success.",
}
```

**응답 : 실패 (존재하지 않는 게시글)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "NQP",
  "message": "No exist qna Post"
}
```

**응답 : 실패 (존재하지 않는 댓글)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "NQC",
  "message": "No exist qna Comment"
}
```

**응답 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "AF",
  "message": "Authentication fail."
}
```

**응답 : 실패 (권한 없음)**
```bash
HTTP/1.1 403 Forbidden
Content-Type: application/json;charset=UTF-8

{
  "code": "NP",
  "message": "No Permission"
}
```

**응답 : 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```


***
#### 관리자(Admin)
  
<h2 style='background-color: rgba(55, 55, 55, 0.2); text-align: center'>Admin 모듈</h2>

Plogger 서비스의 관리자와 관련된 REST API 모듈입니다.  
구인 게시물 제거, 활동 게시물 제거, 유저 삭제 등의 API가 포함되어 있습니다.  
  
- url : /api/v1/admin

***

#### - 구인 게시물 삭제  
  
##### 설명

관리자는 URL에 구인 게시물 번호를 포함하여 요청하고 구인 게시물 삭제가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 만약 존재하지 않는 게시글일 경우 존재하지 않는 게시글에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **DELETE**  
- end point : **/recruit/{recruitId}**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | Bearer 토큰 인증 헤더 | O |

###### Example

```bash
curl -v -X DELETE "http://localhost:4000/api/v1/admin/recruit/{1}" \
 -h "Authorization=Bearer XXXX"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success."
}
```

**응답 : 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 : 실패 (존재하지 않는 게시글)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "NRR",
  "message": "No exist recruit report."
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "AF",
  "message": "Authentication fail."
}
```

**응답 : 실패 (권한 없음)**
```bash
HTTP/1.1 403 Forbidden
Content-Type: application/json;charset=UTF-8

{
  "code": "NP",
  "message": "No permission."
}
```

**응답 : 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```

***

#### - 활동 게시물 삭제  
  
##### 설명

관리자는 URL에 활동 게시물 번호를 포함하여 요청하고 활동 게시물 삭제가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 만약 존재하지 않는 게시글일 경우 존재하지 않는 게시글에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **DELETE**  
- end point : **/active/{activeId}**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | Bearer 토큰 인증 헤더 | O |

###### Example

```bash
curl -v -X DELETE "http://localhost:4000/api/v1/admin/active/{1}" \
 -h "Authorization=Bearer XXXX"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success."
}
```

**응답 : 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 : 실패 (존재하지 않는 게시글)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "NAR",
  "message": "No active recruit report."
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "AF",
  "message": "Authentication fail."
}
```

**응답 : 실패 (권한 없음)**
```bash
HTTP/1.1 403 Forbidden
Content-Type: application/json;charset=UTF-8

{
  "code": "NP",
  "message": "No permission."
}
```

**응답 : 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```

***

#### - 유저 삭제  
  
##### 설명

관리자는 URL에 유저 아이디를 포함하여 요청하고 유저 삭제가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 만약 존재하지 유저일 경우 존재하지 않는 유저에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **DELETE**  
- end point : **/{userId}**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | Bearer 토큰 인증 헤더 | O |

###### Example

```bash
curl -v -X DELETE "http://localhost:4000/api/v1/admin/{qwer1234}" \
 -h "Authorization=Bearer XXXX"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success."
}
```

**응답 : 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 : 실패 (존재하지 않는 유저)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "NI",
  "message": "No exist userId."
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "AF",
  "message": "Authentication fail."
}
```

**응답 : 실패 (권한 없음)**
```bash
HTTP/1.1 403 Forbidden
Content-Type: application/json;charset=UTF-8

{
  "code": "NP",
  "message": "No permission."
}
```

**응답 : 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```

***

#### 알람

<h2 style='background-color: rgba(55, 55, 55, 0.2); text-align: center'>Alert 모듈</h2>

Plogger 서비스의 알람과 관련된 REST API 모듈입니다.  
알람 가져오기, 알람 전송, 알람 제거 등의 API가 포함되어 있습니다.  
Alert 모듈은 모두 인증이 필요합니다.  
  
- url : /api/v1/alert  

***

#### - 알람 가져오기
  
##### 설명

클라이언트는 요청 헤더에 Bearer 인증 토큰을 포함하여 요청하고 성공적으로 이루어지면 성공에 대한 응답으로 토큰에 해당하는 알람을 응답 받습니다. 네트워크 에러, 서버 에러, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **GET**  
- end point : **/**  

##### Request

###### Request Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| userId | String | 사용자의 아이디 | O |

###### Example

```bash
curl -v -X POST "http://localhost:4000/api/v1/alert" \
 -d "userId=qwer1234" 
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | Bearer 토큰 인증 헤더 | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| id | Integer | 알람 번호 | O |
| userId | String | 고객 아이디 | O |
| message | String | 알람 메시지 | O |
| isRead | Boolean | 읽음 여부 | O |
| createdAt | String | 알람 발생 시간 | O |
| recruitPostId | Integer | 알람 발생 위치(구인 게시판)  | O |
| activePostId | Integer | 알람 발생 위치(활동 게시판) | O |
| qnaPostId | Integer | 알람 발생 위치(Q&A 게시판) | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
    "code": "SU",
    "message": "Success.",
    "alerts": [
        {
            "id": 1,
            "userId": "qwer1234",
            "message": "qwer4444(이)가 고객님의 글에 댓글을 달았습니다.",
            "isRead": false,
            "createdAt": "2024-11-15 16:36:20",
            "recruitPostId": null,
            "activePostId": 1,
            "qnaPostId": null
        }
    ]
}
```

**응답 : 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```

***

#### - 알람 전송
  
##### 설명

클라이언트는 요청 헤더에 Bearer 인증 토큰을 포함하고 본인 게시물의 알람 발생에 대한 응답을 받습니다. 또한 본인의 팔로잉 발생에 따른 응답을 받습니다. 네트워크 에러, 서버 에러, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **POST**  
- end point : **/**

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | Bearer 토큰 인증 헤더 | O |

###### Request Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| userId | String | 유저 아이디 | O |
| message | String | 메시지 내용 | O |

###### Example

```bash
curl -v -X POST "http://localhost:4000/api/v1/alert" \
 -h "Authorization=Bearer XXXX" \
 -d "userId=qwer1234" \
 -d "message=알람이 발생하였습니다."
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success."
}
```

**응답 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 실패 (존재하지 않는 유저)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "NI",
  "message": "No exist userId."
}
```

**응답 실패 (메시지 전송 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "AMF",
  "message": "Alert message send failed."
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "AF",
  "message": "Authentication fail."
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```

***

#### - 알람 삭제
  
##### 설명

클라이언트는 요청 헤더에 Bearer 인증 토큰을 포함하고 URL에 알람 번호를 포함하여 요청하고 알람 삭제가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 만약 존재하지 않는 알람일 경우 존재하지 않는 알람에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **DELETE**  
- end point : **/{id}**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | Bearer 토큰 인증 헤더 | O |

###### Example

```bash
curl -v -X DELETE "http://localhost:4000/api/v1/alert/1" \
 -h "Authorization=Bearer XXXX"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| id | Integer | 알람 번호 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success."
}
```

**응답 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 : 실패 (존재하지 않는 유저)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "NI",
  "message": "No exist uerId."
}
```

**응답 실패 (존재하지 않는 알람)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "NA",
  "message": "No exist alert."
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "AF",
  "message": "Authentication fail."
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```

***

#### 채팅 

<h2 style='background-color: rgba(55, 55, 55, 0.2); text-align: center'>Chat 모듈</h2>

Plogger 서비스의 활동 게시판과 관련된 REST API 모듈입니다. 채팅방 생성하기, 메세지 보내기, 채팅방 참여하기 등의 API가 포함되어 있습니다. Chat 모듈은 모두 인증이 필요합니다.  
  
- url : /api/v1/chat  

***

#### - 채팅방 생성하기
  
##### 설명

클라이언트는 요청 헤더에 Bearer 인증 토큰을 포함하여 요청하고 조회가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **POST**  
- URL : **/rooms**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | Bearer 토큰 인증 헤더 | O |

###### Request Body

**PostChatRoom**  
| name | type | description | required |
|---|:---:|:---:|:---:|
| room_name | String | 채팅방 이름 | O |

###### Example

```bash
curl -X POST "http://localhost:4000/api/v1/chat/rooms" \
 -h "Authorization=Bearer XXXX"
 -d "roomName": "플로깅 참여자분들 들어오세요."
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success.",
}
```

**응답 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "AF",
  "message": "Authentication fail."
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```
***
#### - 채팅방 메세지 리스트 가져오기
  
##### 설명

클라이언트는 요청 헤더에 Bearer 인증 토큰을 포함하여 요청하고 조회가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 유효성 실패, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **GET**  
- URL : **/messages**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | Bearer 토큰 인증 헤더 | O |

##### Example

```bash
curl -v -X GET "http://localhost:4000/api/v1/chat/messages" \
 -h "Authorization=Bearer XXXX" \
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |
| chatMessages | ChatMessage[] | 채팅방 메세지 리스트 | O |
  
**ChatMessage**  
| name | type | description | required |
|---|:---:|:---:|:---:|
| chat_id | Integer | 채팅 메세지 번호 | O |
| sender_id | String | 작성자 아이디 | O |
| room_id | Integer | 채팅방 번호 | O |
| message | String | 메세지 내용 | O |
| is_read | Boolean | 읽음 여부 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success.",
  "chatMessages": [
    {
      "chatId": 1,
      "senderId": "qwer1234",
      "roomId": 1,
      "message": "안녕하세요!",
      "isRead": false
    },
    ...
  ]
}
```

**응답 : 실패 (존재하지 않는 채팅방)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "NCR",
  "message": "No exist chat room."
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "AF",
  "message": "Authentication fail."
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```
***
#### - 채팅방 리스트 가져오기
  
##### 설명

클라이언트는 요청 헤더에 Bearer 인증 토큰을 포함하여 요청하고 조회가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 유효성 실패, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **GET**  
- URL : **/rooms**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | Bearer 토큰 인증 헤더 | O |

##### Example

```bash
curl -v -X GET "http://localhost:4000/api/v1/chat/messages" \
 -h "Authorization=Bearer XXXX" \
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |
| chatRooms | ChatRoom[] | 채팅방 리스트 | O |

**ChatRoom**  
| name | type | description | required |
|---|:---:|:---:|:---:|
| room_id | Integer | 채팅방 번호 | O |
| room_name | String | 채팅방 이름 | O |
| created_at | String | 채팅방 생성 날짜 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success.",
  "chatRooms": [
    {
      "roomId": 1,
      "roomName": "플로깅하실분!",
      "createdAt": "2024-10-10"
    },
    ...
  ]
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "AF",
  "message": "Authentication fail."
}
```

**응답 : 실패 (존재하지 않는 사용자)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "NU",
  "message": "No exist user."
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```

***
<h2 style='background-color: rgba(55, 55, 55, 0.2); text-align: center'>File 모듈</h2>

Plogger 서비스의 File과 관련된 REST API 모듈입니다.  
파일 업로드 및 저장된 이미지 파일을 조회 등의 API가 포함되어 있습니다.  
  
- url : /file

#### - 파일 업로드
  
##### 설명

클라이언트는 요청 후 조회가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **POST**  
- URL : **/upload**  

##### Request

###### Header

###### Request Body

| name | description | required |
|---|:---:|:---:|
| file | 업로드할 파일 | O |

###### Example

```bash
curl -X POST "http://localhost:4000/file/upload" \
 -H "Content-Type: multipart/form-data" \
 -F "file=@example.png"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| url | String | 업로드된 파일의 접근 경로 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success."
}

```

**응답 : 실패 (파일 업로드 실패)**
```bash
HTTP/1.1 400 BAD_REQUEST
Content-Type: application/json;charset=UTF-8

{
  "code": "BAD_REQUEST",
  "message": "The file is empty or invalid."
}

```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```
***
#### - 파일 요청하기
  
##### 설명

클라이언트가 요청하고 조회가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 유효성 실패, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **GET**  
- URL : **/{fileName}**  

##### Request

###### Header


###### Example

```bash
curl -X GET "http://localhost:4000/file/uuid12345.png" \
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| data | String | 업로드된 파일의 바이너리 데이터 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success.",
  "data=<이미지 바이너리 데이터>"
}
```

**응답 실패 (인증 실패)**
```bash
HTTP/1.1 404 Not Found
Content-Type: application/json;charset=UTF-8

{
  "code": "NOT_FOUND",
  "message": "The requested file does not exist."
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```
***
####  follow

<h2 style='background-color: rgba(55, 55, 55, 0.2); text-align: center'>follow 모듈</h2>

Plogger 서비스의 follow와 관련된 REST API 모듈입니다.  
follow 생성 및 삭제, 사용자의 팔로우&팔로이 목록 보기 등의 API가 포함되어 있습니다.  
follow 모듈은 모두 인증이 필요합니다.  
  
- url : /api/v1/follow

***

#### - 팔로우 생성
  
##### 설명

클라이언트는 요청 헤더에 Bearer 인증 토큰을 포함하여 요청하고 조회가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **POST**  
- URL : **/**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | Bearer 토큰 인증 헤더 | O |

###### Request Body

| name | description | required |
|---|:---:|:---:|
| followee_id | 팔로이 아이디 | O |

###### Example

```bash
curl -X POST "http://localhost:4000/api/v1/follow" \
 -h "Authorization=Bearer XXXX"
 -d "followeeId=qwer1234"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success."
}

```

**응답 : 실패 (존재하지 않는 사용자)**
```bash
HTTP/1.1 400 BAD_REQUEST
Content-Type: application/json;charset=UTF-8

{
  "code": "NI",
  "message": "No exist user id."
}

```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "AF",
  "message": "Authentication fail."
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```
***
#### - 팔로우 삭제하기
  
##### 설명

클라이언트는 요청 헤더에 Bearer 인증 토큰을 포함하여 요청하고 조회가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 유효성 실패, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **Delete**  
- URL : **/{followeeId}**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | Bearer 토큰 인증 헤더 | O |

##### Example

```bash
curl -v -X DELETE "http://localhost:4000/api/v1/follow/qwer1234" \
 -h "Authorization=Bearer XXXX" \
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |
  
###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success.",
}
```

**응답 : 실패 (존재하지 않는 사용자)**
```bash
HTTP/1.1 400 BAD_REQUEST
Content-Type: application/json;charset=UTF-8

{
  "code": "NI",
  "message": "No exist user id."
}

```

**응답 : 실패 (존재하지 않는 팔로우)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "NF",
  "message": "No exist follow."
}
```


**응답 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "AF",
  "message": "Authentication fail."
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```
***
#### - 로그인한 사용자 팔로워 리스트 조회하기
  
##### 설명

클라이언트는 요청 헤더에 Bearer 인증 토큰을 포함하여 요청하고 조회가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 유효성 실패, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **GET**  
- URL : **/** 

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | Bearer 토큰 인증 헤더 | O |

###### Example

```bash
curl -X GET "http://localhost:4000/api/v1/follower" \
 -h "Authorization=Bearer XXXX"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |
| follows | follower[] | 팔로워 리스트 | O |  
  
**follower**  
| name | type | description | required |
|---|:---:|:---:|:---:|
| follow_id | 팔로우 아이디 | O |
| follower_id | 팔로워 아이디 | O |
| followee_id | 팔로이 아이디 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success.",
  "follows": [
    {
      "followId=1" \
		  "followerId=qwer5678" \
			"followeeId=qwer1234"
    },
   ... 
  ]
}
```

**응답 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "AF",
  "message": "Authentication fail."
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```
***
#### - 로그인한 사용자 팔로이 리스트 조회하기
  
##### 설명

클라이언트는 요청 헤더에 Bearer 인증 토큰을 포함하여 요청하고 조회가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 유효성 실패, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **GET**  
- URL : **/**

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | Bearer 토큰 인증 헤더 | O |

###### Example

```bash
curl -X GET "http://localhost:4000/api/v1/followee" \
 -h "Authorization=Bearer XXXX"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |
| follows | followee[] | 팔로워 리스트 | O |  
  
**follower**  
| name | type | description | required |
|---|:---:|:---:|:---:|
| follow_id | 팔로우 아이디 | O |
| follower_id | 팔로워 아이디 | O |
| followee_id | 팔로이 아이디 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success.",
  "follows": [
    {
      "followId=1" \
		  "followerId=qwer5678" \
			"followeeId=qwer1234"
    },
   ... 
  ]
}
```

**응답 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "AF",
  "message": "Authentication fail."
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```
***
#### - 사용자 팔로워 리스트 조회하기
  
##### 설명

클라이언트는 경로에 사용자 아이디를 넣어서 요청하고 조회가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 유효성 실패, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **GET**  
- URL : **/{followeeId}** 

##### Request

###### Example

```bash
curl -X GET "http://localhost:4000/api/v1/follower/qwer1234" 
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |
| follows | follower[] | 팔로워 리스트 | O |  
  
**follower**  
| name | type | description | required |
|---|:---:|:---:|:---:|
| follow_id | 팔로우 아이디 | O |
| follower_id | 팔로워 아이디 | O |
| followee_id | 팔로이 아이디 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success.",
  "follows": [
    {
      "followId=1" \
		  "followerId=qwer5678" \
			"followeeId=qwer1234"
    },
   ... 
  ]
}
```

**응답 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "AF",
  "message": "Authentication fail."
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```
***
#### - 사용자 팔로이 리스트 조회하기
  
##### 설명

클라이언트는 경로에 사용자 아이디를 넣어서 요청하고 조회가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 유효성 실패, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **GET**  
- URL : **/{followeeId}** 

##### Request

###### Example

```bash
curl -X GET "http://localhost:4000/api/v1/followee/qwer5678" 
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |
| follows | followee[] | 팔로워 리스트 | O |  
  
**followee**  
| name | type | description | required |
|---|:---:|:---:|:---:|
| follow_id | 팔로우 아이디 | O |
| follower_id | 팔로워 아이디 | O |
| followee_id | 팔로이 아이디 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success.",
  "follows": [
    {
      "followId=1" \
		  "followerId=qwer5678" \
			"followeeId=qwer1234"
    },
   ... 
  ]
}
```

**응답 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "AF",
  "message": "Authentication fail."
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```

***
#### Gifticon
<h2 style='background-color: rgba(55, 55, 55, 0.2); text-align: center'>Gifticon 모듈</h2>

Plogger 서비스의 Gifticon와 관련된 REST API 모듈입니다.  
기프티콘 정보 확인 및 수정, 로그인된 회원의 기프티콘 구매 등의 API가 포함되어 있습니다.  
Gifticon 모듈은 모두 인증이 필요합니다.  
  
- url : /api/v1/gifticon

#### - 기프티콘 생성
  
##### 설명

클라이언트는 요청 헤더에 Bearer 인증 토큰을 포함하여 요청하고 조회가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **POST**  
- URL : **/**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | Bearer 토큰 인증 헤더 | O |

###### Request Body

| name | description | required |
|---|:---:|:---:|
| name | 기프티콘 이름 | O |
| image | 기프티콘 이미지 | O |
| mileage_cost | 소모 마일리지 | O |

###### Example

```bash
curl -X POST "http://localhost:4000/api/v1/gifticon" \
 -h "Authorization=Bearer XXXX"
 -d "name=스타벅스 10만원 교환권" \
 -d "image=http:~~~~" \
 -d "mileageCost=1000" 
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success."
}

```

**응답 : 실패 (존재하지 않는 사용자)**
```bash
HTTP/1.1 400 BAD_REQUEST
Content-Type: application/json;charset=UTF-8

{
  "code": "NI",
  "message": "No exist user id."
}

```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "AF",
  "message": "Authentication fail."
}
```

**응답 : 실패 (권한 없음)**
```bash
HTTP/1.1 403 FORBIDDEN
Content-Type: application/json;charset=UTF-8

{
  "code": "NP",
  "message": "No permission."
}

```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```
***
#### - 기프티콘 리스트 조회하기
  
##### 설명

클라이언트는 요청 헤더에 Bearer 인증 토큰을 포함하여 요청하고 조회가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 유효성 실패, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **GET**  
- URL : **/**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | Bearer 토큰 인증 헤더 | O |

###### Example

```bash
curl -X GET "http://localhost:4000/api/v1/gifticon" \
 -h "Authorization=Bearer XXXX"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |
| gifticons | gifticon[] | 기프티콘 리스트 | O |  
  
**gifticon**  
| name | type | description | required |
|---|:---:|:---:|:---:|
| gifticon_id | 기프티콘 아이디 | O |
| name | 기프티콘 이름 | O |
| image | 기프티콘 이미지 | O |
| mileage_cost | 소모 마일리지 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success.",
  "gifticons": [
    {
      "name=스타벅스 10만원 교환권" \
		  "image=http:~~~~" \
			"mileageCost=1000" 
    },
   ... 
  ]
}
```

**응답 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "AF",
  "message": "Authentication fail."
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```

***

#### - 기프티콘 조회하기
  
##### 설명

클라이언트는 요청 헤더에 Bearer 인증 토큰을 포함하여 요청하고 조회가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 유효성 실패, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **GET**  
- URL : **/{gifticonId}**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | Bearer 토큰 인증 헤더 | O |

###### Example

```bash
curl -X GET "http://localhost:4000/api/v1/gifticon/1" \
 -h "Authorization=Bearer XXXX"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |
| gifticon | gifticon | 기프티콘 리스트 | O |  
  
**gifticon**  
| name | type | description | required |
|---|:---:|:---:|:---:|
| gifticon_id | 기프티콘 아이디 | O |
| name | 기프티콘 이름 | O |
| image | 기프티콘 이미지 | O |
| mileage_cost | 소모 마일리지 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success.",
  "gifticons": [
    {
      "name=스타벅스 10만원 교환권" \
		  "image=http:~~~~" \
			"mileageCost=1000" 
    },
   ... 
  ]
}
```

**응답 : 실패 (존재하지 않는 기프티콘)**
```bash
HTTP/1.1 400 BAD_REQUEST
Content-Type: application/json;charset=UTF-8

{
  "code": "NG",
  "message": "No exist gifticon."
}

```

**응답 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "AF",
  "message": "Authentication fail."
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```

***

#### - 기프티콘 수정하기
  
##### 설명

클라이언트는 요청 헤더에 Bearer 인증 토큰을 포함하여 요청하고 조회가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 유효성 실패, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **Patch**  
- URL : **/{gifticonId}**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | Bearer 토큰 인증 헤더 | O |

###### Request Body
  
| name | type | description | required |
|---|:---:|:---:|:---:|
| name | String | 기프티콘 이름 | O |
| image | String | 기프티콘 이미지 | O | 
| mileage_cost | String | 소모 마일리지 | O |

###### Example

```bash
curl -v -X PATCH "http://localhost:4000/api/v1/gifticon/1" \
 -h "Authorization=Bearer XXXX" \
 -d "name=스타벅스 8만원 교환권" \
 -d "image=http:~~" \
 -d "mileageCost=500" \
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |
  
###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success.",
}
```

**응답 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 : 실패 (존재하지 않는 사용자)**
```bash
HTTP/1.1 400 BAD_REQUEST
Content-Type: application/json;charset=UTF-8

{
  "code": "NI",
  "message": "No exist user id."
}

```

**응답 : 실패 (존재하지 않는 기프티콘)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "NG",
  "message": "No exist gifticon"
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "AF",
  "message": "Authentication fail."
}
```

**응답 : 실패 (권한 없음)**
```bash
HTTP/1.1 403 Forbidden
Content-Type: application/json;charset=UTF-8

{
  "code": "NP",
  "message": "No Permission"
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```

***

#### - 기프티콘 삭제하기
  
##### 설명

클라이언트는 요청 헤더에 Bearer 인증 토큰을 포함하여 요청하고 조회가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 유효성 실패, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **Delete**  
- URL : **/{gifticonId}**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | Bearer 토큰 인증 헤더 | O |

##### Example

```bash
curl -v -X DELETE "http://localhost:4000/api/v1/gifticon/1" \
 -h "Authorization=Bearer XXXX" \
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |
  
###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success.",
}
```

**응답 : 실패 (존재하지 않는 사용자)**
```bash
HTTP/1.1 400 BAD_REQUEST
Content-Type: application/json;charset=UTF-8

{
  "code": "NI",
  "message": "No exist user id."
}

```

**응답 : 실패 (존재하지 않는 기프티콘)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "NG",
  "message": "No exist gifticon"
}
```


**응답 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "AF",
  "message": "Authentication fail."
}
```

**응답 : 실패 (권한 없음)**
```bash
HTTP/1.1 403 Forbidden
Content-Type: application/json;charset=UTF-8

{
  "code": "NP",
  "message": "No Permission"
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```
***
#### - 기프티콘 구매
  
##### 설명

클라이언트는 요청 헤더에 Bearer 인증 토큰을 포함하여 요청하고 조회가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **POST**  
- URL : **/{gifticonId}** 

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | Bearer 토큰 인증 헤더 | O |

###### Example

```bash
curl -X POST "http://localhost:4000/api/v1/gifticon/1" \
 -h "Authorization=Bearer XXXX"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |

###### Value Object

| name | description | required |
|---|:---:|:---:|
| user_id | 사용자 아이디 | O |
| gifticon_id | 기프티콘 아이디 | O |
| mileage_result | 최종 마일리지 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success."
}

```

**응답 : 실패 (존재하지 않는 사용자)**
```bash
HTTP/1.1 400 BAD_REQUEST
Content-Type: application/json;charset=UTF-8

{
  "code": "NI",
  "message": "No exist user id."
}

```

**응답 : 실패 (존재하지 않는 기프티콘)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "NG",
  "message": "No exist gifticon"
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "AF",
  "message": "Authentication fail."
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```

***
#### Mileage
<h2 style='background-color: rgba(55, 55, 55, 0.2); text-align: center'>mileage 모듈</h2>

Plogger 서비스의 mileage와 관련된 REST API 모듈입니다.  
mileage 내역 보기의 API가 포함되어 있습니다.  
mileage 모듈은 모두 인증이 필요합니다.  
  
- url : /api/v1/mileage

***

#### - mileage 내역 열람하기

##### 설명

클라이언트는 요청 헤더에 Bearer 인증 토큰을 포함하여 요청하고 조회가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 유효성 실패, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **GET**  
- URL : **/{userId}**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | Bearer 토큰 인증 헤더 | O |

###### Example

```bash
curl -X GET "http://localhost:4000/api/v1/mileage" \
 -h "Authorization=Bearer XXXX"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |
| mileages | mileage[] | 마일리지 내역 리스트 | O |
  
**userMileage**  
| name | type | description | required |
|---|:---:|:---:|:---:|
| mileage_id | Integer | 마일리지 아이디 | O |
| user_id | String | 유저 아이디 | O |
| mileage_change | Integer | 변동 마일리지 | O |
| mileage_result | Integer | 결과 마일리지 | O |
| description | String | 변동 사유 | O |
| created_at | String | 변경 날짜 | O |
| active_id | Integer | 활동게시판 아이디 | O |
| gifticon_id | Integer | 기프티콘 아이디 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success.",
  "": [
    {
      "mileageId": 1,
      "userId": "qwer1234",
      "mileageChange": 150,
      "mileageResult": 300,
      "description": "AT",
      "createdAt": "2024-10-20"    
    }
    ...
  ]
}
```

**응답 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "AF",
  "message": "Authentication fail."
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```
***
#### Mypage

<h2 style='background-color: rgba(55, 55, 55, 0.2); text-align: center'>mypage 모듈</h2>

Plogger 서비스의 mypage와 관련된 REST API 모듈입니다.  
마이 페이지 조회, 수정 API가 포함되어 있습니다.  
Mypage 모듈은 모두 인증이 필요합니다.  
  
- url : /api/v1/mypage
***
#### - 내 정보 수정
  
##### 설명

클라이언트는 요청 헤더에 Bearer 인증 토큰을 포함하여 요청하고 조회가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **PATCH**  
- URL : **/**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | Bearer 토큰 인증 헤더 | O |

###### Reqeust Body
| name | type | description | required |
| --- | :---: | :---: | :---: |
| name | String | 사용자 이름 | O |
| profileImage | text | 프로필 이미지 | O |
| password | String | 사용자 비밀번호 | O |
| telNumber | String | 사용자 전화번호 | O |
| authNumber | String | 인증번호 | O |
| address | String | 주소 | O |

###### Example

```bash
curl -X PATCH "http://localhost:4000/api/v1/mypage/qwer1234" \
 -h "Authorization=Bearer XXXX" \
 -d "name=홍길동" \
 -d "profileImage="http:/~" \
 -d "password=qwer1234" \
 -d "telNumber=01012345678" \
 -d "authNumber = 1234" \
 -d "address=부산광역시 남구" 
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |
  
###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success."
}
```

**응답 : 실패 (존재하지 않는 사용자)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "NI",
  "message": "No exist user."
}
```

**응답 : 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 : 실패 (존재하지 않는 아이디와 전화번호)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "NIT",
  "message": "No Exist user Id and Tel Number."
}
```

**응답 : 실패 (존재하지 않는 전화번호)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "NT",
  "message": "No exist TelNumber."
}
```

**응답 : 실패 (중복된 전화번호)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "DT",
  "message": "Duplicated TelNumber."
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "AF",
  "message": "Authentication fail."
}
```

**응답 : 실패 (권한 없음)**
```bash
HTTP/1.1 403 Forbidden
Content-Type: application/json;charset=UTF-8

{
  "code": "NP",
  "message": "No permission."
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```
***
#### - 내 전화번호 변경
  
##### 설명

클라이언트는 요청 헤더에 Bearer 인증 토큰을 포함하여 요청하고 조회가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **PATCH**  
- URL : **/tel-auth**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | Bearer 토큰 인증 헤더 | O |

###### Reqeust Body
| name | type | description | required |
| --- | :---: | :---: | :---: |
| telNumber | String | 사용자 전화번호 | O |
| authNumber | String | 인증번호 | O |

###### Example

```bash
curl -X PATCH "http://localhost:4000/api/v1/mypage/qwer1234" \
 -h "Authorization=Bearer XXXX" \
 -d "telNumber=01012345678" \
 -d "authNumber = 1234" \
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |
  
###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success."
}
```

**응답 : 실패 (존재하지 않는 아이디)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "NI",
  "message": "No exist userId."
}
```

**응답 : 실패 (존재하지 않는 사용자)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "NU",
  "message": "No exist user."
}
```

**응답 : 실패 (존재하지 않는 전화번호)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "NT",
  "message": "No exist TelNumber."
}
```

**응답 : 실패 (중복된 전화번호)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "DT",
  "message": "Duplicated TelNumber."
}
```

**응답 : 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "AF",
  "message": "Authentication fail."
}
```

**응답 : 실패 (권한 없음)**
```bash
HTTP/1.1 403 Forbidden
Content-Type: application/json;charset=UTF-8

{
  "code": "NP",
  "message": "No permission."
}
```

**응답 실패 (인증번호 전송 실패)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "TF",
  "message": "Message send failed."
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```
***
#### - 전화번호 변경 시 인증번호 확인
  
##### 설명

클라이언트는 요청 헤더에 Bearer 인증 토큰을 포함하여 요청하고 조회가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **PATCH**  
- URL : **/tel-auth-check**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | Bearer 토큰 인증 헤더 | O |

###### Reqeust Body
| name | type | description | required |
| --- | :---: | :---: | :---: |
| telNumber | String | 사용자 전화번호 | O |
| authNumber | String | 인증번호 | O |

###### Example

```bash
curl -X PATCH "http://localhost:4000/api/v1/mypage/qwer1234" \
 -h "Authorization=Bearer XXXX" \
 -d "telNumber=01012345678" \
 -d "authNumber = 1234" \
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |
  
###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success."
}
```

**응답 : 실패 (존재하지 않는 아이디)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "NI",
  "message": "No exist userId."
}
```

**응답 : 실패 (존재하지 않는 사용자)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "NU",
  "message": "No exist user."
}
```

**응답 : 실패 (중복된 전화번호)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "DT",
  "message": "Duplicated TelNumber."
}
```

**응답 : 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "AF",
  "message": "Authentication fail."
}
```

**응답 실패 (인증번호 인증 실패)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "TAF",
  "message": "TelNumber Authentication failed."
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```
***
#### - 사용자 한 줄 소개 수정하기
  
##### 설명

클라이언트는 요청 헤더에 Bearer 인증 토큰을 포함하여 요청하고 조회가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 유효성 실패, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **Patch**  
- URL : **/comment**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | Bearer 토큰 인증 헤더 | O |

###### Request Body
  
| name | type | description | required |
|---|:---:|:---:|:---:|
| comment | String | 소개글 내용 | O |

###### Example

```bash
curl -v -X PATCH "http://localhost:4000/api/v1/mypage/comment" \
 -h "Authorization=Bearer XXXX" \
 -d "comment=처음 뵙겠습니다." 
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |
  
###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success.",
}
```

**응답 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 : 실패 (존재하지 않는 사용자)**
```bash
HTTP/1.1 400 BAD_REQUEST
Content-Type: application/json;charset=UTF-8

{
  "code": "NI",
  "message": "No exist user id."
}

```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "AF",
  "message": "Authentication fail."
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```
***
#### - 사용자 비밀번호 수정하기
  
##### 설명

클라이언트는 요청 헤더에 Bearer 인증 토큰을 포함하여 요청하고 조회가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 유효성 실패, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **Patch**  
- URL : **/update-password**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | Bearer 토큰 인증 헤더 | O |

###### Request Body
  
| name | type | description | required |
|---|:---:|:---:|:---:|
| currentPassword | String | 현재 비밀번호 | O |
| newPassword | String | 새 비밀번호 | O |

###### Example

```bash
curl -v -X PATCH "http://localhost:4000/api/v1/mypage/update-password" \
 -h "Authorization=Bearer XXXX" \
 -d "currentPassword"="qwer1234" \
 -d "currentPassword"="qwer5678"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |
  
###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success.",
}
```

**응답 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 : 실패 (존재하지 않는 사용자)**
```bash
HTTP/1.1 400 BAD_REQUEST
Content-Type: application/json;charset=UTF-8

{
  "code": "NI",
  "message": "No exist user id."
}

```

**응답 : 실패 (비밀번호 불일치)**
```bash
HTTP/1.1 400 BAD_REQUEST
Content-Type: application/json;charset=UTF-8

{
  "code": "PM",
  "message": "Password mismatch."
}

```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "AF",
  "message": "Authentication fail."
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```
***
#### - 유저 조회하기
  
##### 설명

클라이언트는 요청하고 조회가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 유효성 실패, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **GET**  
- URL : **/**  

##### Request

###### Example

```bash
curl -X GET "http://localhost:4000/api/v1/mypage" \
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |
| users | user[] | 사용자 리스트 | O |  
  
**user**  
| name | type | description | required |
|---|:---:|:---:|:---:|
| userId | String | 아이디 | O |
| password | String | 비밀번호 | O |
| telNumber | String | 전화번호 | O |
| address | String | 주소 | O |
| profileImage | String | 프로필 이미지 | O |
| ecoScore | Integer | 에코 스코어 | O |
| mileage | Integer | 마일리지 | O |
| comment | String | 한 줄 소개 | X |
| joinPath | String | 가입 경로 | O |
| snsId | String | SNS 아이디 | X |
| isAdmin | Boolean | 관리자 유무 | O |

private String userId;
    private String name; 
    private String password; 
    private String telNumber;
    private String address;
    private String profileImage = "https://blog.kakaocdn.net/dn/4CElL/btrQw18lZMc/Q0oOxqQNdL6kZp0iSKLbV1/img.png";
    private Integer ecoScore = 0;
    private Integer mileage = 0;
    private String comment;
    private String joinPath = "home";
    private String snsId;
    private Boolean isAdmin = false;

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success.",
  "gifticons": [
    {
      "userId"="qwer1234",
		  "password"="qwer1234",
      "telNumber"="01011112222",
		  "address"="부산광역시 부산진구 중앙대로 717",
      "profileImage"="http:~~~~" ,
		  "ecoScore"=80 ,
      "mileage"=2000 ,
		  "comment"="처음 뵙겠습니다.",
      "joinPath"="home",
		  "snsId"="qwer1234",
			"isAdmin"=false
    },
   ... 
  ]
}
```

**응답 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "AF",
  "message": "Authentication fail."
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```

***