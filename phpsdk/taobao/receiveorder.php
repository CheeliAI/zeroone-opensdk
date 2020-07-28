<?php
	  
	   
	  
$tmpfile = 'push_order_log.txt'; // 临时文件，用于保存接收到的文件流
  
    $content = file_get_contents('php://input');
 
file_put_contents($tmpfile, $content, true); 

echo '{"StatusCode":"0","Msg":""}';

?> 
