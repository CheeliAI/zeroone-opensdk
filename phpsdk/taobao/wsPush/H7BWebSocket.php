<?php
/**
 * 蜂巢开放平台websocket客户端
 * http://help.fw199.com/docs/h7b/taobao-preface
 * Auth:Miller
 * Date:2020-11-11
 */

class H7BWebSocket
{
    protected $sp; 
    protected $host;
    protected $port;
    protected $headers;
    protected $error_string;
    protected $ssl = false ;
    protected $persistant = false ; // 建议false
    protected  $timeout=10   ; 
    protected  $path=10; 
 
    protected $isConnecting  = 0 ;

     function __construct($host='',$port=80,$headers='',&$error_string='', $ssl = false,  $path = '/'){
          $this->host = $host;
          $this->port = $port;
          $this->headers = $headers;
          $this->error_string = $error_string;
          $this->ssl = $ssl;
          $this->path = $path;

          $this->isConnecting = 0;
        
      }


    public function Connect(){ 
         if ($this->isConnecting == 1 ) {  
             return ;
         }
     
       $this->isConnecting = 1;
       echo "start  connecting .....  " ."\r\n";
       if ($this->sp) {
        stream_socket_shutdown($this->sp, STREAM_SHUT_WR);
        fclose($this->sp);
        $this->sp = NULL; 
       }
       sleep(5); 
      // Generate a key (to convince server that the update is not random)
      // The key is for the server to prove it i websocket aware. (We know it is)
      $key=base64_encode(openssl_random_pseudo_bytes(16)); 
      $header = "GET " . $this->path . " HTTP/1.1\r\n"
        ."Host: $this->host\r\n"
        ."pragma: no-cache\r\n"
        ."Upgrade: WebSocket\r\n"
        ."Connection: Upgrade\r\n"
        ."Sec-WebSocket-Key: $key\r\n"
        ."Sec-WebSocket-Version: 13\r\n";
    
      // Add extra headers
      if(!empty( $this->headers )) foreach($this->headers as $h)  $header .=$h."\r\n";
    
      // Add end of header marker
      $header .="\r\n";
    
      // Connect to server
      $this->host = $this->host ? $this->host : "127.0.0.1";
      $this->port = $this->port <1 ? 80 : $this->port;
       $address = ($this->ssl ? 'ssl://' : '') . $this->host . ':' . $this->port;
      // put in persistant ! if used in php-fpm, no handshare if same.
      if ($this->persistant)
     {
        $newsp = stream_socket_client( $address,  $errno, $errstr, $this->timeout, STREAM_CLIENT_CONNECT | STREAM_CLIENT_PERSISTENT);
         $this->sp = $newsp;
     }

      else { 
        $newsp = stream_socket_client( $address,  $errno,  $errstr, $this->timeout);
        $this->sp = $newsp;
      }

      if(!$this->sp){
        $this->error_string = "Unable to connect to websocket server: $errstr ($errno)";
        echo  $this->error_string;
        return false;
      }
    
      // Set timeouts
      stream_set_timeout($this->sp,$this->timeout);
    
      if (!$this->persistant  or ftell($this->sp) === 0) {
    
        //Request upgrade to websocket
        $rc = fwrite($this->sp,$header);
        if(!$rc){
          $this->error_string
            = "Unable to send upgrade header to websocket server: $errstr ($errno)";
            echo  $this->error_string;
          return false;
        }
    
        // Read response into an assotiative array of headers. Fails if upgrade failes.
        $reaponse_header=fread($this->sp, 1024);
    
        // status code 101 indicates that the WebSocket handshake has completed.
        if (stripos($reaponse_header, ' 101 ') === false
          || stripos($reaponse_header, 'Sec-WebSocket-Accept: ') === false) {
            $this->error_string = "Server did not accept to upgrade connection to websocket."
            .$reaponse_header. E_USER_ERROR;
            echo  $this->error_string;
          return false;
        }
        // The key we send is returned, concatenate with "258EAFA5-E914-47DA-95CA-
        // C5AB0DC85B11" and then base64-encoded. one can verify if one feels the need...
    
      }
     
      $this->isConnecting = 0;  
      
    }
     
    function websocket_write( $data, $final=true){
       
      // Assamble header: FINal 0x80 | Opcode 0x02
      $header=chr(($final?0x80:0) | 0x02); // 0x02 binary
    
      // Mask 0x80 | payload length (0-125)
      if(strlen($data)<126) $header.=chr(0x80 | strlen($data));
      elseif (strlen($data)<0xFFFF) $header.=chr(0x80 | 126) . pack("n",strlen($data));
      else $header.=chr(0x80 | 127) . pack("N",0) . pack("N",strlen($data));
    
      // Add mask
      $mask=pack("N",rand(1,0x7FFFFFFF));
      $header.=$mask;
    
      // Mask application data.
      for($i = 0; $i < strlen($data); $i++)
        $data[$i]=chr(ord($data[$i]) ^ ord($mask[$i % 4]));
    
      return fwrite($this->sp,$header.$data);
    }
    
    /*============================================================================*\
      Read from websocket
    
      string websocket_read(resource $handle [,string &error_string])
    
      read a chunk of data from the server, using hybi10 frame encoding
    
      handle
        the resource handle returned by websocket_open, if successful
    
      error_string (optional)
        A referenced variable to store error messages, i any
    
      Read
    
      Note:
        - This implementation waits for the final chunk of data, before returning.
        - Reading data while handling/ignoring other kind of packages
     \*============================================================================*/
    function websocket_read( &$error_string=NULL){
      
      if( $this->sp == NULL || !is_resource($this->sp)  ){
        return false;
      }
    
      // $status = socket_get_status ($this->sp);  
      // echo 'socket status :' .  var_dump($status)   .  "\r\n";
      $data="";
    
      do{
        // Read header     $header=fread($sp,2);
        $header=fread($this->sp,2);
        if(!$header){
          $error_string = "Reading header from websocket failed.";
           // echo $error_string;   
          return false;
        }
    
        $opcode = ord($header[0]) & 0x0F;
        $final = ord($header[0]) & 0x80;
        $masked = ord($header[1]) & 0x80;
        $payload_len = ord($header[1]) & 0x7F;
    
        // Get payload length extensions
        $ext_len = 0;
        if($payload_len >= 0x7E){
          $ext_len = 2;
          if($payload_len == 0x7F) $ext_len = 8;
          $header=fread($this->sp,$ext_len);
          if(!$header){
            $error_string = "Reading header extension from websocket failed.";
            echo $error_string;
            return false;
          }
    
          // Set extented paylod length
          $payload_len= 0;
          for($i=0;$i<$ext_len;$i++)
            $payload_len += ord($header[$i]) << ($ext_len-$i-1)*8;
        }
    
        // Get Mask key
        if($masked){
          $mask=fread($this->sp,4);
          if(!$mask){
            $error_string = "Reading header mask from websocket failed.";
            echo $error_string;
            return false;
          }
        }
    
        // Get payload
        $frame_data='';
        do{
          $frame= fread($this->sp,$payload_len);
          if(!$frame){
            $error_string = "Reading from websocket failed.";
            echo $error_string;
            return false;
          }
          $payload_len -= strlen($frame);
          $frame_data.=$frame;
        }while($payload_len>0);
    
        // Handle ping requests (sort of) send pong and continue to read
        if($opcode == 9){
          // Assamble header: FINal 0x80 | Opcode 0x0A + Mask on 0x80 with zero payload
          fwrite($this->sp,chr(0x8A) . chr(0x80) . pack("N", rand(1,0x7FFFFFFF)));
          continue;
    
        // Close
        } elseif($opcode == 8){
          fclose($this->sp);
           
    
        // 0 = continuation frame, 1 = text frame, 2 = binary frame
        }elseif($opcode < 3){
          // Unmask data
          $data_len=strlen($frame_data);
          if($masked)
            for ($i = 0; $i < $data_len; $i++)
              $data.= $frame_data[$i] ^ $mask[$i % 4];
          else
            $data.= $frame_data;
    
        }else 
          continue;
     
      } while(!$final);
    
      return $data;
    } 

  } // cls


?>
