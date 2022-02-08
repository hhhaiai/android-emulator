package me.hhhaiai.aemulator.runner;

import java.io.IOException;

/**
 * @Copyright © 2022 sanbo Inc. All rights reserved.
 * @Description: TODO
 * @Version: 1.0
 * @Create: 2022/2/8 6:03 PM
 * @author: sanbo
 */
public class MainApplication {
    public static void main(String[] args) {


    }

    private static void help() {
        StringBuilder sb = new StringBuilder();
        sb.append("command download and install android emulator:").append("\r\n")
                .append("\t-api-level:")
        ;

        System.out.println(sb.toString());
    }


    public static void prepare(String[] commands) {
        // create emulator
        if (AndroidEmulatorHelper.createEmulator()) {
            // run commands
            prepare(commands);
        }
    }



    private static void runcommand(String[] commands) {
        if (commands != null && commands.length > 0) {
            for (String cmd : commands) {
                try {
                    Runtime.getRuntime().exec(cmd);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
