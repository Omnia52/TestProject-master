<?php

require 'init.php';

$Acc=$_POST["Acc"];
$Pass=$_POST["Password"];

$sql="SELECT Password FROM `doctor` WHERE Email='$Acc'";
$result=mysqli_query($conn,$sql);
$response= array();

if(mysqli_num_rows($result)==1){
	$row = mysqli_fetch_assoc($result);
	$PasswordHashed= $row["Password"];
	if(password_verify($Pass,$PasswordHashed)){
		array_push($response, array('code'=>"Success",'message'=>"Done"));
		echo json_encode($response);
	}
	else{
		array_push($response, array('code'=>"Fail",'message'=>"Wrong Password!"));
		echo json_encode($response);
	}
}
else{
	array_push($response, array('code'=>"Fail",'message'=>"Wrong Email !"));
	echo json_encode($response);

}

mysqli_close($conn);

?>