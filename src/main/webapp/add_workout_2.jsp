<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Add Exercise</title>

    <!-- Font Icon -->
    <link rel="stylesheet"
          href="fonts/material-icon/css/material-design-iconic-font.min.css">

    <!-- Main css -->
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<div class="main">
    <section class="signup">
        <div class="container">
            <div class="signup-content">
                <div class="signup-form">
                    <h2 class="form-title">add exercise</h2>
                    <form method="post" action="add_workout" class="register-form"
                          id="register-form">
                        <div class="form-group">
                            <label for="category"></label> <input
                                type="text" name="category" id=category placeholder="muscle group"/>
                        </div>
                        <div class="form-group">
                            <label for="exercise"></label> <input
                                type="text" name="exercise" id="exercise" placeholder="exercise"/>
                        </div>
                        <div class="form-group">
                            <label for="sets"></label> <input
                                type="text" name="sets" id="sets" placeholder="sets"/>
                        </div>
                        <div class="form-group form-button">
                            <input type="submit" name="save" id="save"
                                   class="form-submit" value="save"/>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>


</div>
<!-- JS -->
<script src="vendor/jquery/jquery.min.js"></script>
<script src="js/main.js"></script>


</body>
</html>