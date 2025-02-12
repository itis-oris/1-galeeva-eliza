<!DOCTYPE html>
<html>
<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script>
        $(document).ready(function () {
            var i = 1;
            var adding = '<tr id="row' + i + '">' +
                '<td>' +
                '<select id="category"> ' +
                '<option value="quads">quads</option> ' +
                '<option value="chest">chest</option> ' +
                '</select></td>' +
                '<td><input type="text" name="sets' + i + '" id="sets' + i + '"/></td>' +
                '<td><input type="text" name="reps' + i + '" id="reps' + i + '"/></td>' +
                '<td><button type="button" name="remove" id="' + i + '" class="btn btn-danger btn_remove">X</button></td></tr>';
            $("#addRow").click(function () {
                $("#addWorkout").append(adding);
                i++;
            });


            $(document).on('click', '.btn_remove', function () {
                var button_id = $(this).attr("id");
                $('#row' + button_id + '').remove();
            });

            $("#submit").click(function () {
                // check for duplicate model numbers
                var modelNumbers = $('input[name^="model"]').map(function () {
                    return this.value;
                }).get();
                var duplicateModels = modelNumbers.filter(function (item, index) {
                    return modelNumbers.indexOf(item) != index;
                });
                if (duplicateModels.length > 0) {
                    alert("Duplicate model numbers found: " + duplicateModels.join(", "));
                    $('input[name^="model"]').each(function () {
                        if (duplicateModels.indexOf(this.value) != -1) {
                            $(this).closest('tr').css("background-color", "red");
                        }
                    });
                } else {

                }
            });
        });
    </script>

</head>
<body>
<table id="addWorkout">
<%--        <td>--%>
<%--            <select name="idCountry">--%>
<%--                <c:forEach items="${countries}" var="country">--%>
<%--                <option value="${country.id}">${contry.countryName}</option>--%>
<%--                </c:forEach>--%>
<%--        </td>--%>

    <tr>
        <th>
            category
        </th>
        <th>Brand</th>
        <th>Model Name</th>
        <th>Price</th>
        <th>Action</th>
    </tr>
</table>
<button id="addRow">Add New Row</button>
<button id="submit">Submit</button>
</body>
</html>