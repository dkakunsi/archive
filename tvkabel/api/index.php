<?php
include("root.php");

$data = array(
  idPerusahaan => 17,
  kode => "ws01001"
);
$json = json_encode($data);
$json = "{\"idPerusahaan\":17, \"kode\":\"ws01001\"}";

$url = "http://uvs-t001.whelastic.net/api/tagihan/kode.php";
$rest = new Rest($url, $json, "GET");
$response = $rest->execute();
echo $response;
echo "<br /><br />";

$url = "http://uvs-t001.whelastic.net/api/pelanggan/kode.php";
$rest->set($url, $json, "GET");
$response = $rest->execute();
echo $response;
echo "<br /><br />";

$arr = array(
  username => "melky",
  password => "melky"
);
$arrJson = json_encode($arr);

$url = "http://uvs-t001.whelastic.net/api/login.php";
$rest->set($url, $arrJson, "POST");
$response = $rest->execute();
echo $response;
?>
