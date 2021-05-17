<?php
namespace es\ucm\fdi\aw;
require_once __DIR__.'/includes/config.php';

$tituloPagina = 'Inscrito - CLASSTUBE';

$contenidoPrincipal = '';
$idAcademia=$_GET['idAcademia'];

Academia::inscribirse($idAcademia);
$academia = Academia::buscaAcademiaId($idAcademia);
$academiaNombre = $academia->nombre();

$contenidoPrincipal .= <<<EOS
    <p> <h1>Te has inscrito correctamente.</h1> </p>
    <p><a href='verAcademia.php?nombre=$academiaNombre'> Volver a la Academia</a> </p>
    EOS;



require __DIR__.'/includes/plantillas/plantilla.php';