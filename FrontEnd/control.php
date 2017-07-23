<?php
require_once("Java.inc");
java_require("sql_analysis1.jar");
require_once 'runTime.php';

$sql_analysis=new Java("sql_analysis");

if (!isset($_GET['text'])||empty($_GET['text'])){
    echo "<script>alert('请输入命令');window.location.href='index.php'</script>";
}

$text_arr=explode(' ',$_GET['text']);

if ($text_arr[0]=='help'){
    header('location:help.php');
}elseif ($text_arr[0]=='select'){
    $starttime = microtime_float();
    if ($sql_analysis->execute($_GET['text'])){
        $runtime=number_format((microtime_float()-$starttime),4).'s';
        header('location:selectResult.php?runtime='.$runtime);
    }else{
        echo "<script>alert('查询失败');window.location.href='index.php'</script>";
    }
}elseif ($text_arr[0]=='insert'){
    $starttime = microtime_float();
    if ($sql_analysis->execute($_GET['text'])){
        $runtime=number_format((microtime_float()-$starttime),4).'s';
        header('location:insertResult.php?runtime='.$runtime);
    }else{
        echo "<script>alert('插入失败');window.location.href='index.php'</script>";
    }
}elseif ($text_arr[0]=='update'){
    $starttime = microtime_float();
    if ($sql_analysis->execute($_GET['text'])){
        $runtime=number_format((microtime_float()-$starttime),4).'s';
        header('location:updateResult.php?runtime='.$runtime);
    }else{
        echo "<script>alert('更新失败');window.location.href='index.php'</script>";
    }
}elseif ($text_arr[0]=='delete'){
    $starttime = microtime_float();
    if ($sql_analysis->execute($_GET['text'])){
        $runtime=number_format((microtime_float()-$starttime),4).'s';
        header('location:deleteResult.php?runtime='.$runtime);
    }else{
        echo "<script>alert('删除失败');window.location.href='index.php'</script>";
    }
}elseif ($text_arr[0]=='count'){
    $starttime = microtime_float();
    if ($sql_analysis->execute($_GET['text'])){
        $runtime=number_format((microtime_float()-$starttime),4).'s';
        header('location:CountResult.php?runtime='.$runtime);
    }else{
        echo "<script>alert('删除失败');window.location.href='index.php'</script>";
    }
}else{
    echo "<script>alert('命令错误');window.location.href='index.php'</script>";
}