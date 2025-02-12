<!DOCTYPE html>
<html lang="en">
<head>
    <title>workouts</title>
    <meta charset="utf-8">
    <style>
        body {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: flex-start;
            height: 200vh;
            margin: 0;
            font-family: Arial, sans-serif;
        }

        .header-image {
            width: 100%;
            max-width: 800px;
            margin: 20px auto;
        }

        table {
            border-collapse: collapse;
            width: 90%;
            text-align: left;
            background-color: #f8bddc;
            margin-top: 20px;
        }

        th, td {
            border: 1px solid #f16aa6;
            padding: 8px;
        }

        th {
            background-color: #f46ab3;
            font-weight: bold;
        }

        caption {
            font-size: 1.5em;
            margin-bottom: 10px;
            font-weight: bold;
        }

        tr:nth-child(even) {
            background-color: #f19dcf;
        }
    </style>
</head>
<body>
<img src="images/progress.png" alt="Workouts Header" class="header-image">
<div>

    <#list progress as category_exercise, list_progress>
        <table>
            <caption>${category_exercise.exercise()} (${category_exercise.category()})</caption>
            <tr>
                <th>Date</th>
                <th>Reps</th>
                <th>Weight</th>
            </tr>
            <#list list_progress as l>
                <tr>
                    <td>${l.date()}</td>
                    <td>${l.reps()}</td>
                    <td>${l.weight()}</td>
                </tr>
            </#list>
        </table>
    </#list>

</div>
</body>
</html>
