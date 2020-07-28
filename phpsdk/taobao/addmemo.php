  
<?php
 
 
 function send_post($url, $post_data) { 
    $postdata = http_build_query($post_data);  
    $options = array( 
      'http' => array( 
        'method' => 'POST', 
        'header' => 'Content-type:application/x-www-form-urlencoded;charset=utf-8', 
        'content' => $postdata, 
        'timeout' => 15 * 60 // 超时时间（单位:s） 
      ) 
    ); 
    $context = stream_context_create($options); 
    $result = file_get_contents($url, false, $context);   
    return $result; 
  }

 
 $url  = "http://tbapi.fw199.com/tbbridge/trade/addmemo";  
 $userkey  = ""; //private key  ，please change ! 自行修改
 $sellerid =  " "; //卖家ID please change !  自行修改
 $tid = "631267075544565831" ;// 订单号  please change !  自行修改  
 $memo ="很好";// 备注内容,  自行修改 
 $flag = 1;// 交易备注旗帜，可选值为：0(灰色),1(红色),2(黄色),3(绿色),4(蓝色),5(粉红色)，默认值为0	 , 自行修改  

 $token = md5($userkey .  $sellerid . $tid   ); 
 $post_data = array( 
    'tb_seller_id' => $sellerid , 
    'tid' =>  $tid,
    'memo' =>  $memo,
    'flag' =>  $flag ,
    'token' =>  $token 
  );
  
   $return_content= send_post($url, $post_data);  
   echo $return_content; 
 
 ?>