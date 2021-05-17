<?php
namespace es\ucm\fdi\aw;
require_once __DIR__.'/includes/config.php';


$tituloPagina = 'Borrar academia - CLASSTUBE';

$id = $_GET['nombre'];

$contenidoPrincipal = '';

if (isset($_POST['delete'])) {
	Academia::eliminaAcademia($id);

	$contenidoPrincipal .= <<<EOS
	<h1>Academia eliminada correctamente</h1>
	EOS;
} else {
	$contenidoPrincipal .= <<<EOS
	<h1>Acceso denegado!</h1>
	<p>No tienes permisos suficientes para administrar la web.</p>
	EOS;
}

require __DIR__.'/includes/plantillas/plantilla.php';