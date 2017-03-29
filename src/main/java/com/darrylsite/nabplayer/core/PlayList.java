
package com.darrylsite.nabplayer.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import logging.appLogger;

/**
 *
 * @author nabster
 */
public class PlayList
{
    private static String path ="nabplayer.lst";

    public static File[] load()
    {
      FileReader reader = null;
      List<File> lst = new ArrayList<File>();
        try {
            File file = new File(path);
            if (!file.exists()) {
                return new File[0];
            }
            reader = new FileReader(file);
            BufferedReader buff = new BufferedReader(reader);
            String s;
            while((s=buff.readLine())!=null)
            {
                File ff = new File(s);
                if (ff.exists())
                 lst.add(ff);
            }

        } catch (Exception ex) {
            Logger.getLogger(PlayList.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try
            {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(PlayList.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
       return lst.toArray(new File[0]);
    }

    public static void save(String[] files)
    {
        FileWriter writer = null;
        try
        {
            File file = new File(path);
            writer = new FileWriter(file);
            for(String f : files)
            {
             writer.write(f+"\n");
            }
        }
        catch (IOException ex)
        {
             appLogger.getLogger().log(Level.SEVERE, null, ex);
        } 
        finally
        {
            try
            {
                writer.close();
            } catch (IOException ex)
            {
                appLogger.getLogger().log(Level.SEVERE, null, ex);
            }
        }

    }



}
