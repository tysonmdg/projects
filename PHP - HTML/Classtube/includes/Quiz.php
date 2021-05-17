<?php
namespace es\ucm\fdi\aw;

class Quiz
{

    public static function buscaQuiz($nombre, $curso)
    {
        $app = Aplicacion::getSingleton();
        $conn = $app->conexionBd();
        $query = sprintf("SELECT * FROM quizes WHERE nombre = '%s' AND curso = '%s'"
            , $conn->real_escape_string($nombre)
            , $conn->real_escape_string($curso));
        $rs = $conn->query($query);
        $result = false;
        if ($rs) {
            if ( $rs->num_rows == 1) {
                $fila = $rs->fetch_assoc();
                $curso = new Quiz($fila['Id'],$fila['Nombre'],$fila['Instrucciones'],$fila['Curso'], $fila['numPreguntas']);
                $curso->id = $fila['Id'];
                $result = $curso;
            }
            $rs->free();
        } else {
            echo "Error al consultar en la BD: (" . $conn->errno . ") " . utf8_encode($conn->error);
            exit();
        }
        return $result;
    }

     public static function buscaQuizId($id)
    {
        $app = Aplicacion::getSingleton();
        $conn = $app->conexionBd();
        $query = sprintf("SELECT * FROM quizes WHERE Id = '%s'"
            , $conn->real_escape_string($id));
        $rs = $conn->query($query);
        $result = false;
        if ($rs) {
            if ( $rs->num_rows == 1) {
                $fila = $rs->fetch_assoc();
                $curso = new Quiz($fila['Id'],$fila['Nombre'],$fila['Instrucciones'],$fila['Curso'], $fila['numPreguntas']);
                $curso->id = $fila['Id'];
                $result = $curso;
            }
            $rs->free();
        } else {
            echo "Error al consultar en la BD: (" . $conn->errno . ") " . utf8_encode($conn->error);
            exit();
        }
        return $result;
    }

    public static function listaQuizes($curso)
    {
        $html = "";
        $app = Aplicacion::getSingleton();
        $conn = $app->conexionBd();
        $query =  "SELECT * FROM quizes WHERE Curso LIKE '%$curso%'";
        $rs = $conn->query($query);

        if ($rs && $rs->num_rows > 0) {
            while($fila = $rs->fetch_assoc()){
                $quiz = new Quiz($fila['Id'],$fila['Nombre'],$fila['Instrucciones'],$fila['Curso'], $fila['numPreguntas']); 
                $c = Curso::buscaCursoId($curso);
                $gestor = Academia::esGestor($_SESSION['nombre'], $c->academia());
                if(!$gestor){
                	$query = sprintf("SELECT * FROM rel_usuario_quiz WHERE IdUsuario = '%s' AND IdQuiz = '%s'"
                					, $conn->real_escape_string($_SESSION["nombre"])
                					, $conn->real_escape_string($quiz->id));
                	$res = $conn->query($query);               	
                	if($res && $res->num_rows>0){
                		$col = $res->fetch_assoc();
                		$html .= "<p><li> $quiz->nombre --> {$col['Nota']}</li></p>";
                	}
                	else
                		$html .= "<p><li><a href='verQuiz.php?nombre=$quiz->nombre&&curso=$quiz->curso'> $quiz->nombre</a></li></p> ";	
                } 
                else {
                    $html .= "<form action=borradoQuiz.php?nombre=$quiz->id() method='post'><li>$quiz->nombre 
                    <input type='submit' name='delete' value='Borrar quiz' /></form></li>";
                }
            }
        }else  $html .= '<p>No hay quizes abiertos.</p>';

        $rs->free();

        return $html;
    }

    public static function crea($nombre, $instrucciones, $curso)
    {
        $quiz = self::buscaQuiz($nombre, $curso);
        if ($quiz) {
            return false;
        }
        $lastId = self::lastId();
        $quiz = new Quiz($lastId, $nombre, $instrucciones, $curso, null);
        return self::guarda($quiz);
    }
    
    public static function lastId()
    {
        $app = Aplicacion::getSingleton();
        $conn = $app->conexionBd();
        $lastId = "SELECT Id FROM quizes ORDER BY Id DESC LIMIT 1";
        $res = mysqli_query($conn, $lastId);
        $i = 0;
        while ($registro = mysqli_fetch_array($res)){
        $i = intval($registro['Id']);
        }
        $ress = $i + 1;
        return $ress;
    }

    public static function guarda($quiz)
    {
        $q = self::buscaQuiz($quiz->nombre(), $quiz->curso());
        if ($q) {
            return self::actualiza($quiz);
        }
        return self::inserta($quiz);
    }
    
    private static function inserta($quiz)
    {
        $app = Aplicacion::getSingleton();
        $conn = $app->conexionBd();

        //insertar en la tabla de curso
        $query=sprintf("INSERT INTO quizes VALUES('%s', '%s', '%s', '%s', '%s')"
            , $conn->real_escape_string($quiz->id)
            , $conn->real_escape_string($quiz->nombre)
            , $conn->real_escape_string($quiz->instrucciones)
            , $conn->real_escape_string($quiz->curso)
            , $conn->real_escape_string($quiz->numPreguntas));
        if (!$conn->query($query) ) {
            echo "Error al insertar en la BD: (" . $conn->errno . ") " . utf8_encode($conn->error);
            exit();
        }
        //insertamos en la tabla rel_usuario_curso
        /*$query=sprintf("INSERT INTO rel_usuario_curso(Usuario, Curso) VALUES ('%s', '%s')"
            ,$conn->real_escape_string($_SESSION['nombre'])
            ,$conn->real_escape_string($curso->id));
        if ( $conn->query($query) ){
            $curso->id = $conn->insert_id;
        } else {
            echo "Error al insertar en la BD: (" . $conn->errno . ") " . utf8_encode($conn->error);
            exit();
        }*/
        return $quiz;
    }
    
    public static function mostrarQuiz($quiz, $curso)
    {
        $q = self::buscaQuiz($quiz, $curso);
        $contenidoPrincipal = '';
        $html='';
        $idQuiz = $q->id();
        if(isset($_SESSION['nombre'])){

        }

        $contenidoPrincipal .= <<<EOS
            <p> <h1>$q->nombre</h1> </p>
            <p> <h3>$q->instrucciones</h3> </p>
            <p> <h3>$q->numPreguntas</h3> </p>
            <p> $html </p>
    
        EOS;

     return $contenidoPrincipal;
    }

    public static function aumentarPregunta($quiz){
        $app = Aplicacion::getSingleton();
        $conn = $app->conexionBd();

        $query=sprintf("SELECT * FROM quizes WHERE Id = '%s'", $conn->real_escape_string($quiz));
        $rs = $conn->query($query);
        $fila = $rs->fetch_assoc();
        $preguntas = $fila["numPreguntas"] + 1;
        echo "$preguntas";
        $query=sprintf("UPDATE quizes SET numPreguntas = '%s' WHERE Id = '%s'"
        , $conn->real_escape_string($preguntas)
        , $conn->real_escape_string($quiz));
        $conn->query($query);


    }

    public static function eliminaQuiz($quiz)
    {
        $app = Aplicacion::getSingleton();
        $conn = $app->conexionBd();
        $query = sprintf("DELETE FROM quizes WHERE id = '%s'", $conn->real_escape_string($quiz));
        $rs = $conn->query($query);
        if ( !$rs ) {
            echo "Error al borrar en la BD: (" . $conn->errno . ") " . utf8_encode($conn->error);
            exit();
        }
    }


    public static function realizarQuiz($nombreQuiz, $idCurso){
        $html = "";
        $quiz = self::buscaQuiz($nombreQuiz, $idCurso);
        for($i = 1; $i < $quiz->numPreguntas() + 1; $i++){
            $pregunta = Pregunta::buscaPreguntaPorNumero($i, $quiz->id());
            $idPregunta = $pregunta->id();
            $html .= "<li><a href='verPregunta.php?nombre=$idPregunta'> Pregunta $i</a></li>";
        }  
        return $html;
    }

    public static function calcularNotaQuiz($idQuiz){

        $idUsuario = $_SESSION["nombre"];
        $quiz = self::buscaQuizId($idQuiz);
        $numPreguntas = $quiz->numPreguntas();
        $app = Aplicacion::getSingleton();
        $conn = $app->conexionBd();
        $query = sprintf("SELECT * FROM preguntas WHERE Quiz = '%s'", $conn->real_escape_string($idQuiz));
        $rs = $conn->query($query);
        $puntuacion = 0;
        while($fila = $rs->fetch_assoc()){
            $puntuacion = $puntuacion + Pregunta::comprobarSolucion($fila["Id"], $idUsuario);
            Respuesta::eliminarMiRespuesta($fila["Id"], $idUsuario);
        }
        $puntuacion = ($puntuacion/$numPreguntas) * 10;

        $query = sprintf("INSERT INTO rel_usuario_quiz VALUES('%s', '%s', '%s')"
        , $conn->real_escape_string($idUsuario)
        , $conn->real_escape_string($idQuiz)
        , $conn->real_escape_string($puntuacion));
        $rs = $conn->query($query);


    }

    private $id;

    private $nombre;

    private $instrucciones;

    private $curso;

    private $numPreguntas;


    private function __construct($id, $nombre, $instrucciones, $curso, $numPreguntas)
    {
        $this->nombre = $nombre;
        $this->id=$id;       
        $this->instrucciones=$instrucciones;
        $this->curso=$curso;
        $this->numPreguntas=$numPreguntas;
    }

     public function nombre()
    {
        return $this->nombre;
    }

     public function id()
    {
        return $this->id;
    }

    
    public function instrucciones()
    {
        return $this->instrucciones;
    }

    public function curso()
    {
        return $this->curso;
    }

    public function numPreguntas()
    {
        return $this->numPreguntas;
    }


}