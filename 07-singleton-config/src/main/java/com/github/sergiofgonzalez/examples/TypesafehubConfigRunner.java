package com.github.sergiofgonzalez.examples;

import static com.github.sergiofgonzalez.examples.WaterfallConfigurationSingleton.*;

public class TypesafehubConfigRunner {

	public static void main(String[] args) throws NoSuchFieldException, SecurityException {	
		System.out.println(wconf().get("name"));
		System.out.println(wconf().get("message"));
		System.out.println(wconf().get("friends", true));
	}	
}
