![header](https://capsule-render.vercel.app/api?type=waving&color=6667ab&height=300&section=header&text=뭉게구름%20BACK-END&fontSize=70&fontColor=f0f0fd)

# 🤗 WELCOME!

> <h4>　<br>창작을 자유롭게, 뭉게구름<br>　

영진전문대학교 컴퓨터정보계열 웹데이터베이스반 2조 **"투게더(TwoGether)"** 캡스톤디자인

**"뭉게구름"** 프로젝트의 백엔드 리포지토리입니다. 반가워요!👋


## 👨‍👩‍👦‍👦 누가 프로젝트에 참여했나요?
<div align="center">
<table style="margin: auto">
    <tr> 
<td align="center"><a href="https://github.com/sila0319"><img src="https://avatars.githubusercontent.com/u/102634882?v=4"
 width="100px;" alt=""/><br /><sub><b>유원규</b></sub></a><br /><a href="https://github.com/sila0319" title="Code">🏠</a></td>

<td align="center"><a href="https://github.com/JongchanJeon"><img src="https://avatars.githubusercontent.com/u/103109563?v=4"
width="100px;" alt=""/><br /><sub><b>전종찬</b></sub></a><br /><a href="https://github.com/JongchanJeon" title="Code">🏠</a></td>

</tr>
</table>
</div>

## 🛠️개발에 어떤 기술을 사용했나요?
<div align="center">
<table>
<tr>
<td>
<b>개발 도구
</td>
<td>
<b>사용 기술
</td>
<td>
<b>협업 툴
</td>
</tr>
<tr>
<td>
<img src="https://img.shields.io/badge/webstorm-000000?style=for-the-badge&logo=Webstorm&logoColor=white"><br>
<img src="https://img.shields.io/badge/Visual%20Studio-5c2d91?style=for-the-badge&logo=Visual Studio&logoColor=white">
</td>
<td>
<img src="https://img.shields.io/badge/Spring Boot-6db33f?style=for-the-badge&logo=Spring%20Boot&logoColor=white"><br>
<img src="https://img.shields.io/badge/spring%20security-6db33f?style=for-the-badge&logo=Spring-security&logoColor=white"><br>
<img src="https://img.shields.io/badge/Spring%20Boot%20AOP-6db33f?style=for-the-badge&logo=spring&logoColor=white"><br>
<img src="https://img.shields.io/badge/Apache%20Maven-c71a36?style=for-the-badge&logo=Apache%20Maven&logoColor=white"><br>
<img src="https://img.shields.io/badge/JSON%20Web%20Token-000000?style=for-the-badge&logo=JSON Web Tokens&logoColor=fffff"><br>
<img src="https://img.shields.io/badge/Amazon%20S3-ff9900?style=for-the-badge&logo=Amazon AWS&logoColor=232f3e"><br>
<img src="https://img.shields.io/badge/MariaDB-003545?style=for-the-badge&logo=MariaDB&logoColor=white"><br>
</td>
<td>
<img src="https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=Git&logoColor=white"><br>
<img src="https://img.shields.io/badge/Github-181717?style=for-the-badge&logo=GitHub&logoColor=white"><br>
<img src="https://img.shields.io/badge/notion-ffffff?style=for-the-badge&logo=notion&logoColor=black">
</td>
</tr>
</table>
</div>

서비스를 구현하기 위해 `java` 언어를. 그 중에서 `Spring` 프레임워크를 중심으로 사용하였습니다.
개발 도구(IDE)로는 JetBrain 사의 `WebStorm`와 MS사의 `Visual Studio`를 사용했으며, 데이터베이스(DB) 관리로 `MariaDB`와 `Amazon S3`를, 팀원과 협업을 위해 `Git`, `GitHub` 그리고 `Notion`을 사용하였습니다.

그 외에 로그인 같은 보안요소를 위한 `Spring Security`, 로그인 정보 유지를 위한 `JWT`, 프로젝트 설정 파일 관리를 위한 `Maven` 등 여러 기술을 활용하였습니다.

## 📟데이터베이스는 어떻게 구성되어 있나요?
<img src="https://parkmoc21.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Fb8eaefa1-1e44-484e-9b47-d0cf2c161e27%2F%25EB%25AD%2589%25EA%25B2%258C%25EA%25B5%25AC%25EB%25A6%2584ERD.png?id=fb9f36fa-d81c-4d65-881a-45a2fd248be4&table=block&spaceId=e83329e6-93c0-4d92-9deb-ac0205e34351&width=1920&userId=&cache=v2">

DB의 테이블 구성 및 관계는 <a href="https://www.erdcloud.com/d/QG6WqXowDwiEdpqet">위의 ERD</a> 같이 이루어져 있으며, 각 테이블의 설명은 아래와 같습니다.

- **board**: 게시판에 대한 정보를 담고 있습니다.
- **file**: 게시글에 첨부된 파일의 정보를 담고 있습니다.
- **hashtag**: 해시태그에 관한 정보를 담고 있습니다.
- **heart**: 게시글에 남겨진 "좋아요"와 관련된 정보를 담고 있습니다.
- **log**: 로그 정보를 담고 있습니다.
- **log_state**: ***[몰?루]***
- **post**: 게시글에 대한 정보를 담고 있습니다.
- **post_hashtag**: 게시글과 연관된 해시태그를 관리하는 테이블입니다.
- **post_source**: 재창작이 이루어졌을 때 게시글의 부모-자식을 판단하는 테이블입니다.
- **refresh_token**: 로그인 정보 유지를 위한 토큰 정보를 담고 있습니다.
- **reply**: 답글에 대한 정보를 담고 있습니다.
- **user**: 유저의 정보를 담고 있습니다.
- **user_grade**: 유저의 등급(일반, 관리자 등)에 대한 정보를 담고 있습니다.


## ❔백엔드의 동작흐름이 궁금해요!
<img src="https://camo.githubusercontent.com/cdad0ba218b752518efcee77e122e7a254411b76d23f14cb0a0bf44b91c65f00/68747470733a2f2f76656c6f672e76656c63646e2e636f6d2f696d616765732f6a7970313130322f706f73742f37383535373934382d336634382d343765382d616561652d3666666366393532303836382f696d6167652e706e67">

## 🖥️ 프로젝트의 서버를 직접 구축해볼 수 있나요?
물론이죠. 아래의 절차를 따름으로서 사용자의 PC에 서버를 구축할 수 있습니다.

1. <a href="https://mariadb.org/download/">MariaDB 다운로드 페이지</a>에 접속하여 `MariaDB Server`을 설치합니다.
    - 설치가 끝나면 MySQL Client 프로그램을 실행한 후 `show databases;` 명령어를 통해 데이터베이스 정보가 정상적으로 표시되는지 확인합니다.
2. 같이 설치되어진 HeidiSQL을 실행한 후 root 계정을 이용해 DB에 접근합니다.
3. ``CREATE DATABASE `capstone`;`` 명령어를 쿼리로 실행하거나, 우클릭 - [새로 생성] - [데이터베이스] 메뉴를 클릭하여 `capstone` 이름의 새로운 데이터베이스를 추가합니다.
4. [도구] - [사용자 관리자] 메뉴를 클릭한 후 [추가] 버튼을 통해 새로운 사용자를 생성합니다.
    - 사용자 이름은 `tom`으로, 암호는 `jerry`로 설정하세요.
    - [객체 추가] 버튼을 클릭하여 `capstone` DB를 추가한 후, `capstone` DB의 모든 접근을 허용해주세요.
    - 설정이 완료 되었으면 [저장] 후 [닫기] 버튼을 클릭하세요.
5. 리포지토리 오른쪽 위, 초록색 [Code] 버튼을 클릭한 후 [Download Zip] 메뉴를 선택합니다.
6. 다운로드 받은 파일의 압축을 풀고, Spring 프레임워크를 지원하는 IDE를 실행하여 해당 프로젝트를 import 합니다.(`Eclipse` 권장)
    - 필요에 따라 사용자의 PC에 JDK 또는 JRE를 설치해야할 수 있습니다. CMD에서 `java -version` 그리고 `javac -version` 명령어를 입력하여 버전이 출력되는지 확인하세요.
7. `src\main\java\com\capstone\` 위치에 있는 `Capstone2023Application.java` 클래스를 실행합니다.
8. 정상적으로 실행됬다면 HeidiSQL을 통해 DB를 확인하였을 때 아래와 같이 여러 테이블이 자동으로 생성되어짐을 확인 할 수 있습니다.
<img src="https://file.notion.so/f/s/b984fb7c-c4dd-46c3-9d44-893dc267e58d/DB_01.bmp?id=97a90a66-30bf-4f30-b263-40116bd4e063&table=block&spaceId=e83329e6-93c0-4d92-9deb-ac0205e34351&expirationTimestamp=1688544000000&signature=YVmEXwnZMnecqC26QoAyya6M1KqnuLndwmq7ZtY1biU">
9. DB에 추가적인 컬럼 삽입이 필요합니다. 안전한 삽입을 위해 서버를 잠시 중단하시는 것을 권장합니다.
    - 아래 사진과 같이 데이터를 왼쪽에서부터 각각 `board`, `user_grade`, `log_state` 테이블에 추가해주세요.
    <table>
    <tr>
    <td><img src="https://file.notion.so/f/s/4a92ffd2-2d9b-4a27-80a5-e5f12363e13d/DB_02.bmp?id=09a1eda5-2abb-42bb-8b43-0e3c592f29f6&table=block&spaceId=e83329e6-93c0-4d92-9deb-ac0205e34351&expirationTimestamp=1688544000000&signature=kquwaroi5iuqifIkkmtzV5JKVD-Wify3p5md-XCLBYU"></td>
    <td><img src="https://file.notion.so/f/s/eca222c7-9335-46a5-ac48-c0b77c211f22/DB_03.bmp?id=50704fd1-b998-43e4-871d-e845b67cca7e&table=block&spaceId=e83329e6-93c0-4d92-9deb-ac0205e34351&expirationTimestamp=1688544000000&signature=p3gJkWbPF6j8DAMlurFV5pWtHRFLbJiUta_lSkrue-c"></td>
    <td><img src="https://file.notion.so/f/s/e333419e-9a59-46cb-985d-2309cddc716f/DB_04.bmp?id=93583ccc-399c-42d4-9c20-edd28b491822&table=block&spaceId=e83329e6-93c0-4d92-9deb-ac0205e34351&expirationTimestamp=1688544000000&signature=7EnnGsuW9JVC15sABKAAijJ4d9ExQHECuVaQjY4lo_k"></td>
    </tr>
    </table>
10. 추가가 완료되면 다시 서버를 시작하여 동작을 확인할 수 있습니다.


## 😀 멋지네요!

프로젝트에 대한 더 자세한 내용은 각각의 리포지토리 페이지를 방문해보세요.
- **소개 페이지**: https://github.com/YJU-3WDA-Twogether/mung-geguleum-interduce
- **프론트엔드 페이지**: https://github.com/YJU-3WDA-Twogether/mung-geguleum-FE
- **모바일 페이지**: https://github.com/YJU-3WDA-Twogether/mung-geguleum-mobile


<hr>

![java-version](https://img.shields.io/badge/java-v17.0.7-lightgray??style=flat-square&logoColor=white)
![node-version](https://img.shields.io/badge/spring%20boot-v3.0.5-lightgray??style=flat-square&logoColor=white)
![maven-version](https://img.shields.io/badge/maven-v4.0.0-lightgray??style=flat-square&logoColor=white)
![jwt-version](https://img.shields.io/badge/jwt-v0.11.5-lightgray??style=flat-square&logoColor=white)
![spring-version](https://img.shields.io/badge/spring%20boot%20security-v6.0.2-lightgray??style=flat-square&logoColor=white)
![spring-version](https://img.shields.io/badge/spring%20boot%20AOP-v2.3.12-lightgray??style=flat-square&logoColor=white)
![spring-version](https://img.shields.io/badge/AWS%20S3-v2.4.4-lightgray??style=flat-square&logoColor=white)
<br>
[![Hits](https://hits.seeyoufarm.com/api/count/incr/badge.svg?url=https%3A%2F%2Fgithub.com%2FYJU-3WDA-Twogether&count_bg=%2379C83D&title_bg=%23555555&icon=&icon_color=%23E7E7E7&title=hits&edge_flat=false)](https://hits.seeyoufarm.com)

![Footer](https://capsule-render.vercel.app/api?type=waving&color=6667ab&height=200&section=footer)
