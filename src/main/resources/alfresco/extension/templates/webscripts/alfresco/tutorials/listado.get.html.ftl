<#-- <#escape x as jsonUtils.encodeJSONString(x)>
{
   "route": "${(route)!''}",
   "message": "Operacion exitosa",
   "status": {
   		"code": 200,
   		"description": "Reporte Generado exitosamente"
   },
   "code": "OK"
}
</#escape> -->
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Seleccionar Sitio</title>
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

    form {
        background-color: #fff;
        border: 1px solid #ccc;
        border-radius: 5px;
        width: 300px;
        margin: 0 auto;
        padding: 20px;
    }

    label {
        display: block;
        margin-bottom: 10px;
        font-weight: bold;
    }

    select {
        width: 100%;
        padding: 5px;
        margin-bottom: 10px;
        border: 1px solid #ccc;
        border-radius: 3px;
    }

    input[type="submit"] {
        background-color: #007bff;
        color: #fff;
        border: none;
        border-radius: 3px;
        padding: 10px 20px;
        cursor: pointer;
    }

    input[type="submit"]:hover {
        background-color: #0056b3;
    }
</style>
<body>
    <h1>Informe de Sitios y Miembros</h1>
    <form action="resultadoListado" method="post">
    
        <label for="sitio">Elija un sitio:</label>
        <select id="sitio" name="sitio">
           <option value="0">Todos los sitios</option>
            <#list sites as site>
            <option value="${(site.id)!''}">${(site.nombre)!''}</option>
            </#list>
        </select>
        <br>
        <input type="submit"  value="Generar Reporte">
    </form>
    
</body>
</html>


