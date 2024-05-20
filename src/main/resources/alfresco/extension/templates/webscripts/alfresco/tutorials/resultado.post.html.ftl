<#--  <#escape x as jsonUtils.encodeJSONString(x)>
{
   "route": "${(route)!''}",
   "message": "Operacion exitosa",
   "status": {
   		"code": 200,
   		"description": "Reporte Generado exitosamente"
   },
   "code": "OK"
}
</#escape> 
 -->
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Respuesta del Reporte Generado</title>
</head>

<style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f4f4f4;
        margin: 0;
        padding: 0;
    }

    h1 {
        text-align: center;
        color: #333;
    }

    .response-container {
        background-color: #fff;
        border: 1px solid #ccc;
        border-radius: 5px;
        width: 330px;
        margin: 0 auto;
        padding: 20px;
    }
    .response-container p {
        
        margin-bottom: 5px; /* Agrega espacio inferior entre párrafos */
        font-family: Arial, sans-serif;
    }

    p {
        margin: 0;
    }

    a {
        display: block;
        text-align: center;
        background-color: #007bff;
        color: #fff;
        text-decoration: none;
        border: none;
        border-radius: 3px;
        padding: 10px 20px;
        margin-top: 20px;
        cursor: pointer;
        font-weight: bold;
    }

    a:hover {
        background-color: #0056b3;
    }
    
</style>
<body>
    <h1>Resultado del Reporte</h1>
    <div class="response-container">
        <p><strong>Ruta:</strong> ${(route)!''}</p>
        <p><strong>Mensaje:</strong> Operación exitosa</p>
        <p><strong>Código de estado:</strong> 200</p>
        <p><strong>Descripción del estado:</strong> Reporte Generado exitosamente</p>
        <p><strong>Código:</strong> OK</p>
    
    
        <!-- Botón para regresar a la página de listado de sitios -->
        <a href="listadoSitios">Regresar al listado de Sitios</a>
    </div>
</body>
</html>