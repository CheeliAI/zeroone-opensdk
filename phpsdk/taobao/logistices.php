<?php

    $APP_ID = "aaa" ;  // 开发者AppId
    $AppSecret = "bbbbb";   // 开发者密钥
    $TBSellerNick  = "百鞋馆"; //要操作的商家，提前是商家要先授权给开发者
    $baseUrl = "https://open.fw199.com";
      
    $LogisticesCompanyUrl = $baseUrl  .   "/gateway/taobao/logistices/getcompany";
    $LogisticesOnlineSendUrl = $baseUrl .   "/gateway/taobao/logistices/onlinesend";


  function getLogisticesCompany() { 
		global $APP_ID;
		global $AppSecret;
		global $LogisticesCompanyUrl;
		global $TBSellerNick;

    	$apiHost = $LogisticesCompanyUrl; 
	    $config = array(
			"appid" =>   $APP_ID ,//平台ID号 
			"timestamp" => time()  , 
			"tb_seller_nick" =>  $TBSellerNick ,  

	       //  是否查询推荐物流公司.可选值:true,false.如果不提供此参数,将会返回所有支持电话联系的物流公司. 
			"is_recommended" => "true" , //         

			// 推荐物流公司的下单方式.可选值:offline(电话联系/自己联系),online(在线下单),all(即电话联系又在线下单).
            // 此参数仅仅用于is_recommended 为ture时。就是说对于推荐物流公司才可用.如果不选择此参数将会返回推荐物流中支持电话联系的物流公司.
			"order_mode" =>  'offline', //   
			  
		);
 
			 
			$sign =  _signParams($config, $AppSecret );
			$config['sign'] = $sign ;
			$response =   _curlPost( $apiHost, $config);  
		    // 请按实际业务需求处理接口返回结果，下面代码仅打印返回结果。 
			print $response;
		 
		  
	}
		


	function LogisticesOnlineSend() { 
		global $APP_ID;
		global $AppSecret;
		global $LogisticesOnlineSendUrl;
		global $TBSellerNick;
	
			$apiHost = $LogisticesOnlineSendUrl; 
			$config = array(
				"appid" =>   $APP_ID , 
				"timestamp" => time()  , 
				"tb_seller_nick" =>  $TBSellerNick ,  // 对应的卖家，注意卖家要先授权给开发者
				"tid" => "1148987073290565830" , // 淘宝订单号
				"company_code" =>  'SF', //  物流公司代码
				"out_sid" => "SF1191992347149" ,   // 快递单号
				"flag" => "" ,   //  卖家交易备注旗帜，   可选值为：0(灰色), 1(红色), 2(黄色), 3(绿色), 4(蓝色), 5(粉红色)，说明：如果不想加旗帜，则传空串""
				"memo" => "" ,   // 卖家交易备注。最大长度: 1000个字节  ，说明：如果不想加旗帜，则传空串""
			); 
				$sign =  _signParams($config, $AppSecret );
				$config['sign'] = $sign ;
				$response =   _curlPost( $apiHost,$config); 
	 
				// 请按实际业务需求处理接口返回结果，下面代码仅打印返回结果。 
				print $response ;
			 
		}



	  function _curlPost($url,$params){
		$ch = curl_init();
		curl_setopt($ch, CURLOPT_URL,$url);
		curl_setopt($ch, CURLOPT_HEADER, 0);
		curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
		curl_setopt($ch, CURLOPT_TIMEOUT,300); //设置超时
		curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, FALSE); // https请求 不验证证书和hosts
		curl_setopt($ch, CURLOPT_SSL_VERIFYHOST, FALSE);
		curl_setopt($ch, CURLOPT_POSTFIELDS, $params);
		$result = curl_exec($ch);
		curl_close($ch);
		return $result;	
	}



  function _signParams($params , $secret) {
	$sign = $signstr = "";
	if(!empty($params)){
		ksort($params);
		reset($params);
		
		foreach ($params AS $key => $val) {
			if ($key == 'sign') { 
				 continue; 
			}
			$signstr .= $key.$val;
		}
		$sign = md5($secret.$signstr.$secret);
	}
	return $sign;
  
  }

 
  // 获取物流公司信息接口 
    getLogisticesCompany();

   // 淘宝物流发货
   LogisticesOnlineSend();

?> 