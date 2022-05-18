# gRPC - 유저 정보 생성 및 조회하기
-- --
## 목표

### 1. Subscriber, Producer, Subscription 구조 맛보기

-- -- 

1. 단일 Application에 Subscriber, Producer, Subscription을 구현한다.
2. **Domain**
   1. **User**
        * userId
        * userName
        * email
        * role
3. **service**
    1. **getUser**
        1. request
            * userId
        2. response
            * userId
            * userName
            * email
            * role
    2. **setUser**
       1. request
            * userName
            * email
            * role
        2. response
            * userId