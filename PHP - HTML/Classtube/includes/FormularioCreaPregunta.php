<?php
namespace es\ucm\fdi\aw;

class FormularioCreaPregunta extends Form
{
    public function __construct() {
        parent::__construct('formRegistro');
    }


    protected function generaCamposFormulario($datos, $errores = array()){
        $pregunta = $datos['pregunta'] ?? '';
        $respuesta1 = $datos['respuesta1'] ?? '';
        $respuesta2 = $datos['respuesta2'] ?? '';
        $respuesta3 = $datos['respuesta3'] ?? '';
        $respuesta4 = $datos['respuesta4'] ?? '';

        // Se generan los mensajes de error si existen.
        $htmlErroresGlobales = self::generaListaErroresGlobales($errores);
        $errorPregunta = self::createMensajeError($errores, 'pregunta', 'span', array('class' => 'error')); 
        $errorRespuesta1 = self::createMensajeError($errores, 'respuesta1', 'span', array('class' => 'error'));
        $errorRespuesta2 = self::createMensajeError($errores, 'respuesta2', 'span', array('class' => 'error'));
        $errorRespuesta3 = self::createMensajeError($errores, 'respuesta3', 'span', array('class' => 'error'));
        $errorRespuesta4 = self::createMensajeError($errores, 'respuesta4', 'span', array('class' => 'error'));

        $html = <<<EOF
            <fieldset>
                $htmlErroresGlobales

                <div class="grupo-control">
                    <input class="control" type="text" name="pregunta" value="$pregunta" placeholder="Pregunta"/>$errorPregunta
                </div>
                <div class="grupo-control">
                    <input class="control" type="text" name="respuesta1" value="$respuesta1" placeholder="Introduzca la primera posible respuesta"/>$errorRespuesta1
                    <input type="radio" name="op1" value="1" checked/>
                </div>
                <div class="grupo-control">
                    <input class="control" type="text" name="respuesta2" value="$respuesta2" placeholder="Introduzca la segunda posible respuesta"/>$errorRespuesta2
                    <input type="radio" name="op1" value="2"/>
                </div>
                <div class="grupo-control">
                    <input class="control" type="text" name="respuesta3" value="$respuesta3" placeholder="Introduzca la tercera posible respuesta"/>$errorRespuesta3
                    <input type="radio" name="op1" value="3"/>
                </div>
                <div class="grupo-control">
                    <input class="control" type="text" name="respuesta4" value="$respuesta4" placeholder="Introduzca la cuarta posible respuesta"/>$errorRespuesta4
                    <input type="radio" name="op1" value="4"/>
                </div>

                <div class="grupo-control">
                    <input type="radio" name="op2" value="1" checked/>Continuar Añadiendo Preguntas
                </div>

                <div class="grupo-control">
                    <input type="radio" name="op2" value="2"/>Finalizar Quiz después de añadir esta pregunta
                </div>
                 
                <div class="grupo-control"><button type="submit" name="marcar">Continuar</button>
                </div>

                </fieldset>
        EOF;
        return $html;
    }

    protected function procesaFormulario($datos)
    {
    	$result = array();

        $idQuiz = $_GET['nombre'];
        $opcion = $datos['op1'];
        $continuar = $datos['op2'];

        $pregunta = $datos['pregunta'] ?? null;
        if ( empty($pregunta)) {
            $result['pregunta'] = "Introduzca la pregunta";
        }
        
        $respuesta1 = $datos['respuesta1'] ?? null;
        if ( empty($respuesta1) ) {
            $result['respuesta1'] = "Introduzca la primera respuesta";
        }

        $respuesta2 = $datos['respuesta2'] ?? null;
        if ( empty($respuesta2)) {
            $result['respuesta2'] = "Introduzca la segunda respuesta";
        }

        $respuesta3 = $datos['respuesta3'] ?? null;
        if ( empty($respuesta3)) {
            $result['respuesta3'] = "Introduzca la tercera respuesta";
        }

        $respuesta4 = $datos['respuesta4'] ?? null;
        if ( empty($respuesta4)) {
            $result['respuesta4'] = "Introduzca la cuarta respuesta";
        }


        if (count($result) === 0) {
             
            $preg = Pregunta::crea($pregunta, $idQuiz);

            if($opcion == 1) Respuesta::guardaRespuesta($respuesta1, $preg->id(), 1, 1);
            else Respuesta::guardaRespuesta($respuesta1, $preg->id(), 0, 1);

            if($opcion == 2) Respuesta::guardaRespuesta($respuesta2, $preg->id(),1, 2);
            else Respuesta::guardaRespuesta($respuesta2, $preg->id(), 0, 2);

            if($opcion == 3) Respuesta::guardaRespuesta($respuesta3, $preg->id(),1, 3);
            else Respuesta::guardaRespuesta($respuesta3, $preg->id(), 0, 3);

            if($opcion == 4) Respuesta::guardaRespuesta($respuesta4, $preg->id(),1, 4);
            else Respuesta::guardaRespuesta($respuesta4, $preg->id(), 0, 4);
            
            if (!$preg) {
                $result[] = "la pregunta ya existe";
            } else {
            
                if($continuar == 1)
                    $result = "creaPregunta.php?nombre=$idQuiz";
                else{
                    $quiz = Quiz::buscaQuizId($idQuiz);
                    $curso = Curso::buscaCursoId($quiz->curso());
                    $nombre = $curso->nombre();
                    $academia = $curso->academia();
                    $result = "verCurso.php?nombre=$nombre&&academia=$academia";
                }
            }
        }
        return $result;
    }
}