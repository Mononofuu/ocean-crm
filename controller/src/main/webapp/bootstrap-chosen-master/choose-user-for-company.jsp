<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="bootstrap-chosen-master/bootstrap-chosen.css" />
    <link rel="stylesheet" href="bootstrap-chosen-master/bootstrap-chosen.less" />
    <link rel="stylesheet" href="bootstrap-chosen-master/bootstrap-chosen.scss" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
    <script src="http://harvesthq.github.io/chosen/chosen.jquery.js"></script>
    <script>
      $(function() {
        $('.chosen-select').chosen();
        $('.chosen-select-deselect').chosen({ allow_single_deselect: true });
      });
    </script>
</head>
  <body>
        <div class="form-group">
            <label>Ответственный</label>
            <select data-placeholder="Выбрать..." class="chosen-select" tabindex="2">
              <option value=""></option>
              <option value="User1">Юзер1</option>
                <option value="User2">Юзер2</option>
                <option value="User3">Юзер3</option>
                <option value="User4">Юзер4</option>
                <option value="User5">Юзер5</option>
            </select>
        </div>
  </body>
</html>
