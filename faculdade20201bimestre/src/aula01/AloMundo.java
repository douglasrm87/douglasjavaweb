package aula01;

import java.util.Date;

import aula011.Secundaria;

public class AloMundo {
	String nome;
	int idade;
	double valor;

	public static void main(String[] args) {
		new AloMundo().processar();
	}

	private void processar() {
		System.out.println("Meu primeiro programa.");
		System.out.println("Outro metodo.");
		Date hoje = new Date();
		System.out.println(hoje);

		Secundaria objSec = new Secundaria();
		objSec.imprimir();

		String str;
		str = "str" + "ola";
		StringBuilder strB = new StringBuilder();
		strB.append("ola");
		System.out.println(str);
		System.out.println(strB);
		
		
		
		

	}

}
