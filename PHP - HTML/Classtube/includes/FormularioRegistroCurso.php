<?php
namespace es\ucm\fdi\aw;

class FormularioRegistroCurso extends Form
{
    public function __construct() {
        parent::__construct('formRegistro');
    }

   
    protected function generaCamposFormulario($datos, $errores = array())
    {
        $nombre = $datos['nombre'] ?? '';
        $fechaI = $datos['fechaInicio'] ?? '';
        $fechaF = $datos['fechaFin'] ?? '';

        // Se generan los mensajes de error si existen.
        $htmlErroresGlobales = self::generaListaErroresGlobales($errores);
        $errorNombre = self::createMensajeError($errores, 'nombre', 'span', array('class' => 'error'));
        $errorFechaI = self::createMensajeError($errores, 'fechaInicio', 'span', array('class' => 'error'));
        $errorFechaF = self::createMensajeError($errores, 'fechaFin', 'span', array('class' => 'error'));

        $html = <<<EOF
            <fieldset>
                $htmlErroresGlobales
               
                <div class="grupo-control">
                    <input class="control" type="text" name="nombre" value="$nombre" placeholder="Nombre del curso"/>$errorNombre
                </div>
                <div class="grupo-control">
                    <input class="control" type="date" name="fechaInicio" value="$fechaI" placeholder="Fecha Inicio"/>$errorFechaI
                </div>
                <div class="grupo-control">
                    <input class="control" type="date" name="fechaFin" value="$fechaF" placeholder="Fecha Final"/>$errorFechaF
                </div>

                <div class="grupo-control"><button type="submit" name="registro">Registrar curso</button></div>
            </fieldset>
        EOF;
        return $html;
    }
    

    protected function procesaFormulario($datos)
    {
        $result = array();
        $academia = $_GET['nombre'];
    
        $nombre = $datos['nombre'] ?? null;
        if ( empty($nombre) || mb_strlen($nombre) < 4 ) {
            $result['nombre'] = "El nombre tiene que tener una longitud de al menos 4 caracteres.";
        }
        
        $hoy = date("d/m/Y", strtotime("yesterday"));

        $fechaI = $datos['fechaInicio'] ?? $hoy;
        if (empty($fechaI) || $fechaI <= $hoy) {
            $result['fechaInicio'] = "Debes introducir una fecha de Inicio válida";
        }
     
        $fechaF = $datos['fechaFin'] ?? $fechaI;
        if (empty($fechaF) || $fechaF <= $fechaI) {
            $result['fechaFin'] = "Debes introducir una fecha de Fin válida";
        }

        if (count($result) === 0) {
            $curso = Curso::crea($nombre, $fechaI, $fechaF, $academia);
            if ( ! $curso ) {
                $result[] = "El curso ya existe";
            } else {
                $nombre2 = $curso->nombre();
                $result = "verCurso.php?nombre=$nombre2&&academia=$academia";
            }
        }
        return $result;
    
    }
}