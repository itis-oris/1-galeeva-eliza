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
<img src="images/workouts.png" alt="Workouts Header" class="header-image">
<div>

        <table>
            <caption>workout on ${workout.date()}</caption>
            <tr>
                <th>Exercise</th>
                <th>Category</th>
                <th>Set</th>
                <th>Reps</th>
                <th>Weight</th>
            </tr>
            <#list viewWorkout as w>
                <tr>
                    <td>${w.exercise()}</td>
                    <td>${w.category()}</td>
                    <td>${w.sets()}</td>
                    <td>${w.reps()}</td>
                    <td>${w.weight()}</td>
                </tr>
            </#list>
        </table>

</div>
<div>
    <th>
        <form action="/view_workouts" method="GET" style="position: fixed; top: 50%; left: 10px; transform: translateY(-50%);">
            <input type="hidden" name="page" value="${page - 1}">
            <button type="submit" class="navigation-button prev-button">❮ prev</button>
        </form>
    </th>
    <th>
        <form action="/view_workouts" method="GET" style="position: fixed; top: 50%; right: 10px; transform: translateY(-50%);">
            <input type="hidden" name="page" value="${page + 1}">
            <button type="submit" class="navigation-button next-button">next ❯</button>
        </form>
    </th>
</div>
</body>
</html>
