<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<div class="signup-image">
    <figure>
        <img src="images/thoughts.jpg" alt="sing up image">
    </figure>
    <form method="post" action="add_workout">
        <div class="form-group form-button">
            <a href="add_workout_2.jsp" class="signup-image-link">add exercise</a>
        </div>
    </form>
    <form method="get" action="workouts">
        <div class="form-group form-button">
            <a href="view_workouts" class="signup-image-link">save workout</a>
        </div>
    </form>
</div>
</body>
</html>
