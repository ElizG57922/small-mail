package network;

public class CommandProtocol {
	/**
	 * process commands sent to the server
	 * @param cmd: command to be processed
	 * @param na: NetworkAccess object for communication
	 * @param ch: ClientHandler object requesting the processing
	 * @return
	 */
	public static void processCommand(String cmd, NetworkAccess na, ClientHandler ch)
	{

		if (cmd.equals("disconnect")) {
			// -- no response to the client is necessary
			na.close();
			ch.getServer().removeID(ch.getID());
			ch.Stop();
		}

		else if (cmd.contains("register")) {
			String[] parts = cmd.split(";");
			if(Authenticate.validSimplePassword(parts[2])) {
				if(Authenticate.validEmailAddress(parts[3])) {
					DBaseConnection dbc = new DBaseConnection();
					String message = dbc.insertIntoDatabase(parts[1], parts[2], parts[3]);
					dbc.disconnect();
					na.sendString(message + "\n", false);
				}
				else
					na.sendString("invalidemail" + "\n", false);
			}
			else
    			na.sendString("invalidpass" + "\n", false);
		}
		
		else if (cmd.contains("login")) {
			DBaseConnection dbc = new DBaseConnection();
			String message;
			String[] parts = cmd.split(";");
			if(dbc.doesUsernameExist(parts[1])) {
				if(dbc.getLockoutCount(parts[1]) < 3) {
        			message = dbc.login(parts[1], parts[2]);
				}
				else {
					message = "lockedout";
				}
			}
			else {
	    		message = "invaliduser";
			}
			dbc.disconnect();
			na.sendString(message + "\n", false);
		}

		else if (cmd.contains("logout")) {
			DBaseConnection dbc = new DBaseConnection();
			String message;
			String[] parts = cmd.split(";");
			if(dbc.doesUsernameExist(parts[1])) {
				dbc.setLoggedInStatus(parts[1], false);
				message = "success";
			}
			else {
	    		message = "invaliduser";
			}
			dbc.disconnect();
			na.sendString(message + "\n", false);
		}
		
		else if (cmd.contains("changepass")) {
			String message;
			String[] parts = cmd.split(";");
			if(!parts[3].equals(parts[4])) //password confirmation does not match
				message = "no_confirmation_match";
			else if(!Authenticate.validSimplePassword(parts[3])) //invalid new password
				message = "invalid_new_pass";
			else {
    			DBaseConnection dbc = new DBaseConnection();
	    		if(dbc.doesUsernameExist(parts[1])) {
	    			if(parts[2].equals(dbc.getPass(parts[1]))) {
    		    		dbc.setPass(parts[1], parts[3]);
			    	    message = "success";
	    			}
	    			else {
	    				message = "no_old_match";
	    			}
			    }
			    else {
	    		    message = "invaliduser";
			    }
	    		dbc.disconnect();
			}
			na.sendString(message + "\n", false);
		}
		
		else if (cmd.contains("recoverpass")) {
			DBaseConnection dbc = new DBaseConnection();
			String message;
			String[] parts = cmd.split(";");
			if(dbc.doesUsernameExist(parts[1])) {
				String password = dbc.getPass(parts[1]);
				dbc.setLockoutCount(parts[1], 0);
				String email = dbc.getEmail(parts[1]);
				//send mail with the password string
				SendEmailUsingGMailSMTP.sendMail(email, password);
				message = "success";
			}
			else {
	    		message = "invaliduser";
			}
			dbc.disconnect();
			na.sendString(message + "\n", false);
		}
		
		else {//invalid command
			na.sendString(cmd + "\n", false);
		}		
	}
}