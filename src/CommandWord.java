public class CommandWord {
    // a constant array that holds all valid command words
    private static final String[] validCommands = {
            "UNKNOWN", "quit", "help", "back"
    };

    /**
     * Constructor to initialize the CommandWords
     */
    public CommandWord()
    {
        // Do nothing
    }

    /**
     * Check whether a given String is a valid command word.
     * @return true if a given string is a valid command, otherwise false
     */
    public boolean isCommand(String aString)
    {
        for(int i = 0; i < validCommands.length; i++) {
            if(validCommands[i].equals(aString))
                return true;
        }
        // Else the string was not found in the commands
        return false;
    }
}
