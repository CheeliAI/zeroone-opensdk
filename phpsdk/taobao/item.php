<?php

    $APP_ID = "xxx" ;  // 开发者AppId
    $AppSecret = "xxx";   // 开发者密钥
    $TBSellerNick  = "xxx"; //要操作的商家，提前是商家要先授权给开发者
    $baseUrl = "https://open.fw199.com";
 


	// 发布一个淘宝商品(非天猫)
  function addItem() { 
		global $APP_ID;
		global $AppSecret;
		global $baseUrl;
		global $TBSellerNick;

		$apiHost =  $baseUrl  .   "/gateway/taobao/item/add";
		
		 // 上传主图，如果不想上传主图，字段main_img不要传入， 图片控制3M以内
		 $file  = "/Users/miller/Downloads/pdd.jpeg" ; 
	     $base64ImageLogo =  base64_encode(file_get_contents($file)); 

	    $config = array(
			"appid" =>   $APP_ID ,//平台ID号 
			"timestamp" => time()   , 
			"tb_seller_nick" =>  $TBSellerNick ,   
		       "location.state" => "浙江",
               "location.city"=> "杭州",
		       "num"=> "40",
		       "type" => "fixed",
		       "stuff_status"=> "new",
		       "title"=> "真皮男士增高鞋男高帮工装鞋-api", 
		       "desc"=> "这是一个好商品,但是不要拍",
		       "cid"=> "50012907", // 鞋类类目
		       "price"=> "2000",
		      // 尺码，品牌,鞋头款式  闭合方式 鞋面材质 帮面材质 帮面内里材质 上市年份季节 里料材质
		       "props"=>  "1627207:28321;20549:10145050;20000:519854451;122216351:30233;20490:115481;122216640:3323086;124128491:19597500;139520082:3323086;122216347:1586027483;122216587:6474787",
		       "sku_properties"=>  "1627207:28321;20549:10145050", 
		       "cid"=>  "50012907", // 鞋类类目
		       "sku_prices"=> "100",
		       "sku_quantities" =>  "20",
			   "main_img" => $base64ImageLogo, 
		      
		);
  
			$sign =  _signParams($config, $AppSecret );
			$config['sign'] = $sign ; 
			$response =   _curlPost( $apiHost, $config);  
		    // 请按实际业务需求处理接口返回结果，下面代码仅打印返回结果。 
			print $response;
		 
		  
	}
		


	// 获取后台供卖家发布商品的标准商品类目
	function getItemCats() { 
		global $APP_ID;
		global $AppSecret; 
		global $TBSellerNick;
		global $baseUrl;
		    $apiHost =  $baseUrl  .   "/gateway/taobao/itemcats/get";
			$config = array(
				"appid" =>   $APP_ID , 
				"timestamp" => time()  , 
				"tb_seller_nick" =>  $TBSellerNick ,  // 对应的卖家，注意卖家要先授权给开发者
			//	"cids" => "" , 
			//	"fields" =>  'cid,parent_cid,name,is_parent',  
			 	"parent_cid" => "50011740" ,   //比如查询鞋类下面的子类
				"datetime" => '2020-01-01 22:00:00' // date("Y-m-d H:i:s", time() ),
	 
			); 
				$sign =  _signParams($config, $AppSecret );
				$config['sign'] = $sign ;
				print_r($config);
				$response =   _curlPost( $apiHost,$config); 
	 
				// 请按实际业务需求处理接口返回结果，下面代码仅打印返回结果。 
				print $response ;
			 
		}


	// 获取标准商品类目属性
	function getItemProps() { 
		global $APP_ID;
		global $AppSecret; 
		global $TBSellerNick;
		global $baseUrl;
		    $apiHost =  $baseUrl  .   "/gateway/taobao/itemprops/get";
			$config = array(
				"appid" =>   $APP_ID , 
				"timestamp" => time()  , 
				"tb_seller_nick" =>  $TBSellerNick ,  // 对应的卖家，注意卖家要先授权给开发者
			 	"cid" => "50012907" , 
		    	"fields" =>  'pid, name, must, multi, prop_values',  
			  
			); 
				$sign =  _signParams($config, $AppSecret );
				$config['sign'] = $sign ;
				print_r($config);
				$response =   _curlPost( $apiHost,$config); 
	 
				// 请按实际业务需求处理接口返回结果，下面代码仅打印返回结果。 
				print $response ;
			 
		}

// 获取标准类目属性值
function getItemPropsValues() { 
	global $APP_ID;
	global $AppSecret; 
	global $TBSellerNick;
	global $baseUrl;
		$apiHost =  $baseUrl  .   "/gateway/taobao/itempropvalues/get";
		$config = array(
			"appid" =>   $APP_ID , 
			"timestamp" => time()  , 
			"tb_seller_nick" =>  $TBSellerNick ,  // 对应的卖家，注意卖家要先授权给开发者
		     "datetime" =>  "2000-01-01 22:00:00" ,
		 	"cid" => "50012907" , 
			"fields" =>  'cid,pid,prop_name,vid,name,name_alias,status,sort_order',  
		  
		); 
			$sign =  _signParams($config, $AppSecret );
			$config['sign'] = $sign ; 
			$response =   _curlPost( $apiHost,$config);  
			// 请按实际业务需求处理接口返回结果，下面代码仅打印返回结果。 
			print $response ;
		 
	}

	// 删除一个淘宝商品
function deleteItem() { 
	global $APP_ID;
	global $AppSecret; 
	global $TBSellerNick;
	global $baseUrl;
		$apiHost =  $baseUrl  .   "/gateway/taobao/item/delete";
		$config = array(
			"appid" =>   $APP_ID , 
			"timestamp" => time()  , 
			"tb_seller_nick" =>  $TBSellerNick ,  // 对应的卖家，注意卖家要先授权给开发者
		     "num_iid" =>  "635946082358" ,
		  
		  
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

     // 获取后台供卖家发布商品的标准商品类目
	// getItemCats();

	// 获取标准商品类目属性
	//   getItemProps();

	// 获取标准类目属性值
	// getItemPropsValues();

	// 添加一个宝贝
	// addItem();
	

	// 删除一个淘宝商品
	deleteItem();

?> 