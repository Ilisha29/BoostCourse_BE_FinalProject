<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
  <head>
    <title>회원 정보</title>
  </head>
  <body>
    <div>
      <div>
        <h1>회원정보</h1>
        <p>로그인한 회원 정보를 표기합니다.</p>
      </div>

        <div>
          <label>id</label>
          <p>${user.id}</p>
        </div>
        <div>
          <label>이름</label>
          <p>${user.name}</p>
        </div>
        <div>
          <label>암호</label>
          <p>${user.password}</p>
        </div>
        <div>
          <label>등록일</label>
          <p>${user.createDate}</p>
        </div>
        <div>
          <label>수정일</label>
          <p>${user.modifyDate}</p>
        </div>

    </div>
  </body>
</html>