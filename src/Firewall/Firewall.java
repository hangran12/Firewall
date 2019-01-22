package Firewall;

import java.util.ArrayList;

/**
 * The Firewall class sets up a firewall that determines whether an 
 * inputted network packet will be accepted based on input rules.
 * 
 */

public class Firewall {
 
	String CSVpath;
	final int IP_range_min = 0;
	final int IP_range_max = 255;
	final int port_range_min = 1;
	final int port_range_max = 65535;
	
	
    /**
    * The constructor for the Firewall object.
    */
	Firewall(String path) {
		this.CSVpath = path;
	}
	
	
    /**
    * Determines whether the network packet will be accepted or not based on
    * direction, protocol, port, and IP address.
    * @param direction 
    * @param protocol
    * @param port
    * @param ip_address
    * @return True if packet accepted, False if not.
    */
	boolean accept_packet(String direction, String protocol, String port, String ip_address) {
		if (!direction.equals("inbound") && !direction.equals("outbound")) {
			return false;
		} if (!protocol.equals("tcp") && !protocol.equals("udp")) {
			return false;
		} if (!isPort(port)) {
			return false;
		} if (!isIP(ip_address)) {
			return false;
		}
		return true;
	}
	
	
    /**
    * Determines whether the port will be accepted based on whether or not
    * the port or port range is within the set minimums and maximums 
    * as defined by the class.
    * @param port
    * @return True if accepted, False if not accepted.
    */
	boolean isPort(String port) {
		if (port.contains("-")) {
			int portMin = Integer.parseInt(port.substring(0, port.indexOf("-")-1));
			int portMax = Integer.parseInt(port.substring(port.indexOf("-")+1));
			if (portMin >= port_range_min && portMax <= port_range_max) {
				return true;
			}
			return false;
		} else {
			int portMin = Integer.parseInt(port);
			if (portMin >= port_range_min && portMin <= port_range_max) {
				return true;
			}
			return false;
		}
	}
	
	
    /**
    * Determines whether the IP address or IP address range will be 
    * accepted based on whether or not the port or port range is 
    * within the set minimums and maximums as defined by the class.
    * @param IP
    * @return True if accepted, False if not accepted.
    */
	boolean isIP(String IP) {
		if (IP.contains("-")) {
			String firstIP = IP.substring(0, IP.indexOf("-"));
			String secondIP = IP.substring(IP.indexOf("-")+1);
			if (isIP(firstIP) && isIP(secondIP)) {
				return true;
			} else {
				return false;
			}
		}
		if (IP.contains(".")) {
			ArrayList<Integer> octets = new ArrayList<Integer>();
			int i = 0;
			int j = 1;
			while (j <= IP.length()) {
				String octet = IP.substring(i, j);
				if (j == IP.length()) {
					if (Integer.parseInt(octet) >= IP_range_min && Integer.parseInt(octet) <= IP_range_max) {
						octets.add(Integer.parseInt(octet));
						i = j+1;
						j = j+2;
					}
				}
				if (j < IP.length()) {
					if (IP.charAt(j) == '.') {
						if (Integer.parseInt(octet) >= IP_range_min && Integer.parseInt(octet) <= IP_range_max) {
							octets.add(Integer.parseInt(octet));
							i = j+1;
							j = j+2;
						} else {
							return false;
						}
					} else {
						j++;
					}
				}
			}
			if (octets.size() == 4) {
				return true;
			} else {
				return false;
			}
		} 
		return false;
	}
}
