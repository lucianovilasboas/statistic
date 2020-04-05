package com.lucianovilasboas.estatistica;

import py4j.GatewayServer;

public class Estatistica4PythonRun {

	public static void main(String[] args) {
		Estatistica app = new Estatistica();
		GatewayServer server = new GatewayServer(app);
		server.start();
		System.out.println("Estatistica: Gateway Server Started");
	}

}
