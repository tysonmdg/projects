<?php
namespace es\ucm\fdi\aw;

class Pregunta
{

    public static function buscaPregunta($pregunta, $quiz)
    {
        $app = Aplicacion::getSingleton();
        $conn = $app->conexionBd();
        $query = sprintf("SELECT * FROM preguntas WHERE Pregunta = '%s' AND Quiz = '%s'"
            , $conn->real_escape_string($pregunta)
            , $conn->real_escape_string($quiz));
        $rs = $conn->query($query);
        $result = false;
        if ($rs) {
            if ( $rs->num_rows == 1) {
                $fila = $rs->fetch_assoc();
                $pregunta = new Pregunta($fila['Id'],$fila['NumPregunta'],$fila['Pregunta'], $fila['Quiz']);
                $pregunta->id = $fila['Id'];
                $result = $pregunta;
            }
            $rs->free();
        } else {
            echo "Error al consultar en la BD: (" . $conn->errno . ") " . utf8_encode($conn->error);
            exit();
        }
        return $result;
    }

    public static function buscaPreguntaPorId($IdPregunta)
    {
        $app = Aplicacion::getSingleton();
        $conn = $app->conexionBd();
        $query = sprintf("SELECT * FROM preguntas WHERE Id = '%s'"
            , $conn->real_escape_string($IdPregunta));
        $rs = $conn->query($query);
        $result = false;
        if ($rs) {
            if ( $rs->num_rows == 1) {
                $fila = $rs->fetch_assoc();
                $pregunta = new Pregunta($fila['Id'],$fila['NumPregunta'],$fila['Pregunta'], $fila['Quiz']);
                $pregunta->id = $fila['Id'];
                $result = $pregunta;
            }
            $rs->free();
        } else {
            echo "Error al consultar en la BD: (" . $conn->errno . ") " . utf8_encode($conn->error);
            exit();
        }
        return $result;
    }

    public static function buscaPreguntaPorNumero($numPregunta, $quiz)
    {
        $app = Aplicacion::getSingleton();
        $conn = $app->conexionBd();
        
        $query = sprintf("SELECT * FROM preguntas WHERE NumPregunta = '%s' AND Quiz = '%s'"
            , $conn->real_escape_string($numPregunta)
            , $conn->real_escape_string($quiz));
        $rs = $conn->query($query);
        $result = false;
        if ($rs) {
            if ( $rs->num_rows == 1) {
                $fila = $rs->fetch_assoc();
                $pregunta = new Pregunta($fila['Id'],$fila['NumPregunta'],$fila['Pregunta'], $fila['Quiz']);
                $pregunta->id = $fila['Id'];
                $result = $pregunta;
            }
            $rs->free();
        } else {
            echo "Error al consultar en la BD: (" . $conn->errno . ") " . utf8_encode($conn->error);
            exit();
        }
        return $result;
    }

   

    
    public static function listaPreguntas($quiz)
    {
        $html = "";
        $app = Aplicacion::getSingleton();
        $conn = $app->conexionBd();
        $query =  "SELECT * FROM preguntas WHERE Quiz LIKE '%$quiz%'";
        $rs = $conn->query($query);

        if ($rs && $rs->num_rows > 0) {
            while($fila = $rs->fetch_assoc()){
                $pregunta = new Pregunta($fila['Id'],$fila['NumPregunta'],$fila['Pregunta'], $fila['Quiz']);
                $html .= "<p> <a href='verPregunta.php?numPregunta=$pregunta->numPregunta&&quiz=$pregunta->quiz'> $pregunta->numPregunta</a> </p> ";
            }
        }else  $html .= '';

        $rs->free();

        return $html;
    }

    public static function crea($pregunta, $quiz)
    {
        $preg = self::buscaPregunta($pregunta, $quiz);
        if ($preg) {
            return false;
        }
        $numPregunta = self::preguntaActual($quiz);
        $lastId = self::lastId();
        $preg = new Pregunta($lastId, $numPregunta, $pregunta, $quiz);
        return self::guarda($preg);
    }
    
    public static function lastId()
    {
        $app = Aplicacion::getSingleton();
        $conn = $app->conexionBd();
        $lastId = "SELECT Id FROM preguntas ORDER BY Id DESC LIMIT 1";
        $res = mysqli_query($conn, $lastId);
        $i = 0;
        while ($registro = mysqli_fetch_array($res)){
        $i = intval($registro['Id']);
        }
        $ress = $i + 1;
        return $ress;
    }

    public static function guarda($pregunta)
    {
        $p = self::buscaPregunta($pregunta->pregunta(), $pregunta->quiz());
        if ($p) {
            return self::actualiza($pregunta);
        }
        
        return self::inserta($pregunta);
    }
    
    private static function inserta($pregunta)
    {
        $app = Aplicacion::getSingleton();
        $conn = $app->conexionBd();

        //insertar en la tabla de pregunta
        $query=sprintf("INSERT INTO preguntas(Id, NumPregunta, Pregunta, Quiz) VALUES('%s', '%s', '%s', '%s')"
            , $conn->real_escape_string($pregunta->id)
            , $conn->real_escape_string($pregunta->numPregunta)
            , $conn->real_escape_string($pregunta->pregunta)
            , $conn->real_escape_string($pregunta->quiz));
            
        if (!$conn->query($query) ) {
            echo "Error al insertar en la BD: (" . $conn->errno . ") " . utf8_encode($conn->error);
            exit();
        }
        
        Quiz::aumentarPregunta($pregunta->quiz);
        return $pregunta;
    }
    
    public static function mostrarPregunta($idPregunta)
    {
        $p = self::buscaPreguntaPorId($idPregunta);
        $contenidoPrincipal = '';
        $html='';
       
        for($i = 1; $i < 5; $i++){
            $aux = Respuesta::mostrarRespuesta($i, $p->id());
            $html .= "<p><ul>$aux<input type='radio' name='op1' value='$i'/></ul></p>";
        } 

        $contenidoPrincipal .= <<<EOS
            <p> <h1>$p->numPregunta. $p->pregunta</h1> </p>

            <p> $html </p>
    
        EOS;

    return $contenidoPrincipal;
    }

   

    public static function preguntaActual($idQuiz){
        $app = Aplicacion::getSingleton();
        $conn = $app->conexionBd();

        $query=sprintf("SELECT * FROM quizes WHERE Id = '%s'"
            , $conn->real_escape_string($idQuiz));
        $rs = $conn->query($query);
        $fila = $rs->fetch_assoc();
        
        return $fila["numPreguntas"] + 1;

    }


    public static function buscaRespuestaCorrecta($idPregunta){
        $app = Aplicacion::getSingleton();
        $conn = $app->conexionBd();
        $query = sprintf( "SELECT * FROM respuestas WHERE Pregunta = '%s' AND Correcta = 1", $conn->real_escape_string($idPregunta));
        $rs = $conn->query($query);
        $fila = $rs->fetch_assoc();
        return $fila["numRespuesta"];
    }

    public static function comprobarSolucion($idPregunta, $idUsuario){
        $app = Aplicacion::getSingleton();
        $conn = $app->conexionBd();
        $query = sprintf( "SELECT * FROM rel_usuario_respuestas WHERE IdUsuario = '%s' AND IdPregunta = '%s'"
        , $conn->real_escape_string($idUsuario)
        , $conn->real_escape_string($idPregunta));
        $rs = $conn->query($query);
        $fila = $rs->fetch_assoc();
        return $fila["Correcto"];
    }

    private $id;

    private $numPregunta;

    private $pregunta;

    private $quiz;


    private function __construct($id, $numPregunta, $pregunta, $quiz)
    {
        $this->numPregunta = $numPregunta;
        $this->id=$id;       
        $this->pregunta=$pregunta;
        $this->quiz=$quiz;
    }

     public function numPregunta()
    {
        return $this->numPregunta;
    }

     public function id()
    {
        return $this->id;
    }

    public function pregunta()
    {
        return $this->pregunta;
    }

    public function quiz()
    {
        return $this->quiz;
    }


}