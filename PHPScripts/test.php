<?php

echo password_hash('Memomhtt011',PASSWORD_DEFAULT);

if(password_verify('Memomhtt011','$2y$10$mOL8PeJzc10DXBwIEnzZm.bU02PD/SQZ0nCOkLgcnOnOA8f1FBpHK'))
	echo "<br>Done!";

?>