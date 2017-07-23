<?php
require_once("Java.inc");
java_require("sql_analysis1.jar");
//require_once 'runTime.php';
//
//$starttime = microtime_float();
$sql_analysis=new Java("sql_analysis");

//查询结果，\n隔开
$bigResult=$sql_analysis->getResult();

$smallResult=array();
$smallResult=explode("\n",$bigResult);

//print_r($smallResult);

?>

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
<h1 align="center">查询结果</h1>
<!--<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>-->
<!--<script src="js/bootstrap.min.js"></script>-->
<div class="help_table">
    <table class="table table-cover">
        <thead>
        <tr>
            <th>姓名</th>
            <th>电话</th>
            <th>邮箱</th>
            <th>公司</th>
            <th>部门</th>
            <th>职位</th>
        </tr>
        </thead>
        <tbody>
        <?php
        header('content-type:text/html;charset=utf-8');
        for ($i=0;$i<count($smallResult);$i++){

            $data=explode("\t",$smallResult[$i]);

            ?>
            <tr>
                <td><?php echo $data[0]; ?></td>
                <td><?php echo $data[1]; ?></td>
                <td><?php echo $data[2]; ?></td>
                <td><?php echo $data[3]; ?></td>
                <td><?php echo $data[4]; ?></td>
                <td><?php echo $data[5]; ?></td>
            </tr>
            <?php
        }
        ?>

        </tbody>
    </table>
</div>
<button type="button" class="btn btn-primary" onclick="window.location.href='index.php';">
    返回继续输入
</button>
<?php

//$runtime=number_format((microtime_float()-$starttime),4).'s';

//echo 'RunTime:'.$runtime;
echo 'RunTime:'.$_GET['runtime'];
?>
</body>
</html>