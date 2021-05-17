<?php
namespace es\ucm\fdi\aw;
require_once __DIR__.'/includes/config.php';

$tituloPagina = 'Lista de usuarios - CLASSTUBE';

$contenidoPrincipal = '';

$contenidoPrincipal = '';
if(isset($_GET['nombre'])) $nombre=$_GET['nombre'];

if(empty($nombre))
	$htmlU=Usuario::listaUsuarios("");
else 
	$htmlU=Usuario::listaUsuarios("$nombre");

if($htmlU == '') $htmlU = 'No se han encontrado usuarios.';
$html = "<p><h1>Usuarios:</h1></p> <p>$htmlU </p>";

$contenidoPrincipal .= <<<EOS
	<p> <h2>$html</h2> </p>
	EOS;



require __DIR__.'/includes/plantillas/plantilla.php';