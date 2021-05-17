<?php
namespace es\ucm\fdi\aw;
require_once __DIR__.'/includes/config.php';
require_once __DIR__.'/includes/FormularioEditarAcademia.php';

$form = new FormularioEditarAcademia();
$htmlFormEditarPerfil = $form->gestiona();

$tituloPagina = 'Editar academia - CLASSTUBE';

$contenidoPrincipal ='';

$idAcademia = $_GET['nombre'];
$gestor = Academia::esGestor($_SESSION['nombre'], $idAcademia);

if ($gestor) {

	$contenidoPrincipal .= <<<EOS
	<h1>Editar perfil</h1>
	<p>$htmlFormEditarPerfil</p>
	EOS;
} else {
	$contenidoPrincipal .= <<<EOS
	<h1>Acceso denegado!</h1>
	<p>No tienes permisos suficientes para editar la academia.</p>
	EOS;
}


require __DIR__.'/includes/plantillas/plantilla.php';