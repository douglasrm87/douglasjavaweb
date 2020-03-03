package webservicerest;

import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

//http://localhost:8080/psw-atendimentoweb/rest/api/abrirChamadoAPPCopelTelecom/contrato

//http://localhost:8080/nosqlweb/rest/api/registrarItemCartaoFidelidade/123

@Path("/api")
@Stateless
public class APPWebServiceRest {

	@GET
	@Path("/registrarItemCartaoFidelidade/{cpf}")
	@Produces(MediaType.APPLICATION_JSON)
	public String registrarItemCartaoFidelidade(@PathParam("cpf") String cpf) {
		String retorno = "Registrado item cartão fidelidade do CPF " + cpf + " com sucesso." + ".";

		return retorno;
	}

}
