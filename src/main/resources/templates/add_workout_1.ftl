<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>workout</title>
    <link rel="stylesheet" href="css/workout-2-style.css">
</head>
<body>
<div class="signup-image">
    <figure>
        <img src="images/thoughts.jpg" alt="sign up image">
    </figure>
    <a href="add_workout_2.jsp" class="signup-image-link">add exercise</a>
</div>
<form method="post" action="exercise_check" class="register-form" id="register-form">
    ${tables}
    <div class="form-group form-button">
        <input type="submit" name="save" id="save"
               class="form-submit" value="save"/>
    </div>
</form>
</body>

</html>
