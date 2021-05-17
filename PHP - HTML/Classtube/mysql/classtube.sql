-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 12-05-2021 a las 21:11:39
-- Versión del servidor: 10.4.17-MariaDB
-- Versión de PHP: 8.0.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `classtube`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `academia`
--

CREATE TABLE `academia` (
  `id` int(11) NOT NULL,
  `Nombre` tinytext NOT NULL,
  `Direccion` text NOT NULL,
  `Anuncio` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `academia`
--

INSERT INTO `academia` (`id`, `Nombre`, `Direccion`, `Anuncio`) VALUES
(1, 'Educatube', 'Calle de las Flores Blancas, 80', NULL),
(2, 'TonÃºmeros', 'Calle de los Lamentos,35', NULL),
(3, 'pruebaQwe', 'probando', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `anuncio`
--

CREATE TABLE `anuncio` (
  `Id` int(11) NOT NULL,
  `path` varchar(150) NOT NULL,
  `Texto` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `contenidos`
--

CREATE TABLE `contenidos` (
  `Id` int(11) NOT NULL,
  `Creacion` date NOT NULL,
  `path` varchar(150) NOT NULL,
  `Descripcion` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cursos`
--

CREATE TABLE `cursos` (
  `Id` int(11) NOT NULL,
  `Nombre` tinytext NOT NULL,
  `Comienzo` datetime NOT NULL,
  `Final` datetime NOT NULL,
  `Academia` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `cursos`
--

INSERT INTO `cursos` (`Id`, `Nombre`, `Comienzo`, `Final`, `Academia`) VALUES
(1, 'cursoprueba1', '2021-05-13 00:00:00', '2021-05-15 00:00:00', 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `eventos`
--

CREATE TABLE `eventos` (
  `Id` int(11) NOT NULL,
  `Usuario` varchar(100) NOT NULL,
  `Inicio` date NOT NULL,
  `Final` date NOT NULL,
  `Titulo` tinytext NOT NULL,
  `Descripcion` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `preguntas`
--

CREATE TABLE `preguntas` (
  `Id` int(11) NOT NULL,
  `NumPregunta` int(20) NOT NULL,
  `Pregunta` text NOT NULL,
  `RespuestaCorrecta` int(11) NOT NULL,
  `Quiz` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `quizes`
--

CREATE TABLE `quizes` (
  `Id` int(11) NOT NULL,
  `Nombre` tinytext NOT NULL,
  `Instrucciones` text NOT NULL,
  `Curso` int(11) NOT NULL,
  `numPreguntas` int(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `quizes`
--

INSERT INTO `quizes` (`Id`, `Nombre`, `Instrucciones`, `Curso`, `numPreguntas`) VALUES
(1, 'fsdfasf asf as', 'fasf sa asf as', 1, 0),
(2, 'fsadfdasf ', 'fafsafasf asf', 1, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rel_usuario_academia`
--

CREATE TABLE `rel_usuario_academia` (
  `IdAcademia` int(11) NOT NULL,
  `IdUsuario` varchar(100) NOT NULL,
  `Gestor` tinyint(4) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `rel_usuario_academia`
--

INSERT INTO `rel_usuario_academia` (`IdAcademia`, `IdUsuario`, `Gestor`) VALUES
(3, 'qwe', 1),
(3, 'alumno', 0),
(3, 'rober', 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rel_usuario_curso`
--

CREATE TABLE `rel_usuario_curso` (
  `Usuario` varchar(100) NOT NULL,
  `Curso` int(11) NOT NULL,
  `Nota` double DEFAULT NULL,
  `Pago` enum('Pagado','No pagado','Pendiente','') NOT NULL DEFAULT 'No pagado',
  `Gestor` tinyint(4) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `rel_usuario_curso`
--

INSERT INTO `rel_usuario_curso` (`Usuario`, `Curso`, `Nota`, `Pago`, `Gestor`) VALUES
('qwe', 1, NULL, 'No pagado', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rel_usuario_evaluaciones`
--

CREATE TABLE `rel_usuario_evaluaciones` (
  `IdUsuario` varchar(100) NOT NULL,
  `IdEvaluacion` int(11) NOT NULL,
  `Nota` double NOT NULL,
  `Evaluacion` enum('tareas','quizes','','') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `respuestas`
--

CREATE TABLE `respuestas` (
  `Id` int(11) NOT NULL,
  `NumRespuesta` int(20) NOT NULL,
  `Respuesta` text NOT NULL,
  `Creacion` date NOT NULL,
  `Pregunta` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tareas`
--

CREATE TABLE `tareas` (
  `Id` int(11) NOT NULL,
  `Titulo` varchar(50) NOT NULL,
  `path` varchar(150) NOT NULL,
  `Creacion` date NOT NULL,
  `Entrega` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `id` varchar(100) NOT NULL,
  `Tipo` enum('Alumno','Profesor','Admin','') NOT NULL,
  `Nombre` varchar(100) NOT NULL,
  `Apellido1` varchar(100) NOT NULL,
  `Apellido2` varchar(100) NOT NULL,
  `Password` varbinary(100) NOT NULL,
  `Anuncio` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`id`, `Tipo`, `Nombre`, `Apellido1`, `Apellido2`, `Password`, `Anuncio`) VALUES
('alumno', 'Alumno', 'alumno', 'alumno', 'alumno', 0x243279243130244d3772672f5a4e4d6e48464d38505a4a78715774732e4a49537565506f5077786d324b6a6a52537645526f466338426750596e482e, NULL),
('qwe', 'Profesor', 'qwe', 'qwe', 'qwe', 0x24327924313024564330334c663047683173662e445a32394d322e384f316b556e36624d394a6b584551482e776b5467475579325365726175554d57, NULL),
('rober', 'Alumno', 'robe', 'rober', 'rober', 0x2432792431302473654c3445495970513136536a7658584e6a7869544f6734592f45686369416149616c45683363373635615631724e566c744a472e, NULL);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `academia`
--
ALTER TABLE `academia`
  ADD PRIMARY KEY (`id`),
  ADD KEY `Anuncio` (`Anuncio`);

--
-- Indices de la tabla `anuncio`
--
ALTER TABLE `anuncio`
  ADD PRIMARY KEY (`Id`);

--
-- Indices de la tabla `contenidos`
--
ALTER TABLE `contenidos`
  ADD PRIMARY KEY (`Id`);

--
-- Indices de la tabla `cursos`
--
ALTER TABLE `cursos`
  ADD PRIMARY KEY (`Id`),
  ADD KEY `Academia` (`Academia`);

--
-- Indices de la tabla `eventos`
--
ALTER TABLE `eventos`
  ADD PRIMARY KEY (`Id`),
  ADD KEY `Usuario` (`Usuario`);

--
-- Indices de la tabla `preguntas`
--
ALTER TABLE `preguntas`
  ADD PRIMARY KEY (`Id`),
  ADD KEY `Respuesta` (`RespuestaCorrecta`),
  ADD KEY `Quiz` (`Quiz`);

--
-- Indices de la tabla `quizes`
--
ALTER TABLE `quizes`
  ADD PRIMARY KEY (`Id`),
  ADD KEY `Curso` (`Curso`);

--
-- Indices de la tabla `rel_usuario_academia`
--
ALTER TABLE `rel_usuario_academia`
  ADD KEY `IdAcademia` (`IdAcademia`),
  ADD KEY `IdUsuario` (`IdUsuario`);

--
-- Indices de la tabla `rel_usuario_curso`
--
ALTER TABLE `rel_usuario_curso`
  ADD KEY `Usuario` (`Usuario`),
  ADD KEY `Curso` (`Curso`);

--
-- Indices de la tabla `rel_usuario_evaluaciones`
--
ALTER TABLE `rel_usuario_evaluaciones`
  ADD KEY `IdUsuario` (`IdUsuario`),
  ADD KEY `IdEvaluacion` (`IdEvaluacion`);

--
-- Indices de la tabla `respuestas`
--
ALTER TABLE `respuestas`
  ADD PRIMARY KEY (`Id`),
  ADD KEY `Pregunta` (`Pregunta`);

--
-- Indices de la tabla `tareas`
--
ALTER TABLE `tareas`
  ADD PRIMARY KEY (`Id`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`id`),
  ADD KEY `Anuncio` (`Anuncio`);

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `anuncio`
--
ALTER TABLE `anuncio`
  ADD CONSTRAINT `anuncio_ibfk_1` FOREIGN KEY (`Id`) REFERENCES `academia` (`Anuncio`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `cursos`
--
ALTER TABLE `cursos`
  ADD CONSTRAINT `cursos_ibfk_1` FOREIGN KEY (`Academia`) REFERENCES `academia` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `eventos`
--
ALTER TABLE `eventos`
  ADD CONSTRAINT `eventos_ibfk_2` FOREIGN KEY (`Usuario`) REFERENCES `usuario` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `preguntas`
--
ALTER TABLE `preguntas`
  ADD CONSTRAINT `preguntas_ibfk_1` FOREIGN KEY (`Quiz`) REFERENCES `quizes` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `quizes`
--
ALTER TABLE `quizes`
  ADD CONSTRAINT `quizes_ibfk_2` FOREIGN KEY (`Curso`) REFERENCES `cursos` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `rel_usuario_academia`
--
ALTER TABLE `rel_usuario_academia`
  ADD CONSTRAINT `rel_usuario_academia_ibfk_1` FOREIGN KEY (`IdAcademia`) REFERENCES `academia` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `rel_usuario_academia_ibfk_2` FOREIGN KEY (`IdUsuario`) REFERENCES `usuario` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `rel_usuario_curso`
--
ALTER TABLE `rel_usuario_curso`
  ADD CONSTRAINT `rel_usuario_curso_ibfk_1` FOREIGN KEY (`Curso`) REFERENCES `cursos` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `rel_usuario_curso_ibfk_2` FOREIGN KEY (`Usuario`) REFERENCES `usuario` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `rel_usuario_evaluaciones`
--
ALTER TABLE `rel_usuario_evaluaciones`
  ADD CONSTRAINT `rel_usuario_evaluaciones_ibfk_1` FOREIGN KEY (`IdUsuario`) REFERENCES `usuario` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `rel_usuario_evaluaciones_ibfk_2` FOREIGN KEY (`IdEvaluacion`) REFERENCES `quizes` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `respuestas`
--
ALTER TABLE `respuestas`
  ADD CONSTRAINT `respuestas_ibfk_1` FOREIGN KEY (`Id`) REFERENCES `preguntas` (`RespuestaCorrecta`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `respuestas_ibfk_2` FOREIGN KEY (`Pregunta`) REFERENCES `preguntas` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD CONSTRAINT `usuario_ibfk_1` FOREIGN KEY (`Anuncio`) REFERENCES `anuncio` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
