<?php

require_once __DIR__ . '/includes/config.php';

$tituloPagina = 'Portada - CLASSTUBE';
$contenidoPrincipal = <<<EOS
<link rel="stylesheet" href="css/index.css">

<div id="hero">
    <div class="slideshow-container">

        <div class="mySlides fade">
                <img src="img/promo_stress.png" style="width:100%">
        </div>

        <div class="mySlides fade">
            <img src="img/promo_niveles.png" style="width:100%">
        </div>

        <div class="mySlides fade">
            <img src="img/promo_comunidad.png" style="width:100%">
        </div>

        <a class="prev" onclick="plusSlides(-1)">&#10094;</a>
        <a class="next" onclick="plusSlides(1)">&#10095;</a>

    </div>

    <div style="text-align:center">
        <span class="dot" onclick="currentSlide(1)"></span> 
        <span class="dot" onclick="currentSlide(2)"></span> 
        <span class="dot" onclick="currentSlide(3)"></span> 
    </div>

</div>


<div id="login_principal">
    <h2>¡Empieza tu aventura del aprendizaje!</h2>
    <a href='registro.php' id="Registro">Regístrate</a>
</div>

<div id="features">
    <div class="panel_feature">
        <img src="img/confianza.png" alt="">
        <div class="text_feature right">Los mejores profesores comprometidos a que adquieras conocimientos que no olvidarás después del examen</div>
    </div>
    <div class="panel_feature">
        <div class="text_feature">Enfoques alternativos a la enseñanza tradicional</div>
        <img src="img/conocimiento.png" alt="">
    </div>
    <div class="panel_feature">
        <img src="img/creatividad.png" alt="">
        <div class="text_feature right">Espacio para explorar los tópicos de formas creativas e innovadoras</div>
    </div>
    <div class="panel_feature">
        <div class="text_feature">Horarios diseñados para facilitar la concentración</div>
        <img src="img/focus.png" alt="">
    </div>
</div>

<script src="js/index.js"></script>
EOS;

require __DIR__ . '/includes/plantillas/plantilla_basica.php';