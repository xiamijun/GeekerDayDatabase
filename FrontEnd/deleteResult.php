<?php
//require_once 'runTime.php';
//$starttime = microtime_float();
//?>
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
    <link rel="stylesheet" href="css/helpCss.css">
</head>
<body>
<h1 align="center">删除成功</h1>
<!--<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>-->
<!--<script src="js/bootstrap.min.js"></script>-->
<button type="button" class="btn btn-primary" onclick="window.location.href='index.php';">
    返回继续输入
</button>
<?php

//$runtime=number_format((microtime_float()-$starttime),4).'s';

echo 'RunTime:'.$_GET['runtime'];
?>
</body>
</html>