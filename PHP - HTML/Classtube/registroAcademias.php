<?php

require_once __DIR__.'/includes/config.php';
require_once __DIR__.'/includes/FormularioRegistroAcademias.php';

$form = new es\ucm\fdi\aw\FormularioRegistroAcademias();
$htmlFormRegistro = $form->gestiona();

$tituloPagina = 'Registro de academias - CLASSTUBE';

$contenidoPrincipal = '';

if (isset($_SESSION["rol"]) && $_SESSION["rol"] == 'Profesor') {
	$contenidoPrincipal .= <<<EOS
	<h1>Registro de Academias</h1>
	<p>	$htmlFormRegistro </p>
	EOS;
} else {
	$contenidoPrincipal .= <<<EOS
	<h1>Acceso denegado!</h1>
	<p>Debes ser un gestor para poder registrar una academia..</p>
	EOS;
}

require __DIR__.'/includes/plantillas/plantilla.php';