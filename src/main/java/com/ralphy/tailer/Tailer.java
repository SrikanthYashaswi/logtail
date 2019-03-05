package com.ralphy.tailer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by shrk on 04/03/19.
 */
public class Tailer implements Runnable
{
    private static final String[] command = {"tail","-f",""};
    private Hook hook;
    private String filepath;

    public Tailer(String filepath, Hook hook)
    {
        this.hook = hook;
        this.filepath = filepath;
    }

    public void run()
    {
        try{
            command[2] = this.filepath;
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            System.out.println("starting tailing...");
            while((line = reader.readLine()) != null)
            {
                hook.send(line);
            }
        }
        catch(IOException c)
        {
            c.printStackTrace();
        }
    }
}
