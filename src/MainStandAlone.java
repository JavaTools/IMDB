import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.io.FileFilter;

public class MainStandAlone
{
    public static void main(String[] args) throws Exception
    {
        new MainStandAlone().run(args);
    }
    
    private void run(String[] args) throws Exception {

        if (args == null || args.length < 1 || args.length >2) {
            System.out.println();
            System.out.println("  ##################################################################################");
            System.out.println("  # IMDB Tool, Usage");
            System.out.println("  ##################################################################################");
            System.out.println("  imdb.sh scan               (crawls from current directory searching for nfo-files)");
            System.out.println("    or");
            System.out.println("  imdb.sh <movie-id>         (movie-id format: 0123789)");
            System.out.println("    or");
            System.out.println("  imdb.sh <time1> <time2>    (time format: <hours>[.:]<minutes>[.:]<seconds>)");
            System.out.println();
        }
        else
        {
            if (args.length==1) {
                if ("scan".equals(args[0])) {
                    scan(new File("."));
                } else {
                    File file = new File(".");
                    ImageProducer ip = new ImageProducer();
                    ip.run(args[0], file);
                }
            } else {
                System.out.print("Seconds: ");
                for (int argument=0; argument<2; argument++) {
                    String[] times = args[argument].replaceAll(":", "\\.").split("\\.");
                    ArrayUtils.reverse(times);

                    Integer seconds = 0;
                    for(int index=0; index<times.length; index++) {
                        seconds += ( index>0 ? (int)(Math.pow(60D,(double)index)) : 1) * Integer.parseInt(times[index]);
                    }
                    System.out.print(seconds + " ");
                }
                System.out.println();
            }
        }
    }

    private void scan(File directory)
    {
        ImageProducer ip = new ImageProducer();
        Parser parser = new Parser();
        File[] files = directory.listFiles(new FileFilter() {
            public boolean accept(File file) {
                return
                    file.getName().endsWith("nfo") ||
                            (file.isDirectory() && !file.getName().equals(".") && !file.getName().equals(".."));
            }
        });

        for (File file : files)
        {
            if (file.isDirectory())
            {
                scan(file);
            }
            else
            {
                String id = parser.parseIMDBIdentifier(file);
                if (id!=null)
                {
                    try
                    {
                        System.out.println("["+id+"] FOUND   "+file.getAbsolutePath());
                        ip.run(id, file.getParentFile());
                    }
                    catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }
                }
                else
                {
                    System.out.println("[-------]         (" + file.getAbsolutePath() + ")");
                }
            }
        }
    }
}
