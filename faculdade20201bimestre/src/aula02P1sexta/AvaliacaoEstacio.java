package aula02P1sexta;

import java.util.Scanner;

/*
 * Exercícios 28/02/2020.
 * Utilizar a ferramenta de debug do Eclipse para auxiliar em parte dos exercícios.
 * Realizar parte dos exercícios utilizando o teste de mesa no caderno.
 Quais linhas deste programa serão executas para as seguintes notas:

 Usar o caderno para:
 a) Av1 = 100, Av2 = 80								Aprovado
 b) Av1 = 50,  Av2 = 50, 				Av3 = 50 	Final
 b) Av1 = 10,  AVR = 10,	Av2 = 40, 	Av3 = 80 	Final
 c) Av1 = 10,  AVR = 80,  	Av2 = 10				Reprovado
 
 Usar a feramenta do eclipse para:
 d) Av1 = 10,  AVR = 100,  	Av2 = 60, 	Av3 = 10	Reprovado
 e) Av1 = 30,  AVR = 90,  	Av2 = 80, 				Aprovado
 f) Av1 = 35,  AVR = 85,  	Av2 = 40, 	Av3 = 80	Aprovado

 */
public class AvaliacaoEstacio {
	public static void main(String[] args) {
		Scanner leia = new Scanner(System.in);
		double av1 = 0.0;
		double av2 = 0.0;
		double av3 = 0.0;
		double avr = 0.0;
		double media = 0.0;
		System.out.println("digite a nota AV1:");
		av1 = leia.nextDouble();
		if (av1 < 4) {
			// implementar AVR
			System.out.println("digite a nota AVR:");
			avr = leia.nextDouble();
			av1 = (av1 + avr) / 2;
		}
		System.out.println("digite a nota AV2:");
		av2 = leia.nextDouble();
		// avaliar a necessidade da av3
		if ((av1 < 4) && (av2 < 4)) {
			System.out.println("Reprovado.");
		} else {
			// faltou tratar o caso de nao fazer av3
			if ((av1 >= 6) && (av2 >= 6)) {
				media = (av1 + av2) / 2;
			} else {
				System.out.println("digite a nota AV3:");
				av3 = leia.nextDouble();
				if (av1 >= av2) {
					// computar media com av1
					media = (av1 + av3) / 2;
				} else {
					// computar a media com av2
					media = (av2 + av3) / 2;
				}
			}
		}
		if (media >= 6) {
			System.out.println("Aprovado");
		}
		leia.close();
	}

}
