<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Geeker Day SQL</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
<!--    <script src="https://cdn.bootcss.com/html5shiv/3.7.3/html5shiv.min.js"></script>-->
<!--    <script src="https://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>-->
    <link rel="stylesheet" href="css/myCss.css">
</head>
<body>
<h1 align="center">欢迎进入Geeker Day SQL</h1>
<!--<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>-->
<!--<script src="js/bootstrap.min.js"></script>-->
<form action="control.php" method="get">
    <div class="form-group">
        <div class="input_data">
            <label for="sql">请输入你要查询的命令：</label>
            <input type="text" class="form-control" id="sql" name="text">
            <div class="btn_block">
                <input type="submit" class="btn btn-primary" value="查看结果">
                <button type="button" class="btn btn-danger" onclick="document.getElementById('sql').value=''">清空输入</button>
            </div>

        </div>
    </div>
</form>
</body>
</html>