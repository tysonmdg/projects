<?php
namespace es\ucm\fdi\aw;
require_once __DIR__.'/includes/config.php';

$tituloPagina = 'Salir de la academia - CLASSTUBE';

$contenidoPrincipal = '';
$idAcademia=$_GET['idAcademia'];

Academia::salirse($idAcademia);
$academia = Academia::buscaAcademiaId($idAcademia);
$academiaNombre = $academia->nombre();

$contenidoPrincipal .= <<<EOS
    <p> <h1>Has salido de la academia correctamente.</h1> </p>
    <p><a href='verAcademia.php?nombre=$academiaNombre'> Volver a la Academia</a> </p>
    EOS;



require __DIR__.'/includes/plantillas/plantilla.php';