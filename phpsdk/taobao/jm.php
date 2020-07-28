<?php
	  
  
	  
  $CheeliPrivateKey = "i234@378123456p#";// User Key，请修改为智能发货提供给你的KEY


// 加密/Encrypt
function CheeliEncrypt($data) { 
	global $CheeliPrivateKey ; 
	$iv     = $CheeliPrivateKey; 
 	$encrypted = mcrypt_encrypt(MCRYPT_RIJNDAEL_128, $CheeliPrivateKey , $data, MCRYPT_MODE_CBC, $iv);
	return base64_encode($encrypted);
}

// 解密/Decrypt
function CheeliDecrypt($encrypteddata){
	global $CheeliPrivateKey ;
	$iv   = $CheeliPrivateKey; 
	$b4encryptedData = base64_decode($encrypteddata);
	$decrypted = mcrypt_decrypt(MCRYPT_RIJNDAEL_128, $CheeliPrivateKey , $b4encryptedData, MCRYPT_MODE_CBC, $iv);
	return $decrypted; 

}
 

//demo only 



$tid   = "473183814314";  

//加密订单号tid
$encrypt_tid =  CheeliEncrypt($tid); 
$msg =  "encrypted tid:" .  $encrypt_tid; 
echo $msg ; 

//解密订单号tid
$source_tid = CheeliDecrypt($encrypt_tid); 
$msg = "source tid:" .  $source_tid; 
echo $msg; 
?> 
