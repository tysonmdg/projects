<?php
namespace es\ucm\fdi\aw;
require_once __DIR__.'/includes/config.php';


$tituloPagina = 'Borrar perfil - CLASSTUBE';

$nombre = $_GET['nombre'];

$contenidoPrincipal = '';

if (isset($_POST['deletePerfil'])) {
	Usuario::eliminaUsuario($nombre);
	if($_SESSION["nombre"] == $nombre)
	{
		header('Location: logout.php');
		exit();
	}

	$contenidoPrincipal .= <<<EOS
	<h1>Usuario eliminado correctamente</h1>
	EOS;
} else {
	$contenidoPrincipal .= <<<EOS
	<h1>Acceso denegado!</h1>
	<p>No tienes permisos suficientes para administrar la web.</p>
	EOS;
}

require __DIR__.'/includes/plantillas/plantilla.php';