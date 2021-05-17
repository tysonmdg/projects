<?php

require_once __DIR__.'/includes/config.php';
require_once __DIR__.'/includes/FormularioEditarPerfil.php';

$form = new es\ucm\fdi\aw\FormularioEditarPerfil();
$htmlFormEditarPerfil = $form->gestiona();

$tituloPagina = 'Editar perfil - CLASSTUBE';

$contenidoPrincipal ='';

$nombre = $_GET['nombre'];

if (isset($_SESSION["login"]) && $_SESSION['nombre'] == $nombre) {
	
	$boton = "<form action=borrado.php?nombre=$nombre method='post'>
    			<input type='submit' name='deletePerfil' value='Borrar perfil' /></form>";


	$contenidoPrincipal .= <<<EOS
	<h1>Editar perfil</h1>
	<p>$htmlFormEditarPerfil</p>
	<p> $boton</p>
	EOS;
} else {
	$contenidoPrincipal .= <<<EOS
	<h1>Acceso denegado!</h1>
	<p>No tienes permisos suficientes para editar el perfil.</p>
	EOS;
}


require __DIR__.'/includes/plantillas/plantilla.php';