package com.atguigu.etl.bean;

/**
 * @author: realdengziqi
 * @date: 2023-02-18 9:55
 * @description:
 */

import java.io.InputStream;
import java.util.*;

/**
 * @author: tony
 * @date: 2022-12-05 19:31
 * @description:
 */
public class IpGen  {

    ArrayList<IpRange> ipRanges;

    Random random;

    public IpGen() {
        InputStream ipTable = getClass().getClassLoader().getResourceAsStream("ip_table");
        assert ipTable != null;
        Scanner scanner = new Scanner(ipTable);
        ipRanges = new ArrayList<>();
        while (scanner.hasNextLine()){
            String s = scanner.nextLine();
            String[] split = s.split("\t");
            ipRanges.add(new IpRange(split[0],split[1]));
        }

        random = new Random();
    }



    private String num2ip(long num) {
        return String.format("%s.%s.%s.%s",
                (num & 0xff000000) >> 24,
                (num & 0x00ff0000) >> 16,
                (num & 0x0000ff00) >> 8,
                num & 0x000000ff
        );
    }

    private String genIp(long start,long end) {
        boolean flag = true;
        String ip = null;
        while (flag) {
            int bound = Math.toIntExact(end - start + 1L);
            long test = start + (long)random.nextInt(bound);
            ip = num2ip( test);
            if (! ip.endsWith(".0")){
                flag = false;
            }
        }
        return ip;
    }


    public String genOne() {
        IpRange ipRange = ipRanges.get(random.nextInt(ipRanges.size()));
        return genIp(ipRange.getIpStart(),ipRange.getIpEnd());
    }




    private static class IpRange {

        private String ipStart;

        private String ipEnd;

        private long ip2num(String ip) {
            String[] split = ip.split("\\.");

            LinkedList<Long> longs = new LinkedList<>();
            for (String s : split) {
                longs.add(Long.parseLong(s));
            }
            return (longs.get(0) << 24) + (longs.get(1) << 16) + (longs.get(2) << 8) + longs.get(3);
        }

        public IpRange(String ipStart, String ipEnd) {
            this.ipStart =  ipStart;
            this.ipEnd = ipEnd;
        }

        public long getIpStart() {
            return ip2num(ipStart);
        }


        public long getIpEnd() {
            return ip2num(ipEnd);
        }

        @Override
        public String toString() {
            return "IpRange{" +
                    "IpStart='" + ipStart + '\'' +
                    ", IpEnd='" + ipEnd + '\'' +
                    '}';
        }
    }
}

