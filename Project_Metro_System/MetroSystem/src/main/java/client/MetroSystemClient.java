package client;

import java.util.Scanner;
import presentation.MetroSystemPresentation;
import presentation.MetroSystemPresentationImpl;

public class MetroSystemClient {

	public static void main(String[] args) {
		Scanner scanner=new Scanner(System.in);
    	MetroSystemPresentation metroSystemPresentation= new MetroSystemPresentationImpl();
        while(true) {
        	metroSystemPresentation.showMenu();
        	System.out.print("Enter Choice : ");
        	metroSystemPresentation.performMenu(scanner.nextInt());
        }
	}
}
