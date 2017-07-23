<?php
function microtime_float()
{
    list($usec, $sec) = explode(" ", microtime());
return ((float)$usec + (float)$sec);
}

$starttime = microtime_float();
$runtime=number_format((microtime_float()-$starttime),4).'s';

//echo 'RunTime:'.$runtime;
