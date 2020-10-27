<?php
/* 淘宝订单推送客户端 */
/* 文档：http://help.fw199.com/docs/h7b/taobao-preface */ 
/* 请采用php cli 模式运行 */

require __DIR__ . '/vendor/autoload.php';

// 修改自己的appId和appSecret
$appId= 'xxxxxx';
$appSecret = 'bbbbbb';

$token = md5($appSecret . $appId . $appSecret);
$url = 'wss://open.fw199.com:443/acc?appid=' . $appId . "&token=" . $token;
  
$pid = posix_getpid(); //取得主进程ID 
$client= new WebSocket\Client($url); 
process_execute_beat($client);
while (true) {
    try {
          
        $message = $client->receive(); 
        echo '收到消息服务端的消息：' . $message . "\r\n"; 
        // 根据上面收到消息$message，进行相应的业务处理

    } catch (\WebSocket\ConnectionException $e) { 
        echo  '连接异常: ' . $e . "\r\n";
    }
}

$client->close();

// 心跳处理
function process_execute_beat($client ) {  
    $pid = pcntl_fork(); 
    if ($pid == -1 ) {  
            echo '创建子进程失败时返回-1'; 
            exit;  
    } else if($pid) {//主进程  
             pcntl_wait($status, WNOHANG); //取得子进程结束状态   
    } else {
        while(true) { 
            sleep(30);
            echo 'send beat to server' . "\r\n";
            $client->send('{"cmd":"beat"}');
       }
    }
} 
 

?>
 