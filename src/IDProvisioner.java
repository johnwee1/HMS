import databases.AbstractDB;

import java.util.List;

public class IDProvisioner {
    // issue integer IDs by counting the total number
    private final List<AbstractDB<?>> databases;

    /**
     * Takes in all the user-related manager classes at run time.
     * @param databases
     */
    public IDProvisioner(List<AbstractDB<?>> databases){
        this.databases = databases;
    }

    /**
     * Returns the new ID of the user that is going to be registered. The class does NOT modify anything.
     * @return possible new ID (ttl user count)
     */
    public int retrieveNewUserID(){
        int count=0;
        for (AbstractDB<?> database:databases){
            count+=database.getSize();
        }
        return count;
    }
}
