<?php
function mostrarSaludo()
{
	if (isset($_SESSION["login"]) && ($_SESSION["login"] === true)) {
		echo "Bienvenido, <a class='user' href='perfil.php?nombre=${_SESSION['nombre']}'>" . $_SESSION['nombre'] . "</a> <a class='logout' href='logout.php'>Salir</a>";
	} else {
		echo "Usuario desconocido";
	}
}

if (!isset($_SESSION["login"])) {
	$ca = <<<EOS
	<div class="botones">
		<a href='registro.php' id="Registro">Registro</a>	
		<a href='login.php' id="login">Login</a>
	</div>
	EOS;
} else
	$ca = '';
?>
<header>

	<script src="js/comun/comun.js"></script>
	<link rel="stylesheet" href="css/comun/cabecera.css">

	<span id="buttonSidebar" style="font-size:60px;cursor:pointer" onclick="showNav()">&#9776;</span>

	<a href='index.php'><img id="logo" src="img/LogoColor.png" alt="ClassTube"></a>
	<div class="Formulario">
		<?= $ca ?>
		<div class="saludo"><?php
							mostrarSaludo();
		?></div>
	</div>
</header>