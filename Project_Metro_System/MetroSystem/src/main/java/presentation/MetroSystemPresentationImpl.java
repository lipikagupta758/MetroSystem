package presentation;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import entity.Card;
import entity.Station;
import entity.Transaction;
import exceptions.*;
import service.MetroSystemService;
import service.MetroSystemServiceImpl;

public class MetroSystemPresentationImpl implements MetroSystemPresentation{
	
	MetroSystemService metroSystemService= new MetroSystemServiceImpl();
	
	@Override
	public void showMenu() {
		System.out.println("===========================");
		System.out.println("Metro Management System");
		System.out.println("============================");
		System.out.println("1. Issue New Card");
		System.out.println("2. Swipe In");
		System.out.println("3. Swipe Out");
		System.out.println("4. View available metro stations"); 
		System.out.println("5. View Card Details");
		System.out.println("6. Add balance");
		System.out.println("7. View your previous transaction records");
		System.out.println("8. Exit");
	}

	@Override
	public void performMenu(int choice) {
		Scanner scanner=new Scanner(System.in);
		switch (choice) {
		case 1:
			try {
				System.out.print("Enter amount to issue new card (Add minimum amount of 100): ");	
				double amount= scanner.nextDouble();
				int id= metroSystemService.issueCard(amount);
				System.out.println("Your new metro card has been successfully created. Your Card Id is: "+ id);
				System.out.println();
			}
			catch(LowAmountException e) {
				System.out.println("Low Balance! New Card is issued with minimum Rs.100");
				System.out.println();
			}
			break;
		case 2:
			try {
				System.out.print("Enter your card id to swipeIn in metro: ");
				int cardId= scanner.nextInt();
				metroSystemService.checkCardExist(cardId);
				System.out.println("Choose metro station no. from below list: ");
				System.out.println();
				ArrayList<Station> metroList= metroSystemService.getStationList();
				for(Station station: metroList) {
					System.out.println(station);
				}
				System.out.print("Enter Station No from the above list to swipe in: ");
				int stationNo= scanner.nextInt();
				metroSystemService.checkStationExist(stationNo);
				metroSystemService.swipeIn(cardId, stationNo);
				System.out.println("Swiped in successfully!");
				System.out.println();
			} 
			catch (WrongCardNoException e) {
				System.out.println("Invalid Metro Card No! Please try again with correct Card Number");
				System.out.println();
			}
			catch(MetroStationDoNotExistException e) {
				System.out.println("Invalid Metro Station entered!. Please try again with correct Metro Station Number");
				System.out.println();
			}
			catch(InsufficientBalanceException e) {
				System.out.println("Insufficient Balance! Your balance should be minimum of Rs.20 to travel");
				System.out.println();
			}
			catch(InputMismatchException e) {
				System.out.println("Please enter correct details.");
				System.out.println();
			}
			break ;
		case 3:
		    try {
		        System.out.print("Enter your card ID to swipe out: ");
		        int cardId = scanner.nextInt();
		        metroSystemService.checkCardExist(cardId);

		        System.out.println("Choose metro station no. from below list:");
		        ArrayList<Station> metroList = metroSystemService.getStationList();
		        for (Station station : metroList) {
		            System.out.println(station);
		        }

		        System.out.print("Enter Station No from the above list to swipe out: ");
		        int stationNo = scanner.nextInt();
		        metroSystemService.checkStationExist(stationNo);

		        double fare = metroSystemService.swipeOut(cardId, stationNo);
		        System.out.println("Swipe out successful!");
		        System.out.println("Fare deducted: Rs. " + fare);
		        System.out.println("Remaining balance: Rs. " + metroSystemService.getCardDetailsById(cardId).getBalance());
		        System.out.println();
		    } 
		    catch (WrongCardNoException e) {
		        System.out.println("Invalid Metro Card No! Please try again with the correct Card Number.");
		        System.out.println();
		    } 
		    catch (MetroStationDoNotExistException e) {
		        System.out.println("Invalid Metro Station entered! Please try again with the correct Metro Station Number.");
		        System.out.println();
		    } 
		    catch (NotSwipedInException e) {
		        System.out.println("Swipe-in record not found! Please swipe in before swiping out.");
		        System.out.println();
		    } 
		    catch (InsufficientBalanceException e) {
		        System.out.println("Insufficient balance for fare deduction. Please recharge your card.");
		        System.out.println();
		    } 
		    catch (InputMismatchException e) {
		        System.out.println("Please enter correct details.");
		        System.out.println();
		    }
		    break;

		case 4:
			ArrayList<Station> metroStationList= metroSystemService.getStationList();
			for(Station station: metroStationList) {
				System.out.println(station);
			}
			System.out.println();
			break;
		case 5:
			try {
				System.out.print("Enter Metro Card Id: ");
				int cardId= scanner.nextInt();
				Card card= metroSystemService.getCardDetailsById(cardId);
				System.out.println("Your Card Details: ");
				System.out.println("Card No: "+ card.getCardId());
				System.out.println("Card Balance: "+ card.getBalance());
				System.out.println("Card Issue Date: "+ card.getIssueDate() );
				System.out.println();
			} 
			catch (WrongCardNoException e) {
				System.out.println("Invalid Metro Card Number");
				System.out.println();
			}
			break;
					
		case 6:
			try {
				System.out.print("Enter Card No. to recharge: ");
				int cardId= scanner.nextInt();
				metroSystemService.checkCardExist(cardId);
				System.out.print("Enter amount to recharge: ");
				double amount= scanner.nextDouble();
				metroSystemService.addCardBalance(cardId, amount);
				System.out.println("Amount of Rs."+ amount+ " added successfully in Card No. "+ cardId);
				System.out.println();
			}
			catch (WrongCardNoException e) {
				System.out.println("You have entered wrong card no!. Please try again with correct Card Number");
				System.out.println();
			}
			break;
		case 7:
			try {
				System.out.print("Enter your card id: ");
				int cardId= scanner.nextInt();
				metroSystemService.checkCardExist(cardId);
				ArrayList<Transaction> transactionRecordsOfUser= metroSystemService.getTransactionRecord(cardId);
				if(transactionRecordsOfUser.isEmpty()) {
					System.out.println("No transaction records found!");
				}
				System.out.println("Transaction Records: ");
				
				for(Transaction transaction: transactionRecordsOfUser) {
					System.out.println(transaction);
				}
				System.out.println();
			} 
			catch (WrongCardNoException e) {
				System.out.println("You have entered wrong card no!. Please try again with correct Card Number");
				System.out.println();
			}
			break;
			
		case 8:
			System.out.println("Thanks for using Metro Management System");
			System.exit(0);
		default:
			System.out.println("Invalid Choice");
			break;
		}
	}
}
