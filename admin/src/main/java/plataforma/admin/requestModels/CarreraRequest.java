package plataforma.admin.requestModels;

import io.swagger.annotations.ApiModelProperty;

public class CarreraRequest {
    @ApiModelProperty(value = "nombre de la carrera", required = true)
    public String nombre;

    public CarreraRequest() {
    }
}
