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
 
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.QueueSession;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import java.io.Serializable;
import java.util.Properties;
 

class Employee implements java.io.Serializable
{
   public String name;
   public String address;
   public transient int SSN;
   public int number;
   public void mailCheck()
   {
      System.out.println("Mailing a check to " + name
                           + " " + address);
   }
}


public class TopicPublisher {
    public static final String QPID_ICF = "org.wso2.andes.jndi.PropertiesFileInitialContextFactory";
    private static final String CF_NAME_PREFIX = "connectionfactory.";
    private static final String CF_NAME = "qpidConnectionfactory";
    String userName = "admin";
    String password = "admin";
    private static String CARBON_CLIENT_ID = "carbon";
    private static String CARBON_VIRTUAL_HOST_NAME = "carbon";
    private static String CARBON_DEFAULT_HOSTNAME = "localhost";
    private static String CARBON_DEFAULT_PORT = "5672";
    String topicName = "some";
 
    public static void main(String[] args) throws NamingException, JMSException {
    	TopicPublisher topicPublisher = new TopicPublisher();
        topicPublisher.publishMessage();
    }
    public void publishMessage() throws NamingException, JMSException {
        Properties properties = new Properties();
        properties.put(Context.INITIAL_CONTEXT_FACTORY, QPID_ICF);
        properties.put(CF_NAME_PREFIX + CF_NAME, getTCPConnectionURL(userName, password));
        
        
        InitialContext ctx = new InitialContext(properties);
        
        // Lookup connection factory
        TopicConnectionFactory connFactory = (TopicConnectionFactory) ctx.lookup(CF_NAME);
        TopicConnection topicConnection = connFactory.createTopicConnection();
        topicConnection.start();
        TopicSession topicSession =
                topicConnection.createTopicSession(false, QueueSession.AUTO_ACKNOWLEDGE);
        // Send message
        Topic topic = topicSession.createTopic(topicName);
        
        
	
	for(int i=1;i<=1;i=i+1){
		
		String myMessage = "sri lanka matha";
		
		byte[] data = myMessage.getBytes();
		
		Destination dst=topic;
		

        // create the message to send
		ObjectMessage message=topicSession.createObjectMessage();
		//Serializable abc=new Employee();
	    // message.setObject(abc);
		
        javax.jms.TopicPublisher topicPublisher = topicSession.createPublisher(topic);
        
        message.setJMSCorrelationID("a");
		
		message.setJMSCorrelationIDAsBytes(data);
		
		message.setJMSDeliveryMode(1);//aaaaaaaaa
		
		message.setJMSDestination(dst);
		
		message.setJMSExpiration(100000);//aaaaaaaaa
		
		message.setJMSMessageID("message ID");//aaaaaaaaa
		
		message.setJMSPriority(8);//aaaaaaaaa
		
		message.setJMSRedelivered(true);//aaaaaaaaa
		
		message.setJMSReplyTo(dst);
		
		message.setJMSTimestamp(9000);//aaaaaaaaa
		
		message.setJMSType("JMSType");
        
        topicPublisher.publish(message);
	}

        topicSession.close();
        topicConnection.close();
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