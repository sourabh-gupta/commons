package messageTypes;

/*
*  Copyright (c) 2005-2010, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
*  WSO2 Inc. licenses this file to you under the Apache License,
*  Version 2.0 (the "License"); you may not use this file except
*  in compliance with the License.
*  You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied.  See the License for the
* specific language governing permissions and limitations
* under the License.
*/
import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;
public class QueueReceiverMessage {
    public static final String QPID_ICF = "org.wso2.andes.jndi.PropertiesFileInitialContextFactory";
    private static final String CF_NAME_PREFIX = "connectionfactory.";
    private static final String CF_NAME = "qpidConnectionfactory";
    String userName = "admin";
    String password = "admin";
    private static String CARBON_CLIENT_ID = "carbon";
    private static String CARBON_VIRTUAL_HOST_NAME = "carbon";
    private static String CARBON_DEFAULT_HOSTNAME = "localhost";//10.200.3.193
    private static String CARBON_DEFAULT_PORT = "5672";
    String queueName = "lanka";
 
    public static void main(String[] args) throws NamingException, JMSException {
    	QueueReceiverMessage queueReceiver = new QueueReceiverMessage();
        queueReceiver.receiveMessages();
    }
    public void receiveMessages() throws NamingException, JMSException {
        Properties properties = new Properties();
        properties.put(Context.INITIAL_CONTEXT_FACTORY, QPID_ICF);
        properties.put(CF_NAME_PREFIX + CF_NAME, getTCPConnectionURL(userName, password));
        properties.put("queue."+ queueName,queueName);
        System.out.println("getTCPConnectionURL(userName,password) = " + getTCPConnectionURL(userName, password));
        InitialContext ctx = new InitialContext(properties);
        // Lookup connection factory
        QueueConnectionFactory connFactory = (QueueConnectionFactory) ctx.lookup(CF_NAME);
        QueueConnection queueConnection = connFactory.createQueueConnection();
        queueConnection.start();
        QueueSession queueSession =
                queueConnection.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE); //QueueSession.AUTO_ACKNOWLEDGE
        
        //Receive message
        Queue queue =  (Queue) ctx.lookup(queueName);
        MessageConsumer queueReceiver = queueSession.createConsumer(queue);
	int count=1;

	while(true){
	
		ObjectMessage message = (ObjectMessage) queueReceiver.receive();
           
        System.out.println("Got message ==>" + message.toString());
        
        String a=message.getJMSCorrelationID();
        
        byte[] b=message.getJMSCorrelationIDAsBytes();
        
        int c=message.getJMSDeliveryMode();
       
		Queue d=(Queue) message.getJMSDestination();
		
		long e=message.getJMSExpiration();
		
		String f=message.getJMSMessageID();
		
		int g=message.getJMSPriority();
		
		boolean h=message.getJMSRedelivered();
		
		Queue i=(Queue) message.getJMSReplyTo();
		
		long j=message.getJMSTimestamp();
		
		String k=message.getJMSType();
        
        System.out.println(a+":::::::::::::::"+b.toString()+":::::::::::::::"+c+":::::::::::::::"+d.getQueueName()+":::::::::::::::"+e+":::::::::::::::"+f+":::::::::::::::"+g+":::::::::::::::"+h+":::::::::::::::"+i.getQueueName()+":::::::::::::::"+j+":::::::::::::::"+k+":::::::::::::::");
        
	    //System.out.println("Received Message Count ==>" + count);
	    
	    count++;
	     
	}
        //queueReceiver.close();
       // queueSession.close();
       // queueConnection.stop();
        //queueConnection.close();
    }
    public String getTCPConnectionURL(String username, String password) {
    	
        // amqp://{username}:{password}@carbon/carbon?brokerlist='tcp://{hostname}:{port}'
    	
        return new StringBuffer()
                .append("amqp://").append(username).append(":").append(password)
                .append("@").append(CARBON_CLIENT_ID)
                .append("/").append(CARBON_VIRTUAL_HOST_NAME)
                .append("?brokerlist='tcp://").append(CARBON_DEFAULT_HOSTNAME).append(":").append(CARBON_DEFAULT_PORT).append("'")
                .toString();
    }
 
}
