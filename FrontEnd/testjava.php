<?php
require_once("Java.inc"); //必须包含的配置文件
java_require("Test.jar"); //引用包含的jar包

$test = new Java("Test"); //产生实例
$test->setName("哈哈,PHP调用JAVA的方法！"); //后面的调用就跟在php中调用类方法一样

echo "调用类Test的getName方法，返回值为：".$test->getName()."<br>";
echo "调用Test的add方法，返回值为：".$test->add(11.2, 15.7);
