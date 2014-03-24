import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser
{
    public String parseIMDBIdentifier(File file)
    {
        String identifier = null;
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = br.readLine();
            while (line!=null)
            {
                String url = parseLine(line);
                if (url!=null)
                {
                    identifier = url;
                    break;
                }
                line = br.readLine();
            }
            br.close();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return identifier;
    }

    private String parseLine(String line)
    {
        Pattern p = Pattern.compile("(http://)?(www\\.|us\\.)?imdb\\.com/(Title\\?|title/tt)([0123456789]+)");
        Matcher m = p.matcher(line);
        if (m.find())
        {
            return m.group(4);
        }
        return null;
    }
}
