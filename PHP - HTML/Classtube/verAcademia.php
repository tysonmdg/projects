<?php
namespace es\ucm\fdi\aw;
require_once __DIR__.'/includes/config.php';

$tituloPagina = 'Ver academia - CLASSTUBE';

$academia = $_GET['nombre'];

$contenidoPrincipal = '';
$html=Academia::mostrarAcademia($academia);


$contenidoPrincipal .= <<<EOS
	<p> <h1>$html</h1> </p>
	EOS;


require __DIR__.'/includes/plantillas/plantilla.php';