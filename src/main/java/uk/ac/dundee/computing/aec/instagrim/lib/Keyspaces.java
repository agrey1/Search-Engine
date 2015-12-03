package uk.ac.dundee.computing.aec.instagrim.lib;

import java.util.ArrayList;
import java.util.List;

import com.datastax.driver.core.*;

public final class Keyspaces 
{
    public Keyspaces() 
    {
        
    }
    
    public static void SetUpKeySpaces(Cluster c) 
    {
        try
        {
            String createKeySpace = "CREATE KEYSPACE IF NOT EXISTS search WITH replication = {'class':'SimpleStrategy', 'replication_factor':1}";
            
            String createPagesTable = "CREATE TABLE IF NOT EXISTS search.pages ("
                    + " url TEXT PRIMARY KEY,"
                    + " title TEXT,"
                    + " words TEXT,"
                    + " time TIMESTAMP"
                    + ")";
            
            String createLinksTable = "CREATE TABLE IF NOT EXISTS search.links ("
                    + " url TEXT PRIMARY KEY"
                    + ")";
            
            //String createLinksIndex = "CREATE INDEX IF NOT EXISTS ON search.links (visited);";
            
            //System.out.println(setupSearch);
            
            Session session = c.connect();
            
            try 
            {
                PreparedStatement statement = session.prepare(createKeySpace);
                BoundStatement boundStatement = new BoundStatement(statement);
                ResultSet rs = session.execute(boundStatement);
                
                System.out.println("Created keyspace search");
            } 
            catch (Exception et) 
            {
                System.out.println("Can't create search " + et);
            }

            try 
            {
                SimpleStatement cqlQuery = new SimpleStatement(createPagesTable);
                session.execute(cqlQuery);
            } 
            catch (Exception et) 
            {
                System.out.println("Can't create pages table " + et);
            }
            
            try 
            {
                SimpleStatement cqlQuery = new SimpleStatement(createLinksTable);
                session.execute(cqlQuery);
            } 
            catch (Exception et) 
            {
                System.out.println("Can't create links table " + et);
            }
            
            try 
            {
                //SimpleStatement cqlQuery = new SimpleStatement(createLinksIndex);
                //session.execute(cqlQuery);
            } 
            catch (Exception et) 
            {
                //System.out.println("Can't create index 'visited' for links " + et);
            }

            session.close();
        }
        catch (Exception et) 
        {
            System.out.println("Other keyspace or coulm definition error" + et);
        }
    }
}
