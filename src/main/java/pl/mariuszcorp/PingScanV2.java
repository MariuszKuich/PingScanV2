package pl.mariuszcorp;

import org.apache.commons.net.util.SubnetUtils;
import java.util.ArrayList;
import java.util.List;

public class PingScanV2 {

    public static void main(String[] args) {
        if(verifyArgument(args)) {
            String[] hostAddresses = getHostAdressesFromSubnet(args[0]);
            System.out.println("Scanning network: " + args[0]);
            List<String> summaryReport = new ArrayList<>();
            for(String hostAddress : hostAddresses) {
                long responseTime = Scanner.checkResponse(hostAddress);
                if(responseTime >= 0) {
                    System.out.println(hostAddress + " responded in: " + responseTime + " ms. Connection OK.");
                    summaryReport.add(hostAddress);
                }
                else {
                    System.out.println(hostAddress + " - no response from host.");
                }
            }
            printSummaryReport(summaryReport);
        }
        else {
            System.out.println("Incorrect argument. Proper usage:\njava PingScanV2 <subnet_address>/<subnet_mask>\n" +
                    "Allowed subnet masks: /1 - /30");
        }
    }

    private static boolean verifyArgument(String[] args) {
        if(args.length == 1) {
            String regex = "^([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-5][0-5])\\." +
                    "([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-5][0-5])\\." +
                    "([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-5][0-5])\\." +
                    "([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-5][0-5])" +
                    "\\/([1-9]|[12][0-9]|30)$";
            if(args[0].trim().matches(regex)) {
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }

    private static String[] getHostAdressesFromSubnet(String subnet) {
        SubnetUtils utils = new SubnetUtils(subnet);
        return utils.getInfo().getAllAddresses();
    }

    private static void printSummaryReport(List<String> summaryReport) {
        System.out.println("\nScanning complete. Hosts that responded:");
        for(String hostAddress : summaryReport) {
            System.out.println(hostAddress);
        }
    }
}
