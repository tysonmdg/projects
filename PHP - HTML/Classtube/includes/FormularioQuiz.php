<?php
namespace es\ucm\fdi\aw;

class FormularioQuiz extends Form
{
    public function __construct() {
        parent::__construct('formRegistro');
    }


    protected function generaCamposFormulario($datos, $errores = array()){
    	$nombreQuiz = $datos['nombreQuiz'] ?? '';
    	$instrucciones = $datos['instrucciones'] ?? '';

    	// Se generan los mensajes de error si existen.
        $htmlErroresGlobales = self::generaListaErroresGlobales($errores);
        $errorNombreQuiz = self::createMensajeError($errores, 'nombreQuiz', 'span', array('class' => 'error')); 
        $errorInstrucciones = self::createMensajeError($errores, 'instrucciones', 'span', array('class' => 'error'));

        $html = <<<EOF
        	<fieldset>
        		$htmlErroresGlobales

        		<div class="grupo-control">
                    <input class="control" type="text" name="nombreQuiz" value="$nombreQuiz" placeholder="Titulo"/>$errorNombreQuiz
                </div>
                <div class="grupo-control">
                    <input class="control" type="text" name="instrucciones" value="$instrucciones" placeholder="Instrucciones"/>$errorInstrucciones
                </div>

                <div class="grupo-control"><button type="submit" name="Crear">Crear </button>
                    </div>
                </fieldset>
        EOF;
        return $html;
    }

    protected function procesaFormulario($datos)
    {
    	$result = array();
    	$curso = $_GET['nombre'];
    	$nombreQuiz = $datos['nombreQuiz'] ?? null;

    	if ( empty($nombreQuiz) || mb_strlen($nombreQuiz) < 3 ) {
            $result['nombreQuiz'] = "El nombre del quiz tiene que tener una longitud de al menos 3 caracteres.";
        }
        
        $instrucciones = $datos['instrucciones'] ?? null;
        if ( empty($instrucciones) || mb_strlen($instrucciones) < 3 ) {
            $result['instrucciones'] = "Las instrucciones del quiz tienen que tener una longitud de al menos 3 caracteres.";
        }
        if (count($result) === 0) {
        	$quiz = Quiz::crea($nombreQuiz, $instrucciones, $curso);
            if ( ! $quiz ) {
                $result[] = "El quiz ya existe";
            } else {
                $id = $quiz->id();
                $result = "creaPregunta.php?nombre=$id";
            }
        }
        return $result;
    }
}