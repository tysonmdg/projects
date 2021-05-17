<?php
namespace es\ucm\fdi\aw;
require_once __DIR__.'/includes/config.php';


$tituloPagina = 'Borrar quiz - CLASSTUBE';

$id = $_GET['nombre'];

$quiz = Quiz::buscaQuizId($id);
$curso = Curso::buscaCursoId($quiz->curso());
$nombre = $curso->nombre();
$idAcademia = $curso->academia();
$academia = Academia::buscaAcademiaId($idAcademia);
$academiaNombre = $academia->nombre();

$contenidoPrincipal = '';

if (isset($_POST['delete'])) {
    Quiz::eliminaQuiz($id);

    $contenidoPrincipal .= <<<EOS
        <h1>Quiz eliminado correctamente</h1>
        <p><a href='verCurso.php?nombre=$nombre&&academia=$idAcademia'> Volver al Curso</a></p>
        <p><a href='verAcademia.php?nombre=$academiaNombre'>Volver a la Academia</a></p>
    EOS;
} else {
    $contenidoPrincipal .= <<<EOS
    <h1>Acceso denegado!</h1>
    <p>No tienes permisos suficientes para administrar la web.</p>
    EOS;
}

require __DIR__.'/includes/plantillas/plantilla.php';