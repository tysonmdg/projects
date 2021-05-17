<?php
namespace es\ucm\fdi\aw;

class FormularioResponderPregunta extends Form
{

    public function __construct() {
        parent::__construct('formRegistro');
    }

    

    protected function generaCamposFormulario($datos, $errores = array()){
        
        $idPregunta = $_GET['nombre'];
        $html = Pregunta::mostrarPregunta($idPregunta);
        $html .= '<div class="grupo-control"><button type="submit" name="marcar">Continuar</button></div>';
        return $html;
    }

    protected function procesaFormulario($datos)
    {
    	$result = array();

        $idPregunta = $_GET['nombre'];
        $opcion = $datos['op1'];

        Respuesta::guardaMiRespuesta($opcion, $idPregunta);


        $pregunta = Pregunta::buscaPreguntaPorId($idPregunta);
        $quiz = Quiz::buscaQuizId($pregunta->quiz());
        
        if($pregunta->numPregunta() < $quiz->numPreguntas()){
            $idPregunta = $idPregunta + 1;
            $result = "verPregunta.php?nombre=$idPregunta";
        }else{
            Quiz::calcularNotaQuiz($quiz->id());
            $curso=Curso::buscaCursoId($quiz->curso());
            $cursoNombre = $curso->nombre();
            $academia = Academia::buscaAcademiaId($curso->academia());
            $academiaId = $academia->id();
            $result = "verCurso.php?nombre=$cursoNombre&&academia=$academiaId";
        }

        return $result;
    }
}