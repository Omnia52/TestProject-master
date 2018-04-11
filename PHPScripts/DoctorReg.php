<?php
require 'init.php';

$Pass=$_POST["Password"];
$Email=$_POST["Email"];

$sql="SELECT * FROM `doctor` WHERE Email ='$Email'";

$result=mysqli_query($conn,$sql);
$response = array();

if(mysqli_num_rows($result)==1){
	$sql2="SELECT Password FROM `doctor` WHERE Email='$Email'";
	$passQuery=mysqli_fetch_assoc(mysqli_query($conn,$sql2));

	if($passQuery["Password"]!=" "){
		array_push($response, array('code'=>"AlreadyCreated",'message'=>"You Are Created the Account Before! Please Sign in"));
		echo json_encode($response);
	}
	else{
		$passwordHashed=password_hash($Pass,PASSWORD_DEFAULT);
		$sql3="UPDATE `doctor` SET `Password`='$passwordHashed' WHERE `Email`='$Email'";
		if(mysqli_query($conn,$sql3)){
			array_push($response, array('code'=>"Done",'message'=>"Thanks For complete your Data !"));
			echo json_encode($response);
		}
		else{
		array_push($response, array('code'=>"Fail",'message'=>"Something went wrong !"));
			echo json_encode($response);

		}
	}
}
else{
	array_push($response, array('code'=>"Fail",'message'=>"Wrong Email ! Please Check your data at the department managment !"));
		echo json_encode($response);
}

mysqli_close($conn);

?>