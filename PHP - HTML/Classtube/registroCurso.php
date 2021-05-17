<?php

require_once __DIR__.'/includes/config.php';
require_once __DIR__.'/includes/FormularioRegistroCurso.php';

$form = new es\ucm\fdi\aw\FormularioRegistroCurso();
$htmlFormRegistro = $form->gestiona();

$tituloPagina = 'Registro de Cursos - CLASSTUBE';

$contenidoPrincipal = '';

if (isset($_SESSION["rol"]) && ($_SESSION["rol"] == 'Profesor' || $_SESSION["rol"] == 'Admin')) {
	$contenidoPrincipal .= <<<EOS
	<h1>Registro de Cursos</h1>
	<p>	$htmlFormRegistro </p>
	EOS;
} else {
	$contenidoPrincipal .= <<<EOS
	<h1>Acceso denegado!</h1>
	<p>Debes ser un profesor para poder registrar una academia..</p>
	EOS;
}

require __DIR__.'/includes/plantillas/plantilla.php';