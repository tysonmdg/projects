<?php
namespace es\ucm\fdi\aw;

class Respuesta
{

    public static function buscaRespuestaPorNumero($numRespuesta, $pregunta)
    {
        $app = Aplicacion::getSingleton();
        $conn = $app->conexionBd();
        $query = sprintf("SELECT * FROM respuestas WHERE NumRespuesta = '%s' AND Pregunta = '%s'"
            , $conn->real_escape_string($numRespuesta)
            , $conn->real_escape_string($pregunta));
        $rs = $conn->query($query);
        $result = false;
        if ($rs) {
            if ( $rs->num_rows == 1) {
                $fila = $rs->fetch_assoc();
                $respuesta = new Respuesta($fila['Id'],$fila['Respuesta'],$fila['Pregunta'], $fila['Correcta'], $fila["numRespuesta"]);
                $respuesta->id = $fila['Id'];
                $result = $respuesta;
            }
            $rs->free();
        } else {
            echo "Error al consultar en la BD: (" . $conn->errno . ") " . utf8_encode($conn->error);
            exit();
        }
        return $result;
    }

    public static function mostrarRespuesta($numRespuesta, $idPregunta)
    {
        $respuesta = self::buscaRespuestaPorNumero($numRespuesta, $idPregunta);
        $html = $respuesta->respuesta;

        return $html;

    }



    public static function guardaRespuesta($datos, $idPregunta, $correcta, $numRespuesta){
        $app = Aplicacion::getSingleton();
        $conn = $app->conexionBd();

        $id = self::lastIdRespuestas();
        $query=sprintf("INSERT INTO respuestas VALUES('%s', '%s', '%s', '%s', '%s')"
        , $conn->real_escape_string($id)
        , $conn->real_escape_string($datos)
        , $conn->real_escape_string($idPregunta)
        , $conn->real_escape_string($correcta)
        , $conn->real_escape_string($numRespuesta));
        if (!$conn->query($query) ) {
            echo "Error al insertar2 en la BD: (" . $conn->errno . ") " . utf8_encode($conn->error);
            exit();
        }

    }

    
    public static function lastIdRespuestas()
    {
        $app = Aplicacion::getSingleton();
        $conn = $app->conexionBd();
        $lastId = "SELECT Id FROM respuestas ORDER BY Id DESC LIMIT 1";
        $res = mysqli_query($conn, $lastId);
        $i = 0;
        while ($registro = mysqli_fetch_array($res)){
        $i = intval($registro['Id']);
        }
        $ress = $i + 1;
        return $ress;
    }

    public static function guardaMiRespuesta($opcion, $idPregunta){
        $app = Aplicacion::getSingleton();
        $conn = $app->conexionBd();
        $numRespuesta = Pregunta::buscaRespuestaCorrecta($idPregunta);
        if($opcion == $numRespuesta){
            $query=sprintf("INSERT INTO rel_usuario_respuestas VALUES('%s', '%s', '%s')"
            , $conn->real_escape_string($_SESSION["nombre"])
            , $conn->real_escape_string($idPregunta)
            , $conn->real_escape_string(1));
        }
        else{
            $query=sprintf("INSERT INTO rel_usuario_respuestas VALUES('%s', '%s', '%s')"
            , $conn->real_escape_string($_SESSION["nombre"])
            , $conn->real_escape_string($idPregunta)
            , $conn->real_escape_string(0));
        }
        $conn->query($query);

    }

    public static function eliminarMiRespuesta($idPregunta, $idUsuario){
        $app = Aplicacion::getSingleton();
        $conn = $app->conexionBd();
        $query=sprintf("DELETE FROM rel_usuario_respuestas WHERE IdUsuario = '%s' AND IdPregunta = '%s'"
            , $conn->real_escape_string($idUsuario)
            , $conn->real_escape_string($idPregunta));
        $conn->query($query);

    }


    private $id;

    private $respuesta;

    private $pregunta;

    private $correcta;

    private $numRespuesta;


    private function __construct($id, $respuesta, $pregunta, $correcta, $numRespuesta)
    {
        $this->respuesta = $respuesta;
        $this->id=$id;       
        $this->pregunta=$pregunta;
        $this->correcta=$correcta;
        $this->numRespuesta=$numRespuesta;
    }

     public function respuesta()
    {
        return $this->respuesta;
    }

     public function id()
    {
        return $this->id;
    }

    public function pregunta()
    {
        return $this->pregunta;
    }

    public function correcta()
    {
        return $this->correcta;
    }

    public function numRespuesta()
    {
        return $this->numRespuesta;
    }


}