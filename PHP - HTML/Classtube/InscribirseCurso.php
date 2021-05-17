<?php
namespace es\ucm\fdi\aw;
require_once __DIR__.'/includes/config.php';

$tituloPagina = 'Apuntarse - CLASSTUBE';

$contenidoPrincipal = '';
$idCurso=$_GET['idCurso'];

Curso::inscribirse($idCurso);
$curso = Curso::buscaCursoId($idCurso);
$nombre = $curso->nombre();
$idAcademia = $curso->academia();
$academia = Academia::buscaAcademiaId($idAcademia);
$academiaNombre = $academia->nombre();
$contenidoPrincipal .= <<<EOS
    <p> <h1>Te has inscrito correctamente.</h1> </p>
    <p><a href='verCurso.php?nombre=$nombre&&academia=$idAcademia'> Volver al Curso</a> </p>
    <p><a href='verAcademia.php?nombre=$academiaNombre'> Volver a la Academia</a> </p>
    EOS;



require __DIR__.'/includes/plantillas/plantilla.php';