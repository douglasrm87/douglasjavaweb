package corrigehtml;

import java.util.ArrayList;
import java.util.List;

public class CorrigeHTMLOBSChamado {
//	 String html = "<table>estou testando o algoritmo<inicio> meu teste <meio>	 <dolado1> <dolado2> <dolado30><dolado40>testando <terceiro> gerou problema </inicio></table>";
	 String html = "<font color='#008000'>flavi</td></tr><tr><td>";
	// String html = "<table>estou testando o algoritmo<inicio> <dolado>
	// </table>";
	// String html = "</soinicio> <table>estou testando o algoritmo<inicio>
	// </table> </soofim>";

//	String html = "<td1>linha 2, célula 1</td1>	<td2>linha 2, célula 2</td2>	</tr>	</table>";

	public static void main(String[] args) {
		new CorrigeHTMLOBSChamado().processar();
	}

	private void processar() {
		CorrigeHTMLOBSChamado algo = new CorrigeHTMLOBSChamado();
		algo.identificarTokenHTML();

		List<StringBuilder> listaTokenHtml = identificarTokenHTML();
		System.out.println(listaTokenHtml);

		int totalLista = listaTokenHtml.size() - 1;
		// i caminha da esquerda para direita - volta caminha da direita para esquerda.
		int volta = 0;
		for (int i = 0; i < (listaTokenHtml.size() / 2) + 1; i++) {
			String inicio = String.valueOf((StringBuilder) listaTokenHtml.get(i));
			String proximo = String.valueOf((StringBuilder) listaTokenHtml.get(i + 1));
			String proximoOriginal = String.valueOf((StringBuilder) listaTokenHtml.get(i + 1));
			String fim = String.valueOf(listaTokenHtml.get(totalLista - volta));
			inicio = inicio.toString().replaceAll("[<>/]", "");
			proximo = proximo.toString().replaceAll("[<>/]", "");
			fim = fim.toString().replaceAll("[<>/]", "");

			if (i == (listaTokenHtml.size() / 2) && inicio.equals(fim)) {
				alterarTAGSoInicio(inicio);
			}

			if (inicio.toString().contains(fim)) {
				// ok
				System.out.println("OK _____________");
				System.out.println("TAG A:" + inicio);
				System.out.println("TAG F:" + fim);
				System.out.println("ok");
			} else {
				// nok
				System.out.println("NOK_____________");
				System.out.println("TAG A:" + inicio);
				System.out.println("TAG F:" + fim);
				System.out.println("nok");
				if (inicio.toString().contains(proximo) && proximoOriginal.toString().charAt(0) == '<'
						&& proximoOriginal.toString().charAt(1) == '/') {
					System.out.println("TAG A:" + inicio);
					System.out.println("TAG P:" + proximo);
					System.out.println("TAG Original:" + proximoOriginal);
					// adiantar o i
					i++;
				} else {
					alterarTAGSoInicio(inicio);
					alterarTAGSoInicio(fim);
				}
			}
			volta++;
		}
		System.out.println("Campo alterado primeira etapa:" + this.html);
		validarTagSomentecomFIM(listaTokenHtml);
		System.out.println("Campo alterado segunda etapa:" + this.html);

	}

	private void validarTagSomentecomFIM(List<StringBuilder> listaTokenHtml) {
		boolean possuiInicio = false;
		for (int i = 0; i < listaTokenHtml.size(); i++) {
			char tagArray[] = listaTokenHtml.get(i).toString().toCharArray();
			if (tagArray.length > 2 && tagArray[0] == '<' && tagArray[1] == '/') {
				String fim = String.valueOf(listaTokenHtml.get(i));
				fim = fim.toString().replaceAll("[<>/]", "");
				for (int j = 0; j < i; j++) {
					String inicio = String.valueOf((StringBuilder) listaTokenHtml.get(j));
					inicio = inicio.toString().replaceAll("[<>/]", "");
					if (fim.equals(inicio)) {
						possuiInicio = true;
					}
				}
				if (possuiInicio == false) {
					System.out.println("A TAG:" + fim + " sem inicio.");
					alterarTAGSoFIM(fim);
				}
			}
			possuiInicio = false;
		}
	}

	private void alterarTAGSoFIM(String tag) {
		String novaTAG = "<" + tag + "/>";
		this.html = this.html.replace("</" + tag + ">", novaTAG);
	}

	private void alterarTAGSoInicio(String tag) {
		String novaTAG = "<" + tag + "/>";
		this.html = this.html.replace("<" + tag + ">", novaTAG);
	}

	private List<StringBuilder> identificarTokenHTML() {

		List<StringBuilder> listaTokenHtml = new ArrayList<>();
		char vetHTML[] = this.html.toCharArray();

		StringBuilder temp = new StringBuilder();
		for (int i = 0; i < vetHTML.length; i++) {
			if (vetHTML[i] == '<') {
				temp.append(vetHTML[i]);
				for (int j = i + 1; j < vetHTML.length; j++) {
					if (vetHTML[j] == '>') {
						temp.append(vetHTML[j]);
						if (vetHTML[j - 1] != '/') {
							listaTokenHtml.add(temp);
						}
						temp = new StringBuilder();
						i = j;
						break;
					}
					temp.append(vetHTML[j]);
				}
			}
		}
		return listaTokenHtml;
	}
}
