package aula01;

import java.util.Scanner;

public class LerDados {
	public static void main(String[] args) {
		Scanner leia = new Scanner(System.in);
		System.out.println("Digite seu nome:");
		String nome = leia.next();
		
		System.out.println("Digite sua idade:");
		int idade = leia.nextInt();
		
		System.out.println("Nome:" + nome);
		System.out.println("Idade:" + idade);
		
		leia.close();
	}

}
