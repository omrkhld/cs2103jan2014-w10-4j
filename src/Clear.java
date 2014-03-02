import java.util.Scanner;
import java.util.HashMap;

public class Clear {

	private static final String MESSAGE_ERROR_VALID_SELECTION = "Please enter a valid selection (yes/no)";
	private static final String MESSAGE_CONFIRMATION_HISTORY = "Are you sure you want to clear you archived files? (yes/no)";
	private static final String MESSAGE_CLEAR_CMD_ERROR = "The clear command you've entered is not recognised. Please re-enter your command: ";
	private static final String MESSAGE_CLEAR_INCOMPLETE = "All tasks and events have been cleared.";
	private static final String MESSAGE_HISTORY_CLEARED = "History has been cleared.";
	
	private static final int CLEAR_INCOMPLETE = 1;
	private static final int CLEAR_ALL = 2;
	private static final int CLEAR_EVENTS = 3;
	private static final int CLEAR_TASKS = 4;
	private static final int CLEAR_HISTORY = 5;
	
	private static final int FIRST_ARGUMENT = 0;
	
	private static final String MESSAGE_ERROR_CMD = "Error in reading your input. "
			+ "What is it that you want to clear?";
	
	private static HashMap<String, Integer> commandTable = new HashMap<String, Integer>();
	private static Scanner scan = new Scanner(System.in);
	
	/*
	 * Clear constructor
	 */
	public Clear(FileHandler fh) {
		initialiseCmdTypes();
	}

	public static String executeClear(String[] userInput) {
		boolean correctCmd;
		String response = "";
		
		correctCmd = checkCmdInput(userInput[FIRST_ARGUMENT]);
		
		if(correctCmd == true) {
			String cmd = userInput[FIRST_ARGUMENT];
			identifyCmdTypeAndPerform(cmd);
		} else {
			response = MESSAGE_CLEAR_CMD_ERROR;
		}
		
		return response;
	}
	
	/*
	 * for checking if the first argument is exactly "/clear"
	 * user might have input "/clearCS2105" and forgotten the spacing
	 */
	private static boolean checkCmdInput(String cmd) {
		String cmdLowerCase = cmd.toLowerCase();
		
		if(commandTable.containsKey(cmdLowerCase)) {
			return true;
		} else {
			return false;
		}
	}

	private static String identifyCmdTypeAndPerform(String cmd) {
		int cmdType = commandTable.get(cmd);
		String response = "";
		
		if(cmdType == CLEAR_INCOMPLETE) {
			clearIncomplete();
			response = MESSAGE_CLEAR_INCOMPLETE;
					
		} else if(cmdType == CLEAR_HISTORY) {
			clearHistory();
			response = MESSAGE_HISTORY_CLEARED;
			
		} else if(cmdType == CLEAR_ALL) {
			clearHistory();
			clearIncomplete();
			
			
		} else if(cmdType == CLEAR_EVENTS) {
			clearEvents();
			
		} else {
			clearTasks();
		}
		
		return response;
	}

	private static void clearTasks() {
		// TODO Auto-generated method stub
		
	}

	private static void clearEvents() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * method to clear history
	 * 
	 */
	private static void clearHistory() {
		boolean clearConfirmation = false;
		boolean correctUserConfirmation = false;
		print(MESSAGE_CONFIRMATION_HISTORY);
		
		while(correctUserConfirmation == false) {
			String userConfirmation = scan.nextLine();
			
			userConfirmation = userConfirmation.toLowerCase();
			
			if(userConfirmation.equals("yes") || userConfirmation.equals("y")) {
				correctUserConfirmation = true;
				clearConfirmation = true; 
			} else if(userConfirmation.equals("no") || userConfirmation.equals("n")) {
				correctUserConfirmation = true;
				clearConfirmation = false;
			} else {
				print(MESSAGE_ERROR_VALID_SELECTION);
			}
		}
		
		if(clearConfirmation == true) {
			FileHandler.initialiseFileDetails(FileHandler.COMPLETED_TASKS_STORAGE_FILE_NAME);
			FileHandler.writeCompleteTasksFile();
		}
	}
	
	private static void clearIncomplete() {
		FileHandler.initialiseFileDetails(FileHandler.INCOMPLETE_TASKS_STORAGE_FILE_NAME);
		FileHandler.writeIncompleteTasksFile();
	}
	
	/**
	 * handle the error identified in checkCmdInput
	 */
	private static void clearErrorHandling() {
		
	}
	
	private static void print(String message) {
		System.out.println(message);
	}

	private static void initialiseCmdTypes() {
		commandTable.put("/clear", CLEAR_INCOMPLETE);
		commandTable.put("/clearall", CLEAR_ALL);
		commandTable.put("/cleare", CLEAR_EVENTS);
		commandTable.put("cleart", CLEAR_TASKS);
		commandTable.put("/clearh", CLEAR_HISTORY);
	}
	
	
}
