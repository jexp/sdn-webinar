<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Todos with Spring Data Neo4j</title>
    <script src="http://code.jquery.com/jquery-1.6.4.min.js"></script>
    <script type="text/javascript">

    </script>
</head>
<body>
<H1>Todos with Spring Data Neo4j</H1>

<form method="POST" action="/todos">
    I want to <input type="text" name="text" size="180"/>.
</form>
<div>
    <c:choose>
        <c:when test="${not empty todos}">
            <h2>I want to:</h2>
            <ul style="list-style-type: none;">
                <c:forEach items="${todos}" var="todo">
                    <li>
                            ${todo.text}
                        <span style="font-size: small; font-style: italic;">(since ${todo.date})</span>
                        Tags:
                        <c:forEach items="${todo.tags}" var="tag">
                            <a href="/todos/tag2/${tag.name}">${tag.name}</a>
                        </c:forEach>
                    </li>
                </c:forEach>
            </ul>
        </c:when>
        <c:otherwise>
            do nothing. :)
        </c:otherwise>
    </c:choose>
    <h2>Tags</h2>
    <c:choose>
        <c:when test="${not empty tags}">
            <ul class="search-results">
                <c:forEach items="${tags}" var="tag">
                    <a href="/todos/tag/${tag.name}">${tag.name}</a>
                </c:forEach>
            </ul>
        </c:when>
    </c:choose>
</div>
</body>
</html>