package com.lucianovilasboas.estatistica;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

/**
 * <h2>Uma classe simples para calculos estatísticos</h2>
 * <blockquote>Requer: Java 1.6 ou  superior</blockquote>
 * 
 * @author Luciano Vilas Boas Espiridião {lucianovilasboas@gmail.com}
 * @since 30/05/2012
 * @version 1.0.0
 *  
 */
public class Estatistica {

	private double dados[];
	private double min, max;

	public Estatistica(double[] dados) {
		this.dados = dados;
	}

	public Estatistica() {
	}

	/**
	 * Coeficiente de Variação de Pearson
	 * 
	 * @return
	 */
	public double pearson() {
		return (desvioPadrao() / mediaAritmetica()) * 100;
	}

	/**
	 * Calcula a Média Aritmetica dos dados
	 * 
	 * @return
	 */
	public double mediaAritmetica() {
		double total = 0;
		for (int counter = 0; counter < dados.length; counter++)
			total += dados[counter];
		return total / dados.length;
	}

	/**
	 * Soma dos dados
	 * @return
	 */
	public double soma() {
		double total = 0;
		for (int counter = 0; counter < dados.length; counter++)
			total += dados[counter];
		return total;
	}

	/**
	 * Soma o quadrado de cada elemento
	 * @return
	 */
	public double somaAoQuadrado() {
		double total = 0;
		for (int counter = 0; counter < dados.length; counter++)
			total += dados[counter]*dados[counter];
		return total;
	}
	

	/**
	 * Ordena de forma ascendente os dados
	 */
	public void ordenar() {
		Arrays.sort(dados);
	}

	/**
	 * Mostra os dados
	 */
	public void imprimir() {
		System.out.print("\nDados: ");
		print(dados);
	}

	/**
	 * <p>Realiza a busca binária do elemento "value" no  Array "dados"
	 * <p>Array não pode conter valores duplicados
	 * @param value
	 * @return
	 */
	public int buscaPor(int value) {
		return Arrays.binarySearch(dados, value);
	}

	/**
	 * Calcula e Variância Amostral
	 * @return
	 */
	public double getVariancia() {
		double p1 = 1 / Double.valueOf(dados.length - 1);
		double p2 = somaAoQuadrado() - (Math.pow(soma(), 2) / Double.valueOf(dados.length));
		return p1 * p2;
	}

	/**
	 * Calcula a variancia dos dados
	 * @return
	 */
	public double variancia() {
		double sum = 0.0;
		double mean = mediaAritmetica();
		for (int i = 0; i < dados.length; i++) {
			sum += Math.pow(dados[i] - mean, 2);
		}
		return sum / (dados.length - 1);
	}

	/**
	 * Calcula Desvio Padrão
	 * @return
	 */
	public double desvioPadrao() {
		return Math.sqrt(variancia());
	}

	/**
	 * Calcula a mediana (Valor mais central)
	 * @return
	 */
	public double mediana() {
		this.ordenar();
		if (dados.length % 2 == 1) // quatidade impar de elementos
		{
			return dados[((dados.length + 1) / 2) - 1];
		} 
		else // quatidade par de elementos
		{
			int m = dados.length / 2;
			return (dados[m - 1] + dados[m]) / 2;
		}
	}

	/**
	 * Calcula a moda (valor mais frequente)
	 * @return moda
	 */
	public double moda() {
		HashMap<Double, Integer> map = new HashMap<Double, Integer>();
		Integer i;
		Double moda = 0.0;
		Integer numAtual, numMaior = 0;
		for (int count = 0; count < dados.length; count++) {
			i = (Integer) map.get(new Double(dados[count]));
			if (i == null) {
				map.put(new Double(dados[count]), new Integer(1));
			} else {
				map.put(new Double(dados[count]), new Integer(i.intValue() + 1));
				numAtual = i.intValue() + 1;
				if (numAtual > numMaior) {
					numMaior = numAtual;
					moda = new Double(dados[count]);
				}
			}
		}
		// System.out.print("\n Eis o mapa: "+map.toString());
		return moda;
	}

	/**
	 * Calcula o Coeficiente de Assimetria 
	 * @return (mediaAritmetica() - moda()) / desvioPadrao()
	 */
	public double coeficienteAssimetria() {
		return (mediaAritmetica() - moda()) / desvioPadrao();
	}

	/**
	 * Retorna todos o elementos na forma de um array
	 * @return dados
	 */
	public double[] getDados() {
		return dados;
	}

	/**
	 * Seta um novo conjunto de dados
	 * @param dados
	 */
	public void setDados(double[] dados) {
		this.dados = dados;
	}

	/**
	 * Calacula o percentil
	 * @param perc
	 * @return percentil
	 */
	public double percentil(int perc) {
		this.ordenar();
		int pos = perc * dados.length / 100;
		return dados[pos==dados.length ?  pos-1 : pos];
	}

	
	/**
	 * Calcula todos os percentis
	 * @return percentis
	 */
	public double[] percentis(){
		double[] percentis = new double[100];
		for (int i = 0; i < percentis.length; i++) {
			percentis[i] = percentil(i);
		}
		return percentis;
	}
	
	/**
	 * Calcula todos os quartis
	 * @return quartis
	 */
	public double[] quartis(){
		double[] quartis = new double[4];

		quartis[0] = percentil(25);
		quartis[1] = percentil(50);
		quartis[2] = percentil(75);
		quartis[3] = percentil(100);
		
		return quartis;
	}

	/**
	 * Calcula a distribuição de probabilidade de cada elemento (PDF)
	 * @return pdf
	 */
	public double[] pdf() {

		Item[] itens = frequency();
		int n = itens.length;
		double[] pdf = new double[n];

		for (int i = 0; i < n; i++) {
			pdf[i] = itens[i].frequency / Double.valueOf(dados.length);
		}
		return pdf;
	}

	/**
	 * Calcula a distribuição de probabilidade acumulada (CDF)
	 * @return cdf
	 */
	public double[] cdf() {

		Item[] itens = frequency();
		int n = itens.length;
		double[] cdf = new double[n];

		cdf[0] = itens[0].frequency / Double.valueOf(dados.length);
		for (int i = 1; i < n; i++) {
			cdf[i] = cdf[i - 1] + itens[i].frequency / Double.valueOf(dados.length);
		}
		return cdf;
	}

	
	/**
	 * Calcula a frequencia de cada elemento
	 * @return vetor de frequencia
	 */
	public Item[] frequency() {

		HashMap<Double, Integer> map = new HashMap<Double, Integer>();
		Integer i;
		for (int count = 0; count < dados.length; count++) {
			i = (Integer) map.get(Double.valueOf(dados[count]));
			if (i == null) {
				map.put(Double.valueOf(dados[count]), new Integer(1));
			} else {
				map.put(Double.valueOf(dados[count]), new Integer(i.intValue() + 1));
			}
		}

		Item[] itens = new Item[map.keySet().size()];
		int j = 0;
		for (Double key : map.keySet()) {
			itens[j++] = new Item(key, map.get(key));
		}

		Arrays.sort(itens);

		return itens;
	}
	
	/**
	 * Simula uma CDF com base na CDF dos dados atuais
	 * @param n
	 * @return vetor contendo os valores simuladados
	 */
	public double[] simularCDF(int n){
		int cont = 0; 
		double g;
		double[] cdf = this.cdf();
		Item[] freq = this.frequency();
		double[] sim = new double[n];
		while(cont<n)
		{
			g = Math.random();
			for (int i = 0; i < cdf.length; i++) {
				 if(g < cdf[i]){
					 sim[cont] = freq[i].value;
					 break;
				 }
			}
			cont++;	
		}
		return sim;
	}
	
	/**
	 * Exibe um histograma dos dados
	 */
	public String histograma(){ 
		StringBuilder sb = new StringBuilder("Histograma\n");
		Item[] freq = frequency();
		for (Item i : freq) {
			 sb.append(String.format("%5s ",i.value)).append(criaBarra(i.frequency, "=")).append("\n"); 
		} 
		return  sb.toString(); 
	}
	
	/**
	 * Função auxiliar para criar uma barra de caracteres
	 * @param frequency
	 * @param t
	 * @return
	 */
	private String criaBarra(int frequency, String t) { 
		StringBuilder sb = new StringBuilder(" ");
		for (int i = 0; i < frequency; i++) {
			sb.append(t);
		}
		return sb.toString();
	}

	/**
	 * Realiza uma sumarização dos dados
	 * <p>Calcula</p>
	 * <ul>
	 * <li>Tamanho da amostra</li>
	 * <li>Média</li>
	 * <li>Variancia</li>
	 * <li>Desvio Padrão</li>
	 * <li>Mediana</li>
	 * <li>Moda</li>
	 * <li>Frequencia</li>
	 * <li>PDF</li>
	 * <li>CDF</li>
	 * <li>Quartis</li>
	 * 
	 * </ul>
	 */
	public void summary(){
		System.out.println(getSummary());
	}
	
	
	public String getSummary(){
		
		StringBuilder sb = new StringBuilder("========================= Summary ======================\n");
		
		ordenar();
		
		sb.append(String.format("%-20s%-20s\n","Dados",Arrays.toString(dados) ));
		sb.append(String.format("%-20s%-20s\n","N",dados.length)); 
		sb.append(String.format("%-20s%-20s\n","Min",min()));
		sb.append(String.format("%-20s%-20s\n","Media",precisao(mediaAritmetica(),2)));
		sb.append(String.format("%-20s%-20s\n","Variancia",precisao(variancia(),2)));
		sb.append(String.format("%-20s%-20s\n","Desvio padrão",precisao(desvioPadrao(),2)));
		sb.append(String.format("%-20s%-20s\n","Mediana",mediana()));
		sb.append(String.format("%-20s%-20s\n","Max",max()));
		sb.append(String.format("%-20s%-20s\n","Moda",moda()));

		Item[] freq = frequency();
		String[] pdf = precisao(pdf(),2);
		String[] cdf = precisao(cdf(),2);

		sb.append(String.format("%-20s%-20s\n","freq",Arrays.toString(freq)));
		sb.append(String.format("%-20s%-20s\n","pdf",Arrays.toString(pdf)));
		sb.append(String.format("%-20s%-20s\n","cdf",Arrays.toString(cdf)));
		
		sb.append(String.format("%-20s%-20s\n","quartis",Arrays.toString(quartis())));
		
		sb.append(String.format("%-20s\n",histograma()));
		
		
		sb.append(String.format("IC="+Arrays.toString(ic(.90))));
		sb.append(String.format("\n========================================================\n"));
		
		return sb.toString();
	}
	
	
	
	public double max() {
        this.max = Arrays.stream(this.dados).max().getAsDouble();
		return this.max;
	}

	public double  min() {
		this.min = Arrays.stream(this.dados).min().getAsDouble();
		return this.min;
	}

	/**
	 * Calcula o intervalo de confiança para os dados amostrais
	 * @param nivelDeConfiança
	 * @param alpha
	 * @return intervalo de confiança
	 */
	public double[] ic(double nivelDeConfiança, double alpha){
		return null;
	}
	
	/**
	 * <p>Calcula o IC com base apenas no nivel de confiança desejado
	 * <p>Utiliza a tabela t para n <= 30
	 * <p>Caso n > 30 utiliza a tabela z
	 * @param nivelDeConfianca
	 * @return Intervalo de Confiança : {min,max}
	 */
	public double[] ic(double nivelDeConfianca){
	
		double alpha = 1.0-nivelDeConfianca; 
		
		String j = precisao(alpha,3); 
	
		double x;
		
		if(dados.length<=30){
			Table.readT();
			x = Table.valueT(dados.length-1, j);
		}else{
			Table.readZ();
			x = Table.valueZ(dados.length-1, j);
		}
	
//		System.err.println(x);
	
		double media = mediaAritmetica();
		double min, max; 
		
		double ic = x*desvioPadrao()/Math.sqrt(dados.length);
		
		min = media-ic;
		max = media+ic;
		
		return new double[]{min, max};
	}
	
	private static class Table{
		
		static String[] h;
		static double t[][];
		
		private static void readT() {
			if (t == null) {
				
				h = new String[13];
				t = new double[30][h.length];

				Scanner sc = null;
				int l = 0, c;
				try {
					sc = new Scanner(new File("t.csv"));
					h = sc.nextLine().split(";");

					while (sc.hasNextLine()) {
						String[] line = sc.nextLine().split(";");
						c = 0;
						while (c < t[0].length) {
							t[l][c] = Double.parseDouble(line[c++]);
						}
						l++;
					}

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} finally {
					sc.close();
				}
				
				//printT();
			}
		}

		
		private static void readZ() throws UnsupportedOperationException{
			//  TODO implementar
			throw new UnsupportedOperationException("Método não implementado");
		}
		
		static double valueT(int df, String alpha){
			for (int j = 0; j < h.length; j++) {
				if(h[j].equals(alpha))
					return t[df-1][j];
			}
			throw new IllegalArgumentException("Argumentos ["+df+", "+alpha+"] Não aceitáveis");
		}
		
		static double valueZ(int df, String alpha){
			return 0;
		}

		static void printT(){
			
			System.out.printf("%-3s"," ");
			for (String  t : h) {
				System.out.printf("%-10s",t); 
			}
			
			System.out.println();
			
			for (int i = 0; i < t.length; i++) {
				System.out.printf("%-3s",i+1); 					
				for (int j = 0; j < t[0].length; j++) {
					System.out.printf("%-10s",t[i][j]); 					
				}
				System.out.println();
			}
		}
		
	}
	
	public static double[] subtract(double[] c1, double[] c2){
		double[] diff = new double[c1.length];
		for (int i = 0; i < c1.length; i++) {
			diff[i] = c1[i]-c2[i];
		}
		return diff;
	}
	
	/**
	 * Formata os dados com a precisão informada 
	 * @param o
	 * @param precision
	 * @return dados formatados
	 */
	public String[] precisao(double[] o, int precision){
		String[] ret = new String[o.length];
		for (int i = 0; i < o.length; i++) {
			ret[i] = String.format("%."+precision+"f", o[i]);
		}
		return ret;
	}
	
	/**
	 * Formata um valor com a precisão informada 
	 * @param o
	 * @param precision
	 * @return valor formatado
	 */
	private String precisao(double o, int precision){
		return String.format("%."+precision+"f", o);
	}
	
	private static void print(double[] o){
		System.out.println(Arrays.toString(o));
	}
	
	private static void printf(String format,Object... s){
		System.out.printf(format,s);
	}
	

	/**
	 * Classe auxiliar para os calculos de frequencia de termos 
	 * @author Luciano
	 *
	 */
	private static class Item implements Comparable<Item> {
		
		double value;
		int frequency;

		public Item(double value, int frequency) {
			this.value = value;
			this.frequency = frequency;
		}

		@Override
		public int compareTo(Item i) {
			return Double.compare(value, i.value);
		}

		@Override
		public String toString() {
			return value + "=" + frequency;
		}
	}

	/**
	 * Exibe uma demonstração
	 */
	public void demo() {
		
		
		double[] dados = { 10, 16, 47, 48, 74, 30, 81, 42, 57, 67, 7, 13, 56, 44, 54, 17, 60, 32, 45, 28, 33, 60, 36, 59, 73, 46, 10, 40, 35, 65 };
		//double[] dados = {1,2,3,4,5,6,7,8,9,10,11};
		
		//double[] dados = generate(9); 
		
		Estatistica estatistica = new Estatistica(dados);
		estatistica.summary();
		
		
		
		
		System.out.println("\n\nDemonstração com base em um conjunto de dados simulados a partir \ndas características do primeiro conjunto");
		
		double[] simulacao = estatistica.simularCDF(dados.length); 
		Estatistica est = new Estatistica(simulacao);
		est.summary();

		
//		Table t = new Table();
//		t.readT();
//		System.out.println(t.get(1, 0.005));
		
//		double[] diff = Estatistica.subtract(new double[]{1,2,3}, new double[]{0,2,5});
//		Estatistica.print(diff);
		

	}
	
	
	/**
	 * Cria um vetor contendo dados aleatórios 
	 * @param n
	 * @return vetor de dados aleatórios
	 */
	public static double[] generate(int n){
		double[] dados = new double[n];  
		Random r = new Random();
		for (int i = 0; i < n; i++) {
			dados[i] = r.nextInt(n);
		}
		return dados;
	}
	
	public static void main(String[] args) {
		new Estatistica().demo();
	}

}
